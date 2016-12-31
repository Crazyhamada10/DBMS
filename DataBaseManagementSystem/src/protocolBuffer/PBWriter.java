package protocolBuffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import protocolBuffer.TableProtos.Table;
import protocolBuffer.TableProtos.Table.Row;
import accessories.SQLExceptions;
import accessories.StaticData.AttributeWords;
import accessories.TableValues;
import fileManipulator.IFileWriter;

public class PBWriter implements IFileWriter {

	private static PBWriter instance;

	private PBWriter() {

	}

	public static PBWriter getInstance() {
		if (instance == null) {
			instance = new PBWriter();	
		}
		return instance;
	}

	@Override
	public void write(TableValues data, File fileToUpdate) throws SQLException {
		Table.Builder table = Table.newBuilder();
		table.setTableName(data.getTableName());
		table.setRowsNumber(data.getNumRows());
		table.setColumnsNumber(data.getNumCols());
		
		addColumnNames(table, data.getcolNames());
		addColumnTypes(table, data.getColTypes());
		addRows(table, data.getRows());

	    try {
			FileOutputStream write = new FileOutputStream(fileToUpdate);
			table.build().writeTo(write);
			write.close();
		} catch (FileNotFoundException e) {
			SQLExceptions.fileNotFound();
		} catch (IOException e) {
			SQLExceptions.canNotParseFile();
		}
		
	}
	
	private void addColumnNames(Table.Builder table, ArrayList<String> colNames) {
		for (String string : colNames) {
			table.addColumnNames(string);
		}
	}
	
	private void addColumnTypes(Table.Builder table, ArrayList<Integer> colTypes) {
		for (Integer type : colTypes) {
			table.addColumnTypes(type);
		}
	}
	
	private void addRows(Table.Builder table, ArrayList<ArrayList<Object>> rows) {
		for (ArrayList<Object> arrayList : rows) {
			table.addRows(getRow(arrayList));			
		}
	}
	
	
	private Row getRow(ArrayList<Object> row) {
		Row.Builder protoRow = Row.newBuilder();
		for (Object object : row) {
			if (object == null) {
				protoRow.addColumnValue(AttributeWords.Null.name());
			} else {
				protoRow.addColumnValue(object.toString());				
			}
		}
		return protoRow.build();
	}
}
