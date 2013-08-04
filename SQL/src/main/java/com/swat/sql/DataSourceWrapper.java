package com.swat.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.swat.util.XPropUtil;

public class DataSourceWrapper implements DataSource {
    private final DataSource dataSource;

    private String appName;

    public DataSourceWrapper(DataSource dataSource) {
        this(WrapperConstants.APP_NAME, dataSource);
    }

    public DataSourceWrapper(String appName, DataSource dataSource) {
        this.appName = appName;
        this.dataSource = dataSource;
    }

    private static XPropUtil swatProperties = XPropUtil.SWAT_PROP;

    @Override
    public Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        if (swatProperties.getBoolean(appName + ".connection.wrapper.enabled",
                swatProperties.getBoolean("connection.wrapper.enabled", false)))
            return new ConnectionWrapper(appName, con);
        return con;
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        Connection con = dataSource.getConnection(username, password);
        if (swatProperties.getBoolean(appName + ".connection.wrapper.enabled",
                swatProperties.getBoolean("connection.wrapper.enabled", false)))
            return new ConnectionWrapper(appName, con);
        return con;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSource.setLoginTimeout(seconds);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSource.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSource.unwrap(iface);
    }
}
