package za.ac.tut.personaldetails;

import java.util.regex.Pattern;
import za.ac.tut.cellphoneexception.CellphoneException;
import za.ac.tut.data.Data;
import za.ac.tut.emailadressexception.EmailAdressException;
import za.ac.tut.idexception.IdException;
import za.ac.tut.nameexception.NameException;
import za.ac.tut.passwordexception.PasswordException;

public abstract class PersonalDetails implements Data{
	private String name;
	private String surname;
	private String cellnumber;
	private String email;
	private String idNo;
	private String password;
	
	public PersonalDetails() {
		
	}

	public PersonalDetails(String name,String surname, String cellnumber, String email,String password, String idNo){
		this.name = name;
		this.surname = surname;
		this.cellnumber = cellnumber;
		this.email = email;
		this.password = password;
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws NameException{
		if(isNameValid(name)) {
		this.name = name;
		}else {
			throw new NameException(name + ErrorMsg);
		}
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) throws NameException{
		if(isNameValid(surname)) {
			this.surname = surname;
		}else {
			throw new NameException(surname + ErrorMsg);
		}
	}

	public String getCellnumber(){
		return cellnumber;
	}

	public void setCellnumber(String cellnumber) throws CellphoneException{
		
		if(cellIsValid (cellnumber)) {
			this.cellnumber = cellnumber;
		}else {
			throw new CellphoneException("Cell number : "+cellnumber + ErrorMsg);
		}
	}

	private boolean cellIsValid(String cellnumber) {
		boolean isValid=false;
		if(cellnumber.length() == cellMAX) {
			isValid = true;
		}
		return isValid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email)throws EmailAdressException {
		if(emailIsValid(email)) {
			this.email = email;
		}else {
			throw new EmailAdressException("Email Address: "+email+ " "+ErrorMsg);
		}
	}
	
	private boolean emailIsValid(String email) {
		boolean isValid = false;
		
		
		String emailRegex = "^[A-Z0-9._+&*-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}";
        /* "[a-zA-Z0-9_+&-]+)@" + 
         "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,20}$";*/
		Pattern pattern = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		if(pattern.matcher(email).matches() && email.isEmpty() == false){
			isValid = true;
		}
		return isValid;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws PasswordException{
		if(passwoedIsValid(password)) {
			this.password = password;
		}else {
			throw new PasswordException("Password " +ErrorMsg);
		}
	}

	private boolean passwoedIsValid(String password) {
		boolean isValid = false;
		char passChar;
		int digit =0;
		int upper = 0;
		int lower = 0;
		int specialChar = 0;
		
		for(int i=0;i<password.length();i++) {
			
			passChar = password.charAt(i);
			
			if(Character.isDigit(passChar)) {
				 digit++;
			}else if(Character.isUpperCase(passChar)) {
				upper ++;
			}else if(Character.isLowerCase(passChar)) {
				lower ++;
			}else {
				specialChar ++;
			}
		}
		if(password.length() >= passwordMIN && password.length() <= passwordMAX  && digit >= MIN && upper >= MIN && lower >= MIN && specialChar >= MIN) {
			isValid = true;
		}
		return isValid;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo)throws IdException {
		if(IDIsValid(idNo)) {
			this.idNo = idNo;
		}else {
			throw new IdException("ID number : "+idNo + ErrorMsg);
		}
	}

	private boolean IDIsValid(String idNo) {
		boolean isValid = false;
		if(idNo.length() == IdMAX) {
			isValid = true;
		}
		return isValid;
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
	@Override
	public String toString() {
		return "Personal Details name is :" + name +",Surname is:"+surname+ ", cellnumber :" + cellnumber + ", emailaddress :" + email +"Password :"+password+ ", idNo:" + idNo;
	}
	
	
}
