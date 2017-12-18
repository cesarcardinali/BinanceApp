

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlaceOrder {

	float actualPrice = (float) 99.0;
	float lastPrice;
	boolean done = false;
	boolean waiting = true;

	public static void main(String[] args) throws Exception {

		//PlaceOrder http = new PlaceOrder();

		// System.out.println("\nTesting 2 - Send Http POST request");
		// http.sendPost();
		new Thread(new Runnable() {
			@Override
			public void run() {
				PlaceOrder http = new PlaceOrder();
				try {
					http.doBet(http, "TRXBTC");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				PlaceOrder http = new PlaceOrder();
				try {
					http.doBet(http, "XRPBTC");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();;
				

	}

	private void doBet(PlaceOrder http, String coinSymbol) throws InterruptedException {
		while (http.waiting) {
			try {
				http.getPrice(coinSymbol);
				// System.out.println(http.actualPrice - http.lastPrice);

				if (http.actualPrice <= 0.00000001944) {
					//http.waiting = false;
					Toolkit.getDefaultToolkit().beep();
					

					//JOptionPane.showMessageDialog(null, "DROPPED!");
					/*
					while (!done) {
						System.out.println(http.actualPrice - http.lastPrice);
						if (http.actualPrice - http.lastPrice < -100) {
							done = true;

						} else {

						}
						
						Toolkit.getDefaultToolkit().beep();
						
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}*/
				}
				
				if (http.actualPrice > 90.0001980) {
					http.waiting = false;
					Toolkit.getDefaultToolkit().beep();
					Thread.sleep(200);
					Toolkit.getDefaultToolkit().beep();
				}
				
				if (http.actualPrice > 90.0001995) {
					http.waiting = false;
					Toolkit.getDefaultToolkit().beep();
					Thread.sleep(200);
					Toolkit.getDefaultToolkit().beep();
					Thread.sleep(200);
					Toolkit.getDefaultToolkit().beep();
					Thread.sleep(200);
					Toolkit.getDefaultToolkit().beep();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(4500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void getPrice(String coinSymbol) throws IOException, ParseException {
		final String API_URL = "https://api.binance.com/";

		URL url = new URL(API_URL + "api/v1/klines?symbol=" + coinSymbol + "&interval=1m&limit=10");
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Content-Type", "application/json");

		InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line;
		while ((line = br.readLine()) != null) {
			// System.out.println(line);
			JSONArray json = (JSONArray) new JSONParser().parse(line);
			JSONArray lastCandle = (JSONArray) json.get(json.size() - 2);
			JSONArray actualCandle = (JSONArray) json.get(json.size() - 1);
			float open = Float.parseFloat((String) actualCandle.get(1));
			float close = Float.parseFloat((String) actualCandle.get(4));
			float high = Float.parseFloat((String) actualCandle.get(2));
			float low = Float.parseFloat((String) actualCandle.get(3));
			
			actualPrice = close;
			DecimalFormat df = new DecimalFormat("#####.##");
			
			System.out.println(coinSymbol);
			if (open > close) {
				System.out.println(
						"candle decrescente: " + df.format(open * 100000000) + " -> " + df.format(close * 100000000));
			} else {
				System.out.println(
						"candle crescente: " + df.format(open * 100000000) + " -> " + df.format(close * 100000000));
			}
			System.out.println("Trades: " + actualCandle.get(8));
			System.out.println("High: " + high* 100000000 + "   Low: " + low* 100000000);
			System.out.println("-------------------------------------\n");
			/*
			 * JSONObject json = (JSONObject) new JSONParser().parse(line);
			 * System.out.println(json.keySet()); lastPrice = actualPrice;
			 * actualPrice = Float.parseFloat((String) json.get("lastPrice"));
			 * System.out.println("IOTA\nPreco Atual: " + actualPrice);
			 * System.out.println("Preco Anterior: " + lastPrice);
			 */
		}
		br.close();
	}

	// HTTP POST request
	private void sendPost() throws Exception {
		final String USER_AGENT = "binance/java";
		final String API_URL = "https://api.binance.com/";
		final String ORDER_URL = "api/v3/order";
		final String API_KEY = "KQr3QBcMFKxqzDnwI9SOGvAmufR3xCC0YOfVsEdGOsqABzSCurr7N2rzIMkAeffY";
		final String API_SECRET = "3OJpkr6rw4zdzG95S39UgFE9lYG0BE0q7HVfxBUFd29slLGbqi03O6SlFepLBkf2";

		String url = API_URL;
		String urlParameters = "symbol=IOTABTC&side=SELL&type=LIMIT&timeInForce=GTC&quantity=40&price=0.00023&recvWindow=5000&timestamp="
				+ System.currentTimeMillis();
		System.out.println("Http: " + url + ORDER_URL + "?" + urlParameters);

		// Generate signature
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		String signature = Hex.encodeHexString(sha256_HMAC.doFinal(urlParameters.getBytes("UTF-8")));
		System.out.println("Sig: " + signature);

		URL urlObj = new URL(url + ORDER_URL + "?" + urlParameters + "&signature=" + signature);
		HttpsURLConnection uc = (HttpsURLConnection) urlObj.openConnection();
		uc.setRequestMethod("POST");
		uc.setRequestProperty("User-Agent", USER_AGENT);
		uc.setRequestProperty("Accept", "application/json");
		uc.setRequestProperty("X-MBX-APIKEY", API_KEY);

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

			// print result
			System.out.println(response.toString());
		} catch (Exception e) {
			int responseCode = uc.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getErrorStream()));
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
}