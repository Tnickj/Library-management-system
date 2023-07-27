package za.ac.tut.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import za.ac.tut.bookseditor.BooksEditorController;
import za.ac.tut.learnerservice.LearnerServiceController;
import za.ac.tut.logininterface.LoginInterface;
import za.ac.tut.registor.RegistrationFormController;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginController extends Application implements LoginInterface{
	
	@FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnCancel;
    
    @FXML
    private Button btnStudent;

    @FXML
    private Button btnAdmin;

    @FXML
    private Text txtWAY;
    
    @FXML
    private RadioButton radioAdmin;
    
    @FXML
    private RadioButton radioStudent;
    
    String mySQLQuery ,data;
    
    
    
    InetAddress address= null;
    
	Socket  socket = null;
	
    PrintWriter out= null;
    
    BufferedReader in = null;
    
    
    public LoginController(){
    	
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	@Override
	public void cancelButtonPressed(ActionEvent e) {
    	Stage stage = (Stage) btnCancel.getScene().getWindow();
    	stage.close();
    }
	
	@Override
	public void loginButtonPressed (ActionEvent e) throws IOException{
		
	    
		try {
			address = InetAddress.getByName("192.168.56.1");
			
			socket = new Socket(address,1010);
			
			out  = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			if(txtUserName.getText().isBlank() == false && txtPassword.getText().isBlank() == false) {
			
				if(radioStudent.isSelected()) {
					mySQLQuery = "select student_no,password from learner_details where student_no = "+txtUserName.getText()+" and password ="+" '"+txtPassword.getText()+"';";
					
					out.println(mySQLQuery);
					
					data = in.readLine();
					System.out.println(data);
					String [] loginData = data.split("#");
					System.out.println(loginData[0] +"\n" + loginData[1]);
			   		if(!data.isEmpty() && loginData[0].equals(txtUserName.getText()) && loginData[1].equals(txtPassword.getText())) {
				
			    		Pane root = FXMLLoader.load(LearnerServiceController.class.getResource("LearnerService.fxml"));
						Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
						Scene scene = new Scene(root);
						stage.setScene(scene);
						stage.setTitle("Ordering Book");
						stage.show();
					}else{
						JOptionPane.showMessageDialog(null, "Wrong Username or Password","",JOptionPane.ERROR_MESSAGE);
					}
								
				} else if(radioAdmin.isSelected()){
					mySQLQuery = "select employee_id,password from workers_table where employee_id = "+txtUserName.getText() +" and password ="+" '"+txtPassword.getText()+"';";
					out.println(mySQLQuery);
					data = in.readLine();

					System.out.println(data);
		    		String [] loginData = data.split("#");
					if(!data.isEmpty() && loginData[0].equals(txtUserName.getText()) && loginData[1].equals(txtPassword.getText())) {	
			 			Pane root = FXMLLoader.load(BooksEditorController.class.getResource("BooksEditor.fxml"));
			    		Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			    		Scene scene = new Scene(root);
			    		stage.setScene(scene);
			    		stage.setTitle("Book Editor Window");
				    		stage.show();
					}else{
						JOptionPane.showMessageDialog(null, "Wrong Username or Password","",JOptionPane.ERROR_MESSAGE);
					}
		 		}else {
		 			JOptionPane.showMessageDialog(null, "Please tell us if you are Student or Admin","",JOptionPane.ERROR_MESSAGE);
		 		}
			}else {
				JOptionPane.showMessageDialog(null, "Please enter Username and Password");
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
    public void registerButtonPressed(ActionEvent e) throws IOException {
    	if(radioStudent.isSelected()) {
			Pane root = FXMLLoader.load(RegistrationFormController.class.getResource("RegistrationForm.fxml"));
			Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("");
			stage.show();
		}else if (radioAdmin.isSelected()){
			JOptionPane.showMessageDialog(null, "Administrator can't register themselves, Please send your details to Database sector","",JOptionPane.ERROR_MESSAGE);
		}else {
 			JOptionPane.showMessageDialog(null, "Please tell us if you are Student or Admin","",JOptionPane.ERROR_MESSAGE);
 		}
    }	
}
