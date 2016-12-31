package jDBC;

import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {

	private ArrayList<ArrayList<Object>> table;
	private ArrayList<String> colNames;
	private ArrayList<Integer> colTypes;
	private String tableName;

	public ResultSetMetaData(ArrayList<ArrayList<Object>> table, ArrayList<String> colNames,
			ArrayList<Integer> colTypes, String tableName) {
		this.table = table;
		this.colNames = new ArrayList<>(colNames);
		this.colTypes = new ArrayList<>(colTypes);
		this.colTypes = colTypes;
		this.tableName = tableName;
	}

	public ArrayList<ArrayList<Object>> getTable() {
		return table;
	}

	public String getColumnName(int arg0) throws SQLException {
		if (arg0 < 0 || arg0 > colNames.size())
			throw new RuntimeException("Invalid index");
		return colNames.get(arg0 - 1);
	}

	public int getColumnType(int arg0) throws SQLException {
		if (arg0 < 0 || arg0 > colTypes.size())
			throw new RuntimeException("Invalid index");
		return colTypes.get(arg0 - 1);
	}

	public ArrayList<String> getColNames() {
		return colNames;
	}

	public ArrayList<Integer> getColTypes() {
		return colTypes;
	}

	public String getTableName() {
		return tableName;
	}

	public int getColumnCount() throws SQLException {
		return colNames.size();
	}

	public String getTableName(int arg0) throws SQLException {
		return tableName;
	}

	public String getColumnLabel(int arg0) throws SQLException {
		return getColumnName(arg0);
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public String getCatalogName(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public String getColumnClassName(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public int getColumnDisplaySize(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public String getColumnTypeName(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public int getPrecision(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public int getScale(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public String getSchemaName(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isAutoIncrement(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isCaseSensitive(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isCurrency(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public int isNullable(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isReadOnly(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isSearchable(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isSigned(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean isWritable(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

}