package gmb.model;

import gmb.model.financial.FinancialManagement;
import gmb.model.group.GroupManagement;
import gmb.model.member.MemberManagement;

import gmb.model.tip.TipManagement;


public class Lottery 
{
	protected static Lottery INSTANCE = null;
	 
	protected FinancialManagement financialManagement;
	protected MemberManagement memberManagement;
	protected GroupManagement groupManagement;
	protected TipManagement tipManagement;
	
	protected Timer timer;
	
	@Deprecated
	protected Lottery(){}
	
	protected Lottery(FinancialManagement financialManagement, MemberManagement memberManagement, GroupManagement groupManagement, TipManagement tipManagement)
	{
		this.financialManagement = financialManagement;
		this.memberManagement = memberManagement;
		this.groupManagement = groupManagement;
		this.tipManagement = tipManagement;
		
		this.timer = new Timer();
	}
	
	public static void Instanciate(FinancialManagement financialManagement, MemberManagement memberManagement, GroupManagement groupManagement, TipManagement tipManagement)
	{
		if(INSTANCE != null) return;
		
		INSTANCE = new Lottery( financialManagement,  memberManagement,  groupManagement,  tipManagement);
	}

	public static Lottery getInstance()
	{
		assert INSTANCE != null : "LOTTERY INSTANCE DOES NOT EXIST!";
		return INSTANCE;
	}

	public FinancialManagement getFinancialManagement(){ return (FinancialManagement) GmbPersistenceManager.get(FinancialManagement.class, financialManagement.getId()); }
	public MemberManagement getMemberManagement(){ return (MemberManagement) GmbPersistenceManager.get(MemberManagement.class, memberManagement.getId()); }
	public GroupManagement getGroupManagement(){ return (GroupManagement) GmbPersistenceManager.get(GroupManagement.class, groupManagement.getId()); }
	public TipManagement getTipManagement(){ return (TipManagement) GmbPersistenceManager.get(TipManagement.class, tipManagement.getId()); }
	
	public Timer getTimer(){ return timer; }
}
