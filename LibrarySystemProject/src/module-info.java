module LibrarySystemProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens za.ac.tut.login to javafx.graphics, javafx.fxml;
	opens za.ac.tut.registor to javafx.graphics, javafx.fxml;
	opens za.ac.tut.bookseditor to javafx.graphics, javafx.fxml;
	opens za.ac.tut.learnerservice to javafx.graphics, javafx.fxml;
	opens za.ac.tut.addbook to javafx.graphics, javafx.fxml;
	opens za.ac.tut.searchbook to javafx.graphics, javafx.fxml;
	opens za.ac.tut.searchstudent to javafx.graphics, javafx.fxml;
}
