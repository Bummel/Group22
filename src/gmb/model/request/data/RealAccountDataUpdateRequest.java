package gmb.model.request.data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import gmb.model.financial.container.RealAccountData;
import gmb.model.member.Customer;
import gmb.model.request.Request;

/**
 * A request type for updating real bank account data.
 */
@Entity
public class RealAccountDataUpdateRequest extends Request 
{	
	@OneToOne
	protected RealAccountData updatedData;

	@Deprecated
	protected RealAccountDataUpdateRequest(){}
	
	public RealAccountDataUpdateRequest(RealAccountData updatedData, Customer member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Sets the state to Accepted and updates the members' accounts' realAccountData.
	 * @return return code:
	 * <ul>
	 * <li> 0 - successful
	 * <li> 1 - failed because state was not "Unhandled"
	 * </ul>
	 */
	public int accept()
	{
		if(super.accept() == 0)
			{
				((Customer)member).getBankAccount().setRealAccountData(updatedData);
				return 0;
			}
		else
			return 1;
	}
	
	public RealAccountData getUpdatedData(){ return updatedData; }
}
