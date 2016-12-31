package jSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import fileManipulator.IFileWriter;
import accessories.SQLExceptions;
import accessories.StaticData.AttributeWords;
import accessories.TableValues;

public class JSONWriter implements IFileWriter{

	private static JSONWriter instance;
	
	private JSONWriter(){
	}
	
	public static JSONWriter getInstance(){
		if(instance == null) {
			instance = new JSONWriter();			
		}
		return instance;
	}


	@SuppressWarnings("unchecked")
	@Override
    public void write(TableValues data, File fileToUpdate)
            throws SQLException {
        int numCols = data.getNumCols();
        int numRows = data.getNumRows();
        ArrayList<String> colNames = data.getcolNames();
        ArrayList<Integer> colTypes = data.getColTypes();
        JSONObject table = new JSONObject();
        JSONObject details = new JSONObject();
        details.put(AttributeWords.TableName.name(), data.getTableName());
        details.put(AttributeWords.NumberOfColumns.name(), String.valueOf(numCols));
        details.put(AttributeWords.NumberOfRows.name(), String.valueOf(numRows));
        table.put(AttributeWords.Details.name(), details);
        table.put(AttributeWords.ColumnTypes.name(), fillTypes(colTypes));
        table.put(AttributeWords.ColumnNames.name(), fillNames(colNames));
        table.put(AttributeWords.Data, fillRows(data.getRows(), numRows, numCols));
        try {
			FileWriter write = new FileWriter(fileToUpdate);
			write.write(table.toJSONString());
			write.close();
		} catch (IOException e) {
			SQLExceptions.canNotWriteFile();
		}
    }
    
    @SuppressWarnings("unchecked")
	protected org.json.simple.JSONArray fillNames(
            ArrayList<String> colNames) {
        org.json.simple.JSONArray jO = new org.json.simple.JSONArray();
        for (int i = 0; i < colNames.size(); i++) {
            jO.add(colNames.get(i));
        }
        return jO;
    }
    
    @SuppressWarnings("unchecked")
	private org.json.simple.JSONArray fillTypes(
            ArrayList<Integer> colTypes) {
        org.json.simple.JSONArray jO = new org.json.simple.JSONArray();
        for (int i = 0; i < colTypes.size(); i++) {
            jO.add(colTypes.get(i));
        }
        return jO;
    }
    
    @SuppressWarnings("unchecked")
	private org.json.simple.JSONArray fillRows(
            ArrayList<ArrayList<Object>> rows, int numRows, int numCol) {
        org.json.simple.JSONArray tableDataJson = new org.json.simple.JSONArray();
        for (int i = 0; i < numRows; i++) {
        	org.json.simple.JSONArray rowDataJson = new org.json.simple.JSONArray();
            for (int j = 0; j < numCol; j++) {
            	if (rows.get(i).get(j) == null) {
            		rowDataJson.add(AttributeWords.Null.name());            		
            	} else {
            		rowDataJson.add(rows.get(i).get(j).toString());            		
            	}
            }
            tableDataJson.add(rowDataJson);
        }
        return tableDataJson;
    }
}
