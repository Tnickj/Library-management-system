package za.ac.tut.bookseditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import za.ac.tut.addbook.AddBookController;
import za.ac.tut.bookseditorinterface.BooksEditorInterface;
import za.ac.tut.searchbook.SearchBookController;
import za.ac.tut.searchstudent.SearchStudentController;

public class BooksEditorController implements BooksEditorInterface{


	@FXML
    private Button btnAddBook;

    @FXML
    private Button btnSearch;

    @FXML
    private TextArea txtAreaViewStudent;

    @FXML
    private Button btnTAP;

    @FXML
    private TextArea txtAreaAvailableBooks;

    @FXML
    private Button btnViewBooks;

    @FXML
    private TextArea txtAreaIssuedBooks;

    @FXML
    private Button btnIssuedBooks;
    
    @FXML
    private Button btnDone;
    
    InetAddress address= null;
    
	Socket  socket = null;
	
    PrintWriter out= null;
    
    BufferedReader in = null;
    
    String mySQLQuery,data;
    
    @Override
    public void addBooks(ActionEvent e) throws IOException {
    	
    	Pane root = FXMLLoader.load(AddBookController.class.getResource("AddBook.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Add Book");
		stage.show();
    }
    
    @Override
    public void viewAllStudent(ActionEvent e) {
    	txtAreaViewStudent.setText("view all student");
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "select * from learner_details;";
			
			out.println(mySQLQuery);
			
			data = in.readLine();
			
			if(!data.isEmpty()) {
				String learnerDetail = "";
				String [] results = data.split("#");
				
				for(String learner:results) {
					learnerDetail = learnerDetail + learner + "\n";
				}
				
				System.out.println(learnerDetail);
				txtAreaViewStudent.setText(learnerDetail);
			}else {
				txtAreaViewStudent.setText("No students");
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
    public void searchBook(ActionEvent e) throws IOException {
    	Pane root = FXMLLoader.load(SearchBookController.class.getResource("SearchBook.fxml"));
		Scene scene = new Scene(root,400,400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Search Book");
		stage.show();
    }
    
    @Override
    public void searchStudent(ActionEvent e) throws IOException {
    	Pane root = FXMLLoader.load(SearchStudentController.class.getResource("SearchStudent.fxml"));
		Scene scene = new Scene(root,400,400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Search Student");
		stage.show();
    }
    
    @Override
    public void issuedBooks(ActionEvent e) {
    	txtAreaIssuedBooks.setText("view all issued books");
    	
    	try {
    		
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "select * from issued_books";
			
			out.println(mySQLQuery);
			
			data = in.readLine();
			
			if(!data.isEmpty()) {
				String bookDetail = "";
				String [] results = data.split("#");
				
				for(String book:results) {
					bookDetail = bookDetail + book + "\n";
				}
				
				System.out.println(bookDetail);
				txtAreaIssuedBooks.setText(bookDetail);
			}else {
				txtAreaIssuedBooks.setText("No books");
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
    public void viewAllBooks(ActionEvent e) {
    	
    	txtAreaAvailableBooks.setText("view all books");
    	
    	
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "select * from books";
			
			out.println(mySQLQuery);
			
			data = in.readLine();
			
			
			
			if(!data.isEmpty()) {
				String bookDetail = "";
				String [] results = data.split("#");
				
				for(String book:results) {
					bookDetail = bookDetail + book + "\n";
				}
				
				System.out.println(bookDetail);
				txtAreaAvailableBooks.setText(bookDetail);
			}else {
				txtAreaAvailableBooks.setText("No books");
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
	public void doneMethod(ActionEvent e) {
		Stage stage = (Stage) btnDone.getScene().getWindow();
    	stage.close();
    	JOptionPane.showMessageDialog(null, "Thank You, Bye");
	}

}
