package com.swat.sql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//This class is not Thread Safe.

public abstract class SQLUtils {
  private static Logger logger = Logger.getLogger(SQLUtils.class);

  public String getAppName() {
    return "";
  }

  protected Connection con = null;

  ArrayList<Statement> stmts = null;

  ArrayList<ResultSet> rsts = null;

  String prepQuery = null;

  Statement statement = null;

  PreparedStatement pStatement = null;

  ArrayList<ArrayList<PreparedStatement>> arrays = null; // Contains

  // ArrayList for
  // PreparedStatement.

  ArrayList<String> sqls = null; // Contains SQLs for Statement.

  // Default Constructor. Also tells that the Batch Queries will be for
  // Statement.
  public SQLUtils() {
  }

  // Takes Query String for PreparedStatement. Only for Batch Queries.
  public SQLUtils(String prepQuery) {
    this();
    this.prepQuery = prepQuery;
  }

  // Closes any open objects during Garbage Collection.
  @Override
  protected void finalize() {
    if (rsts != null || stmts != null || con != null) {
      logger.fatal("Connection/Statement/ResultSet not closed properly. Hence closing during Garbage Collection.");
      close();
    }
  }

  // Opens a connection and sets it in class variable.
  abstract protected Connection getConnection() throws SQLException;

  // Opens a connection and sets it in class variable.
  private boolean setConnection() throws SQLException {
    if (con != null && !con.isClosed()) {
      return false;
    }
    con = getConnection();
    return true;
  }

  // Adds the open Statement into an ArrayList to keep track.
  private void addStatement(Statement stmt) {
    if (stmt == null) {
      return;
    }
    if (stmts == null) {
      stmts = new ArrayList<Statement>();
    }
    stmts.add(stmt);
  }

  // Adds the open ResultSet into an ArrayList to keep track.
  private void addResultSet(ResultSet rst) {
    if (rst == null) {
      return;
    }
    if (rsts == null) {
      rsts = new ArrayList<ResultSet>();
    }
    rsts.add(rst);
  }

  // Closes all the objects.
  public void close() {
    try {
      close(false);
    } catch (SQLException e) {
      // This exception will never be thrown.
    }
  }

  // Closes all the objects. throwEx tells whether to throw Exception or not
  // if any.
  public void close(boolean throwEx) throws SQLException {
    SQLException sqle = null;
    if (rsts != null) {
      for (int x = 0; x < rsts.size(); x++) {
        try {
          ResultSet rst = rsts.get(x);
          if (rst != null) {
            rst.close();
          }
        } catch (SQLException e) {
          sqle = e;
        }
      }
      rsts.clear();
      rsts = null;
    }
    if (stmts != null) {
      for (int x = 0; x < stmts.size(); x++) {
        try {
          Statement stmt = stmts.get(x);
          if (stmt != null) {
            stmt.close();
          }
        } catch (SQLException e) {
          sqle = e;
        }
      }
      stmts.clear();
      stmts = null;
    }
    try {
      if (con != null) {
        con.close();
      }
    } catch (SQLException e) {
      sqle = e;
    }
    con = null;
    if (throwEx && sqle != null) {
      throw sqle;
    }
  }

  // For Updating with Statement. Doesn't throw Exception is it occurs during
  // close.
  public int executeUpdate(String sql) throws SQLException {
    if (sql == null) {
      throw new SQLException("SQL Can't be null.");
    }
    Statement stmt = null;
    int result = 0;
    try {
      setConnection();
      stmt = con.createStatement();
      result = stmt.executeUpdate(sql);
    } finally {
      close(null, stmt, null);
    }
    return result;
  }

  // For SELECT Query with Statement.
  public ResultSet executeQuery(String sql) throws SQLException {
    if (sql == null) {
      throw new SQLException("SQL Can't be null.");
    }
    Statement stmt = null;
    ResultSet rst = null;
    try {
      setConnection();
      stmt = con.createStatement();
      rst = stmt.executeQuery(sql);
    } finally {
      addStatement(stmt);
      addResultSet(rst);
    }
    return rst;
  }

  // For Batch Query with Statement.
  public void addBatch(String sql) throws SQLException {
    if (prepQuery != null) {
      throw new SQLException("This object is not meant for Statement.");
    }
    if (sql == null) {
      throw new SQLException("SQL Can't be null.");
    }
    if (sqls == null) {
      sqls = new ArrayList<String>();
    }
    sqls.add(sql);
  }

  // For Parameters of the Batch Query with PreparedStatement.
  public void addBatch(ArrayList<PreparedStatement> array) throws SQLException {
    if (prepQuery == null) {
      throw new SQLException("This object is not meant for PreparedStatement.");
    }
    if (arrays == null) {
      arrays = new ArrayList<ArrayList<PreparedStatement>>();
    }
    arrays.add(array);
  }

  // Execution of the Batch Query.
  public int[] executeBatch() throws SQLException {
    int[] result = null;
    if (prepQuery != null && arrays == null) {
      return new int[0];
    }
    if (prepQuery == null && sqls == null) {
      return new int[0];
    }
    setConnection();
    try {
      if (prepQuery == null) {
        statement = con.createStatement();
        for (int x = 0; x < sqls.size(); x++) {
          String sql = sqls.get(x);
          statement.addBatch(sql);
        }
        result = statement.executeBatch();
        sqls.clear();

      } else {
        pStatement = con.prepareStatement(prepQuery);
        for (int x = 0; x < arrays.size(); x++) {
          ArrayList<? extends Object> array = arrays.get(x);
          setParams(pStatement, array);
          pStatement.addBatch();
        }
        result = pStatement.executeBatch();
        arrays.clear();
      }
    } finally {
      addStatement(statement);
      addStatement(pStatement);
      arrays = null;
      sqls = null;
    }
    return result;
  }

  // For Updating with PreparedStatement. Doesn't throw Exception is it occurs
  // during close.
  public int executeUpdate(String sql, List<? extends Object> al) throws SQLException {
    Object[] objs = null;
    if (al != null) {
      objs = al.toArray();
    }
    return executeUpdate(sql, objs);
  }

  public int executeUpdate(String sql, Object... objs) throws SQLException {
    return executeUpdate(false, sql, objs);
  }

  public int executeUpdate(boolean swallowException, String sql, Object... objs) throws SQLException {
    if (sql == null) {
      throw new SQLException("SQL Can't be null.");
    }
    PreparedStatement pstmt = null;
    int result = 0;
    try {
      setConnection();
      pstmt = con.prepareStatement(sql);
      setParams(pstmt, objs);
      result = pstmt.executeUpdate();
    } catch (SQLException e) {
      if (!swallowException) {
        throw e;
      }

    } finally {
      close(null, pstmt, null);
    }
    return result;
  }

  // For SELECT Query with PreparedStatement.
  public ResultSet executeQuery(String sql, List<? extends Object> al) throws SQLException {
    Object[] objs = null;
    if (al != null) {
      objs = al.toArray();
    }
    return executeQuery(sql, objs);
  }

  public ResultSet executeQuery(String sql, Object... objs) throws SQLException {
    if (sql == null) {
      throw new SQLException("SQL Can't be null.");
    }
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    try {
      setConnection();
      pstmt = con.prepareStatement(sql);
      setParams(pstmt, objs);
      rst = pstmt.executeQuery();
    } finally {
      addStatement(pstmt);
      addResultSet(rst);
    }
    return rst;
  }

  public boolean existsRecord(String sql, Object... objs) throws SQLException {
    if (sql == null) {
      throw new SQLException("SQL Can't be null.");
    }
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    try {
      setConnection();
      pstmt = con.prepareStatement(sql);
      setParams(pstmt, objs);
      rst = pstmt.executeQuery();
      if (rst.next()) {
        return true;
      }
      return false;
    } finally {
      close(null, pstmt, rst);
    }
  }

  private void close(Connection connection, Statement statement, ResultSet resultSet) {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException e) {
      }
    }
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
      }
    }
  }

  // Sets given Parameters into the PreparedStatement.
  private void setParams(PreparedStatement pstmt, Object... objs) throws SQLException {
    if (objs == null) {
      return;
    }
    for (Object obj : objs) {
      if (obj == null || obj instanceof Number || obj instanceof java.util.Date || obj instanceof String || obj instanceof Calendar || obj instanceof Boolean) {
      } else {
        SQLException sqle = new SQLException(obj.getClass() + " not supported.");
        sqle.printStackTrace(System.out);
        throw sqle;
      }
    }
    for (int x = 0; x < objs.length; x++) {
      Object object = objs[x];
      if (object instanceof String) {
        pstmt.setString(x + 1, (String) object);
        continue;
      }

      if (object instanceof Byte) {
        pstmt.setByte(x + 1, ((Number) object).byteValue());
        continue;
      }
      if (object instanceof Short) {
        pstmt.setShort(x + 1, ((Number) object).shortValue());
        continue;
      }
      if (object instanceof Integer) {
        pstmt.setInt(x + 1, ((Number) object).intValue());
        continue;
      }
      if (object instanceof Long) {
        // TODO Access doesn't support setLong
        pstmt.setInt(x + 1, ((Number) object).intValue());
        continue;
      }
      if (object instanceof Float) {
        pstmt.setFloat(x + 1, ((Number) object).floatValue());
        continue;
      }
      if (object instanceof Double) {
        pstmt.setDouble(x + 1, ((Number) object).doubleValue());
        continue;
      }

      if (object instanceof Date) {
        pstmt.setDate(x + 1, (Date) object);
        continue;
      }
      if (object instanceof Timestamp) {
        pstmt.setTimestamp(x + 1, (Timestamp) object);
        continue;
      }
      if (object instanceof Calendar) {
        pstmt.setTimestamp(x + 1, new Timestamp(((Calendar) object).getTime().getTime()));
        continue;
      }
      if (object instanceof java.util.Date) {
        pstmt.setDate(x + 1, new Date(((java.util.Date) object).getTime()));
        continue;
      }

      if (object instanceof Boolean) {
        pstmt.setBoolean(x + 1, (Boolean) object);
        continue;
      }

      if (object == null) {
        pstmt.setString(x + 1, null);
        continue;
      }
      // System.out.println(object.toString());

      pstmt.setObject(x + 1, object);
    }
  }
}
