package com.revature.projects.wmagnus.project0.dao;

import java.util.List;

import com.revature.projects.wmagnus.project0.models.UserAccount;

public interface UserAccountDAO {
	public List<UserAccount> getUserAccounts();
	public UserAccount getUserAccountById(int id);
	public UserAccount getUserAccountByName(String uname);
	public int createUserAccount(UserAccount u);
	public int updateUserAccount(UserAccount u);
	public int deleteUserAccount(int id);
}
