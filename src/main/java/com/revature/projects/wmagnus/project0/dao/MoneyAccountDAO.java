package com.revature.projects.wmagnus.project0.dao;

import java.util.List;

import com.revature.projects.wmagnus.project0.models.MoneyAccount;

public interface MoneyAccountDAO {
	public List<MoneyAccount> getMoneyAccounts();
	public MoneyAccount getMoneyAccountById(int id);
	public int createMoneyAccount(MoneyAccount m);
	public int updateMoneyAccount(MoneyAccount m);
	public int deleteMoneyAccount(int id);
	public double getWorthCall(int id);
}
