package trumanz.HibernateLearn;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class ManageEmployeeWithAnnotation {
	private static SessionFactory factory;
	private static Logger logger = Logger.getLogger(ManageEmployeeWithAnnotation.class);
	public static void main(String[] args) {
		
	
		Configuration config = new Configuration().configure();
		config.addAnnotatedClass(trumanz.HibernateLearn.EmployeeWithAnnotation.class);
		factory = config.buildSessionFactory();

		ManageEmployeeWithAnnotation ME = new ManageEmployeeWithAnnotation();
		
	//	ME.deleteAllEmployeeWithAnnotation();
		
		Integer empID1 = ME.addEmployeeWithAnnotation("Zara", "Ali", 1000);
		Integer empID2 = ME.addEmployeeWithAnnotation("Daisy", "Das", 5000);
		Integer empID3 = ME.addEmployeeWithAnnotation("John", "Paul", 10000);
		
	
		ME.listEmployeeWithAnnotations();
		
		ME.updateEmployeeWithAnnotation(empID1, 5000);
		ME.deleteEmployeeWithAnnotation(empID2);
		ME.listEmployeeWithAnnotations();
		
		
		

	}

	public Integer addEmployeeWithAnnotation(String fname, String lname, int salary)  {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer EmployeeWithAnnotationID = null;
		tx = session.beginTransaction();
		EmployeeWithAnnotation empolyee = new EmployeeWithAnnotation(fname, lname, salary);
		EmployeeWithAnnotationID = (Integer) session.save(empolyee);
		tx.commit();

		session.close();
		
		return EmployeeWithAnnotationID;
	}
	
	public void listEmployeeWithAnnotations(){
		Session session =  factory.openSession();

		@SuppressWarnings("rawtypes")
		List  EmployeeWithAnnotations = session.createQuery("FROM EmployeeWithAnnotation").list();
		for(Object obj : EmployeeWithAnnotations){
			logger.info(obj.toString());
		}
		session.close();
	}
	
	public void updateEmployeeWithAnnotation(Integer EmployeeWithAnnotationID, int salary){
		Session session =  factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		EmployeeWithAnnotation EmployeeWithAnnotation = (EmployeeWithAnnotation)session.get(EmployeeWithAnnotation.class, EmployeeWithAnnotationID);
		EmployeeWithAnnotation.setSalary(salary);
		session.update(EmployeeWithAnnotation);
		tx.commit();
		session.close();
	}
	
	public void deleteEmployeeWithAnnotation(Integer EmployeeWithAnnotationID){
		Session session =  factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		EmployeeWithAnnotation EmployeeWithAnnotation = (EmployeeWithAnnotation)session.get(EmployeeWithAnnotation.class, EmployeeWithAnnotationID);
		session.delete(EmployeeWithAnnotation);
		tx.commit();
		session.close();
	}
	
	public void deleteAllEmployeeWithAnnotation(){
		Session session =  factory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		
		session.createQuery("DELETE FROM  EMPLOYEEWithAnnotation ").executeUpdate();
		
	
		tx.commit();
		session.close();
	}
}
