package jDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.util.ArrayList;

import log4j.Log4j;
import accessories.SQLExceptions;
import accessories.StaticData.CommandsTypes;
import controller.Controller;

public class Statement implements java.sql.Statement {

	/*
	 * The object used for executing a static SQL statement and returning the
	 * results it produces.
	 * 
	 * By default, only one ResultSet object per Statement object can be open at
	 * the same time. Therefore, if the reading of one ResultSet object is
	 * interleaved with the reading of another, each must have been generated by
	 * different Statement objects. All execution methods in the Statement
	 * interface implicitly close a statment's current ResultSet object if an
	 * open one exists.
	 */

	/// connection which produce that statement
	protected jDBC.Connection parent_connection;
	/// batch list contain list of insert and update commands
	private ArrayList<String> batchList = new ArrayList<String> ();
	/// is statement is closed or not
	private boolean closed = false;
	/// object from dbms backend of app will replaced with class a4raf
	/// implements
	private Controller core;
	/// result data of last execution
	private ResultSet currentSet;
	/// update count of last execution
	private int currentCount = -1;

	public Statement(jDBC.Connection parent, Controller core) {
		this.parent_connection = parent;
		this.core = core;
	}

	/*
	 * void addBatch(String sql) throws SQLException Adds the given SQL command
	 * to the current list of commmands for this Statement object. The commands
	 * in this list can be executed as a batch by calling the method
	 * executeBatch. Note:This method cannot be called on a PreparedStatement or
	 * CallableStatement.
	 * 
	 * Parameters: sql - typically this is a SQL INSERT or UPDATE statement
	 * Throws: SQLException - if a database access error occurs, this method is
	 * called on a closed Statement, the driver does not support batch updates,
	 * the method is called on a PreparedStatement or CallableStatement Since:
	 * 1.2 See Also: executeBatch(), DatabaseMetaData.supportsBatchUpdates()
	 */

	// check whether database connected exists and statement is not cloed yet
	private void checkDatabaseAndClosing() throws SQLException {
		if (closed) {
			SQLExceptions.closedStatement();
		}

	}

	private ResultSet turnArrayToResultset(ResultSetMetaData data) throws SQLException {
		return new ResultSet(this, data);
	}

	/// add command to batch list
	@Override
	public void addBatch(String sql) throws SQLException {
		checkDatabaseAndClosing();
		batchList.add(sql);
		logtoFile("SQL string is added to the batch.");
		
	}

	/// clear all batch list
	@Override
	public void clearBatch() throws SQLException {
		checkDatabaseAndClosing();
		batchList.clear();
		logtoFile("Batch is cleared.");
	}

	// release sources on closing statement
	@Override
	public void close() throws SQLException {
		batchList = null;
		core = null;
		if(currentSet != null) currentSet.close();
		closed = true;
	}

	/*
	 * boolean execute(String sql) throws SQLException Executes the given SQL
	 * statement, which may return multiple results. In some (uncommon)
	 * situations, a single SQL statement may return multiple result sets and/or
	 * update counts. Normally you can ignore this unless you are (1) executing
	 * a stored procedure that you know may return multiple results or (2) you
	 * are dynamically executing an unknown SQL string. The execute method
	 * executes an SQL statement and indicates the form of the first result. You
	 * must then use the methods getResultSet or getUpdateCount to retrieve the
	 * result, and getMoreResults to move to any subsequent result(s).
	 * 
	 * Note:This method cannot be called on a PreparedStatement or
	 * CallableStatement.
	 * 
	 * Parameters: sql - any SQL statement Returns: true if the first result is
	 * a ResultSet object; false if it is an update count or there are no
	 * results Throws: SQLException - if a database access error occurs, this
	 * method is called on a closed Statement, the method is called on a
	 * PreparedStatement or CallableStatement SQLTimeoutException - when the
	 * driver has determined that the timeout value that was specified by the
	 * setQueryTimeout method has been exceeded and has at least attempted to
	 * cancel the currently running Statement
	 */
	@Override
	public boolean execute(String sql) throws SQLException {
		checkDatabaseAndClosing();
		CommandsTypes fi = core.getFirstIdentifier(sql);
		/// select means that i did not update
		if (fi.equals(CommandsTypes.SELECT) || fi.equals(CommandsTypes.UNION)) {
			ResultSet res = executeQuery(sql);
			if(res.getData().getTable().size() == 0) {
				return false;			
			}
			return true;
		} else {
			executeUpdate(sql);
			return false;
		}
	}

	/*
	 * int[] executeBatch() throws SQLException Submits a batch of commands to
	 * the database for execution and if all commands execute successfully,
	 * returns an array of update counts. The int elements of the array that is
	 * returned are ordered to correspond to the commands in the batch, which
	 * are ordered according to the order in which they were added to the batch.
	 * The elements in the array returned by the method executeBatch may be one
	 * of the following: A number greater than or equal to zero -- indicates
	 * that the command was processed successfully and is an update count giving
	 * the number of rows in the database that were affected by the command's
	 * execution A value of SUCCESS_NO_INFO -- indicates that the command was
	 * processed successfully but that the number of rows affected is unknown If
	 * one of the commands in a batch update fails to execute properly, this
	 * method throws a BatchUpdateException, and a JDBC driver may or may not
	 * continue to process the remaining commands in the batch. However, the
	 * driver's behavior must be consistent with a particular DBMS, either
	 * always continuing to process commands or never continuing to process
	 * commands. If the driver continues processing after a failure, the array
	 * returned by the method BatchUpdateException.getUpdateCounts will contain
	 * as many elements as there are commands in the batch, and at least one of
	 * the elements will be the following:
	 * 
	 * A value of EXECUTE_FAILED -- indicates that the command failed to execute
	 * successfully and occurs only if a driver continues to process commands
	 * after a command fails The possible implementations and return values have
	 * been modified in the Java 2 SDK, Standard Edition, version 1.3 to
	 * accommodate the option of continuing to proccess commands in a batch
	 * update after a BatchUpdateException obejct has been thrown.
	 * 
	 * Returns: an array of update counts containing one element for each
	 * command in the batch. The elements of the array are ordered according to
	 * the order in which commands were added to the batch. Throws: SQLException
	 * - if a database access error occurs, this method is called on a closed
	 * Statement or the driver does not support batch statements. Throws
	 * BatchUpdateException (a subclass of SQLException) if one of the commands
	 * sent to the database fails to execute properly or attempts to return a
	 * result set. SQLTimeoutException - when the driver has determined that the
	 * timeout value that was specified by the setQueryTimeout method has been
	 * exceeded and has at least attempted to cancel the currently running
	 * Statement
	 */
	@Override
	public int[] executeBatch() throws SQLException {
		int[] ret = new int[batchList.size()];
		checkDatabaseAndClosing();
		for (int i = 0; i < batchList.size(); i++) {
			CommandsTypes fi = core.getFirstIdentifier(new String(batchList.get(i)));
			/// select means that i did not update
			if (fi.equals(CommandsTypes.SELECT) || fi.equals(CommandsTypes.UNION)) {
				ret[i] = SUCCESS_NO_INFO;
			} else {
				try {
					/// number of rowupdates done i donna know 0 >>
					/// ulter,create,drop,use or not
					int res = core.excuteUpdateSql(new String(batchList.get(i)));
					ret[i] = res;
				} catch (Exception e) {
					/// command failed
					ret[i] = EXECUTE_FAILED;
				}
			}
		}
		logtoFile("Batch is executed.");
		return ret;
	}

	/*
	 * ResultSet executeQuery(String sql) throws SQLException Executes the given
	 * SQL statement, which returns a single ResultSet object. Note:This method
	 * cannot be called on a PreparedStatement or CallableStatement.
	 * 
	 * Parameters: sql - an SQL statement to be sent to the database, typically
	 * a static SQL SELECT statement Returns: a ResultSet object that contains
	 * the data produced by the given query; never null Throws: SQLException -
	 * if a database access error occurs, this method is called on a closed
	 * Statement, the given SQL statement produces anything other than a single
	 * ResultSet object, the method is called on a PreparedStatement or
	 * CallableStatement SQLTimeoutException - when the driver has determined
	 * that the timeout value that was specified by the setQueryTimeout method
	 * has been exceeded and has at least attempted to cancel the currently
	 * running Statement
	 */
	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		/// check whether data base exists and statement is not closed
		checkDatabaseAndClosing();
		if (!core.getFirstIdentifier(sql).equals(CommandsTypes.SELECT)
				&& !core.getFirstIdentifier(sql).equals(CommandsTypes.UNION))
			SQLExceptions.wrongCommand();
		currentSet = turnArrayToResultset(core.excuteSelectSql(sql));
		currentCount = -1;
		return new ResultSet(currentSet);
	}

	/*
	 * int executeUpdate(String sql) throws SQLException Executes the given SQL
	 * statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL
	 * statement that returns nothing, such as an SQL DDL statement. Note:This
	 * method cannot be called on a PreparedStatement or CallableStatement.
	 * 
	 * Parameters: sql - an SQL Data Manipulation Language (DML) statement, such
	 * as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing,
	 * such as a DDL statement. Returns: either (1) the row count for SQL Data
	 * Manipulation Language (DML) statements or (2) 0 for SQL statements that
	 * return nothing Throws: SQLException - if a database access error occurs,
	 * this method is called on a closed Statement, the given SQL statement
	 * produces a ResultSet object, the method is called on a PreparedStatement
	 * or CallableStatement SQLTimeoutException - when the driver has determined
	 * that the timeout value that was specified by the setQueryTimeout method
	 * has been exceeded and has at least attempted to cancel the currently
	 * running Statement
	 */
	@Override
	public int executeUpdate(String sql) throws SQLException {
		/// check whether data base exists and statement is not closed
		checkDatabaseAndClosing();
		CommandsTypes fi = core.getFirstIdentifier(sql);
		if (fi.equals(CommandsTypes.SELECT) || fi.equals(CommandsTypes.UNION))
			throw new SQLException("this function is not require to return data");
		currentCount = core.excuteUpdateSql(sql);
		currentSet = null;
		return currentCount;
	}

	/*
	 * Connection getConnection() throws SQLException Retrieves the Connection
	 * object that produced this Statement object. Returns: the connection that
	 * produced this statement Throws: SQLException - if a database access error
	 * occurs or this method is called on a closed Statement
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return this.parent_connection;
	}

	/*
	 * ResultSet getResultSet() throws SQLException Retrieves the current result
	 * as a ResultSet object. This method should be called only once per result.
	 * Returns: the current result as a ResultSet object or null if the result
	 * is an update count or there are no more results Throws: SQLException - if
	 * a database access error occurs or this method is called on a closed
	 * Statement
	 */
	@Override
	public ResultSet getResultSet() throws SQLException {
		// check whether database exist and statement is not closed
		checkDatabaseAndClosing();
		return this.currentSet;
	}

	/*
	 * int getUpdateCount() throws SQLException Retrieves the current result as
	 * an update count; if the result is a ResultSet object or there are no more
	 * results, -1 is returned. This method should be called only once per
	 * result. Returns: the current result as an update count; -1 if the current
	 * result is a ResultSet object or there are no more results Throws:
	 * SQLException - if a database access error occurs or this method is called
	 * on a closed Statement
	 */
	@Override
	public int getUpdateCount() throws SQLException {
		// check whether database exist and statement is not closed
		checkDatabaseAndClosing();
		return currentCount;
	}

	private void logtoFile(String string) {
		Log4j.getInstance().info(string);
	}
	
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void cancel() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

}
