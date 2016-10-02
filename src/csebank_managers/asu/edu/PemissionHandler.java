package csebank_managers.asu.edu;

import csebank_dbmodel.asu.edu.User;

public class PemissionHandler{
	boolean isAccountOperationAllowed(Operation operation, User user){
		boolean returnValue = false;
		switch(operation){
			case ADD_ACCOUNT:
			case VIEW_ALL_ACCOUNT:
			case DELETE_ACCOUNT:
			case MODIFY_ACCOUNT_INFO:
			case MODIFY_USER_INFO:
			case ADD_TRANSACTION:
			case DELETE_TRANSACTION:
			case AUTHORIZE_TARNS_NC:
			case MODIFY_TRANSACTION:
			case VERIFY_EXTUSER_REQUEST:
				if(user.getUserRole().equals("System Manager") || user.getUserRole().equals("Employee")){
					returnValue=true;
				}						
				break;
			case REQUEST_PERSONAL_ACCOUNT_MODIFICATION:
			case VIEW_ACCOUNT://all users have access to view his account
			case VIEW_TRANSACTION:
				returnValue=true;
				break;
			case AUTHORIZE_TARNS_C:
			case VERIFY_REG_EMPLOYEE_REQUEST:
				if(user.getUserRole().equals("System Manager")){
					returnValue=true;
				}						
				break;
			case VIEW_EMPLOYEE:
			case ADD_EMPLOYEE:
			case DELETE_EMPLOYEE:
			case MODIFY_EMPLOYEE_DETAIL:
			case ACCESS_SYSTEM_LOG:
			case VERIFY_MANAGER_EMPLOYEE_REQUEST:
			case ACCESS_PII:
				if(user.getUserRole().equals("Administrator")){
					returnValue = true;
				}
				break;
			case SUBMIT_EXT_USER_PAYMENT:
				if(user.getUserRole().equals("Merchant")){
					returnValue = true;
				}
				break;
			case DEBIT_MONEY:
			case CREDIT_MONEY:
			case TRANSFER_MONEY:
				if(user.getUserRole().equals("Merchant") || user.getUserRole().equals("Individual User")){
					returnValue = true;
				}
		}
		return returnValue;
	}
}

