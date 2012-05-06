package bot;

import java.util.ArrayList;

import util.Configuration;
import bot.interfaces.Account;

public class AccountManager {
	static String AccountsFile = Configuration.Paths.getAccountsFile();
	ArrayList<Account> Accounts = new ArrayList<Account>();

	public void loadAccounts() {
		//read
	}

	public void writeAccounts() {
		//write
	}

	public Account getAccount(String User) {
		for (Account a : Accounts) {
			if (a.getUsername().equals(User)) {
				return a;
			}
		}
		return null;
	}

	public void addAccont(Account a) {
		Accounts.add(a);
	}
}
