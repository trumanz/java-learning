package trumanz.HibernateLearn.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import trumanz.HibernateLearn.jpa.entities.Department;
import trumanz.HibernateLearn.jpa.entities.Employee;
import trumanz.HibernateLearn.jpa.entities.Project;

public class ORMTest {
	
	static Logger logger = Logger.getLogger(ORMTest.class);

	@BeforeClass
	public static void reCreateTable(){
		Configuration  cfg = new Configuration().configure()
				.setProperty(Environment.HBM2DDL_AUTO, "create" )
				.addAnnotatedClass(Employee.class)
				.addAnnotatedClass(Department.class)
				.addAnnotatedClass(Project.class);
		 
		SessionFactory sf = cfg.buildSessionFactory(new StandardServiceRegistryBuilder()
		         .applySettings( cfg.getProperties() )
		         .build());
		
		
		sf.close();
	
	}
	
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("testUnit4jpa");
	
	
	@Test
	public void foreignKeyTest()
	{
		//Employee must belong to one exist Department
		
		EntityManager entityManager  = emf.createEntityManager();
		Department department = null;
		Employee employee = null;
		
		
		//1. create department and then create employee, successful
		entityManager.getTransaction().begin();
		department = new Department("XYD/D");
		employee = new Employee("ezoucai", 20000,  department);
		entityManager.persist(department);
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
		
		employee = entityManager.find(Employee.class, employee.getId());
		
		Employee e2 =  new Employee("ezoucai", 20000,  department);
		e2.setId(employee.getId());
		Assert.assertEquals(employee, e2);
				
		entityManager.close();
	}
	
	@Test
	public void nonExistForeignkeyTest()
	{
		//Employee must belong to one exist Department
		
		EntityManager entityManager  = emf.createEntityManager();
		Department department = null;
		Employee employee = null;
		

		//2. create employee with non exist department 
		entityManager.getTransaction().begin();
		department = new Department("XYD/Y");
		employee = new Employee("truman", 20000,  department);
		boolean catheced =false;
		try{
			entityManager.persist(employee);
		} catch(IllegalStateException e){
			catheced = true;
			entityManager.getTransaction().rollback();
			entityManager.getTransaction().begin();
		}
		Assert.assertTrue(catheced);

		entityManager.getTransaction().commit();
	
		
		entityManager.close();
	}
	
	
	@Test
	public void deleteForeignKey()
	{
		//Employee must belong to one exist Department
		
		EntityManager entityManager  = emf.createEntityManager();
		Department department = null;
		Employee employee = null;
		
		
		//1. create department and then create employee, successful
		entityManager.getTransaction().begin();
		department = new Department("IT");
		employee = new Employee("trumanz", 20000,  department);
		entityManager.persist(department);
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
		
		entityManager.getTransaction().begin();
		//2. delete the department
		department = entityManager.find(Department.class, department.getName());
		entityManager.remove(department);
		boolean catched = false;
		try{
			
			entityManager.getTransaction().commit();
		}catch(Exception e){
			logger.info(e.getClass());
			//entityManager.getTransaction().rollback();
			catched = true;
		}
		Assert.assertTrue(catched);
		
		
		Employee e2 =  new Employee("trumanz", 20000,  department);
		e2.setId(employee.getId());
		Assert.assertEquals(employee, e2);
				
		entityManager.close();
	}
	
}
