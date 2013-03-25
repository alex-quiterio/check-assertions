package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public  class FieldMapper {
	
	/**
	 * Structure used to make structural reification of field initialization
	 */
	private static HashMap<Object, ArrayList<String>> fieldMapper = 
			new HashMap<Object, ArrayList<String>>();
	
	/**
	 * stack Object is used to scope the method instance when a
	 * call is made, i.e. to keep the arguments list reachable from
	 * the last part of method before we call the method (see Method Assertion
	 * Class) we save it on a stack for future inspection
	 */
	public  static Stack<Object[]> stack  = new Stack<Object[]>();
	
	/**
	 * 
	 * @param o     - is an instance of any class and unique from Java 
	 * perspective. 
	 * @param field - the String name of the field that we want to check
	 * @return true if the field of Object +o+ is already initialized, false
	 * otherwise 
	 */
	public static boolean fieldInitialized(Object o, String field) {
		ArrayList<String> fieldList = (ArrayList<String>) fieldMapper.get(o);
		return (fieldList == null) ? false : fieldList.contains(field);
	}
	
	/**
	 * 
	 * @param object - an instance of any class with annotation @Assertion on it 
	 * @param field - the specific instance attribute that we want to mark as 
	 * initialized 
	 */
	public static void addField(Object object, String field) {
		ArrayList<String> fieldList = (ArrayList<String>) fieldMapper.get(object);
		if (fieldList == null) {
			fieldList = new ArrayList<String>();
			fieldMapper.put(object, fieldList);
		}
		if (fieldList.contains(field)) {
			return;
		}
		fieldList.add(field);
	}

}
