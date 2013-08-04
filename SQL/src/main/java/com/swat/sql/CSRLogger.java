package com.swat.sql;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.swat.util.DateType;
import com.swat.util.FileUtil;
import com.swat.util.MailSender;

/**
 * The Utility class to keep track of the Open Resources and Cascade Close them.
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public final class CSRLogger implements WrapperConstants {
    private static String LOCK = new String("WrapperLock");

    private static FileWriter fw = null;

    private static long lastDateStamp = 0;

    private static StringBuffer sbr = new StringBuffer();

    private static volatile boolean printing = false;

    private static long lastTraced = System.currentTimeMillis();

    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    // For Logging purposes (Unclosed Connections)
    private static Hashtable<String, ConStmtRstVO> hash = new Hashtable<String, ConStmtRstVO>();

    private static MailSender mailSender;

    /**
     * Create the Logging File with append or not
     * 
     * 
     * @param append
     */
    private static synchronized void createWriter(boolean append) {
        try {
            Date now = new Date();

            if (fw != null) {
                fw.close();
                fw = null;
            }
            lastDateStamp = FileUtil.getDateNo(now, DateType.DAY);

            String folderStr = swatProperties.getString("wrapper.logFolder",
                    System.getProperty("user.home") + "/swat");

            File file = new File(folderStr, FileUtil.getFile("CSRLog.log",
                    DateType.DAY));

            fw = new FileWriter(file, append);
            write("File Created.\n\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add the Connection
     * 
     * 
     * @param appName
     * @param con
     * @param conID
     */
    static void put(String appName, Connection con, long conID) {
        put(appName, con, conID, 0, 0);
    }

    /**
     * Add the Statement
     * 
     * 
     * @param appName
     * @param stmt
     * @param conID
     * @param stmtID
     */
    static void put(String appName, Statement stmt, long conID, long stmtID) {
        put(appName, stmt, conID, stmtID, 0);
    }

    /**
     * Add the ResultSet
     * 
     * 
     * @param appName
     * @param object
     * @param conID
     * @param stmtID
     * @param rstID
     */
    static void put(String appName, Object object, long conID, long stmtID,
            long rstID) {
        StringBuffer remarks = new StringBuffer();

        // Getting the calling ClassName and MethodName.
        Exception e = new Exception();
        StackTraceElement[] ste = e.getStackTrace();
        String[] ignoreTraces = getIgnoreTraces(appName);

        outer: for (int x = 1; x < ste.length; x++) {
            String method = ste[x].getClassName() + "."
                    + ste[x].getMethodName() + "(" + ste[x].getLineNumber()
                    + ")";

            for (String element : ignoreTraces) {
                if (method.toLowerCase().indexOf(element) > 0) {
                    continue outer;
                }
            }

            String lastMethod = null;

            lastMethod = ste[x - 1].getClassName() + "."
                    + ste[x - 1].getMethodName() + "("
                    + ste[x - 1].getLineNumber() + ")";
            remarks.append(method + "-->" + lastMethod);

            break;
        }

        ConStmtRstVO conStmtRstVO = new ConStmtRstVO(appName, object, remarks
                .toString(), conID, stmtID, rstID);

        String keyStr = conStmtRstVO.getKey();

        hash.put(keyStr, conStmtRstVO);
    }

    /**
     * Remove the Resource
     * 
     * 
     * @param type
     * @param counter
     */
    static void remove(String type, long counter) {
        remove(type, counter, CloseType.CLOSE_NORMAL);
    }

    /**
     * Remove the Resource with Close Type
     * 
     * 
     * @param type
     * @param counter
     * @param closeType
     */
    static void remove(String type, long counter, CloseType closeType) {
        try {
            // Remove the Object from Hash.
            String keyStr = ConStmtRstVO.getKeyStr(type, counter);
            ConStmtRstVO csrVO = hash.remove(keyStr);

            if (csrVO != null) {
                csrVO.setCloseType(closeType);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Check for the Open Resources and Print to the Log File
     * 
     */
    static void checkAndPrint() {
        long traceInterval = swatProperties.getLong(
                "wrapper.logging.traceInterval", TRACE_INTERVAL);

        synchronized (LOCK) {
            if (printing == true) {
                return;
            }

            if (System.currentTimeMillis() < lastTraced + traceInterval) {
                return;
            }

            printing = true;
        }

        try {
            String keyStr = null;
            boolean print = false;

            if (sbr.length() > 0) {
                print = true;
            }

            lastTraced = System.currentTimeMillis();

            Enumeration<String> enumeration = hash.keys();

            while (enumeration.hasMoreElements()) {
                print = true;
                keyStr = enumeration.nextElement();

                ConStmtRstVO conStmtRstVO = hash.get(keyStr);
                String appName = conStmtRstVO.getAppName();

                long closeAfter = swatProperties.getLong(appName
                        + ".wrapper.logging.closeAfter", swatProperties
                        .getLong("wrapper.logging.closeAfter", CLOSE_AFTER));
                long removeAfter = swatProperties.getLong(appName
                        + ".wrapper.logging.removeAfter", swatProperties
                        .getLong("wrapper.logging.removeAfter", REMOVE_AFTER));

                long creationTime = conStmtRstVO.getCreationTime();

                // Check and close the expired Objects.
                if ((closeAfter > 0)
                        && (creationTime + closeAfter) <= lastTraced) {
                    conStmtRstVO.setCloseType(CloseType.CLOSE_FORCED);
                    conStmtRstVO.closeObject();
                } else if ((removeAfter > 0)
                        && (creationTime + removeAfter <= lastTraced)) {
                    hash.remove(keyStr);
                    conStmtRstVO.setCloseType(CloseType.REMOVED);
                } else {
                    // Print all Open Resources.
                    append(conStmtRstVO);
                }
            }

            // If contents present, Log and Email.
            if (print == true) {
                String subject = "Log of OPEN Connection/Statement/ResultSet at "
                        + sdf.format(new java.util.Date());

                sbr.insert(0, subject + "\n");

                StringBuffer newSbr = sbr;

                sbr = new StringBuffer();

                // write(newSbr+"\n");
                boolean emailLog = swatProperties.getBoolean(
                        "wrapper.logging.emailLog", EMAIL_LOG);

                if (emailLog == true) {
                    email(subject, newSbr.toString());
                }
            }
        } finally {
            printing = false;
        }
    }

    public static void setMailSender(MailSender mailSender) {
        CSRLogger.mailSender = mailSender;
    }

    private static void email(String subject, String body) {
        if (mailSender == null)
            return;
        mailSender.mail(subject, body);
    }

    private static synchronized void write(String str) {
        try {
            Date now = new Date();
            long dateStamp = FileUtil.getDateNo(now, DateType.DAY);

            if (dateStamp != lastDateStamp) {
                createWriter(true);
            }

            fw.write(sdf.format(now) + " " + str);
            fw.flush();
        } catch (Exception ioe) {
        }
    }

    /**
     * Get the String Array of the Methods to Ignore
     * 
     * 
     * @param appName
     * 
     * @return
     */
    static String[] getIgnoreTraces(String appName) {
        String[] ignoreTraces = null;
        String ignoreTrace = swatProperties.getString(appName
                + ".wrapper.logging.ignoreTrace", swatProperties.getString(
                "wrapper.logging.ignoreTrace",
                "Wrapper,Connection,LogOpen,DBConnection"));

        ignoreTrace = ignoreTrace.toLowerCase();

        StringTokenizer st = new StringTokenizer(ignoreTrace, ", ");
        int size = st.countTokens();

        ignoreTraces = new String[size];

        for (int x = 0; x < size; x++) {
            ignoreTraces[x] = st.nextToken();
        }

        return ignoreTraces;
    }

    /**
     * Cascade Close Objects
     * 
     * 
     * @param key
     * @param closeType
     */
    static void closeObjects(String key, CloseType closeType) {
        Enumeration<ConStmtRstVO> enumeration = hash.elements();

        while (enumeration.hasMoreElements()) {
            ConStmtRstVO vo = enumeration.nextElement();

            if (vo.isChild(key)) {
                vo.setCloseType(closeType);
                vo.closeObject();
            }
        }
    }

    /**
     * Append to the Log File
     * 
     * 
     * @param obj
     */
    static void append(Object obj) {
        sbr.append(obj + "\n");
        write(obj + "\n");
    }
}
