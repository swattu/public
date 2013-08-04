package com.swat.sql;

import com.swat.util.XPropUtil;

/**
 * Constants for Wrapper Utility
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public interface WrapperConstants {
    static String APP_NAME = "SWAT";

    static String CONNECTION = "CONN";

    static String STATEMENT = "STMT";

    static String RESULTSET = "RSET";

    static String TIME = "Secs";

    static XPropUtil swatProperties = XPropUtil.SWAT_PROP;

    // Default Properties
    static long CLOSE_AFTER = -1; // In mSec. -1 to disable.

    static long IDLE_TIMEOUT = 30 * 1000; // In mSec.

    static long REMOVE_AFTER = 300 * 1000; // In mSec. -1 to disable.

    static long TRACE_INTERVAL = 30 * 1000; // In mSec.

    static boolean EMAIL_LOG = false;

    static boolean AUTO_CLOSE = true;
}
