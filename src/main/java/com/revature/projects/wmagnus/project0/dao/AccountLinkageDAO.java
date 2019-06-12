package com.revature.projects.wmagnus.project0.dao;

import java.util.List;

import com.revature.projects.wmagnus.project0.models.AccountLinkage;

public interface AccountLinkageDAO {
	public List<AccountLinkage> getAccountLinkages();
	public List<AccountLinkage> getAccountLinkagesByUser(int uid);
	public AccountLinkage getAccountLinkageById(int id);
	public int createAccountLinkage(AccountLinkage a);
	public int updateAccountLinkage(AccountLinkage a);
	public int deleteAccountLinkage(int id);
}
