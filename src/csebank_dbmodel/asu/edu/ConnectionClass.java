package csebank_dbmodel.asu.edu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ibatis.common.jdbc.ScriptRunner;

public class ConnectionClass {

	private final String JDBC_DRIVER;
	private final String JDBC_URL;
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	private Connection connection=null;
	private HashMap<String,String> parameterMapping;
	List<String> encyptionParamters;
	public Connection getConnection() {
		return connection;
	}
	private PreparedStatement preparedStatement=null;
	
	
	public ConnectionClass(){
		PropertiesLoader propertiesLoader=new PropertiesLoader();
		JDBC_DRIVER=propertiesLoader.getJDBC_DRIVER();
		JDBC_URL=propertiesLoader.getJDBC_URL();
		DB_USERNAME=propertiesLoader.getDB_USERNAME();
		DB_PASSWORD=propertiesLoader.getDB_PASSWORD();
		parameterMapping=propertiesLoader.getParamterMapping();
		encyptionParamters=new ArrayList<String>();
		encyptionParamters.add("AccountId");
		encyptionParamters.add("UserId");
		encyptionParamters.add("Email");
		encyptionParamters.add("Phone");
		encyptionParamters.add("Password");
		encyptionParamters.add("SecurityAns");
		encyptionParamters.add("SessionOTP");
			
		try
		{
			Class.forName(JDBC_DRIVER);
			connection=DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
			connection.setAutoCommit(false);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public PreparedStatement makePreparedStatemntWithValues(PreparedStatement preparedStatement,HashMap<String,String> queryParameterValues) throws ParseException, SQLException
	{
		Set<String> parameterNameSet=queryParameterValues.keySet();
		Iterator<String> iterator=parameterNameSet.iterator();
		int i=1;
		while(iterator.hasNext())
		{
			String parameterName=iterator.next();
			String parameterValue=queryParameterValues.get(parameterName);
			if(encyptionParamters.contains(parameterName))
				parameterValue=Encryption.encrypt(parameterValue);
			if(parameterMapping.get(parameterName).equalsIgnoreCase("STRING"))
			preparedStatement.setString(i,parameterValue );
			

			if(parameterMapping.get(parameterName).equalsIgnoreCase("INTEGER"))
			preparedStatement.setInt(i, Integer.parseInt(parameterValue));
			

			if(parameterMapping.get(parameterName).equalsIgnoreCase("TIMESTAMP"))
			{
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date date=simpleDateFormat.parse(parameterValue);
				preparedStatement.setTimestamp(i,new Timestamp(date.getTime()));
			}
			
			if(parameterMapping.get(parameterName).equalsIgnoreCase("DATE"))
			{
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
				Date date=simpleDateFormat.parse(parameterValue);
				preparedStatement.setDate(i,new java.sql.Date(date.getTime()));
				}	
			i++;
	}
		return preparedStatement;
	}
	
	public boolean executeUpdateWithSQLQuery(String sqlQuery, HashMap<String,String> queryParameterValues){
		boolean result=false;
		try{
			preparedStatement=connection.prepareStatement(sqlQuery);
			if(queryParameterValues!=null)
			{
				preparedStatement=makePreparedStatemntWithValues(preparedStatement,queryParameterValues);
			}
			Integer rowsImpacted=preparedStatement.executeUpdate();
			if(rowsImpacted==1)
				{
				result=true;
				connection.commit();
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally {
			try {
				preparedStatement.close();
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
		public ResultSet executeSelectQuery(String sqlQuery,HashMap<String,String> queryParameterValues)
	{
		ResultSet resultSet = null;
		try{
			preparedStatement=connection.prepareStatement(sqlQuery);
			if(queryParameterValues!=null)
			{
				preparedStatement=makePreparedStatemntWithValues(preparedStatement,queryParameterValues);
				
			}
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.getRow()>=0)
				resultSet=rs;
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally {
			try {
				preparedStatement.close();
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	public void executeScript(String scriptFilePath)
	{
		
		try{
			File scriptFile=new File(scriptFilePath);
			FileReader fileReader=new FileReader(scriptFile);
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			ScriptRunner scriptRunner=new ScriptRunner(connection,false,false);
			scriptRunner.runScript(bufferedReader);
			connection.commit();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally {
			try {
				connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
