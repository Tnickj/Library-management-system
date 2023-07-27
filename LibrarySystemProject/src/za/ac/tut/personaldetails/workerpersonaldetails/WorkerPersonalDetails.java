package za.ac.tut.personaldetails.workerpersonaldetails;

import za.ac.tut.personaldetails.PersonalDetails;

public class WorkerPersonalDetails extends PersonalDetails{
	private String employNumber;
	
	public WorkerPersonalDetails() {
		
	}

	public WorkerPersonalDetails(String name,String Surname, String cellnumber, String email, String password, String idNo,String registrationNumber) throws Exception {
		super(name,Surname, cellnumber, email, password, idNo);
		this.employNumber = registrationNumber;
	}

	public String getRegistrationNumber() {
		return employNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.employNumber = registrationNumber;
	}

	@Override
	public String toString() {
		return super.toString()+"Worker registration Number :" + employNumber ;
	}
	
	
}

