package za.ac.tut.bookdetails;

import za.ac.tut.data.Data;
import za.ac.tut.isbnexception.ISBNException;
import za.ac.tut.nameexception.NameException;

public class BookDetails  implements Data{
	private String title;
	private String isbn;
	private String publisher;
	private String author;
	private String category;
	
	public BookDetails() {
		
	}

	public BookDetails(String title, String isbn, String publisher,String author,String category) {
		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
		this.author = author;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws NameException{
		if(isOtherValid( title)) {
			this.title = title;
		}else {
			throw new NameException(title + ErrorMsg);
		}
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) throws ISBNException{
		if(isbnIsValid(isbn)) {
			this.isbn = isbn;
		}else {
			throw new ISBNException(isbn + ErrorMsg);
		}
	}

	private boolean isbnIsValid(String isbn) {
		boolean isVailid = false;
		
		if(isbn.length() == isbnMAX) {
			isVailid = true;
		}
		return isVailid;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) throws NameException{
		if(isOtherValid( publisher)) {
			this.publisher = publisher;
		}else {
			throw new NameException(publisher + ErrorMsg);
		}
	}
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) throws NameException{
		if(isNameValid(author)){
			this.author = author;
		}else {
			throw new NameException(author + ErrorMsg);
		}
	}

	private static boolean isNameValid(String author) {
        boolean isValid = false;
        int k = 0;
        
        char letter = ' ';
        for(int i = 0; i < author.length(); i++){
            letter = author.charAt(i); 
            
            if((Character.isDigit(letter)) ){
                k ++;
            }
        }
        if(k == 0 && (author.isEmpty() == false)){
            isValid = true;
        }
        
        return isValid;
    }
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) throws NameException{
		if(isOtherValid( category)) {
			this.category = category;
		}else {
			throw new NameException(category + ErrorMsg);
		}
	}

	private static boolean isOtherValid(String names) {
        boolean isValid = false;
        
        if((names.isEmpty() == false)){
            isValid = true;
        }
        
        return isValid;
    }
	
	@Override
	public String toString() {
		return "Book Details --> bookName :" + title + ", isbn :" + isbn + ", publisher :" + publisher +"Author name :"+author + "category :"+category;
	}
	
}
