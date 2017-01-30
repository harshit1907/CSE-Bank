package csebank_utility.asu.edu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class CaptchaCheck {

	public static final String googleurl = "https://www.google.com/recaptcha/api/siteverify";
	public static final String key = "6LdxawoUAAAAALlmMmr5pOkNuiraZLPadWjw1Q35";
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";

	public static boolean verify(String captchaRespone) throws IOException {
		System.out.println("in captcha check");
		if (captchaRespone == null || "".equals(captchaRespone)) {
			return false;
		}
		
		try{
		URL obj = new URL(googleurl);
		HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", USER_AGENT);
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String postParams = "secret=" + key + "&response="
				+ captchaRespone;

		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		int responseCode = connection.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
		JsonObject jsonObject = jsonReader.readObject();
		jsonReader.close();
		
		return jsonObject.getBoolean("success");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}