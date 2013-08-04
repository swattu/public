package com.swat.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MDBUtils extends SQLUtils {
    private String mdbFile = null;

    private boolean debug = false;

    public MDBUtils(String mdbFile) {
        this.mdbFile = mdbFile;
    }

    public MDBUtils(String mdbFile, boolean debug) {
        this.mdbFile = mdbFile;
        this.debug = debug;
    }

    public MDBUtils(String mdbFile, boolean debug, boolean printQuery) {
        this.mdbFile = mdbFile;
        this.debug = debug;
        if (printQuery) {
            QueryLogger.setPrintQuery(printQuery);
        }
    }

    @Override
    protected Connection getConnection() throws SQLException {
        con = DriverManager.getConnection(
                "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ="
                        + mdbFile, "", "");
        con.setAutoCommit(true);
        if (debug) {
            con = new ConnectionWrapper(con);
        }
        return con;
    }
}
