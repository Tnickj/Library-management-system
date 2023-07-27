package application;
	
import javafx.application.Application;
import za.ac.tut.login.LoginController;

public class Main {
	
	public static void main(String[] args) {
		try {
			Application.launch(LoginController.class,args);
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
}