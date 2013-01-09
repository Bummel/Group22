package gmb.model;

import gmb.model.tip.tip.single.SingleTip;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Factory class for creation of various initialized ArrayLists.
 */
public class ArrayListFac 
{
	/**
	 * Creates a CDecimal ArrayList and initializes each element with CDecimal(0).
	 * @param element_count The size of the ArrayList
	 * @return The created ArrayList.
	 */
	public static ArrayList<CDecimal> new_CDecimalArray(int element_count)
	{
		ArrayList<CDecimal> array = new ArrayList<CDecimal>(element_count);
		
		for(int i = 0; i < element_count; ++i)
			array.add(new CDecimal(0));
		
		return array;
	}
	
	/**
	 * Creates a LinkedList<SingleTip> ArrayList and initializes each element with a new LinkedList<SingleTip>.
	 * @param element_count The size of the ArrayList
	 * @return The created ArrayList.
	 */
	public static ArrayList<LinkedList<SingleTip>> new_SingleTipLinkedListArray(int element_count)
	{
		ArrayList<LinkedList<SingleTip>> array = new ArrayList<LinkedList<SingleTip>>(element_count);
		
		for(int i = 0; i < element_count; ++i)
			array.add(new LinkedList<SingleTip>());
		
		return array;
	}
}
