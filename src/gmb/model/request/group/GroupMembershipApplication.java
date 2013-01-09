package gmb.model.request.group;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.member.Customer;

/**
 * A request type for group membership applications and invitations.
 */
@Entity
public class GroupMembershipApplication extends GroupRequest 
{
	@Deprecated
	protected GroupMembershipApplication(){}
	
	public GroupMembershipApplication(Group group, Customer member, String note)
	{
		super(group, member, note);
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Sets the state to Accepted and adds the member as groupMember to the group.
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
				group.addGroupMember((Customer) member);
				return 0;
			}
		else
			return 1;
	}
}
