/**
 * by sid
 */
checkingId = '';
	 savingId = '';
	 creditId = '';
$(document).ready(function() {
	var massaged_msg = '';
	
	url = 'account/allaccounts/'
		$.ajax({
	        url : url,
	        type : 'GET',
	        contentType: "application/json",
	        success : function(data) {
	        	if(data == "") {
	        		$('div#accList').html("No Accounts for you.");
	        		return;
	        	}
	        	
	        	var myJsonString = JSON.stringify(data);
	        	//console.log(myJsonString)
	        	
	        	var obj = JSON.parse(myJsonString);	
	        	checkingId = getValueByKey('CHECKING', obj)
	        	savingId = getValueByKey('SAVINGS', obj);
        		creditId = getValueByKey('CREDIT', obj);
	        }, 
            async: false,
	        error : function(request,error)
	        {
	            //console.log(error)
	        }
	    });
	
	var url = '/group1/transactions/accountId/' + creditId  ;
	$.get('templates/tables/AccountTransaction.html', function (template) {
    	$.ajax({
            url : url,
            type : 'GET',
            contentType: "application/json",
            success : function(data) {
            	if(typeof(data) == 'string' && data.indexOf("Login with Username and Password") != -1) { 
            		$(location).attr('href', 'index.html');
            	}
            	console.log("i am here");
            	console.log(data);
	        	 var html = Mustache.to_html(template, data);
	             $('div#accList').html(html);
            },
            error : function(request,error)
            {
                console.log(error)
            }
        });
    	
    });
	
})


function getValueByKey(str, data) {
	var arth = '';
    var i, len = data.length;
    for (i = 0; i < len; i++) {
    	if(data[i].accType == str) {
    		arth = data[i].accountId;
    	}
//    	if(data[i].accType == 'SAVINGS') {
//    		savingId = data[i].accountId;
//    	}
//    	if(data[i].accType == 'CHECKING') {
//    		checkingId = data[i].accountId;
//    	}
    }
    return arth;
}