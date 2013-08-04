package com.swat.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.swat.util.XPropUtil;

/**
 * The Wrapper for the Statement
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public class StatementWrapper implements Statement {
    private static long counter = 0;

    protected final static String type = WrapperConstants.STATEMENT;;

    private static XPropUtil swatProperties = XPropUtil.SWAT_PROP;

    String appName = null;

    protected long conID = 0;

    Statement stmt = null;

    protected long stmtID = 0;

    ArrayList<String> batch = new ArrayList<String>();

    /**
     * Instantiate a StatementWrapper
     * 
     * 
     * @param appName
     * @param stmt
     * @param conID
     * @param stmtID
     */
    StatementWrapper(String appName, Statement stmt, long conID, long stmtID) {
        this.appName = appName;
        this.stmtID = stmtID;
        this.conID = conID;
        this.stmt = stmt;
    }

    /**
     * Get the Wrapper to the Statement
     * 
     * 
     * @param appName
     * @param con
     * @param conID
     * 
     * @return
     */
    static StatementWrapper getInstance(String appName, Statement con,
            long conID) {
        long stmtID = getNextID();

        StatementWrapper stmtWrapper = null;

        stmtWrapper = new StatementWrapper(appName, con, conID, stmtID);
        CSRLogger.put(appName, stmtWrapper, conID, stmtID);

        return stmtWrapper;
    }

    protected synchronized static long getNextID() {
        return counter++;
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return long
     */
    public long getStmtID() {
        return stmtID;
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * 
     * @return ResultSet
     * 
     * @throws SQLException
     */
    public ResultSet executeQuery(String str) throws SQLException {
        long start = System.currentTimeMillis();
        ResultSet rst = null;

        try {
            rst = ResultSetWrapper.getInstance(appName, stmt.executeQuery(str),
                    conID, stmtID);

            long end = System.currentTimeMillis();

            QueryLogger.print(appName, stmtID, "Statement : " + processStr(str)
                    + " took " + (end - start) + " mSec.");
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "Statement : " + processStr(str)
                    + " Threw " + e.getMessage(), true);

            throw e;
        }

        return rst;
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int executeUpdate(String str) throws SQLException {
        long start = System.currentTimeMillis();
        int rst = 0;

        try {
            rst = stmt.executeUpdate(str);

            long end = System.currentTimeMillis();

            QueryLogger.print(appName, stmtID, "Statement : " + processStr(str)
                    + " " + rst + " updates took " + (end - start) + " mSec.");
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "Statement : " + processStr(str)
                    + " Threw " + e.getMessage(), true);

            throw e;
        }

        return rst;
    }

    /**
     * Required Statement Method
     * 
     * 
     * @throws SQLException
     */
    public void close() throws SQLException {
        try {
            CSRLogger.remove(type, stmtID);
            stmt.close();
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "Statement : Threw During Close " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getMaxFieldSize() throws SQLException {
        return stmt.getMaxFieldSize();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void setMaxFieldSize(int x) throws SQLException {
        stmt.setMaxFieldSize(x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getMaxRows() throws SQLException {
        return stmt.getMaxRows();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void setMaxRows(int x) throws SQLException {
        stmt.setMaxRows(x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param boo
     * 
     * @throws SQLException
     */
    public void setEscapeProcessing(boolean boo) throws SQLException {
        stmt.setEscapeProcessing(boo);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getQueryTimeout() throws SQLException {
        return stmt.getQueryTimeout();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void setQueryTimeout(int x) throws SQLException {
        stmt.setQueryTimeout(x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @throws SQLException
     */
    public void cancel() throws SQLException {
        stmt.cancel();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return SQLWarning
     * 
     * @throws SQLException
     */
    public SQLWarning getWarnings() throws SQLException {
        return stmt.getWarnings();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @throws SQLException
     */
    public void clearWarnings() throws SQLException {
        stmt.clearWarnings();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * 
     * @throws SQLException
     */
    public void setCursorName(String str) throws SQLException {
        stmt.setCursorName(str);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean execute(String str) throws SQLException {
        long start = System.currentTimeMillis();
        boolean rst = false;

        try {
            rst = stmt.execute(str);

            long end = System.currentTimeMillis();
            int count = 0;

            if (rst == false) {
                count = stmt.getUpdateCount();
                QueryLogger.print(appName, stmtID, "Statement : "
                        + processStr(str) + " " + count + " updates took "
                        + (end - start) + " mSec.");
            } else {
                QueryLogger
                        .print(appName, stmtID, "Statement : "
                                + processStr(str) + " took " + (end - start)
                                + " mSec.");
            }
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "Statement : " + processStr(str)
                    + " Threw " + e.getMessage(), true);

            throw e;
        }

        return rst;
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return ResultSet
     * 
     * @throws SQLException
     */
    public ResultSet getResultSet() throws SQLException {
        return ResultSetWrapper.getInstance(appName, stmt.getResultSet(),
                conID, stmtID);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getUpdateCount() throws SQLException {
        return stmt.getUpdateCount();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean getMoreResults() throws SQLException {
        return stmt.getMoreResults();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void setFetchDirection(int x) throws SQLException {
        stmt.setFetchDirection(x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getFetchDirection() throws SQLException {
        return stmt.getFetchDirection();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void setFetchSize(int x) throws SQLException {
        stmt.setFetchSize(x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getFetchSize() throws SQLException {
        return stmt.getFetchSize();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getResultSetConcurrency() throws SQLException {
        return stmt.getResultSetConcurrency();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getResultSetType() throws SQLException {
        return stmt.getResultSetType();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * 
     * @throws SQLException
     */
    public void addBatch(String str) throws SQLException {
        batch.add(str);
        stmt.addBatch(str);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @throws SQLException
     */
    public void clearBatch() throws SQLException {
        batch = new ArrayList<String>();
        stmt.clearBatch();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int[]
     * 
     * @throws SQLException
     */
    public int[] executeBatch() throws SQLException {
        int[] rst = null;
        int totalUpdates = 0;

        try {
            long start = System.currentTimeMillis();

            QueryLogger.print(appName, stmtID, "StmtBatch : Started with "
                    + batch.size() + " Statements in Batch.");
            rst = stmt.executeBatch();

            long end = System.currentTimeMillis();

            for (int x = 0; x < rst.length; x++) {
                if (rst[x] == SUCCESS_NO_INFO) {
                    QueryLogger.print(appName, stmtID, "StmtBatch : "
                            + processStr("" + batch.get(x))
                            + " No Info on total updates.");
                } else if (rst[x] == EXECUTE_FAILED) {
                    QueryLogger.print(appName, stmtID, "StmtBatch : "
                            + processStr("" + batch.get(x))
                            + " Batch Continued after Failure.");
                } else {
                    totalUpdates += rst[x];
                    QueryLogger.print(appName, stmtID, "StmtBatch : "
                            + processStr("" + batch.get(x)) + " " + rst[x]
                            + " updates.");
                }
            }

            QueryLogger.print(appName, stmtID, "StmtBatch : Total "
                    + batch.size() + " Statements in Batch, Total "
                    + totalUpdates + " updates took " + (end - start)
                    + " mSec.");
        } catch (SQLException e) {
            for (int x = 0; x < batch.size(); x++) {
                QueryLogger.print(appName, stmtID, "StmtBatch : "
                        + processStr("" + batch.get(x)) + " See Below.", true);
            }

            QueryLogger.print(appName, stmtID, "StmtBatch : Total "
                    + batch.size() + " Statements in Batch. Threw "
                    + e.getMessage(), true);

            throw e;
        } finally {
            batch = new ArrayList<String>();
        }

        return rst;
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return Connection
     * 
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return stmt.getConnection();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param x
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean getMoreResults(int x) throws SQLException {
        return stmt.getMoreResults(x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return ResultSet
     * 
     * @throws SQLException
     */
    public ResultSet getGeneratedKeys() throws SQLException {
        return ResultSetWrapper.getInstance(appName, stmt.getGeneratedKeys(),
                conID, stmtID);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * @param x
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int executeUpdate(String str, int x) throws SQLException {
        QueryLogger.print(appName, stmtID, "Statement : " + processStr(str));

        return stmt.executeUpdate(str, x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * @param x
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int executeUpdate(String str, int[] x) throws SQLException {
        QueryLogger.print(appName, stmtID, "Statement : " + processStr(str));

        return stmt.executeUpdate(str, x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * @param strs
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int executeUpdate(String str, String[] strs) throws SQLException {
        QueryLogger.print(appName, stmtID, "Statement : " + processStr(str));

        return stmt.executeUpdate(str, strs);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * @param x
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean execute(String str, int x) throws SQLException {
        QueryLogger.print(appName, stmtID, "Statement : " + processStr(str));

        return stmt.execute(str, x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * @param x
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean execute(String str, int[] x) throws SQLException {
        QueryLogger.print(appName, stmtID, "Statement : " + processStr(str));

        return stmt.execute(str, x);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * @param strs
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean execute(String str, String[] strs) throws SQLException {
        QueryLogger.print(appName, stmtID, "Statement : " + processStr(str));

        return stmt.execute(str, strs);
    }

    /**
     * Required Statement Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getResultSetHoldability() throws SQLException {
        return stmt.getResultSetHoldability();
    }

    /**
     * Required Statement Method
     * 
     * 
     * @param str
     * 
     * @return String
     */
    public String processStr(String str) {
        if (str == null) {
            return null;
        }

        // To remove the queries containing Confidential Informations.
        String newStr = str.toLowerCase();
        String[] ignoreTracesAfter = getIgnoreTracesAfter();
        int index = -1;
        int length = 0;

        for (String element : ignoreTracesAfter) {
            index = newStr.indexOf(element);

            if (index > 0) {
                length = element.length();

                break;
            }
        }

        if (index >= 0) {
            str = str.substring(0, index + length) + " ...";
        }

        return str;
    }

    private String[] getIgnoreTracesAfter() {
        String[] ignoreTracesAfter = null;
        String ignoreTraceAfter = swatProperties.getString(appName
                + ".wrapper.logging.ignoreTraceAfter", swatProperties
                .getString("wrapper.logging.ignoreTraceAfter", "password"));

        ignoreTraceAfter = ignoreTraceAfter.toLowerCase();

        StringTokenizer st = new StringTokenizer(ignoreTraceAfter, ", ");
        int size = st.countTokens();

        ignoreTracesAfter = new String[size];

        for (int x = 0; x < size; x++) {
            ignoreTracesAfter[x] = st.nextToken();
        }

        return ignoreTracesAfter;
    }

    public boolean isClosed() throws SQLException {
        return stmt.isClosed();
    }

    public boolean isPoolable() throws SQLException {
        return stmt.isPoolable();
    }

    public void setPoolable(boolean poolable) throws SQLException {
        stmt.setPoolable(poolable);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return stmt.isWrapperFor(iface);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return stmt.unwrap(iface);
    }
}
