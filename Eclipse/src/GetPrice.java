import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetPrice {

	static String API_URL = "https://api.binance.com/api/";
	static String PRIVATE_API_VERSION = "v3";
	static String PUBLIC_API_VERSION = "v1";
	static String TICKER_24H = "v1/ticker/24hr";
	static String SERVER_TIME = "v1/time";
	static String ORDER = "v3/order";

	static String TIME_IN_FORCE_FOK = "FOK";
	static String TIME_IN_FORCE_GTC = "GTC";
	static String TIME_IN_FORCE_IOC = "IOC";

	static String ORDER_TYPE_LIMIT = "LIMIT";
	static String ORDER_TYPE_MARKET = "MARKET";
	static String ORDER_TYPE_STOP_LOSS = "STOP_LOSS";
	static String ORDER_TYPE_STOP_LOSS_LIMIT = "STOP_LOSS_LIMIT";
	static String ORDER_TYPE_TAKE_PROFIT = "TAKE_PROFIT";
	static String ORDER_TYPE_TAKE_PROFIT_LIMIT = "TAKE_PROFIT_LIMIT";
	static String ORDER_TYPE_LIMIT_MAKER = "LIMIT_MAKER";

	static float actualPrice;
	static float lastPrice;

	public static void main(String[] args) throws Exception {
		URL url = new URL(API_URL + TICKER_24H + "?symbol=IOTABTC");
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Content-Type", "application/json");

		InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line;
		while ((line = br.readLine()) != null) {
			JSONObject json = (JSONObject) new JSONParser().parse(line);
			System.out.println(json.keySet());
			actualPrice = Float.parseFloat((String) json.get("bidPrice"));
			lastPrice = Float.parseFloat((String) json.get("lastPrice"));
			System.out.println("IOTA\nPreco Atual: " + actualPrice);
			System.out.println("Preco Anterior: " + lastPrice);
		}
		br.close();
	}
	
}