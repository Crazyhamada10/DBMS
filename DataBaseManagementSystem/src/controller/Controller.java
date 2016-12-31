package controller;

import jDBC.ResultSetMetaData;

import java.sql.SQLException;

import log4j.Log4j;
import parser.Statements;
import accessories.SQLExceptions;
import accessories.StaticData.CommandsTypes;
import accessories.StaticData.FileType;
import accessories.TableValues;
import command.ICommand;
import dBMS.DBMS;

public class Controller implements IController{

	DBMS dBMS;
	Statements parser;

	public Controller() {
		dBMS = DBMS.getInstance();
		parser = Statements.getInstance();
	}

	@Override
	public void makeDir(String path, String directory, String random, String protocol) throws SQLException {
		protocol = protocol.toLowerCase();
		switch (protocol) {
		case "xmldb":
			dBMS.setPathProtocol(FileType.XML, path, directory, random);
			break;
		case "jsondb":
			dBMS.setPathProtocol(FileType.JSON, path, directory, random);
			break;
		case "pbdb": 
			dBMS.setPathProtocol(FileType.PB, path, directory, random);
			break;
		default:
			SQLExceptions.undefinedProtocol();
		}
	}

	@Override
	public ResultSetMetaData excuteSelectSql(String sql) throws SQLException {
		dBMS.clear();
		ICommand command = parser.Check(sql);
		parserLog();
		dBMS.take(command);
		executeLog();
		TableValues tableData = dBMS.getResult();
		return new ResultSetMetaData(tableData.getRows(), tableData.getcolNames(), tableData.getColTypes(), tableData.getTableName());
	}

	@Override
	public CommandsTypes getFirstIdentifier(String sql) throws SQLException {
		ICommand command = parser.Check(sql);
		parserLog();
		return command.getCommandType();
	}

	@Override
	public int excuteUpdateSql(String sql) throws SQLException {
		dBMS.clear();
		ICommand command = parser.Check(sql);
		parserLog();
		dBMS.take(command);
		executeLog();
		return dBMS.getUpdateCount();
	}

	private void parserLog() {
		logtoFile("Command is parsed successfully.");
	}
	
	private void executeLog() {
		logtoFile("Command is executed successfully.");
	}
	
	private void logtoFile(String string) {
		Log4j.getInstance().info(string);
	}
}
