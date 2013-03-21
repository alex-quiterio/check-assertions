package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public  class FieldMapper {
	
	private static HashMap<Object, ArrayList<String>> fieldMapper = 
			new HashMap<Object, ArrayList<String>>();
	
	/**
	 * stack Object is used to scope the method instance when a
	 * call is made, i.e. to keep the arguments list reachable from
	 * the last part of method before we call the method (see Method Assertion
	 * Class) we save it on a stack for future inspection
	 */
	public  static Stack<Object[]> stack  = new Stack<Object[]>();
	
	public static boolean initializationComplete(Object object, String field) {
		ArrayList<String> fieldList;
		fieldList = (ArrayList<String>) fieldMapper.get(object);
		if (fieldList != null) {
			fieldList.remove(field);
			fieldMapper.remove(object);
			fieldMapper.put(object, fieldList);
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean fieldInitialized(Object object, String field) {
		ArrayList<String> fieldList = (ArrayList<String>) fieldMapper.get(object);
		if (fieldList == null) {
			throw new RuntimeException("NULL FIELD");
		} else {
			return !(fieldList.contains(field));
		}
	}
	
	public static void addField(Object object, String field) {
		ArrayList<String> fieldList;
		fieldList = (ArrayList<String>) fieldMapper.get(object);
		if (fieldList == null) {
			fieldList = new ArrayList<String>();
			fieldMapper.put(object, fieldList);
		}
		fieldList.add(field);
	}

}
