package com.swat.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Properties files can be customized at various levels. If a key is not found
 * at bottom level, it is searched for next higher level. If the key is not
 * found at all, it is checked in System Property.
 * 
 * 
 * @version 3.0.2, 07/05/03
 * @author Swatantra Agrawal
 */
public class XPropUtil {
    private Properties properties;

    private String fileName;

    public final static XPropUtil SWAT_PROP;
    static {
        String fileName = System.getenv("swatCfgFile");
        if (fileName == null) {
            fileName = System.getProperty("swatCfgFile");
        }
        if (fileName == null) {
            fileName = System.getProperty("user.home")
                    + "/swat/swatConfig.properties";
        }
        SWAT_PROP = new XPropUtil(fileName) {
            @Override
            protected boolean saveOnDefault() {
                return true;
            }
        };
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        if (fileName == null) {
            return prop;
        }
        File file = new File(fileName);
        if (file.exists()) {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                prop.load(reader);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return prop;
    }

    protected boolean saveOnDefault() {
        return false;
    }

    public final void reload() {
        properties = getProperties();
    }

    /**
     * Constructor. Takes the parameter to check from Config or System Property.
     * 
     * @param isDynamic
     */
    public XPropUtil(Properties properties) {
        this.properties = properties;
    }

    /**
     * Constructor. Takes the parameter to check from Config or System Property.
     * 
     * @param isDynamic
     */
    public XPropUtil(String fileName) {
        this.fileName = fileName;
        reload();
    }

    /**
     * Returns an array of files to check for the key..
     * 
     * @return The String array of Files
     */
    public String[] getFiles() {
        return new String[0];
    }

    /**
     * Returns boolean for the key. It does not throw any exception.
     * 
     * @param key
     * @return boolean
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Returns boolean for the key. If key is not set returns the defaultValue.
     * It does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return boolean
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        boolean returnValue = false;

        if (value == null) {
            returnValue = defaultValue;
        } else {
            returnValue = Boolean.valueOf(value);
        }

        return returnValue;
    }

    /**
     * Returns byte for the key. It does not throw any exception.
     * 
     * @param key
     * @return byte
     */
    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    /**
     * Returns byte for the key. If key is not set returns the defaultValue. It
     * does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return byte
     */
    public byte getByte(String key, byte defaultValue) {
        String value = getProperty(key);

        return NumberUtils.parseByte(value, defaultValue);
    }

    /**
     * Returns short for the key. It does not throw any exception.
     * 
     * @param key
     * @return short
     */
    @SuppressWarnings("PMD")
    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    /**
     * Returns short for the key. If key is not set returns the defaultValue. It
     * does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return short
     */
    @SuppressWarnings("PMD")
    public short getShort(String key, short defaultValue) {
        String value = getProperty(key);

        return NumberUtils.parseShort(value, defaultValue);
    }

    /**
     * Returns int for the key. It does not throw any exception.
     * 
     * @param key
     * @return int
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Returns int for the key. If key is not set returns the defaultValue. It
     * does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return int
     */
    public int getInt(String key, int defaultValue) {
        String value = getProperty(key);

        return NumberUtils.parseInt(value, defaultValue);
    }

    /**
     * Returns long for the key. It does not throw any exception.
     * 
     * @param key
     * @return long
     */
    public long getLong(String key) {
        return getLong(key, 0);
    }

    /**
     * Returns long for the key. If key is not set returns the defaultValue. It
     * does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return long
     */
    public long getLong(String key, long defaultValue) {
        String value = getProperty(key);

        return NumberUtils.parseLong(value, defaultValue);
    }

    /**
     * Returns float for the key. It does not throw any exception.
     * 
     * @param key
     * @return float
     */
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    /**
     * Returns float for the key. If key is not set returns the defaultValue. It
     * does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return float
     */
    public float getFloat(String key, float defaultValue) {
        String value = getProperty(key);

        return NumberUtils.parseFloat(value, defaultValue);
    }

    /**
     * Returns double for the key. It does not throw any exception.
     * 
     * @param key
     * @return double
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
     * Returns double for the key. If key is not set returns the defaultValue.
     * It does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return double
     */
    public double getDouble(String key, double defaultValue) {
        String value = getProperty(key);

        return NumberUtils.parseDouble(value, defaultValue);
    }

    /**
     * Returns String for the key. It does not throw any exception.
     * 
     * @param key
     * @return String
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * Returns String for the key. If key is not set returns the defaultValue.
     * It does not throw any exception.
     * 
     * @param key
     * @param defaultValue
     * @return String
     */
    public String getString(String key, String defaultValue) {
        String value = getProperty(key);

        if (value == null && defaultValue != null) {
            value = defaultValue;
            if (saveOnDefault()) {
                properties.setProperty(key, value);
                saveProperties();
            }
        }

        return value;
    }

    private String getProperty(String key) {
        String returnValue = null;
        if (properties == null) {
            return returnValue;
        }
        returnValue = properties.getProperty(key);
        return returnValue;
    }

    private void saveProperties() {
        if (fileName == null) {
            return;
        }
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            properties.store(writer, "Auto Saved");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
