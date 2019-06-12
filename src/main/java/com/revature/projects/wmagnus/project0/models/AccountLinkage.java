package com.revature.projects.wmagnus.project0.models;

import java.io.Serializable;

public class AccountLinkage implements Serializable{

	private static final long serialVersionUID = 8685943810586160964L;

	private int userID;
	private int moneyAccountID;
	private int linkageID;
	
	public AccountLinkage()
	{
		super();
	}
	
	public AccountLinkage(int linkageID, int userID, int moneyAccountID)
	{
		this.setLinkageID(linkageID);
		this.setMoneyAccountID(moneyAccountID);
		this.setUserID(userID);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getMoneyAccountID() {
		return moneyAccountID;
	}

	public void setMoneyAccountID(int moneyAccountID) {
		this.moneyAccountID = moneyAccountID;
	}

	public int getLinkageID() {
		return linkageID;
	}

	public void setLinkageID(int linkageID) {
		this.linkageID = linkageID;
	}
	
	@Override
	public String toString()
	{
		return "linkid=" + linkageID + ",uid=" + userID + ",moneyAccID=" + moneyAccountID;		
	}
	
	@Override
	public boolean equals(Object a)
	{
		if (this == a) return true;
		if (a == null) return false;
		if (getClass() != a.getClass()) return false;
		AccountLinkage ao = (AccountLinkage) a;
		if(ao.linkageID != this.linkageID) return false;
		if(ao.moneyAccountID != this.moneyAccountID) return false;
		if(ao.userID != this.userID) return false;
		return true;
		
	}
	
}
