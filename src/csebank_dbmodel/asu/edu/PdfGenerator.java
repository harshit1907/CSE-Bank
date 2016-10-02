package csebank_dbmodel.asu.edu;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.sql.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PdfGenerator {
	
@SuppressWarnings("deprecation")
public  String genPdf(int acctno) throws DocumentException, SQLException
{
	ConnectionClass connectionClass=new ConnectionClass();
    String filepath="C:\\Users\\Pushkar\\"+acctno+".pdf";
	String getDob="Select DOB from user usr,account acct where acct.AccountId=? and acct.UserId=usr.UserId; ";
    String selectSQL ="SELECT trans.transtimestamp,trans.transsrcaccno,trans.transdestaccno,acctlog.accountbalance FROM `accountlog` acctlog,transaction trans where trans.transid=acctlog.transid and AccountId=? order by trans.transtimestamp";
    String accountBalSQL="Select AccBalance from account where AccountId=?;";
	LinkedHashMap<String, String> queryParameterValues=new LinkedHashMap<String,String>();
	queryParameterValues.put("AccountId",String.valueOf(acctno));
	List<HashMap<String,Object>> resultList = connectionClass.executeSelectQuery(selectSQL, queryParameterValues);
	List<HashMap<String,Object>> dobresultList=connectionClass.executeSelectQuery(getDob, queryParameterValues);
	List<HashMap<String,Object>> accballist=connectionClass.executeSelectQuery(accountBalSQL, queryParameterValues);
	String dob=dobresultList.get(0).get("DOB").toString();
	String accBal=accballist.get(0).get("AccBalance").toString();
	Document document = new Document(PageSize.A4, 50, 50, 50, 50);
    try 
    {	
	 PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	 String USER_PASS = dob;
     String OWNER_PASS = "Owner123";
     writer.setEncryption(USER_PASS.getBytes(), OWNER_PASS.getBytes(),
	 PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
	} 
	 catch (DocumentException | FileNotFoundException e)
     {
		e.getMessage();
	}
    document.open();
    Font font=new Font(FontFamily.HELVETICA,20,Font.BOLD|Font.UNDERLINE,new CMYKColor(8,79,0,38));
    Paragraph heading_paragraph=new Paragraph();
    heading_paragraph.setAlignment(Element.ALIGN_CENTER);
    heading_paragraph.setSpacingBefore(25);
    heading_paragraph.setSpacingAfter(25);
    heading_paragraph.setFont(font);
    heading_paragraph.add("CSE BANK");
    document.add(heading_paragraph);
    Paragraph body_paragraph=new Paragraph();
    body_paragraph.setSpacingBefore(25);
    body_paragraph.setSpacingAfter(25);
    body_paragraph.setAlignment(Element.ALIGN_LEFT);
    body_paragraph.add("Account summary for "+mask_acctno(acctno)+" is: ");
    document.add(body_paragraph);
	PdfPTable transaction_table = new PdfPTable(4);
	transaction_table.setSpacingBefore(25);
	transaction_table.setSpacingAfter(25);
	PdfPCell date_cell = new PdfPCell(new Phrase("Date"));  
	transaction_table.addCell(date_cell);
	PdfPCell debit_cell = new PdfPCell(new Phrase("Debitted to"));
	transaction_table.addCell(debit_cell);
	PdfPCell credit_cell = new PdfPCell(new Phrase("Creditted from")); 
	transaction_table.addCell(credit_cell);
	PdfPCell balance_cell=new PdfPCell(new Phrase("Balance"));
	transaction_table.addCell(balance_cell);
	System.out.println(resultList);
	for(int i=0;i<resultList.size();i++)
	 {
	  Date transtime=((ResultSet) resultList.get(i)).getDate("transtimestamp");
	  transaction_table.addCell(transtime.toString());
	  String transsrcaccno=((ResultSet) resultList.get(i)).getString("transsrcaccno");
	  String transdestaccno=((ResultSet) resultList.get(i)).getString("transdestaccno");
	  if(transsrcaccno.equals(acctno))
	  {
		  transaction_table.addCell(transdestaccno); 
		  transaction_table.addCell(""); 
	  }
	  else if(transdestaccno.equals(acctno))
	  {
		  transaction_table.addCell(""); 
		  transaction_table.addCell(transsrcaccno); 
      }
	  String accountbalance=((ResultSet) resultList.get(i)).getString("accountbalance");
	  transaction_table.addCell(accountbalance);  
	  System.out.println(transtime+","+transsrcaccno+","+transdestaccno+","+accountbalance);
	}		
	document.add(transaction_table); 
	Paragraph summary=new Paragraph();
	summary.setAlignment(Element.ALIGN_BOTTOM);
	summary.add("closing balance is :"+accBal);
	document.add(summary);
	
	document.close();
	return filepath;
}
//Only first and last two digits will be visible
private String mask_acctno(int acctno) {
	 StringBuilder accountno=new StringBuilder();
	 accountno.append(acctno);
	 for(int i=1;i<accountno.length()-2;i++)
	 {
		 accountno.setCharAt(i, 'X');
	 }
	     return accountno.toString();
	 
}

}
