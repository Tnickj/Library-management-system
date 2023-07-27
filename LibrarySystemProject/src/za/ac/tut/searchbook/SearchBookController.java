package za.ac.tut.searchbook;

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
import za.ac.tut.searchbookinterface.SearchBookInterface;

public class SearchBookController implements SearchBookInterface{

	@FXML
    private TextField txtISBN;

    @FXML
    private TextArea txtAreaShowSearched;

    @FXML
    private Button btnOK;

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
    public void searchBook(ActionEvent e) {
    	
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			mySQLQuery = "select * from books where isbn = '"+txtISBN.getText()+"'"; 
			
			out.println(mySQLQuery);
			
			data = in.readLine();
			
	    	if(!data.isEmpty()) {
	    		String bookDetail = "";
				String [] results = data.split("#");
				
				for(String book:results) {
					bookDetail = bookDetail + book + "\n";
				}
				
				System.out.println(bookDetail);
	    		txtAreaShowSearched.setText(bookDetail);
	    	}else {
	    		txtAreaShowSearched.setText("Book with ISBN " +txtISBN.getText()+ " is not available");
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
    public void done(ActionEvent e) {
    	Stage stage = (Stage) btnDone.getScene().getWindow();
    	stage.close();
    }

    @Override
	public void removeBook(ActionEvent e) {
    	
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			mySQLQuery = "delete from books where isbn = "+ "'"+txtISBN.getText()+"'"; 
			
			out.println(mySQLQuery);
			
	    	txtAreaShowSearched.setText("Book with ISBN :"+txtISBN.getText()+ " removed");
	    	
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
}
