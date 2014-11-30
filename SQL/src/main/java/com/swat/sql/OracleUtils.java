package com.swat.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleUtils extends SQLUtils {
    public static void main(String[] args) throws SQLException {
        Connection con = new OracleUtils(false).getConnection();
        System.out.println(con);
        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT * FROM USER_TABLES");
        while (rst.next()) {
            System.out.println(rst.getString(1));
        }
        con.close();
    }

    private boolean debug = false;

    public OracleUtils() {
    }

    public OracleUtils(boolean debug) {
        this.debug = debug;
    }

    public OracleUtils(boolean debug, boolean printQuery) {
        this.debug = debug;
        if (printQuery) {
            QueryLogger.setPrintQuery(printQuery);
        }
    }

    @Override
    protected Connection getConnection() throws SQLException {
        String driverName = "oracle.jdbc.driver.OracleDriver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Create a connection to the database
        String serverName = "localhost";
        String portNumber = "1521";
        String sid = "ORCL";
        String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":"
                + sid;
        String username = "scott";
        String password = "tiger";
        con = DriverManager.getConnection(url, username, password);
        if (debug) {
            con = new ConnectionWrapper(con);
        }
        return con;
    }
}
