/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack1;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Akroma
 */
public class DBClass {

    static private Connection  connection;
    String user = "root";
    String pass = "123456A";
    public static Connection getConnection() throws Exception{
        if(connection == null){
            //JDBC
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/guateDiscoBD", "root", "123456A");
        }
        return connection;
    }

}