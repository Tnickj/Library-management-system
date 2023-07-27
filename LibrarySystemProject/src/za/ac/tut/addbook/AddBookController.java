package za.ac.tut.addbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import za.ac.tut.addbookinterface.AddBookInterface;
import za.ac.tut.bookdetails.BookDetails;
import za.ac.tut.isbnexception.ISBNException;
import za.ac.tut.nameexception.NameException;

public class AddBookController implements AddBookInterface{

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtPublisher;

    @FXML
    private TextField txtCategory;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;
    
    private BookDetails bookDetail = null;

    String mySQLQuery ,data;
    
    InetAddress address= null;
    
	Socket  socket = null;
	
    PrintWriter out= null;
    
    BufferedReader in = null;
    
    
    public AddBookController() {
    	
    	this.bookDetail = new BookDetails();
    	
    }
    
    @Override
    public void addBooks(ActionEvent e)  {
    	
    	try {
    		
	    	bookDetail.setAuthor(txtAuthor.getText());
	    	
	    	bookDetail.setCategory(txtCategory.getText());
	    	
	    	bookDetail.setIsbn(txtISBN.getText());
	    	
	    	bookDetail.setPublisher(txtPublisher.getText());
	    	
	    	bookDetail.setTitle(txtTitle.getText());
    	
    	
	    	if(txtISBN.getText().isBlank() == false && txtAuthor.getText().isBlank() == false  && txtPublisher.getText().isBlank() == false && txtCategory.getText().isBlank() == false) {
	    		try {
	    			address = InetAddress.getByName("192.168.56.1");
	    			
	    			socket = new Socket(address,1010);
	    			
	    			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	    			
			    	mySQLQuery= "insert into books values ('"+this.bookDetail.getIsbn()+"','"+this.bookDetail.getTitle()+"','"+this.bookDetail.getAuthor()+"','"+this.bookDetail.getPublisher()+"','"+this.bookDetail.getCategory()+"');";
			    	
			    	out.println(mySQLQuery);
			    	
			    	System.out.print(mySQLQuery);
			    	
			    	Stage stage = (Stage) btnSubmit.getScene().getWindow();
			    	stage.close();
			    	
			    	JOptionPane.showMessageDialog(null, " is added into book database", "", JOptionPane.PLAIN_MESSAGE);
			    	
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
	    	}else {
	    		JOptionPane.showMessageDialog(null, " fill in all space", "", JOptionPane.ERROR_MESSAGE);
	    	}
		}catch(ISBNException | NameException ex) {
			
			System.out.println(ex);
		}
    }
    
    @Override
    public void cancel(ActionEvent e) {
    	Stage stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }
}
