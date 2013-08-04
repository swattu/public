package com.swat.sql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Properties;

import com.swat.util.XPropUtil;

/**
 * The Wrapper for the Connection for various added functionalitied like Logging
 * of Queries, Cascade Closing of Statement/ResultSet etc.
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public class ConnectionWrapper implements Connection {
  // For Logging purposes (Connections)
  private static File conFile = null;

  private static FileWriter confw = null;

  private static long counter = 0;

  private static final String type = WrapperConstants.CONNECTION;

  private static XPropUtil swatProperties = XPropUtil.SWAT_PROP;

  public static boolean LOG_DETAILS = false;

  private String appName = null;

  private Connection con = null;

  private long conID = 0;

  public ConnectionWrapper(Connection con) {
    this(WrapperConstants.APP_NAME, con);

  }

  public ConnectionWrapper(String appName, Connection con) {
    printConnection();
    this.appName = appName;
    this.conID = ++counter;
    this.con = con;

    CSRLogger.checkAndPrint();
    CSRLogger.put(appName, this, counter);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return int
   * 
   * @throws SQLException
   */
  @Override
  public int getHoldability() throws SQLException {
    return con.getHoldability();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return int
   * 
   * @throws SQLException
   */
  @Override
  public int getTransactionIsolation() throws SQLException {
    return con.getTransactionIsolation();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @throws SQLException
   */
  @Override
  public void clearWarnings() throws SQLException {
    con.clearWarnings();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @throws SQLException
   */
  @Override
  public void close() throws SQLException {
    try {
      CSRLogger.remove(type, conID);
      con.close();
    } catch (SQLException e) {
      QueryLogger.print(appName, conID, "Conection : Threw During Close " + e.getMessage(), true);

      throw e;
    }
  }

  /**
   * Required Connection Method
   * 
   * 
   * @throws SQLException
   */
  @Override
  public void commit() throws SQLException {
    con.commit();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @throws SQLException
   */
  @Override
  public void rollback() throws SQLException {
    con.rollback();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return boolean
   * 
   * @throws SQLException
   */
  @Override
  public boolean getAutoCommit() throws SQLException {
    return con.getAutoCommit();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return boolean
   * 
   * @throws SQLException
   */
  @Override
  public boolean isClosed() throws SQLException {
    return con.isClosed();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return boolean
   * 
   * @throws SQLException
   */
  @Override
  public boolean isReadOnly() throws SQLException {
    return con.isReadOnly();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param x
   * 
   * @throws SQLException
   */
  @Override
  public void setHoldability(int x) throws SQLException {
    con.setHoldability(x);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param x
   * 
   * @throws SQLException
   */
  @Override
  public void setTransactionIsolation(int x) throws SQLException {
    con.setTransactionIsolation(x);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param boo
   * 
   * @throws SQLException
   */
  @Override
  public void setAutoCommit(boolean boo) throws SQLException {
    con.setAutoCommit(boo);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param boo
   * 
   * @throws SQLException
   */
  @Override
  public void setReadOnly(boolean boo) throws SQLException {
    con.setReadOnly(boo);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return String
   * 
   * @throws SQLException
   */
  @Override
  public String getCatalog() throws SQLException {
    return con.getCatalog();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * 
   * @throws SQLException
   */
  @Override
  public void setCatalog(String str) throws SQLException {
    con.setCatalog(str);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return DatabaseMetaData
   * 
   * @throws SQLException
   */
  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    return con.getMetaData();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return SQLWarning
   * 
   * @throws SQLException
   */
  @Override
  public SQLWarning getWarnings() throws SQLException {
    return con.getWarnings();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return Savepoint
   * 
   * @throws SQLException
   */
  @Override
  public Savepoint setSavepoint() throws SQLException {
    return con.setSavepoint();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param sp
   * 
   * @throws SQLException
   */
  @Override
  public void releaseSavepoint(Savepoint sp) throws SQLException {
    con.releaseSavepoint(sp);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param sp
   * 
   * @throws SQLException
   */
  @Override
  public void rollback(Savepoint sp) throws SQLException {
    con.rollback(sp);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return Statement
   * 
   * @throws SQLException
   */
  @Override
  public Statement createStatement() throws SQLException {
    return StatementWrapper.getInstance(appName, con.createStatement(), conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param x
   * @param y
   * 
   * @return Statement
   * 
   * @throws SQLException
   */
  @Override
  public Statement createStatement(int x, int y) throws SQLException {
    return StatementWrapper.getInstance(appName, con.createStatement(x, y), conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param x
   * @param y
   * @param z
   * 
   * @return Statement
   * 
   * @throws SQLException
   */
  @Override
  public Statement createStatement(int x, int y, int z) throws SQLException {
    return StatementWrapper.getInstance(appName, con.createStatement(x, y, z), conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @return Map
   * 
   * @throws SQLException
   */
  @Override
  public java.util.Map<String, Class<?>> getTypeMap() throws SQLException {
    return con.getTypeMap();
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param map
   * 
   * @throws SQLException
   */
  @Override
  public void setTypeMap(java.util.Map<String, Class<?>> map) throws SQLException {
    con.setTypeMap(map);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * 
   * @return String
   * 
   * @throws SQLException
   */
  @Override
  public String nativeSQL(String str) throws SQLException {
    return con.nativeSQL(str);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * 
   * @return CallableStatement
   * 
   * @throws SQLException
   */
  @Override
  public CallableStatement prepareCall(String str) throws SQLException {
    return CallableStatementWrapper.getInstance(appName, con.prepareCall(str), conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * @param x
   * @param y
   * 
   * @return CallableStatement
   * 
   * @throws SQLException
   */
  @Override
  public CallableStatement prepareCall(String str, int x, int y) throws SQLException {
    return CallableStatementWrapper.getInstance(appName, con.prepareCall(str, x, y), conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * @param x
   * @param y
   * @param z
   * 
   * @return CallableStatement
   * 
   * @throws SQLException
   */
  @Override
  public CallableStatement prepareCall(String str, int x, int y, int z) throws SQLException {
    return CallableStatementWrapper.getInstance(appName, con.prepareCall(str, x, y, z), conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * 
   * @return PreparedStatement
   * 
   * @throws SQLException
   */
  @Override
  public PreparedStatement prepareStatement(String str) throws SQLException {
    return PreparedStatementWrapper.getInstance(appName, con.prepareStatement(str), str, conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * @param x
   * 
   * @return PreparedStatement
   * 
   * @throws SQLException
   */
  @Override
  public PreparedStatement prepareStatement(String str, int x) throws SQLException {
    return PreparedStatementWrapper.getInstance(appName, con.prepareStatement(str, x), str, conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * @param x
   * @param y
   * 
   * @return PreparedStatement
   * 
   * @throws SQLException
   */
  @Override
  public PreparedStatement prepareStatement(String str, int x, int y) throws SQLException {
    return PreparedStatementWrapper.getInstance(appName, con.prepareStatement(str, x, y), str, conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * @param x
   * @param y
   * @param z
   * 
   * @return PreparedStatement
   * 
   * @throws SQLException
   */
  @Override
  public PreparedStatement prepareStatement(String str, int x, int y, int z) throws SQLException {
    return PreparedStatementWrapper.getInstance(appName, con.prepareStatement(str, x, y, z), str, conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * @param x
   * 
   * @return PreparedStatement
   * 
   * @throws SQLException
   */
  @Override
  public PreparedStatement prepareStatement(String str, int[] x) throws SQLException {
    return PreparedStatementWrapper.getInstance(appName, con.prepareStatement(str, x), str, conID);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * 
   * @return Savepoint
   * 
   * @throws SQLException
   */
  @Override
  public Savepoint setSavepoint(String str) throws SQLException {
    return con.setSavepoint(str);
  }

  /**
   * Required Connection Method
   * 
   * 
   * @param str
   * @param strs
   * 
   * @return PreparedStatement
   * 
   * @throws SQLException
   */
  @Override
  public PreparedStatement prepareStatement(String str, String[] strs) throws SQLException {
    return PreparedStatementWrapper.getInstance(appName, con.prepareStatement(str, strs), str, conID);
  }

  /**
   * Prints the Method Trace to a File.
   * 
   */
  public static void printConnection() {
    boolean methodTrace = swatProperties.getBoolean("wrapper.logging.methodTrace", LOG_DETAILS);

    if (methodTrace == false) {
      return;
    }

    StringBuffer sbr = new StringBuffer();
    String[] logOnly = {
        "com.pronto", "_jspservice"
    };
    String[] filter = {
        "connectionfactory", "connectionwrapper"
    };

    Exception e = new Exception();
    StackTraceElement[] ste = e.getStackTrace();
    String method = "";

    for (int x = ste.length - 1; x > 2; x--) {
      boolean first = false;
      boolean second = false;

      for (String element : logOnly) {
        if ((ste[x].getClassName().toLowerCase().indexOf(element) >= 0) || (ste[x].getMethodName().toLowerCase().indexOf(element) >= 0)) {
          first = true;
        }

        if ((ste[x - 1].getClassName().toLowerCase().indexOf(element) >= 0) || (ste[x - 1].getMethodName().toLowerCase().indexOf(element) >= 0)) {
          second = true;
        }
      }

      for (String element : filter) {
        if ((ste[x].getClassName().toLowerCase().indexOf(element) >= 0) || (ste[x].getMethodName().toLowerCase().indexOf(element) >= 0)) {
          first = false;
        }

        if ((ste[x - 1].getClassName().toLowerCase().indexOf(element) >= 0) || (ste[x - 1].getMethodName().toLowerCase().indexOf(element) >= 0)) {
          second = false;
        }
      }

      if (first) {
        method = ste[x].getClassName() + "\t" + ste[x].getMethodName() + "(" + ste[x].getLineNumber() + ")\t";
      }

      if (second && (method.length() > 0)) {
        sbr.append(method + ste[x - 1].getClassName() + "\t" + ste[x - 1].getMethodName() + "(" + ste[x - 1].getLineNumber() + ")\n");
      }
    }

    write(sbr + "\n");
  }

  private static synchronized void write(String str) {
    try {
      if (confw == null) {
        if (conFile == null) {
          conFile = new File(System.getProperty("user.home") + "/swat", "MethodTrace.log");
        }

        confw = new FileWriter(conFile, false);
      }

      confw.write(str);
      confw.flush();
    } catch (IOException e) {
    }
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return con.createArrayOf(typeName, elements);
  }

  @Override
  public Blob createBlob() throws SQLException {
    return con.createBlob();
  }

  @Override
  public Clob createClob() throws SQLException {
    return con.createClob();
  }

  @Override
  public NClob createNClob() throws SQLException {
    return con.createNClob();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return con.createSQLXML();
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return con.createStruct(typeName, attributes);
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return con.getClientInfo();
  }

  @Override
  public String getClientInfo(String name) throws SQLException {
    return con.getCatalog();
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    return con.isValid(timeout);
  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    con.setClientInfo(properties);
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    con.setClientInfo(name, value);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return con.isWrapperFor(iface);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return con.unwrap(iface);
  }
}
