package csebank_controllers.asu.edu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import csebank_database.asu.edu.AccountService;
import csebank_database.asu.edu.DeviceService;
import csebank_database.asu.edu.KYCRequestService;
import csebank_database.asu.edu.UserService;
import csebank_objectmodel.asu.edu.Device;
import csebank_objectmodel.asu.edu.KYCRequest;
import csebank_objectmodel.asu.edu.User;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.EmailService;
import exception.csebank_controllers.asu.edu.CustomInvalidArgumetException;
import exception.csebank_controllers.asu.edu.KYCNotFound;
import exception.csebank_controllers.asu.edu.OperationNotPermitted;

/*
 * author: Mihir
 * 
 */

@Controller
public class KYCController {
	@Autowired
	UserController userController;
	//can be done by any one
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_MERCHANT')")
	@RequestMapping(value="/authenticated/kycrequest/add",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addKYCRequest(@RequestBody KYCRequest kycrequest) {
		String userId=kycrequest.getUserId(), currentId=SecurityContextHolder.getContext().getAuthentication().getName();
		if(userId==null || userId.trim().isEmpty())
			userId= currentId;
		kycrequest.setStatus(DbParamNams.REQUEST_PENDING_VALUE);
		kycrequest.setUserId(userId);
		ControllerUtility.validateRequest(kycrequest, true);
		if(!currentId.equals(kycrequest.getUserId()))
			throw new OperationNotPermitted("Users can create KYC request only for thmeselves, not for other users.");	
		userController.getUser(kycrequest.getUserId());
		HashMap<String , String> mp=new HashMap<String, String>();
		mp.put(DbParamNams.USER_EMAIL, "&");//some random symbol
		mp.put(DbParamNams.USER_PHONE, "&");
		//adding the actual value
		mp.put(kycrequest.getUserFieldId(), kycrequest.getFieldValue());
		UserService userService=new UserService(mp);
		if(userService.getEmailandPhone()){
			throw new CustomInvalidArgumetException("Email and(or) phone number not valid. Please try other value.");
		}
		HashMap<String, String> param=ControllerUtility.getKYCMap(kycrequest);
		String kycreqId = new KYCRequestService(param).addKycReq();	
		return kycreqId;
	}

	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value={"/internal/kycrequest/update"},method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean updateByInternal(@RequestBody KYCRequest kycrequest_) {
		//populate kycrequest, from the KYC table and validate the status, rejected status cannot be approved		
		KYCRequest kycrequest= getKYCRequestOnId(kycrequest_.getKycid());//replace the thing from the DB
		kycrequest.setStatus(kycrequest_.getStatus());
		String userRoleKyc=userController.getUserRole(kycrequest.getUserId()), currentRole=userController.getUserRole();
		ControllerUtility.validateUserPermission(userRoleKyc, currentRole);
		return updateStatus(kycrequest);
	}

	public boolean updateStatus(KYCRequest kycrequest) {
		HashMap<String, String> param=new HashMap<>();//ControllerUtility.getKYCMap(request);
		ControllerUtility.validateRequest(kycrequest, false);		

		param=ControllerUtility.getKYCMap(kycrequest);
		//update's the KYC table and the user table
		boolean check = new KYCRequestService(param).updateStatus();
		//get the new user
		User user = userController.getUser(kycrequest.getUserId());
		//todo send alert to User
//		System.out.println(user.getEmail());
//	    System.out.println(user.getPhoneNumber());
		if(check && kycrequest.getStatus().equals(DbParamNams.APPROVED_VALUE)){

			String emailId = user.getEmail();
			String subject = "Regarding your KYCRequest";
			String text = "Dear customer,\n Your request to update your "+kycrequest.getUserFieldId()+" has been approved.\n\nSincerely,\nCSE-Bank Ltd.";

			EmailService newMail = new EmailService();
			newMail.sendMail(emailId,subject,text);

		}
		else if(check && kycrequest.getStatus().equals(DbParamNams.REJECTED_VALUE)){

			String emailId = user.getEmail();
			String subject = "Regarding your KYCRequest";
			String text = "Dear customer,\n Your request to update your "+kycrequest.getUserFieldId()+" has been rejected.\n\nSincerely,\nCSE-Bank Ltd.";

			EmailService newMail = new EmailService();
			newMail.sendMail(emailId,subject,text);

		}
		
		return check;
	}

	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_ADMIN')")
	@RequestMapping(value="/internal/kycrequest/getkycreqs",method = RequestMethod.GET)
	public @ResponseBody List<KYCRequest>getKycCommon(){
		List <KYCRequest> ret=null;
		switch(userController.getUserRole()){
		case DbParamNams.USER_ROLE_ADMIN:ret=getKycsForAdmin(); break;
		case DbParamNams.USER_ROLE_MANANEGR: ret=getKycsForManager();break;
		case DbParamNams.USER_ROLE_EMPLOYEE: ret=getKycForEmployee(); break;		
		}
		return ret;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_CUSTOMER','ROLE_MERCHANT')")
	@RequestMapping(value="/authenticated/kycrequest/getkycreqs",method = RequestMethod.GET)
	public @ResponseBody List<KYCRequest>getKycOwn(){
		String userId=userController.getCurrentUserId();
		HashMap<String, String> param=new HashMap<>();//ControllerUtility.getKYCMap(request);
		List<KYCRequest> allPendingkycreqs = new KYCRequestService(param).getPendingKycReq();
		List <KYCRequest> ret=new ArrayList<>();
		allPendingkycreqs.forEach(kyc->{
			if(kyc.getUserId().equals(userId)){
				ret.add(kyc);
			}
		});
		return ret;
	}

	public List<KYCRequest>getKycForEmployee(){
		List<KYCRequest> ret = getkycrequests(DbParamNams.USER_ROLE_CUSTOMER);
		List<KYCRequest> ret2 = getkycrequests(DbParamNams.USER_ROLE_MERCHANT);
		if(!ret2.isEmpty()){
			ret2.forEach((kyc)->{
				ret.add(kyc);
			});
		}
		
		return ret;
	}

	public List<KYCRequest>getKycsForManager(){
		List<KYCRequest> ret = getKycForEmployee();
		List<KYCRequest> ret2 = getkycrequests(DbParamNams.USER_ROLE_EMPLOYEE);
		if(!ret2.isEmpty()){
			ret2.forEach((kyc)->{
				ret.add(kyc);
			});
		}
		return ret;
	}

	public List<KYCRequest>getKycsForAdmin(){
		List<KYCRequest> ret =getKycsForManager();
		List<KYCRequest> ret2 = getkycrequests(DbParamNams.USER_ROLE_MANANEGR);
		if(!ret2.isEmpty()){
			ret2.forEach((kyc)->{
				ret.add(kyc);
			});
		}
		return ret;
	}

	private List<KYCRequest> getkycrequests(String userRole) {		
		//lets Filter them out
		HashSet <String>set=new HashSet<>();
		if(userController.getUserList(userRole)!=null){
			userController.getUserList(userRole).forEach((user)->set.add(user.getUserId()));			
		}

		HashMap<String, String> param=new HashMap<>();//ControllerUtility.getKYCMap(request);
		
		List<KYCRequest> allPendingkycreqs = new KYCRequestService(param).getPendingKycReq();
        
		List<KYCRequest> ret=new ArrayList<>();
		
		if(allPendingkycreqs!=null){
			allPendingkycreqs.forEach((req)->{
				if(set.contains(req.getUserId()))//add kyc based on the userlist(which is based on the rol)
					ret.add(req);	
			});
		}
		

		return ret;
	}
	KYCRequest getKYCRequestOnId(String kycId){
		HashMap<String, String> param=new HashMap<>();//ControllerUtility.getKYCMap(request);
		List<KYCRequest> allPendingkycreqs = new KYCRequestService(param).getPendingKycReq();
		boolean found=false;
		KYCRequest kycrequest=null;
		for(KYCRequest kyc:allPendingkycreqs){
			if(kyc.getKycid().equals(kycId)){
				kycrequest=kyc;
				found = true;
				break;
			}
		}				
		if(!found){
			throw new KYCNotFound();
		}
		return kycrequest;
	}
}