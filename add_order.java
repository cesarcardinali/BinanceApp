package main;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Hex;


public class Run {	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, Exception {
		final String USER_AGENT = "binance/java";
		final String API_URL    = "https://api.binance.com/";
		final String ORDER_URL  = "api/v3/order";
		final String API_KEY    = "???";
		final String API_SECRET = "???";
		
		String url = API_URL;
		String urlParameters = "symbol=IOTABTC&side=SELL&type=LIMIT&timeInForce=GTC&quantity=10&price=0.0003&recvWindow=5000&timestamp=" + System.currentTimeMillis();
		System.out.println("Http: " + url + ORDER_URL + "?" + urlParameters);
		
		// Generate signature
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		String signature =  Hex.encodeHexString(sha256_HMAC.doFinal(urlParameters.getBytes("UTF-8")));
		System.out.println("Sig: " + signature);
		
		URL obj = new URL(url + ORDER_URL + "?" + urlParameters + "&signature=" + signature);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("X-MBX-APIKEY", API_KEY);
		
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		// print result
		System.out.println(response.toString());
	}
}
