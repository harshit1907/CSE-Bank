$(document).ready(function() {
	$.get('templates/tables/userlist.html', function (template) {
    	$.ajax({
            url : 'getUserList',
            type : 'GET',
            contentType: "application/json",
            success : function(data) {
            	if(typeof(data) == 'string' && data.indexOf("Login with Username and Password") != -1) { 
            		$(location).attr('href', 'index.html');
            	}
            	 var html = Mustache.to_html(template, data);
                 $('div#userlist').html(html);
            },
            error : function(request,error)
            {
                //console.log(error)
            }
        });
    	
    });
	
})