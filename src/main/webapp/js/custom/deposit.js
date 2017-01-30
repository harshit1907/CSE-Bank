$(document).ready(function() {
	$.get('templates/dropdown/toaccountlist.html', function (template) {
    	$.ajax({
            url : 'account/allaccounts/',
            type : 'GET',
            contentType: "application/json",
            success : function(data) {
            	if(typeof(data) == 'string' && data.indexOf("Login with Username and Password") != -1) { 
            		$(location).attr('href', 'index.html');
            	}
            	 var html = Mustache.to_html(template, data);
                 $('div#transferbetween2').html(html);
                
                
            },
            error : function(request,error)
            {
                //console.log(error)
            }
        });
    });
})