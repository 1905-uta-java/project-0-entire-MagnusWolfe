package com.revature.projects.wmagnus.project0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.projects.wmagnus.project0.models.AccountLinkage;
import com.revature.projects.wmagnus.project0.util.ConnectionUtility;

public class AccountLinkageDAOImpl implements AccountLinkageDAO {

	@Override
	public List<AccountLinkage> getAccountLinkages() {
		List<AccountLinkage> links = new ArrayList<AccountLinkage>();
		
		String sql_cmd = "SELECT * FROM ACCOUNT_LINKAGES";
		
		try (Connection connection = ConnectionUtility.getConectionFromFile();
				Statement statement = connection.createStatement();
				ResultSet results = statement.executeQuery(sql_cmd);)
		{
			while (results.next()) {
				int uid = results.getInt("USER_ID");
				int acc = results.getInt("ACCT_NUMBER");
				int lnk = results.getInt("LINKAGE_ID");
				links.add(new AccountLinkage(lnk,uid,acc));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return links;
	}

	@Override
	public List<AccountLinkage> getAccountLinkagesByUser(int uid)
	{
		List<AccountLinkage> links = new ArrayList<AccountLinkage>();
		
		String sql_cmd = "SELECT * FROM ACCOUNT_LINKAGES WHERE USER_ID = ?";
		
		try (Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd);)
		{
			prep.setInt(1, uid);
			ResultSet results = prep.executeQuery();
			while (results.next()) {
				int nuid = results.getInt("USER_ID");
				int acc = results.getInt("ACCT_NUMBER");
				int lnk = results.getInt("LINKAGE_ID");
				links.add(new AccountLinkage(lnk,nuid,acc));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return links;
		
	}
	
	@Override
	public AccountLinkage getAccountLinkageById(int id) {
		String sql_cmd = "SELECT * FROM ACCOUNT_LINKAGES WHERE LINKAGE_ID = ?";
		AccountLinkage a = null;
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd)) {
			prep.setInt(1, id);
			ResultSet results = prep.executeQuery();
			
			while(results.next()) {
				int uid = results.getInt("USER_ID");
				int acc = results.getInt("ACCT_NUMBER");
				int lnk = results.getInt("LINKAGE_ID");
				a = new AccountLinkage(lnk, uid, acc);
			}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

	@Override
	public int createAccountLinkage(AccountLinkage a) {
		//Linkage ID selfiterates. Can't force it, but we'll pretend.
		String sql_cmd = "INSERT INTO ACCOUNT_LINKAGES(USER_ID, ACCT_NUMBER, LINKAGE_ID) VALUES(?,?,?)";
		int createdRows = 0;
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd);)
		{
			prep.setInt(1, a.getUserID());
			prep.setInt(2, a.getMoneyAccountID());
			prep.setInt(3, a.getLinkageID());
			createdRows = prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return createdRows;
	}

	@Override
	public int updateAccountLinkage(AccountLinkage a) {
		int updRows = 0;
		String sql_cmd = "UPDATE ACCOUNT_LINKAGES SET USER_ID = ?, ACCT_NUMBER = ? WHERE LINKAGE_ID = ?";
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd))
		{
			prep.setInt(1, a.getUserID());
			prep.setInt(2, a.getMoneyAccountID());
			prep.setInt(3, a.getLinkageID());
			
			updRows = prep.executeUpdate();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updRows;
	}

	@Override
	public int deleteAccountLinkage(int id) {
		int delRows = 0;
		String sql_cmd = "DELETE FROM ACCOUNT_LINKAGES WHERE LINKAGE_ID = ?";
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd))
		{
			prep.setInt(1,id);
			delRows = prep.executeUpdate();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return delRows;
	}

}
