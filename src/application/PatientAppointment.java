package application;

public class PatientAppointment {

	
	public PatientAppointment(String fullName, int age) {
		super();
		this.fullName = fullName;
		this.age = age;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	private String fullName;
	private int age;
}
