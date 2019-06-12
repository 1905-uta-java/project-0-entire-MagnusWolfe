package com.revature.projects.wmagnus.project0.models;

import java.io.Serializable;

public class MoneyAccount implements Serializable{

	private static final long serialVersionUID = -2018009673808893322L;

	public static int ACCT_TYPE_MAX_LEN = 16;
	
	private int acctNumber;
	private String acctType;
	private double worth;
	
	public MoneyAccount()
	{
		super();
	}
	
	public MoneyAccount(int acctNumber, String acctType, double worth)
	{
		this.setAcctNumber(acctNumber);
		this.setAcctType(acctType);
		this.setWorth(worth);
	}

	public int getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(int acctNumber) {
		this.acctNumber = acctNumber;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public double getWorth() {
		return worth;
	}

	public void setWorth(double worth) {
		if (worth >= 0.0) this.worth = worth;
	}
	
	@Override
	public String toString()
	{
		return "accid=" + this.acctNumber + ",type=" + this.acctType + ",worth=" + this.worth;		
	}
	
	@Override
	public boolean equals(Object a)
	{
		if (this == a) return true;
		if (a == null) return false;
		if (getClass() != a.getClass()) return false;
		MoneyAccount ao = (MoneyAccount) a;
		if(ao.acctNumber != this.acctNumber) return false;
		if(! ao.acctType.equals(this.acctType)) return false;
		if(ao.worth != this.worth) return false;
		return true;
		
	}
}
