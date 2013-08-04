package com.swat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public enum DateType {
    MINUTE(Calendar.MINUTE, "yyyy-MM-dd HH:mm", 60), HOUR(Calendar.HOUR_OF_DAY,
            "yyyy-MM-dd HH", 60 * 60), DAY(Calendar.DAY_OF_MONTH, "yyyy-MM-dd",
            24 * 60 * 60), MONTH(Calendar.MONTH, "yyyy-MM", 30 * 24 * 60 * 60), YEAR(
            Calendar.YEAR, "yyyy", 365 * 24 * 60 * 60);
    private SimpleDateFormat sdf;

    private long mSec;

    private int calType;

    private DateType(int calType, String format, long sec) {
        this.calType = calType;
        sdf = new SimpleDateFormat(format);
        mSec = sec * 1000;
    }

    public long mSec() {
        return mSec;
    }

    public SimpleDateFormat sdf() {
        return sdf;
    }

    public int calType() {
        return calType;
    }
}
