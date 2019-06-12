package com.revature.projects.wmagnus.project0.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.projects.wmagnus.project0.models.MoneyAccount;
import com.revature.projects.wmagnus.project0.util.ConnectionUtility;

public class MoneyAccountDAOImpl implements MoneyAccountDAO {

	@Override
	public List<MoneyAccount> getMoneyAccounts() {
		List<MoneyAccount> money = new ArrayList<MoneyAccount>();
		
		String sql_cmd = "SELECT * FROM MONEY_ACCOUNTS";
		
		try (Connection connection = ConnectionUtility.getConectionFromFile();
				Statement statement = connection.createStatement();
				ResultSet results = statement.executeQuery(sql_cmd);)
		{
			while (results.next()) {
				int aid = results.getInt("ACCT_NUMBER");
				String type = results.getString("ACCT_TYPE");
				Double worth = results.getDouble("WORTH");
				money.add(new MoneyAccount(aid,type,worth));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return money;
	}

	@Override
	public MoneyAccount getMoneyAccountById(int id) {
		String sql_cmd = "SELECT * FROM MONEY_ACCOUNTS WHERE ACCT_NUMBER = ?";
		MoneyAccount m = null;
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd)) {
			prep.setInt(1, id);
			ResultSet results = prep.executeQuery();
			
			while(results.next()) {
				int aid = results.getInt("ACCT_NUMBER");
				String type = results.getString("ACCT_TYPE");
				Double worth = results.getDouble("WORTH");
				m = new MoneyAccount(aid,type,worth);
				}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;
	}

	
	@Override
	public int createMoneyAccount(MoneyAccount m) {
		String sql_cmd = "INSERT INTO MONEY_ACCOUNTS(ACCT_NUMBER, ACCT_TYPE, WORTH) VALUES(?,?,?)";
		int createdRows = 0;
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd);)
		{
			prep.setInt(1, m.getAcctNumber());
			prep.setString(2, m.getAcctType());
			prep.setDouble(3, m.getWorth());
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
	public int updateMoneyAccount(MoneyAccount m) {
		int updRows = 0;
		String sql_cmd = "UPDATE MONEY_ACCOUNTS SET ACCT_TYPE = ?, WORTH = ? WHERE ACCT_NUMBER = ?";
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd))
		{
			prep.setString(1, m.getAcctType());
			prep.setDouble(2, m.getWorth());
			prep.setInt(3, m.getAcctNumber());
			
			updRows = prep.executeUpdate();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updRows;
	}

	@Override
	public int deleteMoneyAccount(int id) {
		int delRows = 0;
		String sql_cmd = "DELETE FROM MONEY_ACCOUNTS WHERE ACCT_NUMBER = ?";
		
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

	@Override
	public double getWorthCall(int id) {
		String sql_cmd = "{call GET_WORTH(?, ?)}";
		Double ret = 0.0;
		
		try (Connection connection = ConnectionUtility.getConectionFromFile();
				CallableStatement call = connection.prepareCall(sql_cmd))
		{
			call.setInt(1, id);
			call.registerOutParameter(2, java.sql.Types.DOUBLE);
			call.execute();
			ret = call.getDouble(2);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	

}
