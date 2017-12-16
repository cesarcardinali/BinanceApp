package supportive;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import models.CoinCandle;
import models.CoinPriceInformation;

public class BinanceApi {	
	// Variables -----------------------------------------------------------------------
	final String USER_AGENT = "binance/java";
	final String API_URL    = "https://api.binance.com/";
	final String ORDER_URL  = "api/v3/order";
	
	final String TICKER_24H = "v1/ticker/24hr";
	final String SERVER_TIME = "v1/time";
	final String ORDER = "v3/order";

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
	public BinanceApi(String aPI_KEY, String aPI_SECRET) {
		super();
		API_KEY = aPI_KEY;
		API_SECRET = aPI_SECRET;
	}
	
	
	// Main Methods --------------------------------------------------------------------
	public boolean placeOrder(String side, String coinSymbol, float quantity, float price) {
		
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
	
	public CoinPriceInformation getPriceUpdate(String coinSymbol) {
		
		return null;
	}
	
	public CoinCandle getCoinCandle(String coinSymbol) {
		
		return null;
	}
	
	
	// Supportive Methods --------------------------------------------------------------
	private String generateSignature(String text)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		String signature = Hex.encodeHexString(sha256_HMAC.doFinal(text.getBytes("UTF-8")));
		System.out.println("Sig: " + signature);
		return signature;
	}
	
	private String post() {
		return "";
	}
	
	private String put() {
		return "";
	}
	
	private String delete() {
		return "";
	}
	
	private String get() {
		
		return "";
	}
	
	// Getters and Setters -------------------------------------------------------------
	
}
