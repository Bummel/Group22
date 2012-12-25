package gmb.model.tip.tip.group;

import java.util.LinkedList;

import javax.persistence.Entity;

import gmb.model.GmbFactory;
import gmb.model.ReturnBox;
import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

/** 
 * A GroupTip for the weekly football-toto evaluation.
 */
@Entity
public class TotoGroupTip extends GroupTip 
{
	
	@Deprecated
	protected TotoGroupTip(){}

	public TotoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Tries to delete this "GroupTip" with all implications.
	 * Returns 0 if successful.
	 */
	public int withdraw()
	{
		int result = super.withdraw();	
		if(result != 0) return result;
		
		if(group.removeGroupTip(this))
			return 0;
		else
			return 4;
	}
	
	public ReturnBox<Integer, LinkedList<SingleTip>> createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<int[]> tipTips)
	{
		assert tickets.size() == tipTips.size() : "Count of tickets does not fit count of tipTips in TotoGroupTip.createAndSubmitSingleTipList()!";
		assert  tickets.getFirst() instanceof TotoSTT : "Wrong TipTicket type given to TotoGroupTip.createAndSubmitSingleTipList()!";
		
		return super.createAndSubmitSingleTipList(tickets,  tipTips);
	}
	
	protected SingleTip createSingleTipSimple(TipTicket ticket)
	{
		return new TotoTip((TotoSTT)ticket, this);
	}
	
	protected SingleTip createSingleTipPersistent(TipTicket ticket)
	{
		return GmbFactory.new_TotoTip((TotoSTT)ticket, this);
	}
}
