package trumanz.HibernateLearn;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class ManageEmployee {
	private static SessionFactory factory;
	private static Logger logger = Logger.getLogger(ManageEmployee.class);
	public static void main(String[] args) {
		
		
		factory = new Configuration().configure().buildSessionFactory();

		ManageEmployee ME = new ManageEmployee();
		
		ME.deleteAllEmployee();
		
		Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
		Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
		Integer empID3 = ME.addEmployee("John", "Paul", 10000);
		
	
		ME.listEmployees();
		
		ME.updateEmployee(empID1, 5000);
		ME.deleteEmployee(empID2);
		ME.listEmployees();
		
		
		

	}

	public Integer addEmployee(String fname, String lname, int salary)  {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		tx = session.beginTransaction();
		Employee empolyee = new Employee(fname, lname, salary);
		employeeID = (Integer) session.save(empolyee);
		tx.commit();

		session.close();
		
		return employeeID;
	}
	
	public void listEmployees(){
		Session session =  factory.openSession();

		@SuppressWarnings("rawtypes")
		List  employees = session.createQuery("FROM Employee").list();
		for(Object obj : employees){
			logger.info(obj.toString());
		}
		session.close();
	}
	
	public void updateEmployee(Integer EmployeeID, int salary){
		Session session =  factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		Employee employee = (Employee)session.get(Employee.class, EmployeeID);
		employee.setSalary(salary);
		session.update(employee);
		tx.commit();
		session.close();
	}
	
	public void deleteEmployee(Integer EmployeeID){
		Session session =  factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		Employee employee = (Employee)session.get(Employee.class, EmployeeID);
		session.delete(employee);
		tx.commit();
		session.close();
	}
	
	public void deleteAllEmployee(){
		Session session =  factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		
		session.createQuery("DELETE FROM  Employee ").executeUpdate();
		
	
		tx.commit();
		session.close();
	}
}
