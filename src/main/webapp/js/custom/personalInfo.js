
$( document ).ready(function() {
	// TODO: harish: remove username dependency once api is fixed
	url = '/group1/getUserDetails'
	var userrole;
	$.ajax({
        url : url,
        type : 'GET',
        contentType: "application/json",
        success : function(data) {
            var template_details = {"user_full_name": data.firstName + ' ' + data.lastName,
            		"user_email_id" : data.email, "user_phone":  data.phoneNumber, "user_address": data.address, "user_userId": data.userId}
            //console.log("console.log ");
            //console.log(template_details);
            $.get('templates/tables/PersonalInformation_personalInfo.html', function (template) {
            	var html = Mustache.to_html(template, template_details);
            	$('div#personalDetailsDiv').html(html);
            })
            
        },
        error : function(request,error)
        {
            console.log(error)
        }
    });
	
	
})


function displayOptions() {
	$("select[name=accType] option:selected").each(function () {
        var value = $(this).val();
        if(value == "Email") {
        	$('#emailDiv').show();
        	$('#phoneDiv').hide();
        } else if(value == "Phone_Number") {
        	//console.log("in phone");
        	$('#emailDiv').hide();
        	$('#phoneDiv').show();
        } 
    });
}

function addKYC() {
    url = '/group1/authenticated/kycrequest/add'
    var userId = $("#username").val();
    var userFieldIdVal = '';
    var fieldVal = '';
    $("select[name=accType] option:selected").each(function () {
        var value = $(this).val();
        if(value == "Email") {
        	userFieldIdVal = "email";
        	fieldVal = $("#emailId").val();
        } else if(value == "Phone_Number") {
        	userFieldIdVal = "phoneNumber";
        	fieldVal = $("#phoneId").val();
        } 
    });
    
    var data = '{"userId":"' + userId + '", "userFieldId":"'+userFieldIdVal+'", "fieldValue": "'+ fieldVal +'" }';
	//console.log("data:: ");
	//console.log(data);
    $.ajax({
       url : url,
       type : 'POST',
       data : data,
       contentType: "application/json",
       success : function(data) {
           //console.log(data)
           location.reload();
       },
       error : function(request,error)
       {    
          // console.log(request)
          // console.log(error)
       }
   });
}
