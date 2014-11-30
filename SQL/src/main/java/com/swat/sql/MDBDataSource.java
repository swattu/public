package com.swat.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class MDBDataSource implements DataSource {
  protected Connection con = null;

  private final String mdbFile;

  public MDBDataSource(String mdbFile) {
    this.mdbFile = mdbFile;
  }

  @Override
  public Connection getConnection() throws SQLException {
    if (con == null || con.isClosed()) {
      con = DriverManager.getConnection("jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=" + mdbFile, "", "");
      con.setAutoCommit(true);
    }
    return con;
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return getConnection();
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {
  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }
}
