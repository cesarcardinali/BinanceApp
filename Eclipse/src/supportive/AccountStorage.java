package supportive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class AccountStorage {

	final String bankFile = "accounts.ini";
	ArrayList<SavedAccount> accs;


	public AccountStorage() {
		accs = new ArrayList<>();

		try {
			File accountsFile = new File(bankFile);
			if (!accountsFile.exists()) {
				accountsFile.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(accountsFile));
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\t");
				if (data.length > 1) {
					accs.add(new SavedAccount(data));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void addAcc(String acc, String k, String sec) {
		accs.add(new SavedAccount(acc, k, sec));
		
		try {
			File accountsFile = new File(bankFile);
			BufferedWriter bw = new BufferedWriter(new FileWriter(accountsFile));
			
			for (SavedAccount ac : accs) {
				bw.write(ac.getAccount() + "\t" + ac.getKey() + "\t" + ac.getSecret() + "\n");
			}
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public SavedAccount getAcc(String name) {
		for (SavedAccount acc : accs) {
			if (acc.getAccount().equals(name)) {
				return acc;
			}
		}

		return null;
	}


	public class SavedAccount {

		String account;
		String key;
		String secret;


		public SavedAccount(String acc, String k, String sec) {
			account = acc;
			key = k;
			secret = sec;
		}


		public SavedAccount(String data[]) {
			account = data[0];
			key = data[1];
			secret = data[2];
		}


		public String getAccount() {
			return account;
		}


		public void setAccount(String account) {
			this.account = account;
		}


		public String getKey() {
			return key;
		}


		public void setKey(String key) {
			this.key = key;
		}


		public String getSecret() {
			return secret;
		}


		public void setSecret(String secret) {
			this.secret = secret;
		}
	}
}
