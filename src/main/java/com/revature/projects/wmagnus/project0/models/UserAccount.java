package com.revature.projects.wmagnus.project0.models;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserAccount implements Serializable{

	private static final long serialVersionUID = 8960528710140735787L;
	public static int USER_NAME_MAX_LEN = 32;
	public static int HASH_PASS_EXACT_LEN = 64;
	
	private int userID;
	private String userName;
	private String hashPass;
	
	public UserAccount()
	{
		super();
	}
	
	public UserAccount(int userID, String userName, String hashPass)
	{
		this.setUserID(userID);
		this.setUserName(userName);
		this.setHashPass(hashPass);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHashPass() {
		return hashPass;
	}

	public void setHashPass(String hashPass) {
		if (hashPass.length() != UserAccount.HASH_PASS_EXACT_LEN) return;
			
		this.hashPass = hashPass.toUpperCase();
	}
	
	public void setUnhashedPass(String unhashPass) {
		this.hashPass = hashStr(unhashPass);
	}
	
	public static String hashStr(String unhashedStr) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedUTF8Pass = digest.digest(unhashedStr.getBytes(StandardCharsets.UTF_8));
			
			char[] hashedHexPass = new char[HASH_PASS_EXACT_LEN];
			for (int iter = 0; iter < hashedUTF8Pass.length; iter++)
			{
				char cur_low = (char) (hashedUTF8Pass[iter] & 0x0F);
				char cur_hi = (char) ((hashedUTF8Pass[iter] & 0xF0) >> 4);
				
				if (cur_hi >= 0x0A) cur_hi = (char) (cur_hi - 0x0A + 'A');
				else cur_hi += '0';
				if (cur_low >= 0x0A) cur_low = (char) (cur_low - 0x0A + 'A');
				else cur_low += '0';
				
				hashedHexPass[2*iter] = cur_hi;
				hashedHexPass[2*iter+1] = cur_low;
			}
			return new String(hashedHexPass).intern();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public String toString()
	{
		return "userid=" + userID + ",uname=" + userName + ",hashpass=" + hashPass;		
	}
	
	@Override
	public boolean equals(Object a)
	{
		if (this == a) return true;
		if (a == null) return false;
		if (getClass() != a.getClass()) return false;
		UserAccount ao = (UserAccount) a;
		if(ao.userID != this.userID) return false;
		if(!ao.userName.equals(userName)) return false;
		if(!ao.hashPass.equals(hashPass)) return false;
		return true;
		
	}
}
