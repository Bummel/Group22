package gmb.model.request;
import java.util.Date;

import org.joda.time.DateTime;

import gmb.model.Lottery;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gmb.model.member.Member;

/**
 * Abstract super class for all request types.
 *
 */
@Entity
public abstract class Request extends Notification
{ 
	/**
	 * An integer encoding the state of the request:<br>
	 * 0 - Unhandled<br>
	 * 1 - Withdrawn<br>
	 * 2 - Accepted<br>
	 * 3 - Refused<br>
	 */
	protected int state;
	
	/**
	 * The date of the last change made to the state attribute.
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date lastStateChangeDate;

	@Deprecated
	protected Request(){}

	public Request(Member member, String note)
	{
		super(note);
		this.member = member;

		this.state = 0;//RequestState.UNHANDELED

		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}

	public Member getMember(){ return member; }

	/**
	 * 
	 * @return The state of the request as {@link RequestState} object.
	 */
	public RequestState getState()
	{
		if(this.state == 1){ return RequestState.Withdrawn; }
		else
			if(this.state == 2){ return RequestState.Accepted; }
			else
				if(this.state == 3){ return RequestState.Refused; }
				else
					return RequestState.Unhandled;
	}

	/**
	 * @return The value of the integer which internally encodes the state.
	 */
	public int getStateAsInt(){ return state; }

	//	public void setNote(String note){ this.note = note; }

	//	public void setState(RequestState state)
	//	{
	//		if(state == RequestState.WITHDRAWN){ this.state = 1; }
	//		else
	//			if(state == RequestState.ACCEPTED){ this.state = 2; }
	//			else
	//				if(state == RequestState.REFUSED){ this.state = 3; }
	//				else
	//					this.state = 0; 
	//	}
	/**
	 * Sets the state to Unhandled.
	 */
	public void resetState()
	{ 
		state = 0; 
		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate(); 
		DB_UPDATE();
	}

	/**
	 * Sets the state to Withdrawn.
	 */
	public boolean withdraw()
	{ 
		if(state != 0) 
			return false; 

		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		state = 1; 
		DB_UPDATE();
		return true;
	}

	/**
	 * Sets the state to Accepted.
	 */
	public int accept()
	{ 
		if(state != 0) 
			return 1; 

		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		state = 2; 
		DB_UPDATE();
		return 0;
	}

	/**
	 * Sets the state to Refused.
	 */
	public boolean refuse()
	{ 
		if(state != 0) 
			return false; 

		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		state = 3; 
		DB_UPDATE();
		return true;
	}

	public DateTime getLastStateChangeDate(){ return new DateTime(lastStateChangeDate); }
}
