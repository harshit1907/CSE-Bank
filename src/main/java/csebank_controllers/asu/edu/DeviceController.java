package csebank_controllers.asu.edu;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import csebank_database.asu.edu.DeviceService;
import csebank_objectmodel.asu.edu.Device;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.EmailService;
 
/*
 * author: Mihir
 * 
 */
 
@Controller
public class DeviceController {
	@Autowired
	UserController userController;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/device/add",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean addNewDevice(@RequestBody Device device) {		
		device.setUserId(userController.getCurrentUserId());
		ControllerUtility.validateDeviceID(device.getDeviceId());
		//userController.getUser(device.getUserId());
	    // call addAccount from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.DEVICE_ID,device.getDeviceId());
	    param.put(DbParamNams.USER_ID,device.getUserId());
		DeviceService deviceService =  new DeviceService(param);
		boolean result = deviceService.addDevice();
		if(result==true)
		{
			String emailId = userController.getUser().getEmail();
			String subject = "Login from a new Device";
			String text = "Dear customer,\n Your account was just used to sign in from"+device.getDeviceId()+".\n"
					+ "Why are we sending this? We take security very seriously and we want to keep you in \n the loop on important actions in your account.\n"
					+ "\n\nSincerely,\nCSE-Bank Ltd.";

			EmailService newMail = new EmailService();
			newMail.sendMail(emailId,subject,text);
		}
		return result;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/device/check",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean checkDevice(@RequestBody Device device) {
		device.setUserId(userController.getCurrentUserId());
		//ControllerUtility.validateUserID(device.getUserId());
		ControllerUtility.validateDeviceID(device.getDeviceId());
		//userController.getUser(device.getUserId());
	    // call addAccount from AccountService
	    HashMap<String, String> param=new HashMap<>();
	    param.put(DbParamNams.DEVICE_ID,device.getDeviceId());
	    param.put(DbParamNams.USER_ID,device.getUserId());
						
		return new DeviceService(param).checkDeviceDetails();
	}		
}