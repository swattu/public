package com.swat.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * The Wrapper for the PreparedStatement
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public class PreparedStatementWrapper extends StatementWrapper implements PreparedStatement {
  private static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private static String sqlDateFormat = "yyyy-MM-dd";

  private static String sqlTimeFormat = "yyyy-MM-dd HH24:MI:SS";

  private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

  private static NumberFormat nf = NumberFormat.getInstance();

  static {
    nf.setMaximumFractionDigits(20);
    nf.setMinimumFractionDigits(0);
    nf.setMaximumIntegerDigits(20);
    nf.setMinimumIntegerDigits(1);
    nf.setGroupingUsed(false);
  }

  boolean batchBoolean = false;

  private PreparedStatement pstmt = null;

  private String query = null;

  private HashMap<Integer, Object> hash = new HashMap<Integer, Object>();

  private StringBuffer error = new StringBuffer(" ");

  protected PreparedStatementWrapper(String appName, PreparedStatement pstmt, String query, long conID, long stmtID) {
    super(appName, pstmt, conID, stmtID);
    this.pstmt = pstmt;
    this.query = query;
  }

  /**
   * Gets a Wrapper to the provided PreparedStatement for given Application
   * 
   * 
   * @param appName
   * @param pstmt
   * @param query
   * @param conID
   * 
   * @return PreparedStatement
   */
  static PreparedStatementWrapper getInstance(String appName, PreparedStatement pstmt, String query, long conID) {
    long stmtID = getNextID();

    PreparedStatementWrapper prepWrapper = null;

    prepWrapper = new PreparedStatementWrapper(appName, pstmt, query, conID, stmtID);
    CSRLogger.put(appName, prepWrapper, conID, stmtID);

    return prepWrapper;
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @return ResultSet
   * 
   * @throws SQLException
   */
  @Override
  public ResultSet executeQuery() throws SQLException {
    long start = new java.util.Date().getTime();
    ResultSet rst = null;

    try {
      rst = ResultSetWrapper.getInstance(appName, pstmt.executeQuery(), conID, stmtID);

      long end = new java.util.Date().getTime();

      QueryLogger.print(appName, stmtID, getFullQuery("PrepdStmt") + " took " + (end - start) + " mSec.");
    } catch (SQLException e) {
      QueryLogger.print(appName, stmtID, getFullQuery("PrepdStmt") + " Threw " + e.getMessage(), true);

      throw e;
    }

    return rst;
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @return int
   * 
   * @throws SQLException
   */
  @Override
  public int executeUpdate() throws SQLException {
    long start = new java.util.Date().getTime();
    int rst = 0;

    try {
      rst = pstmt.executeUpdate();

      long end = new java.util.Date().getTime();

      QueryLogger.print(appName, stmtID, getFullQuery("PrepdStmt") + " " + rst + " updates took " + (end - start) + " mSec.");
    } catch (SQLException e) {
      QueryLogger.print(appName, stmtID, getFullQuery("PrepdStmt") + " Threw " + e.getMessage(), true);

      throw e;
    }

    return rst;
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @throws SQLException
   */
  @Override
  public void close() throws SQLException {
    try {
      CSRLogger.remove(type, stmtID);
      pstmt.close();
    } catch (SQLException e) {
      QueryLogger.print(appName, stmtID, "PrepdStmt : Threw During Close " + e.getMessage(), true);

      throw e;
    }
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param y
   * 
   * @throws SQLException
   */
  @Override
  public void setNull(int x, int y) throws SQLException {
    put(x, y);
    pstmt.setNull(x, y);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param boo
   * 
   * @throws SQLException
   */
  @Override
  public void setBoolean(int x, boolean boo) throws SQLException {
    put(x, boo);
    pstmt.setBoolean(x, boo);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param b
   * 
   * @throws SQLException
   */
  @Override
  public void setByte(int x, byte b) throws SQLException {
    put(x, new Byte(b));
    pstmt.setByte(x, b);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param s
   * 
   * @throws SQLException
   */
  @Override
  public void setShort(int x, short s) throws SQLException {
    put(x, new Short(s));
    pstmt.setShort(x, s);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param y
   * 
   * @throws SQLException
   */
  @Override
  public void setInt(int x, int y) throws SQLException {
    put(x, new Integer(y));
    pstmt.setInt(x, y);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param l
   * 
   * @throws SQLException
   */
  @Override
  public void setLong(int x, long l) throws SQLException {
    put(x, new Long(l));
    pstmt.setLong(x, l);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param f
   * 
   * @throws SQLException
   */
  @Override
  public void setFloat(int x, float f) throws SQLException {
    put(x, new Float(f));
    pstmt.setFloat(x, f);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param d
   * 
   * @throws SQLException
   */
  @Override
  public void setDouble(int x, double d) throws SQLException {
    put(x, new Double(d));
    pstmt.setDouble(x, d);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param bd
   * 
   * @throws SQLException
   */
  @Override
  public void setBigDecimal(int x, BigDecimal bd) throws SQLException {
    put(x, bd);
    pstmt.setBigDecimal(x, bd);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param str
   * 
   * @throws SQLException
   */
  @Override
  public void setString(int x, String str) throws SQLException {
    put(x, str);
    pstmt.setString(x, str);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param b
   * 
   * @throws SQLException
   */
  @Override
  public void setBytes(int x, byte[] b) throws SQLException {
    put(x, b);
    pstmt.setBytes(x, b);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param d
   * 
   * @throws SQLException
   */
  @Override
  public void setDate(int x, Date d) throws SQLException {
    put(x, d);
    pstmt.setDate(x, d);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param t
   * 
   * @throws SQLException
   */
  @Override
  public void setTime(int x, Time t) throws SQLException {
    put(x, t);
    pstmt.setTime(x, t);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param t
   * 
   * @throws SQLException
   */
  @Override
  public void setTimestamp(int x, Timestamp t) throws SQLException {
    put(x, t);
    pstmt.setTimestamp(x, t);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param in
   * @param y
   * 
   * @throws SQLException
   */
  @Override
  public void setAsciiStream(int x, InputStream in, int y) throws SQLException {
    put(x, in);
    pstmt.setAsciiStream(x, in, y);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param in
   * @param y
   * 
   * @throws SQLException
   */
  @Override
  @SuppressWarnings("deprecation")
  public void setUnicodeStream(int x, InputStream in, int y) throws SQLException {
    put(x, in);
    pstmt.setUnicodeStream(x, in, y);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param in
   * @param y
   * 
   * @throws SQLException
   */
  @Override
  public void setBinaryStream(int x, InputStream in, int y) throws SQLException {
    put(x, in);
    pstmt.setBinaryStream(x, in, y);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @throws SQLException
   */
  @Override
  public void clearParameters() throws SQLException {
    hash.clear();
    pstmt.clearParameters();
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param obj
   * @param y
   * @param z
   * 
   * @throws SQLException
   */
  @Override
  public void setObject(int x, Object obj, int y, int z) throws SQLException {
    put(x, obj);
    pstmt.setObject(x, obj, y, z);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param obj
   * @param y
   * 
   * @throws SQLException
   */
  @Override
  public void setObject(int x, Object obj, int y) throws SQLException {
    put(x, obj);
    pstmt.setObject(x, obj, y);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param obj
   * 
   * @throws SQLException
   */
  @Override
  public void setObject(int x, Object obj) throws SQLException {
    put(x, obj);
    pstmt.setObject(x, obj);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @return boolean
   * 
   * @throws SQLException
   */
  @Override
  public boolean execute() throws SQLException {
    long start = new java.util.Date().getTime();
    boolean rst = false;

    try {
      rst = pstmt.execute();

      long end = new java.util.Date().getTime();

      if (!batchBoolean) {
        if (rst == false) {
          int count = pstmt.getUpdateCount();

          QueryLogger.print(appName, stmtID, getFullQuery("PrepdStmt") + " " + count + " updates took " + (end - start) + " mSec.");
        } else {
          QueryLogger.print(appName, stmtID, getFullQuery("PrepdStmt") + " took " + (end - start) + " mSec.");
        }
      }
    } catch (SQLException e) {
      if (!batchBoolean) {
        QueryLogger.print(appName, stmtID, getFullQuery("PrepdStmt") + " Threw " + e.getMessage(), true);
      } else {
        QueryLogger.print(appName, stmtID, "PrepdStmt : Threw " + e.getMessage(), true);
      }

      throw e;
    }

    return rst;
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @throws SQLException
   */
  @Override
  public void addBatch() throws SQLException {
    batchBoolean = true;
    batch.add(getFullQuery("PrepBatch"));
    pstmt.addBatch();
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @return int[]
   * 
   * @throws SQLException
   */
  @Override
  public int[] executeBatch() throws SQLException {
    int[] rst = null;
    int totalUpdates = 0;

    try {
      long start = System.currentTimeMillis();

      QueryLogger.print(appName, stmtID, "PrepBatch : Started with " + batch.size() + " Statements in Batch.");
      rst = pstmt.executeBatch();

      long end = System.currentTimeMillis();

      for (int x = 0; x < rst.length; x++) {
        if (rst[x] == SUCCESS_NO_INFO) {
          QueryLogger.print(appName, stmtID, batch.get(x) + " No Info on total updates.");
        } else if (rst[x] == EXECUTE_FAILED) {
          QueryLogger.print(appName, stmtID, batch.get(x) + " Batch Continued after Failure.");
        } else {
          totalUpdates += rst[x];
          QueryLogger.print(appName, stmtID, batch.get(x) + " " + rst[x] + " updates.");
        }
      }

      QueryLogger.print(appName, stmtID, "PrepBatch : Total " + batch.size() + " Statements in Batch, Total " + totalUpdates + " updates took " + (end - start)
          + " mSec.");
    } catch (SQLException e) {
      for (int x = 0; x < batch.size(); x++) {
        QueryLogger.print(appName, stmtID, "StmtBatch : " + batch.get(x) + " See Below.", true);
      }

      QueryLogger.print(appName, stmtID, "StmtBatch : Total " + batch.size() + " Statements in Batch. Threw " + e.getMessage(), true);

      throw e;
    } finally {
      batch = new ArrayList<String>();
    }

    return rst;
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param reader
   * @param y
   * 
   * @throws SQLException
   */
  @Override
  public void setCharacterStream(int x, Reader reader, int y) throws SQLException {
    put(x, reader);
    pstmt.setCharacterStream(x, reader, y);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param r
   * 
   * @throws SQLException
   */
  @Override
  public void setRef(int x, Ref r) throws SQLException {
    put(x, r);
    pstmt.setRef(x, r);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param b
   * 
   * @throws SQLException
   */
  @Override
  public void setBlob(int x, Blob b) throws SQLException {
    put(x, b);
    pstmt.setBlob(x, b);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param c
   * 
   * @throws SQLException
   */
  @Override
  public void setClob(int x, Clob c) throws SQLException {
    put(x, c);
    pstmt.setClob(x, c);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param a
   * 
   * @throws SQLException
   */
  @Override
  public void setArray(int x, Array a) throws SQLException {
    put(x, a);
    pstmt.setArray(x, a);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @return ResultSetMetaData
   * 
   * @throws SQLException
   */
  @Override
  public ResultSetMetaData getMetaData() throws SQLException {
    return pstmt.getMetaData();
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param d
   * @param c
   * 
   * @throws SQLException
   */
  @Override
  public void setDate(int x, Date d, Calendar c) throws SQLException {
    put(x, d);
    pstmt.setDate(x, d, c);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param t
   * @param c
   * 
   * @throws SQLException
   */
  @Override
  public void setTime(int x, Time t, Calendar c) throws SQLException {
    put(x, t);
    pstmt.setTime(x, t, c);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param t
   * @param c
   * 
   * @throws SQLException
   */
  @Override
  public void setTimestamp(int x, Timestamp t, Calendar c) throws SQLException {
    put(x, t);
    pstmt.setTimestamp(x, t, c);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param y
   * @param str
   * 
   * @throws SQLException
   */
  @Override
  public void setNull(int x, int y, String str) throws SQLException {
    put(x, str);
    pstmt.setNull(x, y, str);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @param x
   * @param url
   * 
   * @throws SQLException
   */
  @Override
  public void setURL(int x, java.net.URL url) throws SQLException {
    put(x, url);
    pstmt.setURL(x, url);
  }

  /**
   * Required PreparedStatement Method
   * 
   * 
   * @return ParameterMetaData
   * 
   * @throws SQLException
   */
  @Override
  public ParameterMetaData getParameterMetaData() throws SQLException {
    return pstmt.getParameterMetaData();
  }

  private String getFullQuery(String comment) {
    StringTokenizer st = new StringTokenizer(query + " ", "?", false);
    StringBuffer sbr = new StringBuffer(comment + " : ");
    int count = 0;

    sbr.append(st.nextToken());

    while (st.hasMoreTokens()) {
      count++;

      Object obj = hash.get(count);
      String str = null;

      if (obj == null) {
        sbr.append("?");
        error.append(count + " not set, ");
      } else if (obj instanceof String) {
        str = (String) obj;

        if (str.trim().equals("null")) {
          sbr.append("null");
        } else {
          sbr.append("'" + str + "'");
        }
      } else if (obj instanceof java.sql.Timestamp) {
        str = "to_date('" + sdfTime.format((java.util.Date) obj) + "', '" + sqlTimeFormat + "')";
        sbr.append(str);
      } else if (obj instanceof java.sql.Date) {
        str = "to_date('" + sdfDate.format((java.util.Date) obj) + "', '" + sqlDateFormat + "')";
        sbr.append(str);
      } else if (obj instanceof Number) {
        sbr.append("" + nf.format(obj));
      } else {
        sbr.append("'" + obj + "'");
      }

      sbr.append(st.nextToken());
    }

    sbr.append(error);
    error = new StringBuffer(" ");
    hash = new HashMap<Integer, Object>();

    return processStr(sbr.toString());
  }

  private void put(int key, Object value) {
    Object obj = hash.put(key, value);

    if (obj != null) {
      error.append(key + ":{" + obj + "," + value + "}, ");
    }
  }

  @Override
  public void setAsciiStream(int x, InputStream inputStream) throws SQLException {
    put(x, inputStream);
    pstmt.setAsciiStream(x, inputStream);
  }

  @Override
  public void setAsciiStream(int x, InputStream inputStream, long length) throws SQLException {
    put(x, inputStream);
    pstmt.setAsciiStream(x, inputStream, length);
  }

  @Override
  public void setBinaryStream(int x, InputStream inputStream) throws SQLException {
    put(x, inputStream);
    pstmt.setBinaryStream(x, inputStream);
  }

  @Override
  public void setBinaryStream(int x, InputStream inputStream, long length) throws SQLException {
    put(x, inputStream);
    pstmt.setBinaryStream(x, inputStream, length);
  }

  @Override
  public void setBlob(int x, InputStream inputStream) throws SQLException {
    put(x, inputStream);
    pstmt.setBlob(x, inputStream);
  }

  @Override
  public void setBlob(int x, InputStream inputStream, long length) throws SQLException {
    put(x, inputStream);
    pstmt.setBlob(x, inputStream, length);
  }

  @Override
  public void setCharacterStream(int x, Reader reader) throws SQLException {
    put(x, reader);
    pstmt.setCharacterStream(x, reader);
  }

  @Override
  public void setCharacterStream(int x, Reader reader, long length) throws SQLException {
    put(x, reader);
    pstmt.setCharacterStream(x, reader, length);
  }

  @Override
  public void setClob(int x, Reader reader) throws SQLException {
    put(x, reader);
    pstmt.setClob(x, reader);
  }

  @Override
  public void setClob(int x, Reader reader, long length) throws SQLException {
    put(x, reader);
    pstmt.setClob(x, reader, length);
  }

  @Override
  public void setNCharacterStream(int x, Reader reader) throws SQLException {
    put(x, reader);
    pstmt.setNCharacterStream(x, reader);
  }

  @Override
  public void setNCharacterStream(int x, Reader reader, long length) throws SQLException {
    put(x, reader);
    pstmt.setNCharacterStream(x, reader, length);
  }

  @Override
  public void setNClob(int x, NClob value) throws SQLException {
    put(x, value);
    pstmt.setNClob(x, value);
  }

  @Override
  public void setNClob(int x, Reader reader) throws SQLException {
    put(x, reader);
    pstmt.setNClob(x, reader);
  }

  @Override
  public void setNClob(int x, Reader reader, long length) throws SQLException {
    put(x, reader);
    pstmt.setNClob(x, reader, length);
  }

  @Override
  public void setNString(int x, String value) throws SQLException {
    put(x, value);
    pstmt.setNString(x, value);
  }

  @Override
  public void setRowId(int x, RowId rowId) throws SQLException {
    put(x, rowId);
    pstmt.setRowId(x, rowId);
  }

  @Override
  public void setSQLXML(int x, SQLXML xmlObject) throws SQLException {
    put(x, xmlObject);
    pstmt.setSQLXML(x, xmlObject);
  }
}
