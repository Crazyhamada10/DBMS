package dataTypesController;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import accessories.SQLExceptions;
import accessories.StaticData.AttributeWords;

public class DataTypeController {

	private DataTypeController(){
	}

	public static Object convertData(int type ,String value) throws SQLException{
		if (value.equals(AttributeWords.Null.name())) {
			return null;			
		}
		switch (type) {
		case Types.INTEGER:
			return Integer.valueOf(value);
		case Types.FLOAT:
			return Float.valueOf(value);
		case Types.DATE:
			return Date.valueOf(value);
		case Types.VARCHAR:
			return value.toString();
		default:
			SQLExceptions.unsupportedDataType();
		}
		return null;
	}
	
	public static int getType(Object object) throws SQLException{
		if(object.getClass().equals(Integer.class)) {
			return Types.INTEGER;			
		} else if(object.getClass().equals(Float.class)) {
			return Types.FLOAT;			
		} else if(object.getClass().equals(Date.class)) {
			return Types.DATE;
		} else if(object.getClass().equals(String.class)) {
			return Types.VARCHAR;
		} else {
			SQLExceptions.unsupportedDataType();			
		}
		return -1;
	}
	
}
