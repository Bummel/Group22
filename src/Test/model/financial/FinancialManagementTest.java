/**
 * 
 */
package Test.model.financial;

import static org.junit.Assert.*;
import gmb.model.financial.ExternalTransaction;
import gmb.model.financial.InternalTransaction;
import gmb.model.financial.ReceiptsDistribution;
import gmb.model.financial.TipTicketPrices;
import gmb.model.request.RealAccountDataUpdateRequest;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;
import gmb.model.financial.FinancialManagement;
/**
 * @author Islanzadia
 *
 */
public class FinancialManagementTest {

	BigDecimal credit;
	TipTicketPrices tipTicketPrices;
	ReceiptsDistribution receiptsDistribution;
	LinkedList<InternalTransaction> internalTransactions;
	LinkedList<ExternalTransaction> externalTransactions;
	LinkedList<RealAccountDataUpdateRequest> realAccounDataUpdateRequests;
	FinancialManagement fm;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BigDecimal credit=new BigDecimal(2500000.5);
		TipTicketPrices tipTicketPrices= new TipTicketPrices(new BigDecimal(3.0),new BigDecimal(3.0),new BigDecimal(3.0),new BigDecimal(3.0),new BigDecimal(3.0),new BigDecimal(3.0),new BigDecimal(3.0),new BigDecimal(3.0),new BigDecimal(3.0));
		ReceiptsDistribution receiptsDistribution= new ReceiptsDistribution(3,3,3,3);
		LinkedList<InternalTransaction> internalTransactions= new LinkedList<InternalTransaction>();
		LinkedList<ExternalTransaction> externalTransactions= new LinkedList<ExternalTransaction>();
		LinkedList<RealAccountDataUpdateRequest> realAccounDataUpdateRequests=new LinkedList<RealAccountDataUpdateRequest>();
		FinancialManagement fm=new FinancialManagement(tipTicketPrices,receiptsDistribution);
	}

	/**
	 * Test method for {@link gmb.model.financial.FinancialManagement#getCredit()}.
	 */
	@Test
	public void testGetCredit() {
		
		fm.setCredit(credit);
		BigDecimal c=fm.getCredit();
		assertEquals(c,credit);
	}

	/**
	 * Test method for {@link gmb.model.financial.FinancialManagement#getTipTicketPrices()}.
	 */
	@Test
	public void testGetTipTicketPrices() {
		fm.setTipTicketPrices(tipTicketPrices);
		TipTicketPrices c=fm.getTipTicketPrices();
		assertEquals(c,tipTicketPrices);
	}

	/**
	 * Test method for {@link gmb.model.financial.FinancialManagement#getReceiptsDistribution()}.
	 */
	@Test
	public void testGetReceiptsDistribution() {
		ReceiptsDistribution c=fm.getReceiptsDistribution();
		assertEquals(c,receiptsDistribution);
	}

	/**
	 * Test method for {@link gmb.model.financial.FinancialManagement#getExternalTransactions()}.
	 */
	@Test
	public void testGetExternalTransactions() {
		LinkedList<ExternalTransaction> c=fm.getExternalTransactions();
		assertEquals(c,externalTransactions);
		
	}

	/**
	 * Test method for {@link gmb.model.financial.FinancialManagement#getInternalTransactions()}.
	 */
	@Test
	public void testGetInternalTransactions() {
		LinkedList<InternalTransaction> c=fm.getInternalTransactions();
		assertEquals(c,internalTransactions);
	}
	

	/**
	 * Test method for {@link gmb.model.financial.FinancialManagement#getRealAccounDataUpdateRequests()}.
	 */
	@Test
	public void testGetRealAccountDataUpdateRequests() {
		LinkedList<RealAccountDataUpdateRequest> c=fm.getRealAccountDataUpdateRequests();
		assertEquals(c,realAccounDataUpdateRequests);
	}

}
