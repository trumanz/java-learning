package trumanz.HibernateLearn;

/*
 * the ORM information indicated by Employee.hbm.xml
 */
public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private int salary;
	
	public Employee(){}
	public Employee(String fname, String lname, int salary){
		this.setFirstName(fname);
		this.setLastName(lname);
		this.setSalary(salary);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString(){
		
		return "id=" + this.id + 
				", firstName=" + this.firstName + 
				", lastName=" + this.lastName + 
				", salary=" + this.salary;
	
	}
	

}
