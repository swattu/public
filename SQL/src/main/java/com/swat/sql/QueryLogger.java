package com.swat.sql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.swat.util.DateType;
import com.swat.util.FileUtil;
import com.swat.util.XPropUtil;

/**
 * Utility to log the Queries fired
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public class QueryLogger {
    private static int appNameMaxSize = 0;

    private static FileWriter fw = null;

    private static boolean PRINT_QUERY = false;

    private static long lastDateStamp = 0;

    private static int methodMaxSize = 0;

    private static SimpleDateFormat querySDF = new SimpleDateFormat(
            "HH:mm:ss.SSS");

    private static XPropUtil swatProperties = XPropUtil.SWAT_PROP;

    /**
     * Want to print the Query or not
     * 
     * 
     * @param yesOrNo
     */
    public static void setPrintQuery(boolean yesOrNo) {
        PRINT_QUERY = yesOrNo;
    }

    /**
     * Create the Writer for logging
     * 
     * 
     * @param append
     */
    public static synchronized void createWriter(boolean append) {
        try {
            Date now = new Date();

            if (fw != null) {
                fw.close();
                fw = null;
            }
            lastDateStamp = FileUtil.getDateNo(new Date(), DateType.DAY);

            String folderStr = swatProperties.getString("wrapper.logFolder",
                    System.getProperty("user.home") + "/swat");

            File file = new File(folderStr, FileUtil.getFile("ExecQuery.log",
                    DateType.DAY));

            fw = new FileWriter(file, append);
            write("\n" + querySDF.format(now) + " Query Log Started.\n\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static synchronized void write(String str) throws IOException {

        long dateStamp = FileUtil.getDateNo(new Date(), DateType.DAY);

        if (dateStamp != lastDateStamp) {
            createWriter(true);
        }

        fw.write(str);
        fw.flush();
    }

    /**
     * Print the Query
     * 
     * 
     * @param appName
     * @param count
     * @param str
     */
    public static void print(String appName, long count, String str) {
        print(appName, count, str, false);
    }

    /**
     * Print the Query, with Exception true/false
     * 
     * 
     * @param appName
     * @param count
     * @param str
     * @param isException
     */
    public static void print(String appName, long count, String str,
            boolean isException) {
        boolean printQuery = swatProperties.getBoolean(appName
                + ".wrapper.logging.logQuery", swatProperties.getBoolean(
                "wrapper.logging.logQuery", PRINT_QUERY));

        if ((printQuery == false) && (isException == false)) {
            return;
        }

        Date time = new Date();
        String counterStr = "0000000" + count;
        String method = null;
        String[] ignoreTraces = CSRLogger.getIgnoreTraces(appName);

        counterStr = counterStr.substring(counterStr.length() - 7);

        try {
            throw new Exception();
        } catch (Exception e) {
            StackTraceElement[] ste = e.getStackTrace();

            outer: for (int x = 2; x < ste.length; x++) {
                method = ste[x].getClassName() + "." + ste[x].getMethodName()
                        + "(" + ste[x].getLineNumber() + ")";

                for (String element : ignoreTraces) {
                    if (method.toLowerCase().indexOf(element) >= 0) {
                        continue outer;
                    }
                }

                break;
            }
        }

        try {
            if (method.length() > methodMaxSize) {
                methodMaxSize = method.length();
            } else {
                method = method
                        + "................................................................"
                        + "....................................";
            }

            if (appName.length() > appNameMaxSize) {
                appNameMaxSize = appName.length();
            }

            String tAppName = appName + "                        ";

            tAppName = tAppName.substring(0, appNameMaxSize);
            write(querySDF.format(time) + " " + tAppName + " "
                    + method.substring(0, methodMaxSize) + " [" + counterStr
                    + "] " + str + "\n");
        } catch (Exception e) {
        }
    }
}
