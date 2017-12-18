package supportive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import models.CoinCandles;
import models.CoinPriceInformation;

public class BinanceApi {	
	// Variables -----------------------------------------------------------------------
	final String USER_AGENT = "binance/java";
	final String API_URL    = "https://api.binance.com/";
	
	final String ORDER_URL  = "api/v3/order";
	final String TICKER_24H = "api/v1/ticker/24hr";
	final String SERVER_TIME = "api/v1/time";

	final String TIME_IN_FORCE_FOK = "FOK";
	final String TIME_IN_FORCE_GTC = "GTC";
	final String TIME_IN_FORCE_IOC = "IOC";

	final String ORDER_TYPE_LIMIT = "LIMIT";
	final String ORDER_TYPE_MARKET = "MARKET";
	final String ORDER_TYPE_STOP_LOSS = "STOP_LOSS";
	final String ORDER_TYPE_STOP_LOSS_LIMIT = "STOP_LOSS_LIMIT";
	
	String API_KEY;
	String API_SECRET;
	
	
	// Constructor ---------------------------------------------------------------------
	public BinanceApi(String key, String secret) {
		super();
		API_KEY = key;
		API_SECRET = secret;
	}
	
	
	// Main Methods --------------------------------------------------------------------
	public boolean placeOrder(String side, String coinSymbol, float quantity, float price) {
		String url = API_URL;
		String urlParameters = "symbol=" + coinSymbol + "&side=" + side + "&type=LIMIT&timeInForce=GTC&quantity="
				+ quantity + "&price=" + price + "&recvWindow=5000&timestamp=" + System.currentTimeMillis();
		
		try {
			// Generate signature
			String signature = generateSignature(urlParameters);
			
			// Create URL Connection
			URL urlObj = new URL(url + ORDER_URL + "?" + urlParameters + "&signature=" + signature);
			System.out.println("URL: " + urlObj);
			HttpsURLConnection uc = postConnection(urlObj);

			try {
				int responseCode = uc.getResponseCode();
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println("Response:\n" + response.toString());
				return true;
				
			} catch (Exception e) {
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				System.out.println("Response:\n" + response.toString());
				
			}
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return false;
	}

	public boolean placeBuyOrder(String coinSymbol, float quantity, float price) {
		return placeOrder("BUY", coinSymbol, quantity, price);
	}

	public boolean placeSellOrder(String coinSymbol, float quantity, float price) {
		return placeOrder("SELL", coinSymbol, quantity, price);
	}
	
	public boolean removeOrder(String orderId) {
		return false;
	}
	
	public CoinPriceInformation getPriceUpdate(String coinSymbol) throws MalformedURLException, IOException, ParseException {
		CoinPriceInformation info = null;
		URL url = new URL(API_URL + TICKER_24H + "?symbol=" + coinSymbol);
		URLConnection uc = getConnection(url);

		InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line;
		while ((line = br.readLine()) != null) { //arrumar isso, sempre sera uma linha soh e nao vai precisar while
			JSONObject json = (JSONObject) new JSONParser().parse(line);
			
			info = new CoinPriceInformation(json);
			System.out.println(info.toString());
		}
		br.close();
		
		return info;
	}
	
	public CoinCandles getCoinCandle(String coinSymbol, String interval, String limit) throws MalformedURLException, IOException, ParseException {
		CoinCandles candles = null;
		URL url = new URL(API_URL + "api/v1/klines?symbol=" + coinSymbol + "&interval=" + interval + "&limit=" + limit);
		URLConnection uc = getConnection(url);

		InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line;
		while ((line = br.readLine()) != null) {
			JSONArray json = (JSONArray) new JSONParser().parse(line);
			candles = new CoinCandles(json);
		}
		br.close();
		
		return candles;
	}
	
	
	// Supportive Methods --------------------------------------------------------------
	private String generateSignature(String text)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		String signature = Hex.encodeHexString(sha256_HMAC.doFinal(text.getBytes("UTF-8")));
		
		return signature;
	}
	
	private HttpsURLConnection postConnection(URL urlObj) throws IOException {
		// Configure headers
		HttpsURLConnection uc = (HttpsURLConnection) urlObj.openConnection();
		
		uc.setRequestMethod("POST");
		uc.setRequestProperty("User-Agent", USER_AGENT);
		uc.setRequestProperty("Accept", "application/json");
		uc.setRequestProperty("X-MBX-APIKEY", API_KEY);
							
		return uc;
	}
	
	private HttpsURLConnection putConnection(URL urlObj) throws IOException {
		// Configure headers
		HttpsURLConnection uc = (HttpsURLConnection) urlObj.openConnection();

		uc.setRequestMethod("PUT");
		uc.setRequestProperty("User-Agent", USER_AGENT);
		uc.setRequestProperty("Accept", "application/json");
		uc.setRequestProperty("X-MBX-APIKEY", API_KEY);

		return uc;
	}

	private HttpsURLConnection deleteConnection(URL urlObj) throws IOException {
		// Configure headers
		HttpsURLConnection uc = (HttpsURLConnection) urlObj.openConnection();

		uc.setRequestMethod("DELETE");
		uc.setRequestProperty("User-Agent", USER_AGENT);
		uc.setRequestProperty("Accept", "application/json");
		uc.setRequestProperty("X-MBX-APIKEY", API_KEY);

		return uc;
	}

	private HttpsURLConnection getConnection(URL urlObj) throws IOException {
		// Configure headers
		HttpsURLConnection uc = (HttpsURLConnection) urlObj.openConnection();

		uc.setRequestMethod("GET");
		uc.setRequestProperty("User-Agent", USER_AGENT);
		uc.setRequestProperty("Accept", "application/json");
		uc.setRequestProperty("X-MBX-APIKEY", API_KEY);

		return uc;
	}
	
	// Getters and Setters -------------------------------------------------------------
	
}
