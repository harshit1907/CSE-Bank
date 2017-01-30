/**
 * Created by harish on 10/8/16.
 */
$( document ).ready(function() {

    $.get('templates/tables/Transactions_TransactionHistory.html', function (template) {
        var json = [
            {
                "Posting Date": 0,
                "Type": 161,
                "Status": "Anusha",
                "Amount": "India",
                "Available Balance": 10000
            },
            {
                "Posting Date": 2,
                "Type": 161,
                "Status": "Charles",
                "Amount": "United Kingdom",
                "Available Balance": 28000
            },
            {
                "Posting Date": 3,
                "Type": 161,
                "Status": "Sravani",
                "Amount": "Australia",
                "Available Balance": 7000
            },
            {
                "Posting Date": 4,
                "Type": 161,
                "Status": "Amar",
                "Amount": "India",
                "Available Balance": 18000
            },
            {
                "Posting Date": 5,
                "Type": 161,
                "Status": "Lakshmi",
                "Amount": "India",
                "Available Balance": 12000
            },
            {
                "Posting Date": 6,
                "Type": 161,
                "Status": "James",
                "Amount": "Canada",
                "Available Balance": 50000
            },
            {
                "Posting Date": 7,
                "Type": 161,
                "Status": "Ronald",
                "Amount": "US",
                "Available Balance": 75000
            },
            {
                "Posting Date": 8,
                "Type": 161,
                "Status": "Mike",
                "Amount": "Belgium",
                "Available Balance": 100000
            },
            {
                "Posting Date": 9,
                "Type": 161,
                "Status": "Andrew",
                "Amount": "Argentina",
                "Available Balance": 45000
            },
            {
                "Posting Date": 10,
                "Type": 161,
                "Status": "Stephen",
                "Amount": "Austria",
                "Available Balance": 30000
            }
        ];
        var html = Mustache.to_html(template, json);
        $('div#transaction_history').html(html);
        $('#myTable').dataTable();
        $("#myTable_length").hide();
        $("#myTable_filter").hide();
        $('#myTable_info').html('<a href="http://www.google.com">Download Document</a>');
    });
});