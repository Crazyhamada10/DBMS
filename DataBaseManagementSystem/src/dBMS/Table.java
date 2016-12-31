package dBMS;

import java.sql.SQLException;

import accessories.SQLExceptions;
import accessories.TableValues;
import command.ICommand;
import dBMS.helper.Alter;
import dBMS.helper.CreateDropTable;
import dBMS.helper.Delete;
import dBMS.helper.Insert;
import dBMS.helper.OrderBy;
import dBMS.helper.Select;
import dBMS.helper.Union;
import dBMS.helper.Update;
import fileManipulator.IFile;

public class Table {

	private static Table instance;
	private String dataBaseName;
	private IFile file;
	
	public Table() {

	}
	
	public static Table getInstance() {
		if (instance == null) {
			instance = new Table();
		}
		return instance;
	}
	
	public void setFile(IFile file) {
		this.file = file;
	}
	
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = new String(dataBaseName);
	}
	
	private void checkDB() throws SQLException {
		if (dataBaseName == null) {
			SQLExceptions.unusedDataBase();
		}
	}
	
	public int use(ICommand command) throws SQLException {
		file.checkDataBaseExistance(command.getDataBaseName());
		setDataBaseName(command.getDataBaseName());
		return 0;
	}

	public int createTable(ICommand command) throws SQLException {
		checkDB();
		CreateDropTable.getInstance().createTable(command, file, dataBaseName);
		return 0;
	}
	
	public int dropTable(ICommand command) throws SQLException {
		checkDB();
		CreateDropTable.getInstance().dropTable(command, file, dataBaseName);
		return 0;
	}
	
	public int alterAdd(ICommand command) throws SQLException {
		checkDB();
		Alter.getInstance().alterAdd(command, file, dataBaseName);
		return 0;
	}

	public int alterDrop(ICommand command) throws SQLException {
		checkDB();
		Alter.getInstance().alterDrop(command, file, dataBaseName);
		return 0;
	}
	
	public int insert(ICommand command) throws SQLException {
		checkDB();
		Insert.getInstance().insert(command, file, dataBaseName);
		return 1;
	}
	
	public int update(ICommand command) throws SQLException {
		checkDB();
		int updatedRows = Update.getInstance().update(command, file, dataBaseName);
		return updatedRows;
	}
	
	public int delete(ICommand command) throws SQLException {
		checkDB();
		int deletedRows = Delete.getInstance().delete(command, file, dataBaseName);
		return deletedRows;
	}
	
	public TableValues select(ICommand command) throws SQLException {
		checkDB();
		TableValues tableValues = Select.getInstance().select(command, file, dataBaseName);
		if (command.isOrdered()) {
			tableValues = OrderBy.getInstance().order(command, tableValues);
		}
		return tableValues;
	}
	
	public TableValues union(ICommand command) throws SQLException {
		checkDB();
		return Union.getInstance().union(command, file, dataBaseName);
	}		
}
