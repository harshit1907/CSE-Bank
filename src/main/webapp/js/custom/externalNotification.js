/**
 * sid
 */
$( document ).ready(function() {
	url = '/group1/authenticated/kycrequest/getkycreqs'
	$.ajax({
        url : url,
        type : 'GET',
        contentType: "application/json",
        success : function(data) {
        	//console.log("data:: ");
           // console.log(data)
            $.get('templates/tables/ExternalNotification.html', function (template) {
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

	
