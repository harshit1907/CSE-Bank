var currentUserRole;
$( document ).ready(function() {
	// TODO: harish: remove username dependency once api is fixed
	url = '/group1/getUserDetails'
	var userrole;
	$.ajax({
        url : url,
        type : 'GET',
        contentType: "application/json",
        success : function(data) {
        	if(typeof(data) == 'string' && data.indexOf("Login with Username and Password") != -1) { 
        		$(location).attr('href', 'index.html');
        	}
        	userrole = data.userRole;
        	if(userrole == 'EMPLOYEE') {
               $("#criticalLi").remove(); 
            }
        	if(userrole == 'CUSTOMER') {
        		$("#merchantId").hide();
        		$("#customerId").show();
        	}
        	if(userrole == 'MERCHANT') {
        		$("#merchantId").show();
        		$("#customerId").hide();
        	}
            var template_details = {"user_full_name": data.firstName + ' ' + data.lastName,
            		"user_email_id" : data.email, "current_user_role":  data.userRole}
            $.get('templates/subtemplates/nameboard.html', function (template) {
            	var html = Mustache.to_html(template, template_details);
            	$('div#nameboard').html(html);
            })
            
        },
        error : function(request,error)
        {
            //console.log(error)
        }
    });
	
})
