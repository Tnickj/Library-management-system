/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.requesthandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import za.ac.tut.databaseconnection.DatabaseConnection;

/**
 *
 * @author user
 */
public class RequestHandler extends Thread{

    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    String info = "";
    DatabaseConnection database = new DatabaseConnection();
    PreparedStatement state = null;
    ResultSet rs = null;
    Connection connection = null;
    
    public RequestHandler(Socket socket) throws IOException{
        this.socket = socket;
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

        start();
    }
    
    @Override
    public void run() {
    
        String url = "jdbc:mysql://root@localhost:3306/librrary_system";
        String root = "root";
        String password = "";

        try {

            String data = in.readLine();

            connection = database.dbConnection(url, root, password);
            state = database.prepareStatement(connection, data);
            //rs = database.prepareResults(state);
            System.out.println("i recieved this :"+data);

            boolean bookSelectionIsValid = data.contains("select * from books");
            boolean studentSelectionIsValid = data.contains("select * from learner_details");
            boolean issuedBookSelection = data.contains("select * from issued_books");
            boolean studentLoginIsValid = data.contains("select student_no,password");
            boolean adminLoginIsValid = data.contains("select employee_id,password");
            boolean cormfirmStudentNoIsValid = data.contains("select student_no from");
            boolean insertDataIsValid = data.contains("insert");
            boolean deleteDataIsValid = data.contains("delete");
            boolean updateDataIsValid = data.contains("update");
            boolean searchedStudentIsValid = data.contains("select * from learner_details where student_no");
            boolean searchBookIsValid = data.contains("select * from books where isbn");
            
            if(bookSelectionIsValid == true){
                info = displayBooks(state);
            }
            if(studentSelectionIsValid == true){
                info = displayStudents(state);
            }
            if(issuedBookSelection == true){
                info = displayIssuedBooks(state);
            }
            if(studentLoginIsValid == true){
                info = studentLogin(state);
            }
            if(adminLoginIsValid == true){
                info = adminLogin(state);
            }
            if(cormfirmStudentNoIsValid == true){
                info = confirmStudentNumber(state);
            }
            if(insertDataIsValid == true){
                insertData(state);
            }
            if(deleteDataIsValid == true){
                deleteData(state);
            }
            if(updateDataIsValid == true){
                updateData(state);
            }
            if(searchedStudentIsValid == true){
                info = displayStudent(state);
            }
            if(searchBookIsValid == true){
                info = displayBook(state);
            }

            out.println(info );


        } catch (IOException ex) {
            System.out.println(ex);

        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        
    }

    private synchronized String displayStudents(PreparedStatement state) {
        try{
            rs = database.prepareResults(state);
            
            while(rs.next()){
                int studentNo = (rs.getInt("Student_No"));
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String id  = (rs.getString("Learner_Id"));
                String cellNo = Integer.toString(rs.getInt("Cell_No"));
                String email = rs.getString("Email");
                String course = rs.getString("Course");
                
                info = info +"---------------------#" + "Student number \t: "+ studentNo + "#Name \t\t\t: " +name + "#Surname \t\t\t: "+surname +"#Identity number \t: "+id +"#Cell number \t\t: "+cellNo + "#Email \t\t\t: " +email+"#Course \t\t\t: " + course + "#";
            }
        }catch (SQLException ex) {
            System.out.println(ex); 
        
        }
        
        return info;
    }

    private synchronized String displayIssuedBooks(PreparedStatement state){
        
        try {
            rs = database.prepareResults(state);
            
            while(rs.next()){
                int studentNo = (rs.getInt("Student_No"));
                String isbn = rs.getString("isbn");
                String issuedDate = rs.getDate("Date_Issued").toString();
                String returnDate = rs.getDate("Return_Date").toString();
                
                info = info + "------------------#" + "Student number \t: "+ studentNo + "#ISBN \t\t\t: "+isbn +"#Issued date \t\t: "+issuedDate + "#Return date \t\t: "+returnDate+"#";
            }
        } catch (SQLException ex) {
            System.out.println(ex); 
        }
        
        return info;
    }

    private synchronized String displayBooks(PreparedStatement state) {
        try {
            rs = database.prepareResults(state);
            
            while(rs.next()){
                String isbn = rs.getString("isbn");
                String title = rs.getString("Title");
                String category = rs.getString("Category");
                String publisher = rs.getString("Publisher");
                String author = rs.getString("Author");
                
                info = info + "------------------#" + "ISBN \t\t: "+isbn + "#Title \t\t\t:"+title + "#Author \t\t:" + author+"#Publisher \t\t:" + publisher  + "#Category \t\t:"+ category + "#";
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return info;
    }

    private synchronized String studentLogin(PreparedStatement state) {
        
        try {
            rs = database.prepareResults(state);
            
            while(rs.next()){
            
            int studentNo = (rs.getInt("Student_No"));
            String password = rs.getString("Password");

            info = studentNo +"#"+password;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return info;     
            
    }

    private synchronized String adminLogin(PreparedStatement state) {
        try {
            rs = database.prepareResults(state);
            
            while(rs.next()){
                int employeeId = (rs.getInt("employee_id"));
                String password = rs.getString("password");

                info = employeeId +"#"+password;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return info;
    }

    private synchronized String confirmStudentNumber(PreparedStatement state) {
        
        try {
            rs = database.prepareResults(state);
            
            while(rs.next()){
                int studentNo = (rs.getInt("Student_No"));

                info = ""+studentNo;
            }
        } catch (SQLException ex) {
            System.out.println(ex);        
        }
        
        return info;
    }

    private synchronized void insertData(PreparedStatement state) {
        try {
            state.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void deleteData(PreparedStatement state) {
        try {
            state.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void updateData(PreparedStatement state) {
        try {
            state.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private synchronized String displayStudent(PreparedStatement state) {
        try{
            rs = database.prepareResults(state);
            
            while(rs.next()){
                int studentNo = (rs.getInt("Student_No"));
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String id  = (rs.getString("Learner_Id"));
                String cellNo = Integer.toString(rs.getInt("Cell_No"));
                String email = rs.getString("Email");
                String course = rs.getString("Course");
                
                info = "Student number \t: "+ studentNo + "#Name \t\t\t: " +name + "#Surname \t\t\t: "+surname +"#Identity number \t: "+id +"#Cell number \t\t: "+cellNo + "#Email \t\t\t: " +email+"#Course \t\t\t: " + course + "#";
            }
        }catch (SQLException ex) {
            System.out.println(ex); 
        
        }
        
        return info;
    }
    
    private synchronized String displayBook(PreparedStatement state) {
        try {
            rs = database.prepareResults(state);
            
            while(rs.next()){
                String isbn = rs.getString("isbn");
                String title = rs.getString("Title");
                String category = rs.getString("Category");
                String publisher = rs.getString("Publisher");
                String author = rs.getString("Author");
                
                info = "ISBN \t\t:  "+isbn + "#Title \t\t\t: "+title + "#Author \t\t: " + author+"#Publisher \t\t: " + publisher  + "#Category \t\t: "+ category + "#";
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return info;
    }
}
