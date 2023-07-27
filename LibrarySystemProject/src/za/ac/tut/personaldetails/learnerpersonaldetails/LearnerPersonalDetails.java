package za.ac.tut.personaldetails.learnerpersonaldetails;

import za.ac.tut.nameexception.NameException;
import za.ac.tut.personaldetails.PersonalDetails;
import za.ac.tut.studentnoexception.StudentNoException;

public class LearnerPersonalDetails extends PersonalDetails{
	private String studentNo;
	private String course;
	
	public LearnerPersonalDetails() {
		
	}

	public LearnerPersonalDetails(String name,String Surname, String cellnumber, String email, String password, String idNo,String studentNo,String course){
		super(name,Surname, cellnumber, email, password,idNo);
		this.studentNo = studentNo;
		this.course = course;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) throws StudentNoException{
		
		if(isStudentNoValid(studentNo)) {
			this.studentNo = studentNo;
		}else {
			throw new StudentNoException(ErrorMsg);
		}
	}

	private boolean isStudentNoValid(String studentNo) {
		boolean isValid = false;
		
		if(studentNo.length() == StudNoMAX) {
			isValid = true;
		}
		return isValid;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) throws NameException{
		if(isOtherValid(course)) {
			this.course = course;
		}else {
			throw new NameException(ErrorMsg);
		}
	}

	private static boolean isOtherValid(String course) {
        boolean isValid = false;
        
        if((course.isEmpty() == false)){
            isValid = true;
        }
        
        return isValid;
    }
	
	@Override
	public String toString() {
		return super.toString()+",studentNo :" + studentNo + ", course :" + course ;
	}
	
	
}
