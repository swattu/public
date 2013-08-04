package com.swat.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FileUtil {
    private static final long OFFSET = TimeZone.getDefault().getRawOffset();

    public static String getFile(String file, DateType dateType) {
        SimpleDateFormat sdf = dateType.sdf();
        int index = file.lastIndexOf('.');
        String fileName = null;
        String ext = "";
        if (index > 0) {
            fileName = file.substring(0, index);
            ext = file.substring(index);
        }

        return fileName + "-"
                + sdf.format(new Date(System.currentTimeMillis() + OFFSET))
                + ext;
    }

    public static long getDateNo(Date date, DateType dateType) {
        return (System.currentTimeMillis() + OFFSET) / dateType.mSec();
    }
}
