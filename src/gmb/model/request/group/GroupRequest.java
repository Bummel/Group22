package gmb.model.request.group;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.request.Request;

/**
 * The abstract super class for group related requests.
 */
@Entity
public abstract class GroupRequest extends Request 
{
	/**
	 * The associated group with this request.
	 */
	@ManyToOne
	protected  Group group;
	
	@Deprecated
	protected GroupRequest(){}
	
	public GroupRequest(Group group, Customer member, String note)
	{
		super(member, note);
		this.group = group;
	}
	
	public Group getGroup(){ return group; }
}
