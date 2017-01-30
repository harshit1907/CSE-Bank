$( document ).ready(function() {
	// TODO: harish: remove username dependency once api is fixed
	url = 'account/allaccounts/'
	$.ajax({
        url : url,
        type : 'GET',
        contentType: "application/json",
        success : function(data) {
        	if(data == "") {
        		$('div#accountsboard').html("No Accounts for you.");
        		return;
        	}
            massaged_msg = []
            data.forEach(function(i){ temp ={};
            temp.accType = i.accType; 
            temp.accBalance = i.accBalance; 
            temp.accountId = i.accountId.substr(i.accountId.length - 4);  
            if(i.accType == "CREDIT") {temp.detailsurl = 'CreditAccount.html'} 
            else if(i.accType=="SAVINGS"){temp.detailsurl = 'SavingAccount.html'} 
            else if(i.accType=="CHECKING"){temp.detailsurl = 'CheckingAccount.html'} 
            massaged_msg.push(temp);
            })
   //         console.log(massaged_msg)
            $.get('templates/subtemplates/accountsboard.html', function (template) {
            	var html = Mustache.to_html(template, massaged_msg);
            	$('div#accountsboard').html(html);
            })
            
        },
        error : function(request,error)
        {
            //console.log(error)
        }
    });
    
    
})