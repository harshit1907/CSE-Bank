/**
 * Created by harish on 10/8/16.
 
$( document ).ready(function() {

    $.get('templates/subtemplates/nameboard.html', function (template) {
        var json = [{"accountId":"9243372467722593","userId":"admin1234","accType":"*Credit","accOpenDate":"2016-10-12","accBalance":"50000","accountStatus":"active"},{"accountId":"9800026823965841","userId":"admin1234","accType":"Savings","accOpenDate":"2016-10-12","accBalance":"1000","accountStatus":"active"}]
        var html = Mustache.to_html(template, json);
        $('div#transaction_history').html(html);
        $('#myTable').dataTable();
        $("#myTable_length").hide();
        $("#myTable_filter").hide();
        $('#myTable_info').html('<a href="http://www.google.com">Download Document</a>');
    });
});*/