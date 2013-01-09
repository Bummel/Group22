package gmb.model.request.group;

import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.member.Customer;

/**
 * A request type for group admin right transfers.
 */
@Entity
public class GroupAdminRightsTransfereOffering extends GroupRequest 
{
	@Deprecated
	protected GroupAdminRightsTransfereOffering(){}

	public GroupAdminRightsTransfereOffering(Group group, Customer member, String note)
	{
		super(group, member, note);
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Sets the state to Accepted and sets the new grouAdmin.
	 * @return return code:
	 * <ul>
	 * <li> 0 - successful
	 * <li> 1 - failed because state was not "Unhandled"
	 * <li> 2 - failed because "member" is not in the group's list of members
	 * </ul>
	 */
	public int accept()
	{
		if(super.accept() == 0)
			if(group.switchGroupAdmin((Customer) member)){ return 0; }
			else
			{
				state = 3;
				DB_UPDATE();
				
				return 2;
			}
		else
			return 1;
	}
}
