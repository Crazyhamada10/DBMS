package jSON;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dataTypesController.DataTypeController;
import fileManipulator.IFileReader;
import accessories.SQLExceptions;
import accessories.StaticData.AttributeWords;
import accessories.TableValues;

public class JSONReader implements IFileReader {

	private static JSONReader instance;

	private JSONReader() {
		
	}

	public static JSONReader getInstance() {
		if (instance == null) {
			instance = new JSONReader();			
		}
		return instance;
	}

	private ArrayList<String> getColumnNames(JSONArray array) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            arrayList.add(array.get(i).toString());
        }
        return arrayList;
    }

    private ArrayList<Integer> getColumnTypes(JSONArray array) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            arrayList.add(Integer.parseInt(array.get(i).toString()));
        }
        return arrayList;
    }

    private ArrayList<ArrayList<Object>> getTableData(
            JSONArray tableDataJson, int numRows, int numCol, ArrayList<Integer> colTypes) throws SQLException {
        ArrayList<ArrayList<Object>> tableDataList = new ArrayList<>();
        ArrayList<Object> rowDataList;
        for (int i = 0; i < numRows; i++) {
            rowDataList = new ArrayList<>();
            JSONArray rowDataJson = (JSONArray) tableDataJson.get(i);
            for (int j = 0; j < numCol; j++) {
            	Integer type = colTypes.get(j);
                rowDataList.add(DataTypeController.convertData(type, rowDataJson.get(j).toString()));
            }
            tableDataList.add(rowDataList);
        }
        return tableDataList;
    }

    public TableValues read(File source) throws SQLException {
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            object = jsonParser.parse(new FileReader(source));
        } catch (IOException e) {
        	SQLExceptions.fileNotFound();
        } catch (org.json.simple.parser.ParseException e) {
        	SQLExceptions.canNotParseFile();
		}
        JSONObject jsonObject = (JSONObject) object;
        ArrayList<String> colNames = new ArrayList<String>(
                getColumnNames((JSONArray) jsonObject.get(AttributeWords.ColumnNames.name())));
        ArrayList<Integer> colTypes = new ArrayList<Integer>(
                getColumnTypes((JSONArray) jsonObject.get(AttributeWords.ColumnTypes.name())));
        JSONObject details = (JSONObject) jsonObject.get(AttributeWords.Details.name());
        String numCols = details.get(AttributeWords.NumberOfColumns.name()).toString();
        String numRows = details.get(AttributeWords.NumberOfRows.name()).toString();
        String tableName = new String(details.get(AttributeWords.TableName.name()).toString());
        ArrayList<ArrayList<Object>> raws = new ArrayList<>(getTableData(
                (JSONArray) jsonObject.get(AttributeWords.Data.name()),
                Integer.parseInt(numRows), Integer.parseInt(numCols), colTypes));
        TableValues rawData = new TableValues(tableName, colTypes, colNames, raws,
                Integer.parseInt(numCols), Integer.parseInt(numRows));
        return rawData;
    }

}
