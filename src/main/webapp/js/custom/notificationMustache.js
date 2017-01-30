/**
 * 
 */
$( document ).ready(function() {
	url = '/group1/internal/kycrequest/getkycreqs'
	$.ajax({
        url : url,
        type : 'GET',
        contentType: "application/json",
        success : function(data) {
        	//console.log("data:: ");
            //console.log(data)
            $.get('templates/tables/Notifications_NotificationRequest.html', function (template) {
            	var html = Mustache.to_html(template, data);
            	$('div#notificationRequest').html(html);
            	$('#myTable').dataTable();
                $("#myTable_length").hide();
                $("#myTable_filter").hide();
            })
            
        },
        error : function(request,error)
        {
            //console.log(error)
        }
    });
})

function updateKYC(status, kycid) {
	//console.log("I am in here 1");
	url = '/group1/internal/kycrequest/update'
	var userId = $("#myUserId_"+kycid).text();
	var comboData = $("#myFieldId_"+kycid).text();
	var res = comboData.split("::");
	var data = '{"userId":"' + userId + '", "userFieldId":"'+res[0].trim()+'", "fieldValue": "'+ res[1].trim() +'", "status":"' + status+'", "kycid": ' +kycid+' }';
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
        	//console.log(request)
            //console.log(error)
        }
    });
}

	
