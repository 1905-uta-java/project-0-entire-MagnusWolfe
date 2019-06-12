package com.revature.projects.wmagnus.project0.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.projects.wmagnus.project0.models.UserAccount;
import com.revature.projects.wmagnus.project0.util.ConnectionUtility;

public class UserAccountDAOImpl implements UserAccountDAO {

	@Override
	public List<UserAccount> getUserAccounts() {
		List<UserAccount> users = new ArrayList<UserAccount>();
		
		String sql_cmd = "SELECT * FROM USER_ACCOUNTS";
		
		try (Connection connection = ConnectionUtility.getConectionFromFile();
				Statement statement = connection.createStatement();
				ResultSet results = statement.executeQuery(sql_cmd);)
		{
			while (results.next()) {
				int uid = results.getInt("USER_ID");
				String uname = results.getString("USER_NAME");
				String hashpass = results.getString("HASHPASS");
				users.add(new UserAccount(uid,uname,hashpass));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	@Override
	public UserAccount getUserAccountById(int id) {
		String sql_cmd = "SELECT * FROM USER_ACCOUNTS WHERE USER_ID = ?";
		UserAccount u = null;
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd)) {
			prep.setInt(1, id);
			ResultSet results = prep.executeQuery();
			
			while(results.next()) {
				int uid = results.getInt("USER_ID");
				String uname = results.getString("USER_NAME");
				String hashpass = results.getString("HASHPASS");
				u = new UserAccount(uid,uname,hashpass);
				}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return u;
	}
	
	@Override
	public UserAccount getUserAccountByName(String uname)
	{
		String sql_cmd = "SELECT * FROM USER_ACCOUNTS WHERE USER_NAME = ?";
		UserAccount u = null;
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd)) {
			prep.setString(1, uname);
			ResultSet results = prep.executeQuery();
			
			while(results.next()) {
				int uid = results.getInt("USER_ID");
				String name = results.getString("USER_NAME");
				String hashpass = results.getString("HASHPASS");
				u = new UserAccount(uid,name,hashpass);
				}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return u;

		
	}

	@Override
	public int createUserAccount(UserAccount u) {
		String sql_cmd = "INSERT INTO USER_ACCOUNTS (USER_ID, USER_NAME, HASHPASS) VALUES(?,?,?)";
		int createdRows = 0;
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd);)
		{
			prep.setInt(1, u.getUserID());
			prep.setString(2, u.getUserName());
			prep.setString(3, u.getHashPass());
			createdRows = prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return createdRows;

	}

	@Override
	public int updateUserAccount(UserAccount u) {
		int updRows = 0;
		String sql_cmd = "UPDATE USER_ACCOUNTS SET USER_NAME = ?, HASHPASS = ? WHERE USER_ID = ?";
		
		try(Connection connection = ConnectionUtility.getConectionFromFile();
				PreparedStatement prep = connection.prepareStatement(sql_cmd))
		{
			prep.setString(1, u.getUserName());
			prep.setString(2, u.getHashPass());
			prep.setInt(3, u.getUserID());
			
			updRows = prep.executeUpdate();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updRows;
	}

	@Override
	public int deleteUserAccount(int id) {
		int delRows = 0;
		String sql_cmd = "DELETE FROM USER_ACCOUNTS WHERE USER_ID = ?";
		
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
