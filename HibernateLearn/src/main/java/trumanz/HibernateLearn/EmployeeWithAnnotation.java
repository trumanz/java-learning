package trumanz.HibernateLearn;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEEWithAnnotation")
public class EmployeeWithAnnotation {
	@Id 
	@GeneratedValue
	private int id;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "salary")
	private int salary;



	public EmployeeWithAnnotation(){}
	public EmployeeWithAnnotation(String fname, String lname, int salary){
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
