package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public  class FieldMapper {
	
	private static HashMap<Object, ArrayList<String>> fieldMapper = new HashMap<Object, ArrayList<String>>();
	public  static Stack<Object[]> stack  = new Stack<Object[]>();
	
	public static void initializationComplete(Object object, String field) {
		ArrayList<String> fieldList;
		fieldList = (ArrayList<String>) fieldMapper.get(object);
		if (fieldList != null) {
			fieldList.remove(field);
		}
	}
	
	public static boolean fieldInitialized(Object object, String field) {
		ArrayList<String> fieldList = (ArrayList<String>) fieldMapper.get(object);
		return (fieldList == null) ? null : fieldList.contains(field);
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
