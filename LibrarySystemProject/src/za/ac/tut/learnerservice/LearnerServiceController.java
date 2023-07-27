package za.ac.tut.learnerservice;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import za.ac.tut.learnerserviceinterface.LearnerServiceInterface;
import za.ac.tut.login.LoginController;

public class LearnerServiceController implements LearnerServiceInterface{

  	@FXML
    private TextArea txtAreaAllBooks;

    @FXML
    private Button btnViewAllBooks;

    @FXML
    private TextArea txtAreaOrderedBook;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnRenew;

    @FXML
    private Button btnOrderedBooks;

    /*@FXML
    private TextField txtStudentNO;*/
    @FXML
    private TextArea txtStudentNO;
    
    @FXML
    private TextField txtISBN;

    @FXML
    private Button btnOKSearch;

    @FXML
    private TextArea txtAreaSearchedBook;

    @FXML
    private Button txtSubmitOrder;

    @FXML
    private Button btnDONE;

    String query;
    
    String studentNo ;
    
    InetAddress address= null;
    
   	Socket  socket = null;
   	
   	PrintWriter out= null;
   
   	BufferedReader in = null;
   
   	String mySQLQuery,data,studentNumber;
    
   	LoginController lc = new LoginController();
   	
    @Override
    public void diplayAllBooks() {
    	
    	
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
				txtAreaAllBooks.setText(bookDetail);
		    }else {
		    	txtAreaAllBooks.setText("No books");
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
    public void submitOrder(ActionEvent e) {
    	boolean isValid = true;
    	
    	try {
    		do {
        		studentNumber = JOptionPane.showInputDialog("Please confirm your studend number");
        		studentNo = confirmStudentNumber(studentNumber);
        		
        		if(studentNo.isEmpty() == true) {
        			isValid = false;
        		}
        	}while(isValid = false);
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "insert into issued_books VALUE ("+studentNo+",'"+txtISBN.getText()+"',sysdate(),date_issued + 5);";
			
			out.println(mySQLQuery);
	    	
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
    
    private String confirmStudentNumber(String studentNumber) {
       	
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010); 
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "select student_no from learner_details where student_no = " + studentNumber+";";
			out.println(mySQLQuery);
			
			data = in.readLine();
			
			if(!data.equals(null)) {
				JOptionPane.showMessageDialog(null, "Thanks");
		    }else {
		    	JOptionPane.showMessageDialog(null, "Student number doesn't exist");
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
		return data;
		
	}

	@Override
    public void issuedBooks(ActionEvent e) {
		boolean isValid = true;
    	do {
    		studentNumber = JOptionPane.showInputDialog("Please confirm your student number");
    		studentNo = confirmStudentNumber(studentNumber);
    		
    		if(studentNo.equals(null)) {
    			isValid = false;
    		}
    	}while(isValid = false);
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "select * from issued_books where Student_No = "+studentNo+";";
	    	
	    	out.println(mySQLQuery);
	    	
	    	data = in.readLine();
	    	
	    	if(!data.isEmpty()) {
	    		String bookDetail = "";
				String [] results = data.split("#");
				
				for(String book:results) {
					bookDetail = bookDetail + book + "\n";
				}
				
				System.out.println(bookDetail);
				txtAreaOrderedBook.setText(bookDetail);
		    }else {
		    	txtAreaOrderedBook.setText("No issued");
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
    public void returnBook(ActionEvent e) {
    	boolean isValid = true;
    	do {
    		studentNumber = JOptionPane.showInputDialog("Please confirm your studend number");
    		studentNo = confirmStudentNumber(studentNumber);
    		
    		if(studentNo.isEmpty()) {
    			isValid = false;
    		}
    	}while(isValid = false);
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "delete from issued_books where isbn = '"+txtISBN.getText()+"' and Student_No = "+studentNo+";";
			
	    	out.println(mySQLQuery);
	    	
    		txtAreaOrderedBook.setText("Book with this ISBN : "+txtISBN.getText() + " is returned");
    		
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
    public void renewBook(ActionEvent e) {
    	boolean isValid = true;
    	do {
    		studentNumber = JOptionPane.showInputDialog("Please confirm your studend number");
    		studentNo = confirmStudentNumber(studentNumber);
    		
    		if(studentNo.isEmpty()) {
    			isValid = false;
    		}
    	}while(isValid = false);
    	
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "update issued_books set return_date = return_date+3 where isbn = '"+txtISBN.getText()+"' and Student_No = "+studentNo+";";
			
			out.println(mySQLQuery);
	    	
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
    public void searchBook(ActionEvent e) {
    	try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			mySQLQuery = "select * from books where isbn = "+ "'"+txtISBN.getText()+"';"; 
			
			out.println(mySQLQuery);
			
			data = in.readLine();
			
	    	if(!data.isEmpty()) {
	    		String learnerDetail = "";
				String [] results = data.split("#");
				
				for(String learner:results) {
					learnerDetail = learnerDetail + learner + "\n";
				}
				
				System.out.println(learnerDetail);
	    		txtAreaSearchedBook.setText(learnerDetail);
	    	}else {
	    		txtAreaSearchedBook.setText(txtISBN.getText()+ " No found");
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
    	Stage stage = (Stage) btnDONE.getScene().getWindow();
    	stage.close();
    }
}