package za.ac.tut.registor;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import za.ac.tut.cellphoneexception.CellphoneException;
import za.ac.tut.emailadressexception.EmailAdressException;
import za.ac.tut.idexception.IdException;
import za.ac.tut.login.LoginController;
import za.ac.tut.nameexception.NameException;
import za.ac.tut.passwordexception.PasswordException;
import za.ac.tut.personaldetails.learnerpersonaldetails.LearnerPersonalDetails;
import za.ac.tut.registionforminterface.RegistionFormInterface;
import za.ac.tut.studentnoexception.StudentNoException;

public class RegistrationFormController implements RegistionFormInterface{
	
    @FXML
    private TextField txtSudNo;

    @FXML
    private TextField txtIDNo;
        
    @FXML
    private TextField txtCellNumber;

    @FXML
    private TextField txtEmailAdress;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtNames;

    @FXML
    private TextField txtSurname;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;
    
    @FXML
    private Button btnRestart;
    
    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;
    
    private LearnerPersonalDetails lpd = null;
    
    InetAddress address= null;
    
	Socket  socket ;
	
    PrintWriter out= null;
    
    BufferedReader in = null;
    
    String mySQLQuery;
    
    public RegistrationFormController()  {
    	
    	this.lpd = new LearnerPersonalDetails();
    		
    }
    
    //here we are saving the texts we got from the user in our database
    @Override
    public void pressedSubmitButton(ActionEvent e) {
    	
    	this.lpd = new LearnerPersonalDetails();
    	
    	try {
		    lpd.setStudentNo(txtSudNo.getText());
			
			lpd.setName(txtNames.getText());
			
			lpd.setSurname(txtSurname.getText());
			
			lpd.setIdNo(txtIDNo.getText());
			
			lpd.setCellnumber(txtCellNumber.getText());
			
			lpd.setEmail(txtEmailAdress.getText());
			
			lpd.setCourse(txtCourseName.getText());
			
			if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
				lpd.setPassword(txtPassword.getText());

		    	try {
					address = InetAddress.getByName("192.168.56.1");
					
					socket = new Socket(address,1010);
					
					out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					if(txtSudNo.getText().isBlank() == false &&txtIDNo.getText().isBlank() == false && txtCellNumber.getText().isBlank() == false && txtEmailAdress.getText().isBlank() == false && txtCourseName.getText().isBlank() == false && txtSurname.getText().isBlank() == false && txtNames.getText().isBlank() == false) {
			    		
			    		mySQLQuery = "insert into learner_details value ("+this.lpd.getStudentNo()+",'"+this.lpd.getName()+"','"+this.lpd.getSurname()+"','"+this.lpd.getIdNo()+"',"+this.lpd.getCellnumber()+",'"+this.lpd.getEmail()+"','"+this.lpd.getCourse()+"','"+this.lpd.getPassword()+"');";
				    	
			    		out.println(mySQLQuery);
				    	
				    	JOptionPane.showMessageDialog(null, "You are successfully registered", "", JOptionPane.PLAIN_MESSAGE);
				    	
				    	Stage stage = (Stage) btnSubmit.getScene().getWindow();
				    	stage.close();
				    	try {
							BorderPane root = FXMLLoader.load(LoginController.class.getResource("login.fxml"));
							Scene scene = new Scene(root);
							stage = new Stage();
							stage.initStyle(StageStyle.UNDECORATED);
							stage.setScene(scene);
							stage.show();
							
						} catch(Exception ex) {
							System.out.println(ex);
						}
			    	}else {
			    		JOptionPane.showMessageDialog(null, "Please complete the form", "", JOptionPane.ERROR_MESSAGE);
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
	    	}else {
				JOptionPane.showMessageDialog(null, "Password does not match", "", JOptionPane.ERROR_MESSAGE);	
			}
    	} catch (  CellphoneException | EmailAdressException | PasswordException | IdException | NameException | StudentNoException ex) {
    		System.out.print(ex);
		}	
    }
    
    //here we close the window if user pressed cancel
    @Override
    public void cancelButtonPressed(ActionEvent e) {
    	Stage stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }
    
    @Override
    public void restartButtonPressed(ActionEvent e) {
    	txtSudNo.clear();
    	txtIDNo.clear();
    	txtCellNumber.clear();
    	txtEmailAdress.clear();
    	txtCourseName.clear();
    	txtSurname.clear();
    	txtNames.clear();
    }
}
