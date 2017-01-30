var context_path = "group1"
function ConvertFormToJSON(form){
    var array = jQuery(form).serializeArray();
    var json = {};

    jQuery.each(array, function() {
        json[this.name] = this.value || '';
    });
    return json;
}

function get_user_role() {
	var temp;
	var request = $.ajax({
		url : 'authenticated/getUserRole/',
        type : 'GET',
        async: false,
        contentType: "application/json",
        success : function(res) {
        	temp = res;
        	
        },
        error : function(jqXHR, textStatus, errorThrown) {
        	//console.log(jqXHR)
        }
        
	})
	return temp;
}

$(document).ready(function () {
    jQuery("form").submit(function(event) {
        event.preventDefault()
        var form = this
        var json = ConvertFormToJSON(form)
        
        if(jQuery(form).attr('url') == '/group1/login') {
        	content_type = 'application/x-www-form-urlencoded'
        	data = 'userId=' + jQuery('#nameField').val() + '&password=' + jQuery('#pwd').val()
        } else {
        	content_type = "application/json"
        	data = 	JSON.stringify(json)
        	//console.log(data);
        }
        $.ajax({
            url : jQuery(form).attr('url'),
            type : jQuery(form).attr('method'),
            data : data,
            contentType: content_type,
            success : function(data) {
            	if(jQuery(form).attr('url') == "/group1/login") {
            		console.log(data)
                	localStorage.userId = jQuery('#nameField').val();
            		
            		new Fingerprint2().get(function(result, components){
            			//console.log(result); 
            			//a hash, representing your device fingerprint
            			//console.log(components); // an array of FP components
            			data = '{"deviceId" : "' + result + '"}'
            			$.ajax({
            	            url : 'device/add',
            	            type : 'POST',
            	            data : data,
            	            contentType: "application/json",
            	            success : function(data) {
            	            	//console.log("success")
            	            },
            	            error: function(jqXHR, textStatus, errorThrown) {
            	            	//console.log("error")
            	            }
            			});})
                }
                if(jQuery(form).attr('noalert') != 'true') {
                	if(jQuery(form).attr('url') != "/group1/login") {
                		if(jQuery(form).attr('url') == "transactions/addMoney" ||
                				jQuery(form).attr('url') == "transactions/withdrawMoney" ||
                				jQuery(form).attr('url') == "transactions/add") {
                			alert('Your money transfer was successful. Your Transaction Id is: ' + data)
                		} else if(jQuery(form).attr('url') == "/group1/anonymous/signup"){
                			alert('Congratulations ! You have successfully signed up');
                		} else {
                			alert(data)
                		}
                		
                	}
                }
                redirecturl = jQuery(form).attr('tourl')
                if(redirecturl != 'same') {
                	if(jQuery(form).attr('url') == "/group1/login") {
                		role = get_user_role()
                		switch(role) {
        	
                             case "CUSTOMER":
            	               redirecturl = 'Landing.html'
                               break;
                             case "MERCHANT":
            	               redirecturl = 'Landing.html'
                               break;
                             case "MANAGER":
            	               redirecturl = 'InternalUserLanding.html'
                               break;
                             case "EMPLOYEE":
            	               redirecturl = 'InternalUserLanding.html'
                               break;
                            case "ADMIN":
				            	redirecturl = 'AdministratorHub.html'
				                break;
				            default:
				            	redirecturl = 'Landing.html'
				            }
                	}
                    $(location).attr('href', redirecturl);
                }
                
            },
            error : function(jqXHR, textStatus, errorThrown)
            {
            	if(jQuery(form).attr('url') == "/group1/anonymous/signup"){
        			grecaptcha.reset();//reload captcha
        		}
            	if(jqXHR.status == 417) {
            		alert(jqXHR.responseText)
            	} 
            	else if(jqXHR.status == 401) {
                    var temp_content = jqXHR.responseText;
                    if($(temp_content).find("u").first().text())
                    	alert($(temp_content).find("u").first().text());
                    else
                    	alert(jqXHR.responseText);
            	}else if(jqXHR.status == 403) {
            		alert("You are not authorized to make this form submission")
            		$(location).attr('href', "http://google.com");
            	} else {
            		alert(jqXHR.responseText)
            	}
            	
                
            }
        });

    })
});

$( document ).ready(function() {
	var user_details = {'userId' : localStorage.userId}
	//console.log("user_details");
	
	role = get_user_role()
	//console.log(role);
	switch(role) {

         case "CUSTOMER":
        	 $.get('templates/navbar.html', function (template) {
                 var html = Mustache.to_html(template, user_details);
                 $('div#navbarspace').html(html);
             });
             $.get('templates/sidebar_external.html', function (template) {
                 var html = Mustache.to_html(template, template_details);
                 $('div#sidebar_external').html(html);
             });
           break;
         case "MERCHANT":
        	 $.get('templates/navbar.html', function (template) {
                 var html = Mustache.to_html(template, user_details);
                 $('div#navbarspace').html(html);
             });
             $.get('templates/sidebar_external.html', function (template) {
                 var html = Mustache.to_html(template, template_details);
                 $('div#sidebar_external').html(html);
             });
           break;
         case "MANAGER":
        	 $.get('templates/navbar.html', function (template) {
                 var html = Mustache.to_html(template, user_details);
                 $('div#navbarspace').html(html);
             });
             $.get('templates/sidebar_internal_other.html', function (template) {
                 var html = Mustache.to_html(template, template_details);
                 $('div#sidebar_internal_other').html(html);
             });
           break;
         case "EMPLOYEE":
        	 $.get('templates/navbar.html', function (template) {
                 var html = Mustache.to_html(template, user_details);
                 $('div#navbarspace').html(html);
             });
             $.get('templates/sidebar_internal_other.html', function (template) {
                 var html = Mustache.to_html(template, template_details);
                 $('div#sidebar_internal_other').html(html);
             });
           break;
        case "ADMIN":
        	$.get('templates/navbar.html', function (template) {
                var html = Mustache.to_html(template, user_details);
                $('div#navbarspace').html(html);
            });
            $.get('templates/sidebar_internal_admin.html', function (template) {
                var html = Mustache.to_html(template, template_details);
                $('div#sidebar_internal_admin').html(html);
            });
            break;
        default:
        	$.get('templates/navbar.html', function (template) {
                var html = Mustache.to_html(template, user_details);
                $('div#navbarspace').html(html);
            });
            $.get('templates/sidebar_external.html', function (template) {
                var html = Mustache.to_html(template, template_details);
                $('div#sidebar_external').html(html);
            });
        }

});

