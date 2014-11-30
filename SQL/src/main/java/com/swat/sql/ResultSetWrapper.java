package com.swat.sql;

import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.Map;

/**
 * The Wrapper for the ResultSet
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public final class ResultSetWrapper implements ResultSet {
    private static long counter = 0;

    private final static String type = WrapperConstants.RESULTSET;

    private String appName = null;

    private int records = -1;

    ResultSet rst = null;

    private long rstID = 0;

    private long startTime = 0;

    private long stmtID = 0;

    private boolean printed = false;

    private ResultSetWrapper(String appName, ResultSet rst, long stmtID,
            long rstID) {
        this.appName = appName;
        this.rst = rst;
        this.stmtID = stmtID;
        this.rstID = rstID;
    }

    /**
     * Get the Wrapper to the ResultSet for the given Application
     * 
     * 
     * @param appName
     * @param rst
     * @param conID
     * @param stmtID
     * 
     * @return ResultSetWrapper
     */
    static synchronized ResultSetWrapper getInstance(String appName,
            ResultSet rst, long conID, long stmtID) {
        counter++;

        ResultSetWrapper rstWrapper = null;

        rstWrapper = new ResultSetWrapper(appName, rst, stmtID, counter);
        CSRLogger.put(appName, rstWrapper, conID, stmtID, counter);

        return rstWrapper;
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getConcurrency() throws SQLException {
        return rst.getConcurrency();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getFetchDirection() throws SQLException {
        return rst.getFetchDirection();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getFetchSize() throws SQLException {
        return rst.getFetchSize();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getRow() throws SQLException {
        return rst.getRow();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getType() throws SQLException {
        return rst.getType();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void afterLast() throws SQLException {
        rst.afterLast();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void beforeFirst() throws SQLException {
        rst.beforeFirst();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void cancelRowUpdates() throws SQLException {
        rst.cancelRowUpdates();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void clearWarnings() throws SQLException {
        rst.clearWarnings();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void close() throws SQLException {
        try {
            CSRLogger.remove(type, rstID);

            long timeTaken = System.currentTimeMillis() - startTime;

            if (printed == false) {
                printed = true;

                try {
                    if (records == -1) {
                        QueryLogger.print(appName, stmtID,
                                "ResultSet : Closed without any iteration.",
                                true);
                    } else if ((rst.isAfterLast() == true)
                            || ((records == 1) && (rst.next() == false))) {
                        QueryLogger.print(appName, stmtID,
                                "ResultSet : Fully Iterating " + records
                                        + " Records took " + timeTaken
                                        + " mSec.");
                    } else if (records >= 0) {
                        QueryLogger.print(appName, stmtID,
                                "ResultSet : Partially Iterating " + records
                                        + " Records took " + timeTaken
                                        + " mSec.", true);
                    }
                } catch (SQLException e) {
                }
            }

            records = -2;
            rst.close();
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "ResultSet : Threw During Close " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void deleteRow() throws SQLException {
        rst.deleteRow();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void insertRow() throws SQLException {
        rst.insertRow();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void moveToCurrentRow() throws SQLException {
        rst.moveToCurrentRow();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void moveToInsertRow() throws SQLException {
        rst.moveToInsertRow();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void refreshRow() throws SQLException {
        rst.refreshRow();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @throws SQLException
     */
    public void updateRow() throws SQLException {
        rst.updateRow();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean first() throws SQLException {
        return rst.first();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean isAfterLast() throws SQLException {
        return rst.isAfterLast();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean isBeforeFirst() throws SQLException {
        return rst.isBeforeFirst();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean isFirst() throws SQLException {
        return rst.isFirst();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean isLast() throws SQLException {
        return rst.isLast();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean last() throws SQLException {
        return rst.last();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean next() throws SQLException {
        boolean status = rst.next();

        if (records < 0) {
            startTime = System.currentTimeMillis();
            records = 0;
        }

        if (status == true) {
            records++;
        } else {
            long timeTaken = System.currentTimeMillis() - startTime;

            QueryLogger.print(appName, stmtID, "ResultSet : Fully Iterating "
                    + records + " Records took " + timeTaken + " mSec.");
            printed = true;
        }

        return status;
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean previous() throws SQLException {
        return rst.previous();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean rowDeleted() throws SQLException {
        return rst.rowDeleted();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean rowInserted() throws SQLException {
        return rst.rowInserted();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean rowUpdated() throws SQLException {
        return rst.rowUpdated();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean wasNull() throws SQLException {
        return rst.wasNull();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return byte
     * 
     * @throws SQLException
     */
    public byte getByte(int x) throws SQLException {
        try {
            return rst.getByte(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return double
     * 
     * @throws SQLException
     */
    public double getDouble(int x) throws SQLException {
        try {
            return rst.getDouble(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return float
     * 
     * @throws SQLException
     */
    public float getFloat(int x) throws SQLException {
        try {
            return rst.getFloat(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getInt(int x) throws SQLException {
        try {
            return rst.getInt(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return long
     * 
     * @throws SQLException
     */
    public long getLong(int x) throws SQLException {
        try {
            return rst.getLong(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return short
     * 
     * @throws SQLException
     */
    public short getShort(int x) throws SQLException {
        try {
            return rst.getShort(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void setFetchDirection(int x) throws SQLException {
        rst.setFetchDirection(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void setFetchSize(int x) throws SQLException {
        rst.setFetchSize(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @throws SQLException
     */
    public void updateNull(int x) throws SQLException {
        rst.updateNull(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean absolute(int x) throws SQLException {
        return rst.absolute(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean getBoolean(int x) throws SQLException {
        try {
            return rst.getBoolean(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean relative(int x) throws SQLException {
        return rst.relative(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return byte[]
     * 
     * @throws SQLException
     */
    public byte[] getBytes(int x) throws SQLException {
        return rst.getBytes(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param b
     * 
     * @throws SQLException
     */
    public void updateByte(int x, byte b) throws SQLException {
        rst.updateByte(x, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param d
     * 
     * @throws SQLException
     */
    public void updateDouble(int x, double d) throws SQLException {
        rst.updateDouble(x, d);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param f
     * 
     * @throws SQLException
     */
    public void updateFloat(int x, float f) throws SQLException {
        rst.updateFloat(x, f);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param y
     * 
     * @throws SQLException
     */
    public void updateInt(int x, int y) throws SQLException {
        rst.updateInt(x, y);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param l
     * 
     * @throws SQLException
     */
    public void updateLong(int x, long l) throws SQLException {
        rst.updateLong(x, l);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param s
     * 
     * @throws SQLException
     */
    public void updateShort(int x, short s) throws SQLException {
        rst.updateShort(x, s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param b
     * 
     * @throws SQLException
     */
    public void updateBoolean(int x, boolean b) throws SQLException {
        rst.updateBoolean(x, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param b
     * 
     * @throws SQLException
     */
    public void updateBytes(int x, byte[] b) throws SQLException {
        rst.updateBytes(x, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return InputStream
     * 
     * @throws SQLException
     */
    public java.io.InputStream getAsciiStream(int x) throws SQLException {
        return rst.getAsciiStream(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return InputStream
     * 
     * @throws SQLException
     */
    public java.io.InputStream getBinaryStream(int x) throws SQLException {
        return rst.getBinaryStream(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return InputStream
     * 
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    public java.io.InputStream getUnicodeStream(int x) throws SQLException {
        return rst.getUnicodeStream(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param is
     * @param y
     * 
     * @throws SQLException
     */
    public void updateAsciiStream(int x, java.io.InputStream is, int y)
            throws SQLException {
        rst.updateAsciiStream(x, is, y);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param is
     * @param y
     * 
     * @throws SQLException
     */
    public void updateBinaryStream(int x, java.io.InputStream is, int y)
            throws SQLException {
        rst.updateBinaryStream(x, is, y);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Reader
     * 
     * @throws SQLException
     */
    public java.io.Reader getCharacterStream(int x) throws SQLException {
        return rst.getCharacterStream(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param r
     * @param y
     * 
     * @throws SQLException
     */
    public void updateCharacterStream(int x, java.io.Reader r, int y)
            throws SQLException {
        rst.updateCharacterStream(x, r, y);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Object
     * 
     * @throws SQLException
     */
    public java.lang.Object getObject(int x) throws SQLException {
        try {
            return rst.getObject(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param o
     * 
     * @throws SQLException
     */
    public void updateObject(int x, java.lang.Object o) throws SQLException {
        rst.updateObject(x, o);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param o
     * @param y
     * 
     * @throws SQLException
     */
    public void updateObject(int x, java.lang.Object o, int y)
            throws SQLException {
        rst.updateObject(x, o, y);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return String
     * 
     * @throws SQLException
     */
    public java.lang.String getCursorName() throws SQLException {
        return rst.getCursorName();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return String
     * 
     * @throws SQLException
     */
    public java.lang.String getString(int x) throws SQLException {
        try {
            return rst.getString(x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : Index " + x
                    + " Threw " + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param s
     * 
     * @throws SQLException
     */
    public void updateString(int x, java.lang.String s) throws SQLException {
        rst.updateString(x, s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return byte
     * 
     * @throws SQLException
     */
    public byte getByte(java.lang.String s) throws SQLException {
        try {
            return rst.getByte(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return double
     * 
     * @throws SQLException
     */
    public double getDouble(java.lang.String s) throws SQLException {
        try {
            return rst.getDouble(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return float
     * 
     * @throws SQLException
     */
    public float getFloat(java.lang.String s) throws SQLException {
        try {
            return rst.getFloat(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int findColumn(java.lang.String s) throws SQLException {
        return rst.findColumn(s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return int
     * 
     * @throws SQLException
     */
    public int getInt(java.lang.String s) throws SQLException {
        try {
            return rst.getInt(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }

    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return long
     * 
     * @throws SQLException
     */
    public long getLong(java.lang.String s) throws SQLException {
        try {
            return rst.getLong(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return short
     * 
     * @throws SQLException
     */
    public short getShort(java.lang.String s) throws SQLException {
        try {
            return rst.getShort(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @throws SQLException
     */
    public void updateNull(java.lang.String s) throws SQLException {
        rst.updateNull(s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return boolean
     * 
     * @throws SQLException
     */
    public boolean getBoolean(java.lang.String s) throws SQLException {
        try {
            return rst.getBoolean(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return byte[]
     * 
     * @throws SQLException
     */
    public byte[] getBytes(java.lang.String s) throws SQLException {
        try {
            return rst.getBytes(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param b
     * 
     * @throws SQLException
     */
    public void updateByte(java.lang.String s, byte b) throws SQLException {
        rst.updateByte(s, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param d
     * 
     * @throws SQLException
     */
    public void updateDouble(java.lang.String s, double d) throws SQLException {
        rst.updateDouble(s, d);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param f
     * 
     * @throws SQLException
     */
    public void updateFloat(java.lang.String s, float f) throws SQLException {
        rst.updateFloat(s, f);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param x
     * 
     * @throws SQLException
     */
    public void updateInt(java.lang.String s, int x) throws SQLException {
        rst.updateInt(s, x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param l
     * 
     * @throws SQLException
     */
    public void updateLong(java.lang.String s, long l) throws SQLException {
        rst.updateLong(s, l);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param str
     * @param s
     * 
     * @throws SQLException
     */
    public void updateShort(java.lang.String str, short s) throws SQLException {
        rst.updateShort(str, s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param b
     * 
     * @throws SQLException
     */
    public void updateBoolean(java.lang.String s, boolean b)
            throws SQLException {
        rst.updateBoolean(s, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param b
     * 
     * @throws SQLException
     */
    public void updateBytes(java.lang.String s, byte[] b) throws SQLException {
        rst.updateBytes(s, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return BigDecimal
     * 
     * @throws SQLException
     */
    public java.math.BigDecimal getBigDecimal(int x) throws SQLException {
        return rst.getBigDecimal(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param y
     * 
     * @return BigDecimal
     * 
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    public java.math.BigDecimal getBigDecimal(int x, int y) throws SQLException {
        return rst.getBigDecimal(x, y);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param bd
     * 
     * @throws SQLException
     */
    public void updateBigDecimal(int x, java.math.BigDecimal bd)
            throws SQLException {
        rst.updateBigDecimal(x, bd);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return URL
     * 
     * @throws SQLException
     */
    public java.net.URL getURL(int x) throws SQLException {
        return rst.getURL(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Array
     * 
     * @throws SQLException
     */
    public Array getArray(int x) throws SQLException {
        return rst.getArray(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param a
     * 
     * @throws SQLException
     */
    public void updateArray(int x, Array a) throws SQLException {
        rst.updateArray(x, a);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Blob
     * 
     * @throws SQLException
     */
    public Blob getBlob(int x) throws SQLException {
        return rst.getBlob(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param b
     * 
     * @throws SQLException
     */
    public void updateBlob(int x, Blob b) throws SQLException {
        rst.updateBlob(x, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Clob
     * 
     * @throws SQLException
     */
    public Clob getClob(int x) throws SQLException {
        return rst.getClob(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param c
     * 
     * @throws SQLException
     */
    public void updateClob(int x, Clob c) throws SQLException {
        rst.updateClob(x, c);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Date
     * 
     * @throws SQLException
     */
    public Date getDate(int x) throws SQLException {
        return rst.getDate(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param d
     * 
     * @throws SQLException
     */
    public void updateDate(int x, Date d) throws SQLException {
        rst.updateDate(x, d);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Ref
     * 
     * @throws SQLException
     */
    public Ref getRef(int x) throws SQLException {
        return rst.getRef(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param r
     * 
     * @throws SQLException
     */
    public void updateRef(int x, Ref r) throws SQLException {
        rst.updateRef(x, r);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return ResultSetMetaData
     * 
     * @throws SQLException
     */
    public ResultSetMetaData getMetaData() throws SQLException {
        return rst.getMetaData();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return SQLWarning
     * 
     * @throws SQLException
     */
    public SQLWarning getWarnings() throws SQLException {
        return rst.getWarnings();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @return Statement
     * 
     * @throws SQLException
     */
    public Statement getStatement() throws SQLException {
        return rst.getStatement();
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Time
     * 
     * @throws SQLException
     */
    public Time getTime(int x) throws SQLException {
        return rst.getTime(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param t
     * 
     * @throws SQLException
     */
    public void updateTime(int x, Time t) throws SQLException {
        rst.updateTime(x, t);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * 
     * @return Timestamp
     * 
     * @throws SQLException
     */
    public Timestamp getTimestamp(int x) throws SQLException {
        return rst.getTimestamp(x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param t
     * 
     * @throws SQLException
     */
    public void updateTimestamp(int x, Timestamp t) throws SQLException {
        rst.updateTimestamp(x, t);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return InputStream
     * 
     * @throws SQLException
     */
    public java.io.InputStream getAsciiStream(java.lang.String s)
            throws SQLException {
        return rst.getAsciiStream(s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return InputStream
     * 
     * @throws SQLException
     */
    public java.io.InputStream getBinaryStream(java.lang.String s)
            throws SQLException {
        return rst.getBinaryStream(s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return InputStream
     * 
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    public java.io.InputStream getUnicodeStream(java.lang.String s)
            throws SQLException {
        return rst.getUnicodeStream(s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param is
     * @param x
     * 
     * @throws SQLException
     */
    public void updateAsciiStream(java.lang.String s, java.io.InputStream is,
            int x) throws SQLException {
        rst.updateAsciiStream(s, is, x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param is
     * @param x
     * 
     * @throws SQLException
     */
    public void updateBinaryStream(java.lang.String s, java.io.InputStream is,
            int x) throws SQLException {
        rst.updateBinaryStream(s, is, x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Reader
     * 
     * @throws SQLException
     */
    public java.io.Reader getCharacterStream(java.lang.String s)
            throws SQLException {
        return rst.getCharacterStream(s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param r
     * @param x
     * 
     * @throws SQLException
     */
    public void updateCharacterStream(java.lang.String s, java.io.Reader r,
            int x) throws SQLException {
        rst.updateCharacterStream(s, r, x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Object
     * 
     * @throws SQLException
     */
    public java.lang.Object getObject(java.lang.String s) throws SQLException {
        try {
            return rst.getObject(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param obj
     * 
     * @throws SQLException
     */
    public void updateObject(java.lang.String s, java.lang.Object obj)
            throws SQLException {
        rst.updateObject(s, obj);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param obj
     * @param x
     * 
     * @throws SQLException
     */
    public void updateObject(java.lang.String s, java.lang.Object obj, int x)
            throws SQLException {
        rst.updateObject(s, obj, x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return String
     * 
     * @throws SQLException
     */
    public java.lang.String getString(java.lang.String s) throws SQLException {
        try {
            return rst.getString(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param r
     * @param s
     * 
     * @throws SQLException
     */
    public void updateString(java.lang.String r, java.lang.String s)
            throws SQLException {
        rst.updateString(r, s);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return BigDecimal
     * 
     * @throws SQLException
     */
    public java.math.BigDecimal getBigDecimal(java.lang.String s)
            throws SQLException {
        try {
            return rst.getBigDecimal(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param x
     * 
     * @return BigDecimal
     * 
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    public java.math.BigDecimal getBigDecimal(java.lang.String s, int x)
            throws SQLException {
        return rst.getBigDecimal(s, x);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param bd
     * 
     * @throws SQLException
     */
    public void updateBigDecimal(java.lang.String s, java.math.BigDecimal bd)
            throws SQLException {
        rst.updateBigDecimal(s, bd);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return URL
     * 
     * @throws SQLException
     */
    public java.net.URL getURL(java.lang.String s) throws SQLException {
        try {
            return rst.getURL(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Array
     * 
     * @throws SQLException
     */
    public Array getArray(java.lang.String s) throws SQLException {
        try {
            return rst.getArray(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param a
     * 
     * @throws SQLException
     */
    public void updateArray(java.lang.String s, Array a) throws SQLException {
        rst.updateArray(s, a);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Blob
     * 
     * @throws SQLException
     */
    public Blob getBlob(java.lang.String s) throws SQLException {
        try {
            return rst.getBlob(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param b
     * 
     * @throws SQLException
     */
    public void updateBlob(java.lang.String s, Blob b) throws SQLException {
        rst.updateBlob(s, b);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Clob
     * 
     * @throws SQLException
     */
    public Clob getClob(java.lang.String s) throws SQLException {
        try {
            return rst.getClob(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param c
     * 
     * @throws SQLException
     */
    public void updateClob(java.lang.String s, Clob c) throws SQLException {
        rst.updateClob(s, c);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Date
     * 
     * @throws SQLException
     */
    public Date getDate(java.lang.String s) throws SQLException {
        try {
            return rst.getDate(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param d
     * 
     * @throws SQLException
     */
    public void updateDate(java.lang.String s, Date d) throws SQLException {
        rst.updateDate(s, d);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param c
     * 
     * @return Date
     * 
     * @throws SQLException
     */
    public Date getDate(int x, java.util.Calendar c) throws SQLException {
        return rst.getDate(x, c);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Ref
     * 
     * @throws SQLException
     */
    public Ref getRef(java.lang.String s) throws SQLException {
        try {
            return rst.getRef(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param r
     * 
     * @throws SQLException
     */
    public void updateRef(java.lang.String s, Ref r) throws SQLException {
        rst.updateRef(s, r);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Time
     * 
     * @throws SQLException
     */
    public Time getTime(java.lang.String s) throws SQLException {
        try {
            return rst.getTime(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param t
     * 
     * @throws SQLException
     */
    public void updateTime(java.lang.String s, Time t) throws SQLException {
        rst.updateTime(s, t);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param c
     * 
     * @return Time
     * 
     * @throws SQLException
     */
    public Time getTime(int x, java.util.Calendar c) throws SQLException {
        return rst.getTime(x, c);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * 
     * @return Timestamp
     * 
     * @throws SQLException
     */
    public Timestamp getTimestamp(java.lang.String s) throws SQLException {
        try {
            return rst.getTimestamp(s);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "ResultSet : " + s + " Threw "
                    + e.getMessage(), true);

            throw e;
        }
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param t
     * 
     * @throws SQLException
     */
    public void updateTimestamp(java.lang.String s, Timestamp t)
            throws SQLException {
        rst.updateTimestamp(s, t);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param x
     * @param c
     * 
     * @return Timestamp
     * 
     * @throws SQLException
     */
    public Timestamp getTimestamp(int x, java.util.Calendar c)
            throws SQLException {
        return rst.getTimestamp(x, c);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param m
     * 
     * @return Object
     * 
     * @throws SQLException
     */

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param c
     * 
     * @return Date
     * 
     * @throws SQLException
     */
    public Date getDate(java.lang.String s, java.util.Calendar c)
            throws SQLException {
        return rst.getDate(s, c);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param c
     * 
     * @return Time
     * 
     * @throws SQLException
     */
    public Time getTime(java.lang.String s, java.util.Calendar c)
            throws SQLException {
        return rst.getTime(s, c);
    }

    /**
     * Required ResultSet Method
     * 
     * 
     * @param s
     * @param c
     * 
     * @return Timestamp
     * 
     * @throws SQLException
     */
    public Timestamp getTimestamp(java.lang.String s, java.util.Calendar c)
            throws SQLException {
        return rst.getTimestamp(s, c);
    }

    public int getHoldability() throws SQLException {
        return rst.getHoldability();
    }

    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return rst.getNCharacterStream(columnIndex);
    }

    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return rst.getNCharacterStream(columnLabel);
    }

    public NClob getNClob(int columnIndex) throws SQLException {
        return rst.getNClob(columnIndex);
    }

    public NClob getNClob(String columnLabel) throws SQLException {
        return rst.getNClob(columnLabel);
    }

    public String getNString(int columnIndex) throws SQLException {
        return rst.getNString(columnIndex);
    }

    public String getNString(String columnLabel) throws SQLException {
        return rst.getNString(columnLabel);
    }

    public RowId getRowId(int columnIndex) throws SQLException {
        return rst.getRowId(columnIndex);
    }

    public RowId getRowId(String columnLabel) throws SQLException {
        return rst.getRowId(columnLabel);
    }

    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return rst.getSQLXML(columnIndex);
    }

    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return rst.getSQLXML(columnLabel);
    }

    public boolean isClosed() throws SQLException {
        return rst.isClosed();
    }

    public void updateAsciiStream(int columnIndex, InputStream x)
            throws SQLException {
        rst.updateAsciiStream(columnIndex, x);
    }

    public void updateAsciiStream(String columnLabel, InputStream x)
            throws SQLException {
        rst.updateAsciiStream(columnLabel, x);
    }

    public void updateAsciiStream(int columnIndex, InputStream x, long length)
            throws SQLException {
        rst.updateAsciiStream(columnIndex, x, length);

    }

    public void updateAsciiStream(String columnLabel, InputStream x, long length)
            throws SQLException {
        rst.updateAsciiStream(columnLabel, x, length);
    }

    public void updateBinaryStream(int columnIndex, InputStream x)
            throws SQLException {
        rst.updateBinaryStream(columnIndex, x);
    }

    public void updateBinaryStream(String columnLabel, InputStream x)
            throws SQLException {
        rst.updateBinaryStream(columnLabel, x);
    }

    public void updateBinaryStream(int columnIndex, InputStream x, long length)
            throws SQLException {
        rst.updateBinaryStream(columnIndex, x, length);
    }

    public void updateBinaryStream(String columnLabel, InputStream x,
            long length) throws SQLException {
        rst.updateBinaryStream(columnLabel, x, length);
    }

    public void updateBlob(int columnIndex, InputStream inputStream)
            throws SQLException {
        rst.updateBlob(columnIndex, inputStream);
    }

    public void updateBlob(String columnLabel, InputStream inputStream)
            throws SQLException {
        rst.updateBlob(columnLabel, inputStream);
    }

    public void updateBlob(int columnIndex, InputStream inputStream, long length)
            throws SQLException {
        rst.updateBlob(columnIndex, inputStream, length);
    }

    public void updateBlob(String columnLabel, InputStream inputStream,
            long length) throws SQLException {
        rst.updateBlob(columnLabel, inputStream, length);
    }

    public void updateCharacterStream(int columnIndex, Reader x)
            throws SQLException {
        rst.updateCharacterStream(columnIndex, x);
    }

    public void updateCharacterStream(String columnLabel, Reader reader)
            throws SQLException {
        rst.updateCharacterStream(columnLabel, reader);
    }

    public void updateCharacterStream(int columnIndex, Reader x, long length)
            throws SQLException {
        rst.updateCharacterStream(columnIndex, x, length);
    }

    public void updateCharacterStream(String columnLabel, Reader reader,
            long length) throws SQLException {
        rst.updateCharacterStream(columnLabel, reader, length);
    }

    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        rst.updateClob(columnIndex, reader);
    }

    public void updateClob(String columnLabel, Reader reader)
            throws SQLException {
        rst.updateClob(columnLabel, reader);
    }

    public void updateClob(int columnIndex, Reader reader, long length)
            throws SQLException {
        rst.updateClob(columnIndex, reader, length);
    }

    public void updateClob(String columnLabel, Reader reader, long length)
            throws SQLException {
        rst.updateClob(columnLabel, reader, length);
    }

    public void updateNCharacterStream(int columnIndex, Reader x)
            throws SQLException {
        rst.updateNCharacterStream(columnIndex, x);
    }

    public void updateNCharacterStream(String columnLabel, Reader reader)
            throws SQLException {
        rst.updateNCharacterStream(columnLabel, reader);
    }

    public void updateNCharacterStream(int columnIndex, Reader x, long length)
            throws SQLException {
        rst.updateNCharacterStream(columnIndex, x, length);
    }

    public void updateNCharacterStream(String columnLabel, Reader reader,
            long length) throws SQLException {
        rst.updateNCharacterStream(columnLabel, reader, length);
    }

    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        rst.updateNClob(columnIndex, nClob);
    }

    public void updateNClob(String columnLabel, NClob nClob)
            throws SQLException {
        rst.updateNClob(columnLabel, nClob);
    }

    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        rst.updateNClob(columnIndex, reader);
    }

    public void updateNClob(String columnLabel, Reader reader)
            throws SQLException {
        rst.updateNClob(columnLabel, reader);
    }

    @Override
    public <T> T getObject(int i, Class<T> aClass) throws SQLException {
        return rst.getObject(i, aClass);
    }

    @Override
    public <T> T getObject(String s, Class<T> aClass) throws SQLException {
        return rst.getObject(s, aClass);
    }

    public void updateNClob(int columnIndex, Reader reader, long length)
            throws SQLException {
        rst.updateNClob(columnIndex, reader, length);
    }

    public void updateNClob(String columnLabel, Reader reader, long length)
            throws SQLException {
        rst.updateNClob(columnLabel, reader, length);
    }

    public void updateNString(int columnIndex, String nString)
            throws SQLException {
        rst.updateNString(columnIndex, nString);
    }

    public void updateNString(String columnLabel, String nString)
            throws SQLException {
        rst.updateNString(columnLabel, nString);
    }

    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        rst.updateRowId(columnIndex, x);
    }

    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        rst.updateRowId(columnLabel, x);
    }

    public void updateSQLXML(int columnIndex, SQLXML xmlObject)
            throws SQLException {
        rst.updateSQLXML(columnIndex, xmlObject);
    }

    public void updateSQLXML(String columnLabel, SQLXML xmlObject)
            throws SQLException {
        rst.updateSQLXML(columnLabel, xmlObject);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return rst.isWrapperFor(iface);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return rst.unwrap(iface);
    }

    public Object getObject(int columnIndex, Map<String, Class<?>> map)
            throws SQLException {
        return rst.getObject(columnIndex, map);
    }

    public Object getObject(String columnLabel, Map<String, Class<?>> map)
            throws SQLException {
        return rst.getObject(columnLabel, map);
    }
}
