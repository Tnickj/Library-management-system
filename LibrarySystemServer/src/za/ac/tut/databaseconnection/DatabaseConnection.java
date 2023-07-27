/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class DatabaseConnection {
    
    public Connection dbConnection(String url,String root,String password){
        Connection connection = null; 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,root,password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        
        return connection;
    }
    
    public PreparedStatement prepareStatement(Connection connection,String data){
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return state;
    }
    
    public ResultSet prepareResults(PreparedStatement state){
        ResultSet rs = null;
        try {
            
            rs = state.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }
}
