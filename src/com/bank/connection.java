package com.bank;

import java.sql.*;

public class connection {
    static Connection conn;// Global Connection Object

    public static Connection connect() {
        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";  //jdbc driver
            String url = "jdbc:mysql://localhost:3306/bank";      //mysql url
            String user = "root";                                 //mysql username
            String pass = "12345678";                             //mysql passcode
            Class.forName(mysqlJDBCDriver);
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Connection Failed!");
        }

        return conn;
    }
}
