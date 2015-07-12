package trumanz.HibernateLearn;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Hello world!
 *
 */
public class HQLTest {
	private SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static Logger logger = Logger.getLogger(HQLTest.class);
	//TODO, session resource leak
	private Session session = factory.openSession();

	@Before
	public void clearTable() {
		this.deleteAllEmployee();
		logger.info("this called, clear the table");
	}

	@Test
	public void From_Test() {
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);

		Integer empID1 = this.addEmployee("Zara", "Ali", 1000);

		@SuppressWarnings("rawtypes")
		List results = query.list();
		Assert.assertEquals(1, results.size());
		
		Employee e = (Employee) results.get(0);
		Assert.assertEquals("Zara", e.getFirstName());
		Assert.assertEquals("Ali", e.getLastName());
		Assert.assertEquals(1000, e.getSalary());

		this.addEmployee("Daisy", "Das", 5000);
		this.addEmployee("John", "Paul", 10000);

		Assert.assertEquals(3, query.list().size());

	}
	
	
	@Test
	public void AS_Test(){
		String hql = "From Employee AS E";
		Query query = session.createQuery(hql);
		Assert.assertEquals(0, query.list().size());
	}
	
	@Test 
	public void WHERE_Test(){
		String hql = "FROM Employee E WHERE E.salary=1000";
		Query query = session.createQuery(hql);
		this.addEmployee("Zara", "Ali", 1000);
		
		@SuppressWarnings("rawtypes")
		List results = query.list();
		Assert.assertEquals(1, results.size());
		
		Employee e = (Employee) results.get(0);
		Assert.assertEquals("Zara", e.getFirstName());
		Assert.assertEquals("Ali", e.getLastName());
		Assert.assertEquals(1000, e.getSalary());
	}
	
	@Test
	public void GROUP_BY_TEST(){
		String hql = "SELECT SUM(E.salary), E.firstName FROM Employee E GROUP BY E.firstName";
		Query query = session.createQuery(hql);
		this.addEmployee("Zara", "Ali", 1000);
		this.addEmployee("Zara", "Ali", 1000);
		this.addEmployee("Zara2", "Ali", 1000);
		
		
		for(Object obj : query.list()){
		
			//logger.info(obj.getClass().toString());
			//logger.info(obj.toString());
	
		}
	}
	
	@Test
	public void UPDATE_TEST(){
		
		logger.info("enter");
		String hql = "UPDATE Employee set salary = :salary "  + 
	             "WHERE id = :employee_id";
		
		
		
		Integer  id = this.addEmployee("Zara", "Ali", 1000);
	
		
		Query query = session.createQuery(hql);
		query.setParameter("salary", 5000);
		query.setParameter("employee_id", id.intValue());
		

		Transaction tx =  session.beginTransaction();
		
		int updatedCount = query.executeUpdate();
		tx.commit();
				
		Assert.assertEquals(1, updatedCount);
		
		
		Employee employee = (Employee) session.get(Employee.class, id);
		

		
		Assert.assertEquals(5000, employee.getSalary());
		

	}
	
	
	

	public Integer addEmployee(String fname, String lname, int salary) {
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
	
	
	

	public void listEmployees() {
		Session session = factory.openSession();

		@SuppressWarnings("rawtypes")
		List employees = session.createQuery("FROM Employee").list();
		for (Object obj : employees) {
			logger.info(obj.toString());
		}
		session.close();
	}

	public void updateEmployee(Integer EmployeeID, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		Employee employee = (Employee) session.get(Employee.class, EmployeeID);
		employee.setSalary(salary);
		session.update(employee);
		tx.commit();
		session.close();
	}

	public void deleteEmployee(Integer EmployeeID) {
		Session session = factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		Employee employee = (Employee) session.get(Employee.class, EmployeeID);
		session.delete(employee);
		tx.commit();
		session.close();
	}

	public void deleteAllEmployee() {
		Session session = factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		session.createQuery("DELETE FROM  Employee ").executeUpdate();
		tx.commit();
		session.close();
	}
}
