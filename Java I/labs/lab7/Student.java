package lab7;
/*TA:Danield
 * -1 missing indentations
 * 99/100
 */

/**
 * 
 * @author Steven Friedman
 * creates a new data type Student with first and last names, 
 * student ID number, number of credits and GPA
 */
public class Student {
	private String firstName;
	private String lastName;
	private int studentID;
	private int credits;
	private double gpa;
	
	/**
	 * 
	 * @param String firstName
	 * @param String lastName
	 * @param int studentID
	 * also initializes credits and GPA
	 */
	public Student(String firstName, String lastName, int studentID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentID = studentID;
		this.credits = 0;				
		this.gpa = 0;
	}
	
	/**
	 * 
	 * @return name and ID in String form
	 */
	public String toString(){
		return "Name: " + firstName + " " + lastName + "    Student ID: " + studentID;
	}
	
	/**
	 * 
	 * @return name in String form
	 */
	public String getName(){
		return firstName + " " + lastName;
	}
	
	/**
	 * 
	 * @return student ID as an int
	 */
	public int getStudentID(){
		return studentID;
	}
	
	/**
	 * 
	 * @return number of credits as an int
	 */
	public int getCredits(){
		return credits;
	}
	
	/**
	 * 
	 * @return GPA as a double
	 */
	public double getGPA(){
		return gpa;
	}

	/**
	 * 
	 * calculates string based on number of credits with divisions at 30, 60, 90
	 * @return class as String
	 */
	public String getClassStanding(){
		if (credits >= 90) return "Senior";
		else if (credits >= 60) return "Junior";
		else if (credits >= 30) return "Sophomore";
		return "Freshman";
	}
	
	/**
	 * 
	 * calculates graduation based on number of credits being 120
	 * @return truth value of "student has graduated"
	 */
	public boolean isGraduated(){
		if (credits < 120) return false;
		return true;
	}
	
	/**
	 * 
	 * @param Student other
	 * @return truth value of "this student has a higher GPA than 'other'"
	 */
	public boolean hasHigherAverage(Student other){
		if (this.gpa > other.gpa) return true;
		return false;
	}
	
	/**
	 * 
	 * edits student's number of credits and GPA based on their grade in a course with a given number of credits
	 * @param double courseGrade
	 * @param int courseCredits
	 */
	public void submitGrade(double courseGrade, int courseCredits){
		this.credits = this.credits + courseCredits;
		this.gpa = ((this.gpa * (this.credits - courseCredits)) + (courseGrade * courseCredits))/this.credits;
		this.gpa = (10.0*(Math.round(this.gpa*1000)))/10000.0;
	}
	
	/**
	 * 
	 * computes total tuition paid so far based on number of credits
	 * @return tuition paid so far as a double
	 */
	public double computeTuition(){
		if (credits < 18) return 22850.0;
		return Math.round(credits/18.0)*22850.0;
	}
	
	/**
	 * 
	 * creates a student, baby, with name, ID number, GPA, and credits
	 * @param Student other
	 * @return Student baby
	 */
	public Student createBaby(Student other){
		Student baby = new Student(this.firstName + " " + this.lastName, other.firstName + " " + other.lastName, (int)(Math.random()*900000.0) + 100000);
		baby.gpa = (this.gpa + other.gpa)/2.0;
		baby.credits = Math.max(this.credits, other.credits);
		return baby;
	}
	
}
