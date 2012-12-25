package gmb.model.group;

import gmb.model.GmbFactory;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.member.Customer;
import gmb.model.request.RequestState;
import gmb.model.request.group.GroupAdminRightsTransfereOffering;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

/**
 * The group class. <br>
 * Holds group specific information like group admin, members, group tips and associated winnings.
 */
@Entity
@Table(name="GroupTable")
public class Group extends PersiObject
{
	@Column(unique=true)
	protected String name;
	protected String infoText;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date foundingDate;
	protected Boolean closed;
	
	@ManyToOne
	@JoinColumn
	protected GroupManagement groupManagementId;

	@OneToOne
	protected Customer groupAdmin;
	@ManyToMany
	protected List<Customer> groupMembers;

	@OneToMany(mappedBy="group")
	protected List<DailyLottoGroupTip> dailyLottoGroupTips;
	@OneToMany(mappedBy="group")
	protected List<WeeklyLottoGroupTip> weeklyLottoGroupTips;
	@OneToMany(mappedBy="group")
	protected List<TotoGroupTip> totoGroupTips;

	@OneToMany(mappedBy="group")
	protected List<GroupMembershipApplication> groupInvitations;
	@OneToMany(mappedBy="group")
	protected List<GroupAdminRightsTransfereOffering> groupAdminRightsTransfereOfferings;
	@OneToMany(mappedBy="group")
	protected List<GroupMembershipApplication> groupMembershipApplications;

	@Deprecated
	protected Group(){}

	public Group(String name, Customer groupAdmin, String infoText)
	{
		this.closed = false;
		
		this.name = name;
		this.infoText = infoText;
		foundingDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		
		this.groupAdmin = groupAdmin;
		
		groupMembers =  new LinkedList<Customer>();
		groupMembers.add(groupAdmin);
		
		dailyLottoGroupTips = new LinkedList<DailyLottoGroupTip>();
		weeklyLottoGroupTips = new LinkedList<WeeklyLottoGroupTip>();
		totoGroupTips = new LinkedList<TotoGroupTip>();

		groupInvitations = new LinkedList<GroupMembershipApplication>();
		groupAdminRightsTransfereOfferings = new LinkedList<GroupAdminRightsTransfereOffering>();
		groupMembershipApplications = new LinkedList<GroupMembershipApplication>();
		
		this.groupManagementId = Lottery.getInstance().getGroupManagement();
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Creates a "GroupMembershipApplication" and adds it to the "customer" and this group.<br>
	 * Returns the created application.
	 * @param customer
	 * @param note
	 */
	public GroupMembershipApplication applyForMembership(Customer customer, String note)
	{
		GroupMembershipApplication application =  GmbFactory.new_GroupMembershipApplication(this, customer, note);

		customer.addGroupMembershipApplication(application);
		this.groupMembershipApplications.add(application);
		
		DB_UPDATE(); 
		
		return application;
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Creates a "GroupInvitation" and adds it to the "customer" and this group. <br>
	 * Returns the created invitation.
	 * @param customer
	 * @param note
	 */
	public GroupMembershipApplication sendGroupInvitation(Customer customer, String note)
	{
		GroupMembershipApplication invitation =  GmbFactory.new_GroupMembershipApplication(this, customer, note);

		customer.addGroupInvitation(invitation);
		this.groupInvitations.add(invitation);
		
		DB_UPDATE(); 
		
		return invitation;
	}

	/**
	 * [Intended for direct usage by controller] <br>
	 * Creates a "GroupAdminRightsTransfereOffering" and adds it to the "groupMember" and this group. <br>
	 * Returns the created offering.
	 * @param groupMember
	 * @param note
	 */
	public GroupAdminRightsTransfereOffering sendGroupAdminRightsTransfereOffering(Customer groupMember, String note)
	{
		GroupAdminRightsTransfereOffering offering =  GmbFactory.new_GroupAdminRightsTransfereOffering(this, groupMember, note);

		groupMember.addGroupAdminRightsTransfereOffering(offering);
		this.groupAdminRightsTransfereOfferings.add(offering);
		
		DB_UPDATE(); 
		
		return offering;
	}

	/**
	 * Swaps the "groupAdmin" with "groupMember". <br>
	 * Removes the "groupMember" from the "groupMembers" list and adds the old "groupAdmin" <br>
	 * Returns false if the "groupMember" is not in "groupMembers". <br>
	 * @param groupMember
	 * @return
	 */
	public boolean switchGroupAdmin(Customer groupMember)
	{
		if(groupMembers.contains(groupMember))
		{
			groupAdmin = groupMember;
			
			DB_UPDATE(); 
			
			return true;
		}
		else
			return false;
	}
	
	protected boolean resign(Customer groupMember, String notification)
	{
		if(groupMembers.contains(groupMember))
		{
			withdrawUnhandledGroupRequestsOfGroupMember(groupMember);

			groupMember.addNotification(notification);

			if(groupMember == groupAdmin)
				groupAdmin = null;
			
			groupMember.removeGroup(this);
			
			return true;
		}
		else
			return false;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Resigns the "groupMember" by withdrawing all unhandled group related requests in "groupMember",
	 * sending a "Notification" to the member and removing him from the "groupMembers" list. <br>
	 * If it's the "groupAdmin" who resigns "close" the group. 
	 * @param groupMember
	 * @return true if successful, otherwise false.
	 */
	public boolean resign(Customer groupMember)
	{	
		if(groupMember == groupAdmin)
		{
			if(close()) return true;
		}
		else
		if(resign(groupMember, "You have been resigned from Group " + name + "."))
		{
			groupMembers.remove(groupMember);

			DB_UPDATE(); 
			
			return true;
		}

		return false;
	}

	/**
	 * Withdraws all unhandled group related "Requests" in "groupMember".
	 * @param groupMember
	 */
	protected void withdrawUnhandledGroupRequestsOfGroupMember(Customer groupMember)
	{
		for(GroupMembershipApplication application : groupMembershipApplications)
		{
			if(application.getMember() == groupMember && application.getState() == RequestState.Unhandled)
				application.withdraw();
		}

		for(GroupMembershipApplication invitation : groupInvitations)
		{
			if(invitation.getMember() == groupMember && invitation.getState() == RequestState.Unhandled)
				invitation.withdraw();
		}

		for(GroupAdminRightsTransfereOffering offerings : groupAdminRightsTransfereOfferings)
		{
			if(offerings.getMember() == groupMember && offerings.getState() == RequestState.Unhandled)
				offerings.withdraw();
		}
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Closes the group by resigning all "groupMembers" + "groupAdmin",
	 * withdrawing all group related requests in the system which are still unhandled
	 * and setting the "closed" flag to true.<br>
	 * (This doesn't remove the group from the system.)
	 */
	public boolean close()
	{
		if(closed == true) return false;		
		closed = true;
		
		for(DailyLottoGroupTip groupTip : dailyLottoGroupTips)
			for(Customer groupMember : groupMembers)
				groupTip.removeAllTipsOfGroupMember(groupMember);
		
		for(WeeklyLottoGroupTip groupTip : weeklyLottoGroupTips)
			for(Customer groupMember : groupMembers)
				groupTip.removeAllTipsOfGroupMember(groupMember);
		
		for(TotoGroupTip groupTip : totoGroupTips)
			for(Customer groupMember : groupMembers)
				groupTip.removeAllTipsOfGroupMember(groupMember);
		
		//first the groupMembers:
		for(Customer groupMember : groupMembers)
			if(groupMember != groupAdmin)
			resign(groupMember, "The group " + name + " has been closed. You will be automatically resigned.");
		
		//now the groupAdmin:
		resign(groupAdmin, "The group " + name + ", where you had admin status, has been closed. You will be automatically resigned.");
		
		//now clear grouMember list:
		groupMembers.clear();
		
		//withdraw all group related requests not only those which are associated with groupMembers:
		for(GroupMembershipApplication application : groupMembershipApplications)
			if(application.getState() == RequestState.Unhandled)
				application.withdraw();

		for(GroupMembershipApplication invitation : groupInvitations)
			if(invitation.getState() == RequestState.Unhandled)
				invitation.withdraw();
		
		for(GroupAdminRightsTransfereOffering offerings : groupAdminRightsTransfereOfferings)
			if(offerings.getState() == RequestState.Unhandled)
				offerings.withdraw();
		
		DB_UPDATE(); 
		
		return true;
	}
 
	/**
	 * Adds the "customer" to the "groupMembers" list and the group
	 * to the "groups" list of the "customer".
	 * @param customer
	 */
	public void addGroupMember(Customer customer)
	{ 
		groupMembers.add(customer); 
		customer.addGroup(this);
		
		DB_UPDATE(); 
	}
		
	/**
	 * [Intended for direct usage by controller]<br>
	 * Sets the info text for the group.
	 * @param infoText
	 */
	public void SetInfoText(String infoText){ this.infoText = infoText; DB_UPDATE(); }	
	public void setGroupAdmin(Customer groupAdmin){ this.groupAdmin = groupAdmin; DB_UPDATE(); }
	
	public void addGroupTip(DailyLottoGroupTip tip){ dailyLottoGroupTips.add(tip); DB_UPDATE(); }	
	public void addGroupTip(WeeklyLottoGroupTip tip){ weeklyLottoGroupTips.add(tip); DB_UPDATE(); }	
	public void addGroupTip(TotoGroupTip tip){ totoGroupTips.add(tip); DB_UPDATE(); }
	
	public boolean removeGroupTip(DailyLottoGroupTip tip){ boolean result = dailyLottoGroupTips.remove(tip); DB_UPDATE(); return result; }
	public boolean removeGroupTip(WeeklyLottoGroupTip tip){ boolean result = weeklyLottoGroupTips.remove(tip); DB_UPDATE(); return result; }
	public boolean removeGroupTip(TotoGroupTip tip){ boolean result = totoGroupTips.remove(tip); DB_UPDATE(); return result; }
	
	/**
	 * [Intended for direct usage by controller]<br>
	 */
	public boolean isClosed(){ return closed; }
	
	public List<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return groupAdminRightsTransfereOfferings; }
	public List<GroupMembershipApplication> getGroupInvitations(){ return groupInvitations; }
	public List<GroupMembershipApplication> getGroupMembershipApplications(){ return groupMembershipApplications; }	

	public List<Customer> getGroupMembers(){ return groupMembers; }

	public String getName(){ return name;}
	public String getInfoText(){ return infoText; }	
	public Customer getGroupAdmin(){ return groupAdmin; }
	public DateTime getFoundingDate(){ return new DateTime(foundingDate); }

	public List<DailyLottoGroupTip> getDailyLottoGroupTips(){ return dailyLottoGroupTips; }	
	public List<WeeklyLottoGroupTip> getWeeklyLottoGroupTips(){ return weeklyLottoGroupTips; }	
	public List<TotoGroupTip> getTotoGroupTips(){ return totoGroupTips; }	
}
