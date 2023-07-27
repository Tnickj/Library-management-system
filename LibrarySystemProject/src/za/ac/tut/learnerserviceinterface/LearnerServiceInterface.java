package za.ac.tut.learnerserviceinterface;

import javafx.event.ActionEvent;

public interface LearnerServiceInterface {

	public void diplayAllBooks();
	public void submitOrder(ActionEvent e);
	public void searchBook(ActionEvent e);
	public void renewBook(ActionEvent e);
	public void returnBook(ActionEvent e);
	public void issuedBooks(ActionEvent e);
	public void done(ActionEvent e);
}
