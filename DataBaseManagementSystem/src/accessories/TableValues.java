package accessories;

import java.sql.SQLException;
import java.util.ArrayList;

public class TableValues {

	private String tableName;
	private ArrayList<Integer> columnTypes;
	private ArrayList<String> columnNames;
	private ArrayList<ArrayList<Object>> rows;
	private int numCols, numRows;

	public TableValues(String tableName, ArrayList<Integer> colTypes, ArrayList<String> colNames,
			ArrayList<ArrayList<Object>> rows, int numCols, int numRows) {
		this.tableName = tableName;
		this.columnTypes = colTypes;
		this.columnNames = colNames;
		this.rows = rows;
		this.numCols = numCols;
		this.numRows = numRows;
	}

	public Object getValue(int row, int col) {
		return this.rows.get(row).get(col);
	}
	
	public void setValue(int row, int col , Object ob) {
		this.rows.get(row).set(col, ob);
	}

	public ArrayList<Integer> typesByName(ArrayList<String> colnames){
		ArrayList<Integer> types = new ArrayList<>();
		for(int i = 0 ; i < colnames.size() ; i++){
			types.add(getType(getColIndex(colnames.get(i))));
		}
		return types;
	}
	
	public ArrayList<Object> getColWithName(String colName){
		int indexToBring = getColIndex(colName);
		ArrayList<Object> newCol = new ArrayList<>(); 
		for(int j = 0 ; j< this.numRows ; j++){
			newCol.add(rows.get(j).get(indexToBring));
		}
		return newCol;
	}
	
	public ArrayList<ArrayList<Object>> getCols(ArrayList<String> colNames){
		ArrayList<ArrayList<Object>> cols = new ArrayList<>();
		for(int i = 0; i< colNames.size() ; i++){
			cols.add(getColWithName(colNames.get(i)));
		}
		return cols;
	}
	
	public ArrayList<Object> getRow(int RowIndex){
		return rows.get(RowIndex);
	}

	public int getColIndex(String colName){
		return columnNames.indexOf(colName);
	}
	
	public String getName(int col) {
		return this.columnNames.get(col);
	}

	public int getType(int col) {
		return this.columnTypes.get(col);
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<Integer> getColTypes() {
		return columnTypes;
	}

	public void setColTypes(ArrayList<Integer> colTypes) {
		this.columnTypes = colTypes;
	}

	public ArrayList<ArrayList<Object>> getRows() {
		return rows;
	}

	public ArrayList<String> getcolNames() {
		return columnNames;
	}

	public void setcolNames(ArrayList<String> colNames) {
		this.columnNames = colNames;
	}

	public void setRows(ArrayList<ArrayList<Object>> rows) {
		this.rows = rows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public void removeRow(int rowIndex){
		this.rows.remove(rowIndex);
		this.numRows--;
	}
	
	public void removeColumn(String colName) throws SQLException{
		int indexToRemove = getColIndex(colName);
		if (indexToRemove == -1) {
			SQLExceptions.unknownColName();
		}
		for(int i=0 ; i<numRows ; i++){
			rows.get(i).remove(indexToRemove);
		}
		columnNames.remove(indexToRemove);
		columnTypes.remove(indexToRemove);
		numCols--;
	}

	public void addColumn(String colName , int type){
		columnNames.add(colName);
		columnTypes.add(type);
		numCols++;
		for(int i=0 ; i<numRows ; i++){
			rows.get(i).add(null);
		}
	}

	public void addRow(ArrayList<Object> row) throws SQLException {
		this.rows.add(row);
		this.numRows++;
	}
	
	public void swap2rows(int index1, int index2) {
        ArrayList<Object> temp = rows.get(index1);
        rows.set(index1, rows.get(index2));
        rows.set(index2, temp);
	}
	
	public void swap2columns(int index1, int index2) {
		for(int i=0 ; i< numRows ; i++){
            Object temp = rows.get(i).get(index1);
            rows.get(i).set(index1, rows.get(i).get(index2));
            rows.get(i).set(index2, temp);
        }
        int type1 = columnTypes.get(index1);
        columnTypes.set(index1, columnTypes.get(index2));
        columnTypes.set(index2, type1);
        String name1 = columnNames.get(index1);
        columnNames.set(index1, columnNames.get(index2));
        columnNames.set(index2, name1);
	}
	
}
