package com.whyuan.$6utils.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper {
    private static final Logger logger = LoggerFactory.getLogger(JdbcHelper.class);

    private String url;
    private String username;
    private String password;
    private Connection conn;
    private boolean autoCommit;

    public JdbcHelper(String url, String username, String password) {
        this(url, username, password, true);
    }

    public JdbcHelper(String url, String username, String password, boolean autoCommit) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.autoCommit = autoCommit;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return getConnection(this.autoCommit);
    }

    public Connection getConnection(boolean autoCommit) throws SQLException {
        if (conn == null || conn.isClosed()) {
            synchronized (this) {
                if (conn == null || conn.isClosed()) {
                    conn = DriverManager.getConnection(url, username, password);
                    conn.setAutoCommit(autoCommit);
                }
            }
        }
        return conn;
    }

    public int count(String sql) throws SQLException {
//        System.out.println(sql);
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        // close
        return 0;
    }

    public void execute(String sql, Worker worker) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        worker.work(resultSet);
        resultSet.close();
    }

    public PreparedStatement getStmt(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public void execute(String sql, Worker worker, Object... args) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(1 + i, args[i]);
        }

        ResultSet resultSet = stmt.executeQuery();
        worker.work(resultSet);
        resultSet.close();
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    public interface Worker {
        void work(ResultSet result) throws SQLException;
    }

    //=============================================== static method ==================================================
    public static final List<String> getAllFields(String table, JdbcHelper jdbc) throws SQLException {
        List<String> fields = new ArrayList<>();
        PreparedStatement ps = jdbc.getConnection().prepareStatement("select * from " + table + " limit 0,1");
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsme = rs.getMetaData();
        int columnCount = rsme.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            fields.add(rsme.getColumnName(i));
        }
        rs.close();
        return fields;
    }

    public static final String constructInsertAllFieldsSQL(List<String> fields, String table, String suffixSQL) {
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        fields.forEach($field -> {
            key.append($field + ",");
            value.append("?,");
        });
        key.insert(0, "(").insert(key.length(), ")");
        value.insert(0, "(").insert(value.length(), ")");
        key.deleteCharAt(key.lastIndexOf(","));
        value.deleteCharAt(value.lastIndexOf(","));
        final String sql = "insert into " + table + " " + key.toString() + "  values " + value.toString() + " " + suffixSQL;
        logger.info("InsertAllFields prepareStatement SQL={}", sql);
        return sql;
    }

}
