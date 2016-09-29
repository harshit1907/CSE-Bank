package csebank_dbmodel.asu.edu;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class PropertiesLoader {

	private String JDBC_DRIVER;
	private String JDBC_URL;
	private String DB_USERNAME;
	private String DB_PASSWORD;
	private String jsonFileePath;
	private HashMap<String,String> paramterMapping;
	
	public  PropertiesLoader()
	{
	Properties properties=new Properties();
	InputStream inputStream=null;
	String filePath="C:\\Users\\Harshit Kumar\\Desktop\\resumes\\prop.properties";
	try{
		inputStream=new FileInputStream(filePath);
		if(inputStream!=null)
		{
		properties.load(inputStream);
		this.jsonFileePath=properties.getProperty("JSON_MAPPING");
		this.setJDBC_DRIVER(properties.getProperty("JDBC_DRIVER"));
		this.setJDBC_URL(properties.getProperty("JDBC_URL"));
		this.setDB_USERNAME(Encryption.decrypt(properties.getProperty("DB_USERNAME")));
		this.setDB_PASSWORD(Encryption.decrypt(properties.getProperty("DB_PASSWORD")));
		ObjectMapper objectMapper=new ObjectMapper();
		paramterMapping=objectMapper.readValue(new File(jsonFileePath), new TypeReference<HashMap<String,String>>(){});
		inputStream.close();
		}
	}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	
	}

	public HashMap<String, String> getParamterMapping() {
		return paramterMapping;
	}

	public void setParamterMapping(HashMap<String, String> paramterMapping) {
		this.paramterMapping = paramterMapping;
	}

	public void setJDBC_DRIVER(String jDBC_DRIVER) {
		JDBC_DRIVER = jDBC_DRIVER;
	}

	public void setJDBC_URL(String jDBC_URL) {
		JDBC_URL = jDBC_URL;
	}

	public void setDB_USERNAME(String dB_USERNAME) {
		DB_USERNAME = dB_USERNAME;
	}

	public void setDB_PASSWORD(String dB_PASSWORD) {
		DB_PASSWORD = dB_PASSWORD;
	}

	public String getJDBC_DRIVER() {
		return JDBC_DRIVER;
	}

	public String getJDBC_URL() {
		return JDBC_URL;
	}

	public String getDB_USERNAME() {
		return DB_USERNAME;
	}

	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	
}
