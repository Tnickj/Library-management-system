package za.ac.tut.bookseditorinterface;

import java.io.IOException;
import javafx.event.ActionEvent;

public interface BooksEditorInterface {

	public void addBooks(ActionEvent e) throws IOException;
	public void viewAllStudent(ActionEvent e);
	public void searchBook(ActionEvent e) throws IOException;
	public void issuedBooks(ActionEvent e);
	public void viewAllBooks(ActionEvent e);
	public void doneMethod(ActionEvent e);
	public void searchStudent(ActionEvent e) throws IOException;
}
