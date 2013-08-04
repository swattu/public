package com.swat.util;

/**
 * Utility for Number. It doesn't throw any exception This class has static
 * methods like parseInt, etc. Unlike Integer.parseInt, these methods don't
 * throw NumberFormatException. But returns default value or the provided value
 * in case of Exception.
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public class NumberUtils {

    /**
     * Parse Byte
     * 
     * 
     * @param value
     * 
     * @return byte
     */
    public static byte parseByte(String value) {
        return parseByte(value, (byte) 0);
    }

    /**
     * Parse Byte
     * 
     * 
     * @param value
     * @param valueOnError
     * 
     * @return byte
     */
    public static byte parseByte(String value, byte valueOnError) {
        byte returnValue = 0;

        try {
            returnValue = Byte.parseByte(value);
        } catch (NumberFormatException nfe) {
            returnValue = valueOnError;
        }

        return returnValue;
    }

    /**
     * Parse Short
     * 
     * 
     * @param value
     * 
     * @return short
     */
    @SuppressWarnings("PMD")
    public static short parseShort(String value) {
        return parseShort(value, (short) 0);
    }

    /**
     * Parse Short
     * 
     * 
     * @param value
     * @param valueOnError
     * 
     * @return short
     */
    @SuppressWarnings("PMD")
    public static short parseShort(String value, short valueOnError) {
        short returnValue = 0;

        try {
            returnValue = Short.parseShort(value);
        } catch (NumberFormatException nfe) {
            returnValue = valueOnError;
        }

        return returnValue;
    }

    /**
     * Parse Int
     * 
     * 
     * @param value
     * 
     * @return int
     */
    public static int parseInt(String value) {
        return parseInt(value, 0);
    }

    /**
     * Parse Int
     * 
     * 
     * @param value
     * @param valueOnError
     * 
     * @return int
     */
    public static int parseInt(String value, int valueOnError) {
        int returnValue = 0;

        try {
            returnValue = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            returnValue = valueOnError;
        }

        return returnValue;
    }

    /**
     * Parse Long
     * 
     * 
     * @param value
     * 
     * @return long
     */
    public static long parseLong(String value) {
        return parseLong(value, 0);
    }

    /**
     * Parse Long
     * 
     * 
     * @param value
     * @param valueOnError
     * 
     * @return long
     */
    public static long parseLong(String value, long valueOnError) {
        long returnValue = 0;

        try {
            returnValue = Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            returnValue = valueOnError;
        }

        return returnValue;
    }

    /**
     * Parse Float
     * 
     * 
     * @param value
     * 
     * @return float
     */
    public static float parseFloat(String value) {
        return parseFloat(value, 0);
    }

    /**
     * Parse Float
     * 
     * 
     * @param value
     * @param valueOnError
     * 
     * @return float
     */
    public static float parseFloat(String value, float valueOnError) {
        float returnValue = 0;

        try {
            returnValue = Float.parseFloat(value);
        } catch (NumberFormatException nfe) {
            returnValue = valueOnError;
        }

        return returnValue;
    }

    /**
     * Parse Double
     * 
     * 
     * @param value
     * 
     * @return double
     */
    public static double parseDouble(String value) {
        return parseDouble(value, 0);
    }

    /**
     * Parse Double
     * 
     * 
     * @param value
     * @param valueOnError
     * 
     * @return double
     */
    public static double parseDouble(String value, double valueOnError) {
        double returnValue = 0;

        try {
            returnValue = Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            returnValue = valueOnError;
        }

        return returnValue;
    }
}
