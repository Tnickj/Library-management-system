package za.ac.tut.searchstudent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import za.ac.tut.searchstudentinterface.SearchStudentInterface;

public class SearchStudentController implements SearchStudentInterface{

    @FXML
    private TextField txtStudNO;

    @FXML
    private Button btnOK;
    
    @FXML
    private TextArea txtAreaSearchedStudent;

    @FXML
    private Button btnDone;

    @FXML
    private Button btnRemove;

    InetAddress address= null;
    
	Socket  socket = null;
	
    PrintWriter out= null;
    
    BufferedReader in = null;
    
    String mySQLQuery,data;
    
    @Override
	public void searchStudent(ActionEvent e) {
    	
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
			mySQLQuery = "select * from learner_details where student_no = "+txtStudNO.getText()+";";
			
			out.println(mySQLQuery);
			
			data = in.readLine();
			
	    	if(!data.isEmpty()) {
	    		String learnerDetail = "";
				String [] results = data.split("#");
				
				for(String learner:results) {
					learnerDetail = learnerDetail + learner + "\n";
				}
				
				System.out.println(learnerDetail);
	    		txtAreaSearchedStudent.setText(learnerDetail);
	    		
	    	}else {
	    		txtAreaSearchedStudent.setText("Student number "+txtStudNO.getText()+ " not found");
	    	}
    	} catch (UnknownHostException ex) {
    		System.out.print(ex);
		} catch (IOException ex) {
			System.out.print(ex);
		}finally {
			try {
				socket.close();
			} catch (IOException ex) {
				System.out.print(ex);
			}
		}
    }

    @Override
	public void removeStudent(ActionEvent e) {
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "delete from learner_details where student_no = "+txtStudNO.getText(); 
			
			out.println(mySQLQuery);
			
			txtAreaSearchedStudent.setText(txtStudNO.getText()+ " removed");
			
    	} catch (UnknownHostException ex) {
    		System.out.print(ex);
		} catch (IOException ex) {
			System.out.print(ex);
		}finally {
			try {
				socket.close();
			} catch (IOException ex) {
				System.out.print(ex);
			}
		}
    }

    @Override
	public void exitWindow(ActionEvent e) {
		Stage stage = (Stage) btnDone.getScene().getWindow();
		stage.close();
	}
    
    

}
