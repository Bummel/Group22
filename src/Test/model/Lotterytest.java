package Test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import gmb.model.Lottery;
import gmb.model.financial.FinancialManagement;
import gmb.model.tip.TipManagement;
import gmb.model.user.GroupManagement;
import gmb.model.user.MemberManagement;


public class Lotterytest {
	//Vergleichsvariablen
	private  Lottery lottery;
	private  FinancialManagement financialManagement;
	private  MemberManagement memberManagement;
	private  GroupManagement groupManagement;
	private  TipManagement tipManagement;
	
	
	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		FinancialManagement financialManagement= new FinancialManagement();
		MemberManagement memberManagement= new MemberManagement();
		GroupManagement	groupManagement=new GroupManagement();
		TipManagement tipManagement=new TipManagement();
		Lottery lottery = new Lottery (financialManagement,memberManagement,groupManagement,tipManagement) ;
	}
	@Before
	public void setUp () throws Exception {
	
	}
	@Test
	public void getFinancialtest() {
		// Testvariablen
		FinancialManagement s= lottery.getFinancialManagement();
		assertEquals(s,financialManagement);}
	
	@Test
	public void getMembertest() {
		// Testvariablen
		MemberManagement t= lottery.getMemberManagement();
		assertEquals(t,memberManagement);}
	
	@Test
	public void getGrouptest() {
		// Testvariablen
		GroupManagement u= lottery.getGroupManagement();
		assertEquals(u,groupManagement);}
	@Test
	public void getTipManagementtest() {
		// Testvariablen
		TipManagement v=lottery.getTipManagement();
		assertEquals(v,tipManagement);}
	
	@SuppressWarnings("static-access")
	@Test
	public void getInstancetest() {
		// Testvariablen
		Lottery w=lottery.getInstance();
		assertNull(w);}

	
}
