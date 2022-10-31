package application;

public class Appointment {
	
	private int id;
	private String fullName;
	private String phone;
	private String date;
	private String hour;
	
	
	private String date1;
	private String hour1;
	private String date2;
	private String hour2;
	private String date3;
	private String hour3;
	private String date4;
	private String hour4;
	private String date5;
	private String hour5;
	private String date6;
	private String hour6;
	private String date7;
	private String hour7;
	
	//private Appointment[] appointmentTab;
	
	
	
	
	


	public Appointment(String hour, String date) {
		this.hour=hour;
		this.fullName=date;
	}
	public Appointment(String date1, String hour1, String date2, String hour2, String date3, String hour3, String date4,
			String hour4, String date5, String hour5, String date6, String hour6, String date7, String hour7) {
		super();
		this.date1 = date1;
		this.hour1 = hour1;
		this.date2 = date2;
		this.hour2 = hour2;
		this.date3 = date3;
		this.hour3 = hour3;
		this.date4 = date4;
		this.hour4 = hour4;
		this.date5 = date5;
		this.hour5 = hour5;
		this.date6 = date6;
		this.hour6 = hour6;
		this.date7 = date7;
		this.hour7 = hour7;
	}
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getHour1() {
		return hour1;
	}
	public void setHour1(String hour1) {
		this.hour1 = hour1;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	public String getHour2() {
		return hour2;
	}
	public void setHour2(String hour2) {
		this.hour2 = hour2;
	}
	public String getDate3() {
		return date3;
	}
	public void setDate3(String date3) {
		this.date3 = date3;
	}
	public String getHour3() {
		return hour3;
	}
	public void setHour3(String hour3) {
		this.hour3 = hour3;
	}
	public String getDate4() {
		return date4;
	}
	public void setDate4(String date4) {
		this.date4 = date4;
	}
	public String getHour4() {
		return hour4;
	}
	public void setHour4(String hour4) {
		this.hour4 = hour4;
	}
	public String getDate5() {
		return date5;
	}
	public void setDate5(String date5) {
		this.date5 = date5;
	}
	public String getHour5() {
		return hour5;
	}
	public void setHour5(String hour5) {
		this.hour5 = hour5;
	}
	public String getDate6() {
		return date6;
	}
	public void setDate6(String date6) {
		this.date6 = date6;
	}
	public String getHour6() {
		return hour6;
	}
	public void setHour6(String hour6) {
		this.hour6 = hour6;
	}
	public String getDate7() {
		return date7;
	}
	public void setDate7(String date7) {
		this.date7 = date7;
	}
	public String getHour7() {
		return hour7;
	}
	public void setHour7(String hour7) {
		this.hour7 = hour7;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Appointment(int id, String fullName, String phone, String date, String hour) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.phone = phone;
		this.date = date;
		this.hour = hour;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	
	public int compareTo(Appointment appointment) {
		
		if(this.fullName.equals(appointment.fullName)&&this.phone.equals(appointment.phone)&&this.date.equals(appointment.date)&&this.hour.equals(appointment.hour)) {
			return 0;
		}
		return -1;
	}

}
