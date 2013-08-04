package com.swat.sql;

import java.sql.*;

/**
 * Information about the critical resources i.e. Connection,Statement and
 * ResultSet.
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public class ConStmtRstVO implements WrapperConstants {
    private String appName = null;

    private CloseType closeType = null;

    private long conID = 0;

    private Object object = null;

    private long creationTime = System.currentTimeMillis();

    private String remarks = null;

    private long rstID = 0;

    private long stmtID = 0;

    /**
     * For Connection
     * 
     * 
     * @param appName
     * @param object
     * @param remarks
     * @param conID
     */
    ConStmtRstVO(String appName, Object object, String remarks, long conID) {
        this(appName, object, remarks, conID, 0, 0);
    }

    /**
     * For Statement
     * 
     * 
     * @param appName
     * @param object
     * @param remarks
     * @param conID
     * @param stmtID
     */
    ConStmtRstVO(String appName, Object object, String remarks, long conID,
            long stmtID) {
        this(appName, object, remarks, conID, stmtID, 0);
    }

    /**
     * For ResultSet
     * 
     * 
     * @param appName
     * @param object
     * @param remarks
     * @param conID
     * @param stmtID
     * @param rstID
     */
    ConStmtRstVO(String appName, Object object, String remarks, long conID,
            long stmtID, long rstID) {
        this.appName = appName;
        this.object = object;
        this.remarks = remarks;
        this.conID = conID;
        this.stmtID = stmtID;
        this.rstID = rstID;
    }

    /**
     * Remarks
     * 
     * 
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Connection/Statement/ResultSet
     * 
     * 
     * @return object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Creation Time
     * 
     * 
     * @return creationTime
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * Connection ID
     * 
     * 
     * @return conID
     */
    public long getConID() {
        return conID;
    }

    /**
     * Statement ID
     * 
     * 
     * @return stmtID
     */
    public long getStmtID() {
        return stmtID;
    }

    /**
     * ResultSet ID
     * 
     * 
     * @return rstID
     */
    public long getRstID() {
        return rstID;
    }

    /**
     * Application Name
     * 
     * 
     * @return appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Set the closing type of the Resource
     * 
     * 
     * @param closeType
     */
    public void setCloseType(CloseType closeType) {
        if (this.closeType == null) {
            this.closeType = closeType;

            boolean autoClose = swatProperties.getBoolean(appName
                    + ".wrapper.logging.autoClose", swatProperties.getBoolean(
                    "wrapper.logging.autoClose", AUTO_CLOSE));

            if ((autoClose == true) && !getKey().startsWith(RESULTSET)) {
                CSRLogger.closeObjects(getKey(), CloseType.CLOSE_AUTO);
            }

            if (closeType != CloseType.CLOSE_NORMAL) {
                CSRLogger.append(this);
            } else {
                long idleTimeOut = swatProperties.getLong(appName
                        + ".wrapper.logging.idleTimeOut", swatProperties
                        .getLong("wrapper.logging.idleTimeOut", IDLE_TIMEOUT));

                if (creationTime + idleTimeOut <= System.currentTimeMillis()) {
                    CSRLogger.append(this);
                }
            }
        }
    }

    /**
     * String Representation of the Resource
     * 
     * 
     * @return string
     */
    @Override
    public String toString() {
        CloseType state = CloseType.OPEN;

        if (closeType != null) {
            state = closeType;
        }

        String toString = appName
                + "\t"
                + getKeyStr(state.toString(), -1)
                + " "
                + getKeyStr(TIME,
                        (System.currentTimeMillis() - creationTime) / 1000)
                + " " + getKeyStr(CONNECTION, conID) + " "
                + getKeyStr(STATEMENT, stmtID) + " "
                + getKeyStr(RESULTSET, rstID) + " " + remarks;

        return toString;
    }

    /**
     * Get Key of the resource
     * 
     * 
     * @return key
     */
    public String getKey() {
        if (rstID > 0) {
            return getKeyStr(RESULTSET, rstID);
        }

        if (stmtID > 0) {
            return getKeyStr(STATEMENT, stmtID);
        }

        return getKeyStr(CONNECTION, conID);
    }

    // This method returns a constant length String from key and keyID

    /**
     * Get Key of the resource
     * 
     * 
     * @param key
     * @param keyID
     * 
     * @return key
     */
    static String getKeyStr(String key, long keyID) {
        String countStr = null;

        if (keyID > 0) {
            countStr = "                   " + keyID;
        } else {
            countStr = "                   ";
        }

        countStr = countStr.substring(countStr.length() - 15);

        if ((key.length() < 15) && (keyID != 0)) {
            countStr = key + countStr.substring(key.length());
        }

        return countStr;
    }

    /**
     * Is Child of the given Key
     * 
     * 
     * @param key
     * 
     * @return true if child else false
     */
    boolean isChild(String key) {
        String keyStr = null;
        boolean status = false;

        if (rstID > 0) {
            keyStr = getKeyStr(STATEMENT, stmtID);

            if (keyStr.equals(key)) {
                status = true;
            } else {
                status = false;
            }
        } else if (stmtID > 0) {
            keyStr = getKeyStr(CONNECTION, conID);

            if (keyStr.equals(key)) {
                status = true;
            } else {
                status = false;
            }
        }

        return status;
    }

    /**
     * Close the Resource
     * 
     */
    void closeObject() {
        try {
            if (object instanceof Connection) {
                Connection con = (Connection) object;

                con.close();
            }

            if (object instanceof Statement) {
                Statement stmt = (Statement) object;

                stmt.close();
            }

            if (object instanceof ResultSet) {
                ResultSet rst = (ResultSet) object;

                rst.close();
            }
        } catch (SQLException e) {
        }
    }
}
