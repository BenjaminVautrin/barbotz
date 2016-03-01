package data;

import java.util.HashMap;
import java.util.Map;

public class Accounts {

	private static Accounts instance;
	
	private Map<String, String> users;
	
	private Accounts() {
		users = new HashMap<String, String>();
	}
	
	
	public static Accounts getInstance() {
		if (instance == null) {
			instance = new Accounts();
		}
		return instance;
	}
	
	public boolean accountExists(String email) {
		return users.containsKey(email);
	}
	
	public void addAccount(String email, String password) {
		users.put(email, password);
	}
	
}
