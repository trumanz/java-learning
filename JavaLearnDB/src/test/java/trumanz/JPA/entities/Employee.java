package trumanz.JPA.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMP")
public class Employee {

	@Id
	@GeneratedValue
	@Column(name = "EMP_ID")
	private int id;

	@Column(name = "EMP_NAME")
	private String name;

	@Column(name = "EMP_SALARY")
	private long salary;

	@ManyToOne
	@JoinColumn(name = "EMP_DEPT_ID", foreignKey = @ForeignKey(name = "id") , nullable = false)
	public Department department;

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (this.getClass() != o.getClass())
			return false;
		Employee that = (Employee) o;

		if (this.id != that.id)
			return false;
		if (this.salary != that.salary)
			return false;
		if (this.name == null ? that.name != null : (!this.name.equals(that.name)))
			return false;
		if (this.department == null ? that.department != null : (!this.department.equals(that.department)))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int hashCode = this.id;
		hashCode = 32 * hashCode + (this.name == null ? 0 : this.name.hashCode());
		hashCode = (int) (32 * hashCode + salary);
		hashCode = 32 * hashCode + (this.name == null ? 0 : this.department.hashCode());

		return hashCode;
	}

	@Override
	public String toString() {
		return "id=" + this.id + ", name=" + this.name + ", salary=" + this.salary + ", department="
				+ this.department.toString();
	}

	public Employee() {
	}

	public Employee(String name, long salary, Department department) {
		this.setName(name);
		this.setSalary(salary);
		this.department = department;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
