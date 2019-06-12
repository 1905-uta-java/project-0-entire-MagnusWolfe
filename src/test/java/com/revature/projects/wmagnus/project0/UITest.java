package com.revature.projects.wmagnus.project0;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.*;

import com.revature.projects.project0.ui.UI;
import com.revature.projects.project0.ui.UI.ConsoleStates;
import com.revature.projects.wmagnus.project0.models.MoneyAccount;
import com.revature.projects.wmagnus.project0.models.UserAccount;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UITest {

	UI ui;
	
	public UITest()
	{
		ui = new UI();
	}
	
	@Test
	public void aUniqMoneyAccChecking()
	{
		MoneyAccount m = new MoneyAccount(100000006, "CHECKING", 0.0);
		assertEquals(m, ui.createUniqueMoneyAccount("CHECKING"));
	}
	
	
	@Test
	public void aUniqMoneyAccSavings()
	{
		MoneyAccount m = new MoneyAccount(200000002, "SAVINGS", 0.0);
		assertEquals(m, ui.createUniqueMoneyAccount("SAVINGS"));
	}
	
	@Test
	public void aUniqMoneyAccJoint()
	{
		MoneyAccount m = new MoneyAccount(300000002, "JOINT", 0.0);
		assertEquals(m, ui.createUniqueMoneyAccount("JOINT"));
	}
	
	@Test
	public void validUIDShort()
	{
		assertFalse(ui.validUID("aaaaa"));
	}
	
	@Test
	public void validUIDLong()
	{
		assertFalse(ui.validUID("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validUIDBadChars()
	{
		assertFalse(ui.validUID("abc123,#@"));
	}
	
	@Test
	public void validUIDPasswordCrossover()
	{
		assertFalse(ui.validUID("abc123_!"));
	}
	
	@Test
	public void validUIDPassing()
	{
		assertTrue(ui.validUID("validusername9"));
	}

	@Test
	public void validPasswordShort()
	{
		assertFalse(ui.validPassword("aaaaa"));
	}
	
	@Test
	public void validPasswordLong()
	{
		assertFalse(ui.validPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validPasswordBadChars()
	{
		assertFalse(ui.validPassword("abc123,#@"));
	}
	
	@Test
	public void validPasswordPassing()
	{
		assertTrue(ui.validPassword("abc123_!"));
	}
	
	@Test
	public void createUserFailing()
	{
		assertFalse(ui.createUser("someguy", UserAccount.hashStr("password")));
	}

	@Test
	public void createUserPassing()
	{
		assertTrue(ui.createUser("validtestcase", UserAccount.hashStr("password")));
	}
	
	@Test
	public void createAccountNamephaseDuplicates()
	{
		assertTrue(ui.createAccountNamephase("testguy") == null);
	}
	
	@Test
	public void createAccountNamephaseNulls()
	{
		assertTrue(ui.createAccountNamephase(null) == null);
	}
	
	@Test
	public void createAccountNamephaseValid()
	{
		assertTrue(ui.createAccountNamephase("validtestcase2").intern() == "validtestcase2".intern());
	}
	
	@Test
	public void createAccountPassphaseNulls()
	{
		assertTrue(ui.createAccountPassphase(null, "validtestcase3") == UI.ConsoleStates.CREATING_USER_ACCOUNT_PASS);
	}
	
	@Test
	public void createAccountPassphaseValid()
	{
		assertTrue(ui.createAccountPassphase("perfectlyvalidpass", "validtestcase4") == UI.ConsoleStates.LOGGED_OUT);
		
	}
	
	@Test
	public void logInPassActionNull()
	{
		assertEquals(ui.logInPassAction(null, "username"),ConsoleStates.LOGGING_IN_PASS);
	}
	
	@Test
	public void logInPassActionWrongPass()
	{
		assertEquals(ui.logInPassAction("password", "testguy"),ConsoleStates.LOGGING_IN_PASS);
	}

	@Test
	public void logInPassActionGoodPass()
	{
		assertEquals(ui.logInPassAction("password_01", "testguy"),ConsoleStates.LOGGED_IN);
	}
	
	@Test
	public void logInActionNull()
	{
		assertEquals(ui.logInAction(null),ConsoleStates.LOGGING_IN);
	}
	
	@Test
	public void logInActionNoSuchUser()
	{
		assertEquals(ui.logInAction("nosuchuser"),ConsoleStates.LOGGING_IN);
	}
	
	@Test
	public void logInActionGood()
	{
		assertEquals(ui.logInAction("testguy"),ConsoleStates.LOGGING_IN_PASS);
	}
	
	@Test
	public void loggedOutActionSel1()
	{
		assertEquals(ui.loggedOutAction(1), ConsoleStates.LOGGING_IN);
	}
	
	@Test
	public void loggedOutActionSel2()
	{
		assertEquals(ui.loggedOutAction(2), ConsoleStates.CREATING_USER_ACCOUNT_NAME);
	}
	
	@Test
	public void loggedOutActionInvalid()
	{
		assertEquals(ui.loggedOutAction(0), ConsoleStates.LOGGED_OUT);
	}
	
	@Test
	public void loggedInActionExit()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.loggedInAction(-2), ConsoleStates.LOGGED_OUT);
	}
	
	@Test
	public void loggedInActionNewBankAcc()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.loggedInAction(-1), ConsoleStates.CREATING_BANK_ACCOUNT);
	}
	
	@Test
	public void loggedInActionBadSelect()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.loggedInAction(0), ConsoleStates.LOGGED_IN);
	}
	
	@Test
	public void loggedInActionGoodSelect()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		
		assertEquals(ui.loggedInAction(100000001), ConsoleStates.INTERACTING_BANK_ACCOUNT);
	}
	
	@Test
	public void loggedInActionJointGoodSelect()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		
		assertEquals(ui.loggedInAction(300000001), ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT);
	}
	
	@Test
	public void bankAccountActionBal()
	{
		assertEquals(ui.bankAccountAction(1), ConsoleStates.BANK_ACCOUNT_BALANCE);
	}
	
	@Test
	public void bankAccountActionWithdraw()
	{
		assertEquals(ui.bankAccountAction(2), ConsoleStates.BANK_ACCOUNT_WITHDRAWING);
	}
	
	@Test
	public void bankAccountActionDeposit()
	{
		assertEquals(ui.bankAccountAction(3), ConsoleStates.BANK_ACCOUNT_DEPOSITING);
	}
	
	@Test
	public void bankAccountActionBack()
	{
		assertEquals(ui.bankAccountAction(4), ConsoleStates.LOGGED_IN);
	}
	
	@Test
	public void bankAccountActionInvalid()
	{
		assertEquals(ui.bankAccountAction(0), ConsoleStates.INTERACTING_BANK_ACCOUNT);
	}
	
	@Test
	public void jBankAccountActionBal()
	{
		assertEquals(ui.jBankAccountAction(1), ConsoleStates.BANK_ACCOUNT_BALANCE);
	}
	
	@Test
	public void jBankAccountActionWithdraw()
	{
		assertEquals(ui.jBankAccountAction(2), ConsoleStates.BANK_ACCOUNT_WITHDRAWING);
	}
	
	@Test
	public void jBankAccountActionDeposit()
	{
		assertEquals(ui.jBankAccountAction(3), ConsoleStates.BANK_ACCOUNT_DEPOSITING);
	}
	
	@Test
	public void jBankAccountActionLink()
	{
		assertEquals(ui.jBankAccountAction(4), ConsoleStates.JOINT_LINKING);
	}
	
	@Test
	public void jBankAccountActionBack()
	{
		assertEquals(ui.jBankAccountAction(5), ConsoleStates.LOGGED_IN);
	}
	
	@Test
	public void jBankAccountActionInvalid()
	{
		assertEquals(ui.jBankAccountAction(0), ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT);
	}

	@Test
	public void jointLinkNull()
	{
		assertEquals(ui.jointLink(null), ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT);
	}
	
	@Test
	public void jointLinkGood()
	{
		ui.setWorkingBA( new MoneyAccount(300000001, "JOINT", 20000.0) );
		
		assertEquals(ui.jointLink("someguy"), ConsoleStates.INTERACTING_JOINT_BANK_ACCOUNT);
	}

	@Test
	public void depositNAN()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		ui.setWorkingBA( new MoneyAccount(100000001, "CHECKING", 4.0) );
		
		assertEquals(ui.deposit(Double.NaN), ConsoleStates.INTERACTING_BANK_ACCOUNT);
	}
	
	@Test
	public void depositValid()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		ui.setWorkingBA( new MoneyAccount(100000001, "CHECKING", 4.0) );
		assertEquals(ui.deposit(1.0), ConsoleStates.INTERACTING_BANK_ACCOUNT);
	}
	
	@Test
	public void withdrawNAN()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		ui.setWorkingBA( new MoneyAccount(100000001, "CHECKING", 4.0) );
		
		assertEquals(ui.withdraw(Double.NaN), ConsoleStates.INTERACTING_BANK_ACCOUNT);
		
	}
	
	@Test
	public void withdrawValid()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		ui.setWorkingBA( new MoneyAccount(100000001, "CHECKING", 4.0) );
		assertEquals(ui.withdraw(1.0), ConsoleStates.INTERACTING_BANK_ACCOUNT);
	}
	
	@Test
	public void withdrawTooBig()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		ui.setWorkingBA( new MoneyAccount(100000001, "CHECKING", 4.0) );
		assertEquals(ui.withdraw(400000.0), ConsoleStates.INTERACTING_BANK_ACCOUNT);
	}
	
	@Test
	public void createBankAcctInternalsTest()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertTrue(ui.createBankAccountInternals("SAVINGS"));
	}
	
	@Test
	public void createBankAcctType1()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.createBankAccountAction(1), UI.ConsoleStates.LOGGED_IN);
	}
	
	@Test
	public void createBankAcctType2()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.createBankAccountAction(2), UI.ConsoleStates.LOGGED_IN);	
	}
	
	@Test
	public void createBankAcctType3()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.createBankAccountAction(3), UI.ConsoleStates.LOGGED_IN);
	}
	
	@Test
	public void createBankAcctType4()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.createBankAccountAction(4), UI.ConsoleStates.LOGGED_IN);
	}
	
	@Test
	public void createBankAcctInvalid()
	{
		UserAccount ua = new UserAccount(1, "testguy", UserAccount.hashStr("password_01"));
		
		ui.setWorkingUA(ua);
		assertEquals(ui.createBankAccountAction(0), UI.ConsoleStates.LOGGED_IN);
	}
}
