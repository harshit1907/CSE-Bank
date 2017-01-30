package csebank_database.asu.edu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.ibatis.common.jdbc.ScriptRunner;

import csebank_utility.asu.edu.DbParamNams;
import csebank_utility.asu.edu.Encryption;
import csebank_utility.asu.edu.PropertiesLoader;

public class ConnectionClass {

	private final String JDBC_DRIVER;
	private final String JDBC_URL;
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	private String SCRIPT_FILE_PATH;
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
		SCRIPT_FILE_PATH=propertiesLoader.getSCRIPT_FILE_PATH();
		DB_PASSWORD=propertiesLoader.getDB_PASSWORD();
		parameterMapping=propertiesLoader.getParamterMapping();
		encyptionParamters=new ArrayList<String>();
		encyptionParamters.add(DbParamNams.ACCOUNT_ID);
		encyptionParamters.add(DbParamNams.CREDIT_ACCOUNT_ID);
		encyptionParamters.add(DbParamNams.USER_ID);
		encyptionParamters.add(DbParamNams.USER_EMAIL);
		encyptionParamters.add(DbParamNams.USER_PHONE);
		encyptionParamters.add(DbParamNams.USER_PASSWORD);
		encyptionParamters.add(DbParamNams.USER_SEC_ANS1);
		encyptionParamters.add(DbParamNams.USER_SEC_ANS2);
		encyptionParamters.add(DbParamNams.TRANS_DEST_ACC_NO);
		encyptionParamters.add(DbParamNams.TRANS_SRC_ACC_NO);
		encyptionParamters.add(DbParamNams.OTP);

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

	public PreparedStatement makePreparedStatemntWithValues(PreparedStatement preparedStatement,LinkedHashMap<String,String> queryParameterValues) throws ParseException, SQLException
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
		
			if(parameterMapping.get(parameterName).equalsIgnoreCase("VARCHAR"))
				preparedStatement.setString(i,parameterValue.trim());
			
			if(parameterMapping.get(parameterName).equalsIgnoreCase("INTEGER"))
				preparedStatement.setInt(i, Integer.parseInt(parameterValue.trim()));
	

			if(parameterMapping.get(parameterName).equalsIgnoreCase("TIMESTAMP"))
			{
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date date=simpleDateFormat.parse(parameterValue.trim());
				preparedStatement.setTimestamp(i,new Timestamp(date.getTime()));
			}

			if(parameterMapping.get(parameterName).equalsIgnoreCase("DATE"))
			{
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
				Date date=simpleDateFormat.parse(parameterValue.trim());
				preparedStatement.setDate(i,new java.sql.Date(date.getTime()));
			}	
			i++;
		}
		return preparedStatement;
	}

	public boolean executeUpdateWithSQLQuery(String sqlQuery, LinkedHashMap<String,String> queryParameterValues){
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
	public List<HashMap<String,Object>> resultSetToListOfMaps(ResultSet resultSet) throws SQLException
	{
		ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
		List<HashMap<String,Object>> resultList=new ArrayList<HashMap<String,Object>>(resultSet.getFetchSize());
		int columnCount=resultSetMetaData.getColumnCount();
		while(resultSet.next())
		{
			HashMap<String, Object> rowMap=new HashMap<String,Object>(columnCount);
			for(int i=1;i<=columnCount;i++)
			{
				String parameterName=resultSetMetaData.getColumnName(i);
				String value= resultSet.getObject(i).toString();
				if(encyptionParamters.contains(parameterName))
					value=Encryption.decrypt(value);
			rowMap.put(parameterName,value);
			}
			resultList.add(rowMap);
		}
		return resultList;
		
	}
	public List<HashMap<String,Object>> executeSelectQuery(String sqlQuery,LinkedHashMap<String,String> queryParameterValues)
	{
		List<HashMap<String,Object>> resultList=null;
		try{
			preparedStatement=connection.prepareStatement(sqlQuery);
			if(queryParameterValues!=null)
			{
				preparedStatement=makePreparedStatemntWithValues(preparedStatement,queryParameterValues);

			}
			ResultSet rs=preparedStatement.executeQuery();
			if(rs!=null)
				resultList=resultSetToListOfMaps(rs);
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
		return resultList;
	}
	public void executeScript()
	{

		try{
			File scriptFile=new File(SCRIPT_FILE_PATH);
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
