package csebank_database.asu.edu;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.sql.Date;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import csebank_utility.asu.edu.DBQueries;
import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.PropertiesLoader;
import csebank_utility.asu.edu.Utility;
public class PdfGenerator {
    
public String genPdf(String j) throws DocumentException, SQLException
{
    ConnectionClass connectionClass=new ConnectionClass();
    ConnectionClass connectionClassDob=new ConnectionClass();
    ConnectionClass connectionClassAcctbal=new ConnectionClass();
    String filepath=new PropertiesLoader().getPDF_GENERATION_PATH();
    String getDob=DBQueries.getDob;
    String selectSQL =DBQueries.pdfGen;
    String accountBalSQL=DBQueries.accBalSql;
    LinkedHashMap<String, String> queryParameterValues=new LinkedHashMap<String,String>();
    queryParameterValues.put("accountId",String.valueOf(j));
    List<HashMap<String,Object>> resultList=connectionClass.executeSelectQuery(selectSQL, queryParameterValues);
    List<HashMap<String,Object>> dobresultList=connectionClassDob.executeSelectQuery(getDob, queryParameterValues);
    List<HashMap<String,Object>> accballist=connectionClassAcctbal.executeSelectQuery(accountBalSQL, queryParameterValues);
    String dob=dobresultList.get(0).get("DOB").toString();
    String accBal=accballist.get(0).get("accBalance").toString();
    Document document = new Document(PageSize.A4, 50, 50, 50, 50);
    String acctnumber=String.valueOf(j);
    String file=filepath+acctnumber+".pdf";
    try 
    {   
     PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
     String USER_PASS = acctnumber.substring(acctnumber.length()-4, acctnumber.length())+dob;
     System.out.println(USER_PASS);
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
    body_paragraph.add("Account summary for "+mask_acctno(j)+" is: ");
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
    for(int i=0;i<resultList.size();i++)
     {
        HashMap<String, Object> userMap=resultList.get(i);
        System.out.println("resultList = "+resultList);
      String transtime=(String) userMap.get("transTimestamp");
      System.out.println("transtime = "+transtime);
      transaction_table.addCell(transtime);
      String transsrcaccno=(String) userMap.get("transSrcAccNo");
      String transdestaccno=(String) userMap.get("transDestAccNo");
      if(transsrcaccno.equals(j))
      {
          transaction_table.addCell(transdestaccno); 
          transaction_table.addCell(""); 
      }
      else if(transdestaccno.equals(j))
      {
          transaction_table.addCell(""); 
          transaction_table.addCell(transsrcaccno); 
      }
      String accountbalance=(String) userMap.get("accountBalance");
      transaction_table.addCell(accountbalance);  
    }       
    document.add(transaction_table); 
    Paragraph summary=new Paragraph();
    summary.setAlignment(Element.ALIGN_BOTTOM);
    summary.add("closing balance is :"+accBal);
    document.add(summary);
    
    document.close();
    return file;
}
//Only first and last two digits will be visible
private String mask_acctno(String j) {
     StringBuilder accountno=new StringBuilder();
     accountno.append(j);
     for(int i=1;i<accountno.length()-2;i++)
     {
         accountno.setCharAt(i, 'X');
     }
         return accountno.toString();
     
}
public String genLog(String filepath,String[] lines,String password)
{
    Utility util=new Utility();
    String filename=util.createRandomNumber(10).toString();
    Document document = new Document();
    PdfWriter writer;
    try {
        writer = PdfWriter.getInstance(document, new FileOutputStream(filepath+"/"+filename+".pdf"));
         writer.setEncryption(password.getBytes(), "owner123".getBytes(),
                 PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
    document.open();
    Paragraph paragraph=new Paragraph();
    Font font=new Font(FontFamily.TIMES_ROMAN,Font.NORMAL);
     font.setSize(14);
    paragraph.setAlignment(Element.ALIGN_TOP|Element.ALIGN_LEFT);
    paragraph.setSpacingAfter(1);
    paragraph.setFont(font);
    for(String line:lines)
    {
        paragraph.add(line+"\n");
    }
    document.add(paragraph);
    document.close();
    } catch (FileNotFoundException e) {
        e.getMessage();
    } catch (DocumentException e) {
        e.getMessage();
    }
    
return filename;
}
}