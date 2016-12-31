package protocolBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import protocolBuffer.TableProtos.Table;
import protocolBuffer.TableProtos.Table.Row;
import accessories.SQLExceptions;
import accessories.TableValues;

import com.google.protobuf.ProtocolStringList;

import dataTypesController.DataTypeController;
import fileManipulator.IFileReader;

public class PBReader implements IFileReader {
	
	private static PBReader instance;
	
	private PBReader(){
		
	}
	
	public static PBReader getInstance(){
		if(instance == null) {
			instance =  new PBReader();
		}
		return instance;
	}

	@Override
	public TableValues read(File source) throws SQLException {
		Table table = null;
		try {
			table = Table.parseFrom(new FileInputStream(source));
		} catch (FileNotFoundException e) {
			SQLExceptions.fileNotFound();
		} catch (IOException e) {
			SQLExceptions.canNotParseFile();
		}
		ArrayList<String> colNames = new ArrayList<String>(getColNames(table.getColumnNamesList()));
		ArrayList<Integer> colTypes = new ArrayList<Integer>(getColTypes(table.getColumnTypesList()));
		ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>(getRows(table.getRowsList(), colTypes));
		return new TableValues(table.getTableName(), colTypes,
			colNames, rows, table.getColumnsNumber(), table.getRowsNumber());
	}
	
	private ArrayList<ArrayList<Object>> getRows(List<Row> rows, ArrayList<Integer> colTypes) throws SQLException {
		ArrayList<ArrayList<Object>> rowsObjects = new ArrayList<ArrayList<Object>>();
		for (Row row : rows) {
			rowsObjects.add(getRow(row, colTypes));
		}
		return rowsObjects;
	}

	private ArrayList<Object> getRow(Row row, ArrayList<Integer> colTypes) throws SQLException {
		ArrayList<String> strings = getColNames(row.getColumnValueList());
		ArrayList<Object> objects = new ArrayList<Object>();
		for (int i = 0; i < strings.size(); i ++) {
			Integer type = colTypes.get(i);
			String string = strings.get(i);
			objects.add(DataTypeController.convertData(type, string));
		}
		return objects;
	}
	
	private ArrayList<String> getColNames(ProtocolStringList protoColNames) {
		ArrayList<String> colNames = new ArrayList<String>();
		for (String string : protoColNames) {
			colNames.add(string);
		}
		return colNames;
	}
	
	private ArrayList<Integer> getColTypes(List<Integer> protoColTypes) {
		ArrayList<Integer> colTypes = new ArrayList<Integer>();
		for (Integer integer : protoColTypes) {
			colTypes.add(integer);
		}
		return colTypes;
	}
}
