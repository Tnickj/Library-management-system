package za.ac.tut.databaseconnetioninterface;

import java.sql.*;  

public class DatabaseConnetion {
    
    public void conn(){
        try{  
             
            Connection connect = DriverManager.getConnection("jdbc:mysql://root@localhost:3306/library_system","root","Eddy*k071");    
            Statement state = connect.createStatement();  
            String query = "Select * from books";
            ResultSet rs = state.executeQuery(query);
            while(rs.next()) {
            	System.out.print(rs.getString(0)+" "+rs.getShort(1));
            }
           
        }catch(Exception e){ 
            System.out.println(e);
        }  
    } 
}
