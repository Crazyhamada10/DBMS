package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.TableValues;
import command.ICommand;
import fileManipulator.IFile;

public class CreateDropTable {
	
	private static CreateDropTable instance;
	
	private CreateDropTable() {
		
	}
	
	public static CreateDropTable getInstance() {
		if (instance == null) {
			instance = new CreateDropTable();
		}
		return instance;
	}
	
	public void createTable(ICommand command, IFile file, String dataBaseName) throws SQLException {
		TableValues tableValues = new TableValues(
				command.getTableName(), command.getColumnTypeIntegers(), 
				command.getColumnNames(), new ArrayList<ArrayList<Object>> (),
				command.getColumnNames().size(), 0);
		file.write(tableValues, file.createTableFile(dataBaseName, command.getTableName()));
		file.createDTD(file.createNewDTDFile(dataBaseName, command.getTableName()), tableValues);
	}
	
	public void dropTable(ICommand command, IFile file, String dataBaseName) throws SQLException {
		file.deleteTable(dataBaseName, command.getTableName());
	}
}
