package controller;

import jDBC.ResultSetMetaData;

import java.sql.SQLException;

import accessories.StaticData.CommandsTypes;

public interface IController {

	// return data from sql command suppose that it's correct sqlsyntax and if
	// there is no result return empty array
	// constructor of Resultmetdata 
	// private ArrayList<ArrayList<Object>> table; >>> objects not strings
	// private ArrayList<String> colNames;
	// private ArrayList<Integer> colTypes; >>> integers of (java.sql.types)
	// private String tableName;
	//
	//
	// public ResultSetMetaData(ArrayList<ArrayList<Object>> table,
	// ArrayList<String> colNames, ArrayList<Integer> colTypes,
	// String tableName) {
	// int length = colNames.size();
	// this.colNames = new ArrayList<>(colNames);
	// this.colTypes = new ArrayList<>(colTypes);
	// this.colTypes = colTypes;
	// this.tableName = tableName;
	// }
	public ResultSetMetaData excuteSelectSql(String sql)throws SQLException;

	// return select,update,insert,delete,drop,create,use,....
	public CommandsTypes getFirstIdentifier(String sql)throws SQLException;
	
	// return number of rows affected from sql command
	// suppose that it's correct sqlsyntax
	// DDl use create alter drop >>> 0 insert delete update >> count
	public int excuteUpdateSql(String sql)throws SQLException;
	
	// this random to create some randomness in the name of the database/directory, so that each test is done separately from other tests
	//For example, for "/tmp/jdbc/123456", with two databases "A", and "B", the hierarchy should be:
	//
	// | /tmp/jdbc/123456/
	// 		|---- A/
	// 		|---- B/
	//
	// Where "A" and "B" are the directories of each database, respectively.
	// this directory is a folder of databases ,
	// because of multi threading 
	// you should make sure that path exists and there's directory in this path
	// if there's directory i think you should overwrite it :D 
	// if there's no directory in this path create it and make it your current directory 
	// any command is done on dbms object for this thread is done on this directory 
	// protocol is xmldb or jasondb 
	public void makeDir(String path,String directory,String random,String protocol) throws SQLException;

}
