package com.swat.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public final class CallableStatementWrapper extends PreparedStatementWrapper
        implements CallableStatement {
    private CallableStatement wrapper;

    private CallableStatementWrapper(String appName, CallableStatement cstmt,
            long conID, long stmtID) {
        super(appName, cstmt, null, conID, stmtID);
        this.wrapper = cstmt;
    }

    static CallableStatementWrapper getInstance(String appName,
            CallableStatement cstmt, long conID) {
        long stmtID = getNextID();

        CallableStatementWrapper callWrapper = null;

        callWrapper = new CallableStatementWrapper(appName, cstmt, conID,
                stmtID);
        CSRLogger.put(appName, callWrapper, conID, stmtID);

        return callWrapper;
    }

    @Override
    public Array getArray(int parameterIndex) throws SQLException {
        try {
            return wrapper.getArray(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getArray("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Array getArray(String parameterName) throws SQLException {
        try {
            return wrapper.getArray(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getArray("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        try {
            return wrapper.getBigDecimal(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBigDecimal("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        try {
            return wrapper.getBigDecimal(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBigDecimal("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BigDecimal getBigDecimal(int parameterIndex, int scale)
            throws SQLException {
        try {
            return wrapper.getBigDecimal(parameterIndex, scale);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBigDecimal("
                    + parameterIndex + "," + scale + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Blob getBlob(int parameterIndex) throws SQLException {
        try {
            return wrapper.getBlob(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBlob("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Blob getBlob(String parameterName) throws SQLException {
        try {
            return wrapper.getBlob(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBlob("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public boolean getBoolean(int parameterIndex) throws SQLException {
        try {
            return wrapper.getBoolean(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBoolean("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public boolean getBoolean(String parameterName) throws SQLException {
        try {
            return wrapper.getBoolean(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBoolean("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public byte getByte(int parameterIndex) throws SQLException {
        try {
            return wrapper.getByte(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getByte("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public byte getByte(String parameterName) throws SQLException {
        try {
            return wrapper.getByte(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getByte("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public byte[] getBytes(int parameterIndex) throws SQLException {
        try {
            return wrapper.getBytes(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBytes("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public byte[] getBytes(String parameterName) throws SQLException {
        try {
            return wrapper.getBytes(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getBytes("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        try {
            return wrapper.getCharacterStream(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : getCharacterStream(" + parameterIndex
                            + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        try {
            return wrapper.getCharacterStream(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : getCharacterStream(" + parameterName
                            + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Clob getClob(int parameterIndex) throws SQLException {
        try {
            return wrapper.getClob(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getClob("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Clob getClob(String parameterName) throws SQLException {
        try {
            return wrapper.getClob(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getClob("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Date getDate(int parameterIndex) throws SQLException {
        try {
            return wrapper.getDate(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getDate("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Date getDate(String parameterName) throws SQLException {
        try {
            return wrapper.getDate(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getDate("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        try {
            return wrapper.getDate(parameterIndex, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getDate("
                    + parameterIndex + "," + cal + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        try {
            return wrapper.getDate(parameterName, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getDate("
                    + parameterName + "," + cal + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public double getDouble(int parameterIndex) throws SQLException {
        try {
            return wrapper.getDouble(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getDouble("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public double getDouble(String parameterName) throws SQLException {
        try {
            return wrapper.getDouble(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getDouble("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public float getFloat(int parameterIndex) throws SQLException {
        try {
            return wrapper.getFloat(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getFloat("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public float getFloat(String parameterName) throws SQLException {
        try {
            return wrapper.getFloat(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getFloat("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public int getInt(int parameterIndex) throws SQLException {
        try {
            return wrapper.getInt(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getInt("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public int getInt(String parameterName) throws SQLException {
        try {
            return wrapper.getInt(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getInt("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public long getLong(int parameterIndex) throws SQLException {
        try {
            return wrapper.getLong(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getLong("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public long getLong(String parameterName) throws SQLException {
        try {
            return wrapper.getLong(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getLong("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        try {
            return wrapper.getNCharacterStream(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : getNCharacterStream(" + parameterIndex
                            + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        try {
            return wrapper.getNCharacterStream(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : getNCharacterStream(" + parameterName
                            + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public NClob getNClob(int parameterIndex) throws SQLException {
        try {
            return wrapper.getNClob(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getNClob("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        try {
            return wrapper.getNClob(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getNClob("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        try {
            return wrapper.getNString(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getNString("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public String getNString(String parameterName) throws SQLException {
        try {
            return wrapper.getNString(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getNString("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Object getObject(int parameterIndex) throws SQLException {
        try {
            return wrapper.getObject(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getObject("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Object getObject(String parameterName) throws SQLException {
        try {
            return wrapper.getObject(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getObject("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map)
            throws SQLException {
        try {
            return wrapper.getObject(parameterIndex, map);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getObject("
                    + parameterIndex + "," + map + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map)
            throws SQLException {
        try {
            return wrapper.getObject(parameterName, map);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getObject("
                    + parameterName + "," + map + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public Ref getRef(int parameterIndex) throws SQLException {
        try {
            return wrapper.getRef(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getRef("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Ref getRef(String parameterName) throws SQLException {
        try {
            return wrapper.getRef(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getRef("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        try {
            return wrapper.getRowId(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getRowId("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        try {
            return wrapper.getRowId(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getRowId("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        try {
            return wrapper.getSQLXML(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getSQLXML("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        try {
            return wrapper.getSQLXML(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getSQLXML("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public short getShort(int parameterIndex) throws SQLException {
        try {
            return wrapper.getShort(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getShort("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public short getShort(String parameterName) throws SQLException {
        try {
            return wrapper.getShort(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getShort("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public String getString(int parameterIndex) throws SQLException {
        try {
            return wrapper.getString(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getString("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public String getString(String parameterName) throws SQLException {
        try {
            return wrapper.getString(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getString("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Time getTime(int parameterIndex) throws SQLException {
        try {
            return wrapper.getTime(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTime("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Time getTime(String parameterName) throws SQLException {
        try {
            return wrapper.getTime(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTime("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        try {
            return wrapper.getTime(parameterIndex, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTime("
                    + parameterIndex + "," + cal + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        try {
            return wrapper.getTime(parameterName, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTime("
                    + parameterName + "," + cal + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        try {
            return wrapper.getTimestamp(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTimestamp("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Timestamp getTimestamp(String parameterName) throws SQLException {
        try {
            return wrapper.getTimestamp(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTimestamp("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal)
            throws SQLException {
        try {
            return wrapper.getTimestamp(parameterIndex, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTimestamp("
                    + parameterIndex + "," + cal + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public Timestamp getTimestamp(String parameterName, Calendar cal)
            throws SQLException {
        try {
            return wrapper.getTimestamp(parameterName, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getTimestamp("
                    + parameterName + "," + cal + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public URL getURL(int parameterIndex) throws SQLException {
        try {
            return wrapper.getURL(parameterIndex);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getURL("
                    + parameterIndex + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public URL getURL(String parameterName) throws SQLException {
        try {
            return wrapper.getURL(parameterName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : getURL("
                    + parameterName + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType)
            throws SQLException {
        try {
            wrapper.registerOutParameter(parameterIndex, sqlType);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : registerOutParameter(" + parameterIndex + ","
                            + sqlType + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType)
            throws SQLException {
        try {
            wrapper.registerOutParameter(parameterName, sqlType);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : registerOutParameter(" + parameterName + ","
                            + sqlType + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale)
            throws SQLException {
        try {
            wrapper.registerOutParameter(parameterIndex, sqlType, scale);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : registerOutParameter(" + parameterIndex + ","
                            + sqlType + "," + scale + ") Threw "
                            + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType,
            String typeName) throws SQLException {
        try {
            wrapper.registerOutParameter(parameterIndex, sqlType, typeName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : registerOutParameter(" + parameterIndex + ","
                            + sqlType + "," + typeName + ") Threw "
                            + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType,
            int scale) throws SQLException {
        try {
            wrapper.registerOutParameter(parameterName, sqlType, scale);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : registerOutParameter(" + parameterName + ","
                            + sqlType + "," + scale + ") Threw "
                            + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType,
            String typeName) throws SQLException {
        try {
            wrapper.registerOutParameter(parameterName, sqlType, typeName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : registerOutParameter(" + parameterName + ","
                            + sqlType + "," + typeName + ") Threw "
                            + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x)
            throws SQLException {
        try {
            wrapper.setAsciiStream(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setAsciiStream("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, int length)
            throws SQLException {
        try {
            wrapper.setAsciiStream(parameterName, x, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setAsciiStream("
                    + parameterName + "," + x + "," + length + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length)
            throws SQLException {
        try {
            wrapper.setAsciiStream(parameterName, x, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setAsciiStream("
                    + parameterName + "," + x + "," + length + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setBigDecimal(String parameterName, BigDecimal x)
            throws SQLException {
        try {
            wrapper.setBigDecimal(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBigDecimal("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x)
            throws SQLException {
        try {
            wrapper.setBinaryStream(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBinaryStream("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, int length)
            throws SQLException {
        try {
            wrapper.setBinaryStream(parameterName, x, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBinaryStream("
                    + parameterName + "," + x + "," + length + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length)
            throws SQLException {
        try {
            wrapper.setBinaryStream(parameterName, x, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBinaryStream("
                    + parameterName + "," + x + "," + length + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        try {
            wrapper.setBlob(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBlob("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream)
            throws SQLException {
        try {
            wrapper.setBlob(parameterName, inputStream);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBlob("
                    + parameterName + "," + inputStream + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream,
            long length) throws SQLException {
        try {
            wrapper.setBlob(parameterName, inputStream, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBlob("
                    + parameterName + "," + inputStream + "," + length
                    + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        try {
            wrapper.setBoolean(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBoolean("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setByte(String parameterName, byte x) throws SQLException {
        try {
            wrapper.setByte(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setByte("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setBytes(String parameterName, byte[] x) throws SQLException {
        try {
            wrapper.setBytes(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setBytes("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader)
            throws SQLException {
        try {
            wrapper.setCharacterStream(parameterName, reader);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : setCharacterStream(" + parameterName + ","
                            + reader + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader,
            int length) throws SQLException {
        try {
            wrapper.setCharacterStream(parameterName, reader, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : setCharacterStream(" + parameterName + ","
                            + reader + "," + length + ") Threw "
                            + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader,
            long length) throws SQLException {
        try {
            wrapper.setCharacterStream(parameterName, reader, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : setCharacterStream(" + parameterName + ","
                            + reader + "," + length + ") Threw "
                            + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        try {
            wrapper.setClob(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setClob("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setClob(String parameterName, Reader reader)
            throws SQLException {
        try {
            wrapper.setClob(parameterName, reader);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setClob("
                    + parameterName + "," + reader + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setClob(String parameterName, Reader reader, long length)
            throws SQLException {
        try {
            wrapper.setClob(parameterName, reader, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setClob("
                    + parameterName + "," + reader + "," + length + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setDate(String parameterName, Date x) throws SQLException {
        try {
            wrapper.setDate(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setDate("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setDate(String parameterName, Date x, Calendar cal)
            throws SQLException {
        try {
            wrapper.setDate(parameterName, x, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setDate("
                    + parameterName + "," + x + "," + cal + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setDouble(String parameterName, double x) throws SQLException {
        try {
            wrapper.setDouble(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setDouble("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setFloat(String parameterName, float x) throws SQLException {
        try {
            wrapper.setFloat(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setFloat("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setInt(String parameterName, int x) throws SQLException {
        try {
            wrapper.setInt(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setInt("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setLong(String parameterName, long x) throws SQLException {
        try {
            wrapper.setLong(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setLong("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value)
            throws SQLException {
        try {
            wrapper.setNCharacterStream(parameterName, value);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : setNCharacterStream(" + parameterName + ","
                            + value + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value,
            long length) throws SQLException {
        try {
            wrapper.setNCharacterStream(parameterName, value, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : setNCharacterStream(" + parameterName + ","
                            + value + "," + length + ") Threw "
                            + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        try {
            wrapper.setNClob(parameterName, value);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : setNClob(" + parameterName + "," + value
                            + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setNClob(String parameterName, Reader reader)
            throws SQLException {
        try {
            wrapper.setNClob(parameterName, reader);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setNClob("
                    + parameterName + "," + reader + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length)
            throws SQLException {
        try {
            wrapper.setNClob(parameterName, reader, length);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setNClob("
                    + parameterName + "," + reader + "," + length + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setNString(String parameterName, String value)
            throws SQLException {
        try {
            wrapper.setNString(parameterName, value);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID,
                    "CllblStmt : setNString(" + parameterName + "," + value
                            + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setNull(String parameterName, int sqlType) throws SQLException {
        try {
            wrapper.setNull(parameterName, sqlType);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setNull("
                    + parameterName + "," + sqlType + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setNull(String parameterName, int sqlType, String typeName)
            throws SQLException {
        try {
            wrapper.setNull(parameterName, sqlType, typeName);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setNull("
                    + parameterName + "," + sqlType + "," + typeName
                    + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setObject(String parameterName, Object x) throws SQLException {
        try {
            wrapper.setObject(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setObject("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType)
            throws SQLException {
        try {
            wrapper.setObject(parameterName, x, targetSqlType);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setObject("
                    + parameterName + "," + x + "," + targetSqlType
                    + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType,
            int scale) throws SQLException {
        try {
            wrapper.setObject(parameterName, x, targetSqlType, scale);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setObject("
                    + parameterName + "," + x + "," + targetSqlType + ","
                    + scale + ") Threw " + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        try {
            wrapper.setRowId(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setRowId("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setSQLXML(String parameterName, SQLXML xmlObject)
            throws SQLException {
        try {
            wrapper.setSQLXML(parameterName, xmlObject);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setSQLXML("
                    + parameterName + "," + xmlObject + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setShort(String parameterName, short x) throws SQLException {
        try {
            wrapper.setShort(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setShort("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setString(String parameterName, String x) throws SQLException {
        try {
            wrapper.setString(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setString("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setTime(String parameterName, Time x) throws SQLException {
        try {
            wrapper.setTime(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setTime("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setTime(String parameterName, Time x, Calendar cal)
            throws SQLException {
        try {
            wrapper.setTime(parameterName, x, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setTime("
                    + parameterName + "," + x + "," + cal + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x)
            throws SQLException {
        try {
            wrapper.setTimestamp(parameterName, x);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setTimestamp("
                    + parameterName + "," + x + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
            throws SQLException {
        try {
            wrapper.setTimestamp(parameterName, x, cal);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setTimestamp("
                    + parameterName + "," + x + "," + cal + ") Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

    @Override
    public void setURL(String parameterName, URL val) throws SQLException {
        try {
            wrapper.setURL(parameterName, val);
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : setURL("
                    + parameterName + "," + val + ") Threw " + e.getMessage(),
                    true);
            throw e;
        }
    }

    @Override
    public boolean wasNull() throws SQLException {
        try {
            return wrapper.wasNull();
        } catch (SQLException e) {
            QueryLogger.print(appName, stmtID, "CllblStmt : wasNull() Threw "
                    + e.getMessage(), true);
            throw e;
        }
    }

}
