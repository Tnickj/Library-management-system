package za.ac.tut.logininterface;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public interface LoginInterface {

	public void start(Stage primaryStage) throws Exception;
	public void cancelButtonPressed(ActionEvent e);
	public void loginButtonPressed (ActionEvent e) throws IOException;
	public void registerButtonPressed(ActionEvent e) throws IOException;
}
