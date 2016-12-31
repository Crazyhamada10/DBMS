package dBMS;

import jSON.JSONFile;

import java.sql.SQLException;
import java.util.ArrayList;

import protocolBuffer.PBFile;
import xML.XMLFile;
import accessories.SQLExceptions;
import accessories.StaticData;
import accessories.TableValues;
import command.ICommand;
import fileManipulator.IFile;

public class DBMS {

	private static DBMS instance;
	private IFile file;
	private DataBase dataBase;
	private Table table;

	int updateCount;
	TableValues result;
	
	private DBMS() {
		table = Table.getInstance();
		dataBase = DataBase.getInstance();
	}
	
	public static DBMS getInstance() {
		if (instance == null) {
			instance = new DBMS();
		}
		return instance;
	}

	public void setPathProtocol(StaticData.FileType protocol, String directoryPath,
			String directoryName, String random) throws SQLException {
		setFileType(protocol);
		file.setPath(directoryPath, directoryName, random);
		table.setFile(file);
		dataBase.setFile(file);
	}

	private void setFileType(StaticData.FileType protocol) throws SQLException {
		switch (protocol) {
		case XML:
			file = XMLFile.getInstance();
			break;
		case JSON:
			file = JSONFile.getInstance();
			break;
		case PB:
			file = PBFile.getInstance();
			break;
		default:
			SQLExceptions.undefinedProtocol();
			break;
		}
	}

	public void clear() {
		result = new TableValues(new String(), new ArrayList<Integer>(),
				new ArrayList<String>(), new ArrayList<ArrayList<Object>>(), 0, 0);
		updateCount = -1;
	}
	
	public int getUpdateCount() {
		return updateCount;
	}

	public TableValues getResult() {
		return result;
	}

	public void take(ICommand command) throws SQLException {
		switch (command.getCommandType()) {
		case CREATE:
			switchOnCreate(command);
			break;
		case DELETE:
			delete(command);
			break;
		case ALTER:
			switchOnAlter(command);
			break;
		case DROP:
			switchOnDrop(command);
			break;
		case INSERT:
			insert(command);
			break;
		case SELECT:
			select(command);
			break;
		case UPDATE:
			update(command);
			break;
		case USE:
			use(command);
			break;
		case UNION:
			union(command);
			break;
		default:
			SQLExceptions.throwUnknownCommand();
		}
	}

	private void switchOnCreate(ICommand command) throws SQLException {
		switch (command.getDataBaseTableType()) {
		case DATABASE:
			createDataBase(command);			
			break;
		case TABLE:
			createTable(command);			
			break;
		}
	}
	
	private void switchOnDrop(ICommand command) throws SQLException {
		switch (command.getDataBaseTableType()) {
		case DATABASE:
			dropDatabase(command);			
			break;
		case TABLE:
			dropTable(command);			
			break;
		}
	}
	
	private void switchOnAlter(ICommand command) throws SQLException {
		switch (command.getAlterType()) {
		case ADD:
			alterAdd(command);			
			break;
		case DROP:
			alterDrop(command);			
			break;
		}
	}
	
	private void use(ICommand command) throws SQLException {
		updateCount = table.use(command);
		result = null;
	}
	
	private void createDataBase(ICommand command) throws SQLException {
		updateCount = dataBase.createDataBase(command);
		result = null;
	}

	private void dropDatabase(ICommand command) throws SQLException {
		updateCount = dataBase.dropDataBase(command);
		result = null;
	}
	
	private void createTable(ICommand command) throws SQLException {
		updateCount = table.createTable(command);
		result = null;
	}
	
	private void dropTable(ICommand command) throws SQLException {
		updateCount = table.dropTable(command);
		result = null;
	}

	private void insert(ICommand command) throws SQLException {
		updateCount = table.insert(command);
		result = null;
	}

	private void delete(ICommand command) throws SQLException {
		updateCount = table.delete(command);
		result = null;
	}


	private void update(ICommand command) throws SQLException {
		updateCount = table.update(command);
		result = null;
	}
	
	private void select(ICommand command) throws SQLException {
		result = table.select(command);
		updateCount = -1;
	}

	private void alterDrop(ICommand command) throws SQLException {
		updateCount = table.alterDrop(command);
		result = null;
	}
	
	private void alterAdd(ICommand command) throws SQLException {
		updateCount = table.alterAdd(command);
		result = null;
	}
	
	private void union(ICommand command) throws SQLException {
		result = table.union(command);
		updateCount = -1;
	}
}
