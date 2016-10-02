package csebank_controllers.asu.edu;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("Request")
public class RequestServlet {
	
	@GET		
	public String getAllRequest(){
		//call the manager class
		//parse the output to class
		System.out.println("getAllRequest");	
		return "";	
	}
	
	@Path("/{requestID}")
	@GET		
	public String getRequest(@PathParam("requestID")String requestID){
		//parse input to instance
		//call the manager class
		//parse the output to class
		System.out.println("getRequest"+requestID);
		return "";	
	}
	
	@Path("/{input}")
	@POST
	//@Produces(MediaType.APPLICATION_JSON)
	public String addRequest(@PathParam("input")String input){		
		//parse input to instance
		//call the manager class
		//parse the output to class
		System.out.println("addRequest:"+input);
		return "";
	}
	
	@Path("/{requestID}/{input}")
	@PUT
	//@Produces(MediaType.APPLICATION_JSON)
	public String modifyRequest(@PathParam("requestID")String requestID, @PathParam("input")String input){		
		//parse input to instance
		//call the manager class
		//parse the output to class
		System.out.println("modifyRequest:"+requestID+",Input:"+input);
		return "";
	}
	
	
	@DELETE
	@Path("{input}")
	public String deleteRequest(@PathParam("name")String input){
		//parse input to instance
		//call the manager class
		//parse the output to class
		return "";
	}
}
