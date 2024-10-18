package application;


public class Employee implements Comparable<Employee>{

	private String employeeID;
	private String name;
	private int age;
	private String department;
	private String dateOfJoining;
	private String gender;

	public Employee(){
		this.employeeID = "E000";
		this.name = "Qusai Fofof";
		this.age = 80;
		this.department = "CS";
		this.dateOfJoining = "2015-09-18";
		this.gender = "Male";
	}

	public Employee(String line){
		if(line != null) {
			String[] tkz = line.split(",");
			if(tkz.length == 6){
				this.employeeID = tkz[0];
				this.name = tkz[1];
				this.age = Integer.parseInt( tkz[2] );
				this.department = tkz[3];
				this.dateOfJoining = tkz[4];
				this.gender = tkz[5];
			}
		}
	}

	public Employee(String employeeID, String name, int age, String separtment, String dateOfJoining, String gender) {
		super();
		this.employeeID = employeeID;
		this.name = name;
		this.age = age;
		this.department = separtment;
		this.dateOfJoining = dateOfJoining;
		this.gender = gender;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String separtment) {
		this.department = separtment;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", name=" + name + ", age=" + age + ", separtment=" + department
				+ ", dateOfJoining=" + dateOfJoining + ", gender=" + gender + "]";
	}

	@Override
	public int compareTo(Employee o) {
		return this.employeeID.compareTo(o.employeeID);
	}
}