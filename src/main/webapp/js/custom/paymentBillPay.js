//To hide the second div depending on the User Role 
$(document).ready(function() {
	$.get('templates/dropdown/fromaccountlist.html', function (template) {
    	$.ajax({
            url : 'account/allaccounts/',
            type : 'GET',
            contentType: "application/json",
            success : function(data) {
            	if(typeof(data) == 'string' && data.indexOf("Login with Username and Password") != -1) { 
            		$(location).attr('href', 'index.html');
            	}
            	 var html = Mustache.to_html(template, data);
                 $('div#transfertoother').html(html);
                
                
            },
            error : function(request,error)
            {
                //console.log(error)
            }
        });
    });


    var template_details = {
        payment_bill_pay_active: "active"
    }
    var internal_other = false;
    var internal_admin = false;
    var external = false;
    if (currentUserRole == "MANAGER" || currentUserRole == "EMPLOYEE") {
        internal_other = false;
    } else if (currentUserRole == "MERCHANT" || currentUserRole == "CUSTOMER") {
        external = true;
    } else if (currentUserRole == "ADMIN") {
        internal_admin = false;
    }
})