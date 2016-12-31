package dataTypesController;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import accessories.SQLExceptions;

public class Comparators {
	
	private static Comparators comp;
	
	private Comparators() {
	
	}
	
	public static Comparators getInstance() {
		if (comp == null) {
			comp = new Comparators();
		}
		return comp;
	}
	
	public int compare (Object object1 , Object object2 , int type) throws SQLException {
		if (object1 == null) {
			return -1;
		} else if (object2 == null) {
			return 1;
		} else if (type == Types.DATE) {
			return ((Date)object1).compareTo((Date)object2);
		} else if(type == Types.FLOAT) {
			return ((Float)object1).compareTo((Float)object2);
		} else if(type == Types.VARCHAR) {
			return ((String)object1).compareTo((String)object2);
		} else if(type == Types.INTEGER) {
			return ((Integer)object1).compareTo((Integer)object2);
		} else {
			SQLExceptions.unsupportedDataType();			
		}
		return type;
	}
}
