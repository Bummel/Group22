package Test.model.financial;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gmb.model.financial.ExternalTransaction;
import gmb.model.financial.InternalTransaction;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.RealAccountData;
import gmb.model.financial.Transaction;
import gmb.model.financial.Winnings;
import gmb.model.request.RealAccountDataUpdateRequest;
import gmb.model.user.Customer;
import gmb.model.user.MemberData;

public class LotteryBankAccountTest {
	
	private LotteryBankAccount lb;
	private Customer owner;
	private BigDecimal credit;	
	private BigDecimal b=new BigDecimal(15);
	private RealAccountData realAccountData;
	private LinkedList<InternalTransaction> internalTransactions;
	private LinkedList<ExternalTransaction> externalTransactions;
	private LinkedList<Winnings> winnings;
	private LinkedList<RealAccountDataUpdateRequest> realAccounDataUpdateRequests;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Customer owner=new Customer("testname","test",new MemberData(), new LotteryBankAccount());
		BigDecimal credit = new BigDecimal(2500);
		RealAccountData realAccountData = new RealAccountData("test","test");
		LinkedList<InternalTransaction> internalTransactions = new LinkedList<InternalTransaction>();
		LinkedList<ExternalTransaction> externalTransactions = new LinkedList<ExternalTransaction>();
		LinkedList<Winnings> winnings = new LinkedList<Winnings>();
		LinkedList<RealAccountDataUpdateRequest> realAccounDataUpdateRequests = new LinkedList<RealAccountDataUpdateRequest>();
		LotteryBankAccount lb=new LotteryBankAccount(owner,realAccountData);
	}

	@Test
	public void testUpdateCredit() {
		Transaction trans=new Transaction(owner,b,new Date());
		lb.updateCredit(trans);
		BigDecimal c=lb.getCredit();
		credit.add(b);
		assertEquals(credit,c);
		
	}

	@Test
	public void testSendDataUpdateRequest() {
		lb.sendDataUpdateRequest("test",realAccountData);
		LinkedList<RealAccountDataUpdateRequest> rad=lb.getRealAccountDataUpdateRequest();
		boolean test=rad.contains(realAccountData);
		assertEquals(true,test);
	}

	@Test
	public void testGetCredit() {
		BigDecimal c=lb.getCredit();
		assertEquals(credit,c);
	}

	@Test
	public void testGetOwner() {
		Customer o=new Customer("testname","test",new MemberData(), new LotteryBankAccount());
		assertEquals(o,owner);
	}

	@Test
	public void testGetRealAccountData() {
		RealAccountData r=lb.getRealAccountData();
		assertEquals(realAccountData,r);
	}

	@Test
	public void testGetInternalTransactions() {
		LinkedList<InternalTransaction> c=lb.getInternalTransactions();
		assertEquals(c,internalTransactions);
	}

	@Test
	public void testGetExternalTransactions() {
		LinkedList<ExternalTransaction> c=lb.getExternalTransactions();
		assertEquals(c,externalTransactions);
	}

	@Test
	public void testGetWinnings() {
		LinkedList<Winnings> w= lb.getWinnings();
		assertEquals(winnings,w);
	}

	@Test
	public void testGetRealAccountDataUpdateRequest() {
		LinkedList<RealAccountDataUpdateRequest> c=lb.getRealAccountDataUpdateRequest();
		assertEquals(c,realAccounDataUpdateRequests);
	}
	@AfterClass
	public static void setUpAfterClass() throws Exception {
		BigDecimal credit=new BigDecimal(0);
	}

}
