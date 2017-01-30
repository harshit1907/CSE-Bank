/**
 * Created by harish on 10/8/16.
 */
$( document ).ready(function() {

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
                 $('div#transferbetween1').html(html);
                
                
            },
            error : function(request,error)
            {
                console.log(error)
            }
        });
    });
    
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
                console.log(error)
            }
        });
    });
});

$(document).ready(function() {
	$("select[name=accType] option:selected").each(function () {
        var value = $(this).val();
        if(value == "Email") {
        	//console.log("in email");
        	$('#emailDiv').show();
        	$('#phoneDiv').hide();
        	$('#accountDiv').hide();
        } else if(value == "Phone_Number") {
        	//console.log("in phone");
        	$('#emailDiv').hide();
        	$('#phoneDiv').show();
        	$('#accountDiv').hide();
        } else if(value == "Account") {
        	//console.log("in phone");
        	$('#emailDiv').hide();
        	$('#phoneDiv').hide();
        	$('#accountDiv').show();
        }
	});
});

function displayOptions() {
	$("select[name=accType] option:selected").each(function () {
        var value = $(this).val();
        if(value == "Email") {
        	//console.log("in email");
        	$('#emailDiv').show();
        	$('#phoneDiv').hide();
        	$('#accountDiv').hide();
        } else if(value == "Phone_Number") {
        	//console.log("in phone");
        	$('#emailDiv').hide();
        	$('#phoneDiv').show();
        	$('#accountDiv').hide();
        } else if(value == "Account") {
        	//console.log("in phone");
        	$('#emailDiv').hide();
        	$('#phoneDiv').hide();
        	$('#accountDiv').show();
        	
           
        }
    });
}
