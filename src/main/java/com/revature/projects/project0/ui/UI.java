package com.revature.projects.project0.ui;

import java.util.List;
import java.util.Scanner;

import com.revature.projects.wmagnus.project0.dao.AccountLinkageDAO;
import com.revature.projects.wmagnus.project0.dao.AccountLinkageDAOImpl;
import com.revature.projects.wmagnus.project0.dao.MoneyAccountDAO;
import com.revature.projects.wmagnus.project0.dao.MoneyAccountDAOImpl;
import com.revature.projects.wmagnus.project0.dao.UserAccountDAO;
import com.revature.projects.wmagnus.project0.dao.UserAccountDAOImpl;
import com.revature.projects.wmagnus.project0.models.AccountLinkage;
import com.revature.projects.wmagnus.project0.models.MoneyAccount;
import com.revature.projects.wmagnus.project0.models.UserAccount;

public class UI {

	public enum ConsoleStates {
		LOGGED_OUT, 		//Initial State
		
		LOGGING_IN,
		LOGGING_IN_PASS,
		
		LOGGED_IN,			
		CREATING_USER_ACCOUNT_NAME,
		CREATING_USER_ACCOUNT_PASS,
		
		INTERACTING_BANK_ACCOUNT,
		INTERACTING_JOINT_BANK_ACCOUNT,
		
		BANK_ACCOUNT_WITHDRAWING,
		BANK_ACCOUNT_DEPOSITING,
		JOINT_LINKING,
		BANK_ACCOUNT_BALANCE,
		
		CREATING_BANK_ACCOUNT
	}
	
	private ConsoleStates consoleState = ConsoleStates.LOGGED_OUT;
	private UserAccount workingUserAccount = null;
	private MoneyAccount workingBankAccount = null;
	private Scanner sc;
	private int attempts;
	private String internalNameState;
	
	public UserAccount getWorkingUA()
	{
		return workingUserAccount;
	}
	public void setWorkingUA(UserAccount u)
	{
		workingUserAccount = u;
	}
	public MoneyAccount getWorkingBA()
	{
		return workingBankAccount;
	}
	public void setWorkingBA(MoneyAccount m)
	{
		workingBankAccount = m;
	}
	public int getAttempts()
	{
		return attempts;
	}
	public void setAttempts(int a)
	{
		attempts = a;
	}
	public String getInternalNameState()
	{
		return internalNameState;
	}
	public void setInternalNameState(String s)
	{
		internalNameState = s;
	}
	
	UserAccountDAO uadao = new UserAccountDAOImpl();
	AccountLinkageDAO aldao = new AccountLinkageDAOImpl();
	MoneyAccountDAO madao = new MoneyAccountDAOImpl();
	
	public UI()
	{
		sc = new Scanner(System.in);
		attempts = 0;
	}
	
	public ConsoleStates actOnState()
	{
		
		switch(consoleState)
		{
		case LOGGED_OUT:
			System.out.println("Welcome to Project0 Banking Services. Press a number to continue:");
			System.out.println("(1) Log in");
			System.out.println("(2) Create a user account");
			
			this.consoleState = loggedOutAction(integralInput());
			break;
			
		case LOGGING_IN:
			System.out.println("Input your username.");
			
			if (attempts < 3) this.consoleState = this.logInAction(this.usernameInput());
			else
			{
				System.out.println("Too many attempts.");
				attempts = 0;
				this.internalNameState = null;
				this.consoleState = ConsoleStates.LOGGED_OUT;
			}
			break;
			
		case LOGGING_IN_PASS:
			System.out.println("Input your password. Alphanumeric characters, and the !._? characters are allowed, and it must be between 6 and 32 characters in length.");
			
			if (attempts < 3) this.consoleState = this.logInPassAction(this.passwordInput(), internalNameState);
			else
			{
				System.out.println("Too many attempts.");
				attempts = 0;
				this.internalNameState = null;
				this.consoleState = ConsoleStates.LOGGED_OUT;
			}			
			break;
		case LOGGED_IN:			
			System.out.println("Currently Logged In.");
			loggedInOutput();
			this.consoleState = loggedInAction(integralInput());
			break;
		case CREATING_USER_ACCOUNT_NAME:
			System.out.println("Beginning new account creation.");
			System.out.println("Input a username, alphanumeric characters only and between 6 and 32 characters in length.");
			

			internalNameState = this.createAccountNamephase(this.usernameInput());
			if (internalNameState == null) this.consoleState = ConsoleStates.LOGGED_OUT;
			else this.consoleState = ConsoleStates.CREATING_USER_ACCOUNT_PASS;
			break;
		case CREATING_USER_ACCOUNT_PASS:
			System.out.println("Input a password. Alphanumeric characters, and the !._? characters are allowed, and it must be between 7 and 32 characters in length.");
			
			this.consoleState = this.createAccountPassphase(this.passwordInput(), internalNameState);
			break;
		case INTERACTING_BANK_ACCOUNT:
			System.out.println("Interacting with bank account: " + workingBankAccount.getAcctNumber());			
			System.out.println("(1) Check Balance");
			System.out.println("(2) Withdraw"); //I MUST WITHDRAW
			System.out.println("(3) Deposit"); //It was inevitable
			System.out.println("(4) Back to Accounts");
			
			consoleState = bankAccountAction(integralInput());
			break;
		case INTERACTING_JOINT_BANK_ACCOUNT:
			System.out.println("Interacting with joint bank account: " + workingBankAccount.getAcctNumber());
			System.out.println("(1) Check Balance");
			System.out.println("(2) Withdraw"); 
			System.out.println("(3) Deposit"); 
			System.out.println("(4) Add User to Joint Bank Account");
			System.out.println("(5) Back to Accounts");
			
			consoleState = jBankAccountAction(integralInput());
			
			break;
			
		case BANK_ACCOUNT_WITHDRAWING:
			System.out.println("Withdraw How Much?");
			consoleState = this.withdraw(doubleInput());
			
			break;
		case BANK_ACCOUNT_DEPOSITING:
			System.out.println("Deposit How Much?");
			consoleState = this.deposit(doubleInput());
			
			break;
		case JOINT_LINKING:
			System.out.println("Input a username to link to the account.");
			consoleState = this.jointLink(usernameInput());
			
			break;
		case BANK_ACCOUNT_BALANCE:
			consoleState = this.balance();
			
			break;
		case CREATING_BANK_ACCOUNT:
			System.out.println("Select a type of bank account.");
			System.out.println("(1) Checking");
			System.out.println("(2) Savings");
			System.out.println("(3) Joint");
			System.out.println("(4) Go Back");
			
			consoleState = createBankAccountAction(integralInput());
			
			break;
		default:
			workingBankAccount = null;
			workingUserAccount = null;
			break;
		}
		
		return consoleState;
	}
	
	public int integralInput()
	{
		int input = 0;
		String sinput;
		while(!sc.hasNext());
		if(sc.hasNextInt())
		{
			input = sc.nextInt();
			return input;
		}
		else
		{
			sinput = sc.next();
			if (sinput.equalsIgnoreCase("NEW")) return -1;
			if (sinput.equalsIgnoreCase("EXIT")) return -2;
			return Integer.MIN_VALUE;
		}
	}
	
	public double doubleInput()
	{
		double input = 0.0;
		while(!sc.hasNext());
		if(sc.hasNextDouble())
		{
			input = sc.nextDouble();
			return input;
		}
		else
		{
			sc.next();
			return Double.NaN;
		}
	}
	
	public String usernameInput()
	{
		String input;
		while(!sc.hasNext());
		input = sc.next();
		if(this.validUID(input)) return input;
		return null;
	}
	
	public String passwordInput()
	{
		String input;
		while(!sc.hasNext());
		input = sc.next();
		if(this.validPassword(input)) return input;
		return null;
	}
	
	
	//Test this
	public ConsoleStates balance()
	{
		System.out.println("Bank Account Balance:");
		//call the obligatory callable
		System.out.println(madao.getWorthCall(workingBankAccount.getAcctNumber()));

		if (workingBankAccount.getAcctType().equalsIgnoreCase("JOINT")) return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
		return ConsoleStates.INTERACTING_BANK_ACCOUNT;
	}
	
	public ConsoleStates createBankAccountAction(int input)
	{
		String type = null;
		
		switch (input) {
		case 1:
			System.out.println("Creating checking account.");
			type = "CHECKING";
			break;
		case 2:
			System.out.println("Creating savings account.");
			type = "SAVINGS";
			break;
		case 3:
			System.out.println("Creating joint account.");
			type = "JOINT";
			break;
		case 4:
			System.out.println("Cancelling.");
			return ConsoleStates.LOGGED_IN;
		default:
			System.out.println("Invalid Input.");
			return ConsoleStates.LOGGED_IN;
		}
		createBankAccountInternals(type);
		return ConsoleStates.LOGGED_IN;
	}
	
	//test this
	public boolean createBankAccountInternals(String type)
	{
		
		MoneyAccount m = createUniqueMoneyAccount(type);
		if (this.madao.createMoneyAccount(m) == 0) 
		{
			System.out.println("Account creation failure. Going back.");
			return false;
		}
		aldao.createAccountLinkage(new AccountLinkage(1, workingUserAccount.getUserID(), m.getAcctNumber()));
		System.out.println("Account creation successful.");
		return true;
	}
	
	//test this
	public ConsoleStates withdraw(double input)
	{
		if (input == Double.NaN)
		{
			System.out.println("Invalid Input.");
			return ConsoleStates.INTERACTING_BANK_ACCOUNT;
		}
		double total = madao.getWorthCall(workingBankAccount.getAcctNumber());
		
		if (total-input < 0) System.out.println("Attempting to withdraw too much. Withdrawal failed.");
		workingBankAccount.setWorth(total-input);
		
		int success = madao.updateMoneyAccount(workingBankAccount);
		if (success == 1) System.out.println("Withdraw successful.");
		else System.out.println("Withdrawal failed. Contact an operator to find out what happened.");
		
		if (workingBankAccount.getAcctType().equalsIgnoreCase("JOINT")) return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
		return ConsoleStates.INTERACTING_BANK_ACCOUNT;
	}
	
	//testable
	public ConsoleStates deposit(double input)
	{
		if (input == Double.NaN)
		{
			System.out.println("Invalid Input.");
			return ConsoleStates.INTERACTING_BANK_ACCOUNT;
		}
		double total = madao.getWorthCall(workingBankAccount.getAcctNumber());
		
		workingBankAccount.setWorth(total+input);
		int success = madao.updateMoneyAccount(workingBankAccount);
		if (success == 1) System.out.println("Deposit successful.");
		else System.out.println("Deposit failed. Contact an operator to find out what happened.");
		
		if (workingBankAccount.getAcctType().equalsIgnoreCase("JOINT")) return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
		return ConsoleStates.INTERACTING_BANK_ACCOUNT;
	}
	
	//testable
	public ConsoleStates jointLink(String uname)
	{
		if (uname == null)
		{
			System.out.println("Invalid Username.");
			return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
		}
		UserAccount us = uadao.getUserAccountByName(uname);
		if (us == null)
		{
			System.out.println("No such user.");
			return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
		}
		AccountLinkage a = new AccountLinkage(1, us.getUserID(), workingBankAccount.getAcctNumber());
		int success = aldao.createAccountLinkage(a);
		if (success == 1) 
		{
			System.out.println("Link successfully created.");
			return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
		}
		else 
		{
			System.out.println("Link was unable to be created.");
			return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
		}
	}
	
	//Testable
	public ConsoleStates jBankAccountAction(int input)
	{
		switch (input) {
		case 1:
			return ConsoleStates.BANK_ACCOUNT_BALANCE;
		case 2:
			return ConsoleStates.BANK_ACCOUNT_WITHDRAWING;
		case 3:
			return ConsoleStates.BANK_ACCOUNT_DEPOSITING;
		case 4:
			return ConsoleStates.JOINT_LINKING;
		case 5:
			return ConsoleStates.LOGGED_IN;
		default:
			System.out.println("Invalid Input.");
		}
		return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
	}
	
	//Testable
	public ConsoleStates bankAccountAction(int input)
	{
		switch(input)
		{
		case 1:
			return ConsoleStates.BANK_ACCOUNT_BALANCE;
		case 2:
			return ConsoleStates.BANK_ACCOUNT_WITHDRAWING;
		case 3:
			return ConsoleStates.BANK_ACCOUNT_DEPOSITING;
		case 4:
			return ConsoleStates.LOGGED_IN;
		default:
			System.out.println("Invalid Input.");
		}
		return ConsoleStates.INTERACTING_BANK_ACCOUNT;
	}
	
	public void loggedInOutput()
	{
		List<AccountLinkage> al = aldao.getAccountLinkagesByUser(this.workingUserAccount.getUserID());
		System.out.println("Connected Accounts:");
		for (AccountLinkage a : al)
		{
			System.out.println(a.getMoneyAccountID());
		}
		
		System.out.println("");
		System.out.println("Other Options:");
		System.out.println("(NEW) New Account");
		System.out.println("(EXIT) Log Out");
	}
	
	public ConsoleStates loggedInAction(int input)
	{
		List<AccountLinkage> al = aldao.getAccountLinkagesByUser(this.workingUserAccount.getUserID());

		switch(input)
		{
		case -1:
			return ConsoleStates.CREATING_BANK_ACCOUNT;
		case -2:
			return ConsoleStates.LOGGED_OUT;
		default:
			for(AccountLinkage a : al)
			{
				if (a.getMoneyAccountID() == input)
				{
					workingBankAccount = madao.getMoneyAccountById(input);
					
					System.out.println("Account Selected");
					if(workingBankAccount.getAcctType().toUpperCase().equals("JOINT"))
					{
						return ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT;
					}
					return ConsoleStates.INTERACTING_BANK_ACCOUNT;
				}
			}
			System.out.println("Invalid Bank Account. ");
			workingBankAccount = null;
		}
			
		return ConsoleStates.LOGGED_IN;
	}
	
	public ConsoleStates loggedOutAction(int input)
	{
		switch(input)
		{
		case 1:
			return ConsoleStates.LOGGING_IN;
		case 2:
			return ConsoleStates.CREATING_USER_ACCOUNT_NAME;
		default:
			System.out.println("Invalid Input.");
			return ConsoleStates.LOGGED_OUT;
		}
	}
	
	public ConsoleStates logInAction(String username)
	{
		UserAccount u;
		if (username == null)
		{
			System.out.println("Invalid Username. Usernames must be alphanumeric.");
			attempts ++;
			return ConsoleStates.LOGGING_IN;
		}
		else
		{
			u = uadao.getUserAccountByName(username);
			if (u == null)
			{
				System.out.println("No such user exists.");
				attempts ++;
				return ConsoleStates.LOGGING_IN;
			}
			attempts = 0;
			internalNameState = username;
			return ConsoleStates.LOGGING_IN_PASS;
		}
	}

	public ConsoleStates logInPassAction(String password, String username)
	{
		UserAccount u;
		if(password == null)
		{
			System.out.println("Invalid Password, must follow the rules.");
			attempts ++;
			return ConsoleStates.LOGGING_IN_PASS;
		}
		else
		{
			u = uadao.getUserAccountByName(username);
			
			String hashPassword = UserAccount.hashStr(password);
			if (hashPassword.equals(u.getHashPass().toUpperCase())) 
			{
				System.out.println("Login Successful. Welcome, " + username + ".");
				internalNameState = null;
				attempts = 0;
				workingUserAccount = u;
				return ConsoleStates.LOGGED_IN;
			}
			System.out.println("Incorrect Password.");
			attempts ++;
			return ConsoleStates.LOGGING_IN_PASS;
		}
	}
	
	public String createAccountNamephase(String input)
	{
		if (input == null)
		{
			System.out.println("Invalid Username.");
		}
		UserAccount u = null;
		u = uadao.getUserAccountByName(input);
		if (u != null) 
		{
			System.out.println("Username already used.");
			return null;
		}
		return input;
	}
	
	public ConsoleStates createAccountPassphase(String input, String username)
	{
		if (input != null)
		{
			System.out.println("Password valid, Creating Account.");
		}
		else
		{
			System.out.println("Invalid Password.");
			return ConsoleStates.CREATING_USER_ACCOUNT_PASS;
		}
		
		createUser(username, input);
		
		return ConsoleStates.LOGGED_OUT;
	}
	
	public boolean createUser(String username, String password)
	{
		UserAccount u =  new UserAccount(1, username, UserAccount.hashStr(password));
		int success = uadao.createUserAccount(u);
		if (success == 1)
		{
			System.out.println("Account successfully created. Returning to main menu.");
			return true;
		}
		
		System.out.println("Account creation failed. Please contact an operator to figure out what happened. Returning to main menu.");
		return false;
	
	}
		
	
	public boolean validUID(String uid)
	{
		if (uid.length() < 6 || uid.length() >= 32) return false;
		
		char[] charsUID = uid.toLowerCase().toCharArray();
		
		for(int iter = 0; iter< charsUID.length; iter++)
		{
			if ((charsUID[iter] < 'a' || charsUID[iter] > 'z') && (charsUID[iter] < '0' || charsUID[iter] > '9')) return false;
		}
		
		return true;
	}
	
	public boolean validPassword(String pw)
	{
		if (pw.length() < 6 || pw.length() >= 32) return false;
		char[] charsPW = pw.toLowerCase().toCharArray();
		
		for(int iter = 0; iter< charsPW.length; iter++)
		{
			if ((charsPW[iter] < 'a' || charsPW[iter] > 'z') && (charsPW[iter] < '0' || charsPW[iter] > '9') && 
					charsPW[iter] != '!' && charsPW[iter] != '.' && charsPW[iter] != '?' && charsPW[iter] != '_') return false;
		}
		
		return true;
	}
	
	public MoneyAccount createUniqueMoneyAccount(String type)
	{
		MoneyAccount m = null;
		int accNum = 0;
		
		List<MoneyAccount> ma = madao.getMoneyAccounts();
		if (type.equalsIgnoreCase("CHECKING"))
		{
			accNum = 100000001;
			for (MoneyAccount a : ma)
			{
				if (a.getAcctNumber() < 200000000 && a.getAcctNumber() >= accNum) accNum = a.getAcctNumber()+1;
			}
		}
		else if (type.equalsIgnoreCase("SAVINGS"))
		{
			accNum = 200000001;
			for (MoneyAccount a : ma)
			{
				if (a.getAcctNumber() < 300000000 && a.getAcctNumber() >= accNum) accNum = a.getAcctNumber()+1;
			}
		}
		else if (type.equalsIgnoreCase("JOINT"))
		{
			accNum = 300000001;
			for (MoneyAccount a : ma)
			{
				if (a.getAcctNumber() < 400000000 && a.getAcctNumber() >= accNum) accNum = a.getAcctNumber()+1;
			}
		}
		else return null;
		
		m = new MoneyAccount(accNum, type.toUpperCase(), 0.0);
		return m;
	}

}
