package trumanz.HibernateLearn.jpa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Table;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import trumanz.HibernateLearn.EmployeeWithXmlMap;


public class EntityManagerTest {

	
	@Entity
	public static class TestBean {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    public Long id;

	    private String firstName;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
	}
	
	static Logger logger =  Logger.getLogger(EntityManagerTest.class);
	
	@BeforeClass
	public static void setUpTable(){
		//hiberante.cfg.xml  hibernate.hbm2ddl.auto configuration for update table in DB
		Configuration conf = new Configuration().configure().addAnnotatedClass(TestBean.class);
		SessionFactory factory = conf.buildSessionFactory();
		factory.close();
	}
	
	
	@Test
	public void CRUD_TEST() {
	
	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testUnit4jpa");
		
		EntityManager em = emf.createEntityManager();
		
		String name = "TestName";
		
		//Create
		em.getTransaction().begin();
		TestBean e1 = new TestBean();
		e1.setFirstName(name);
		em.persist(e1);
		em.getTransaction().commit();
		
		logger.info("e1:" + e1.toString());
		
		//Retrieval
		TestBean e2 = em.find(TestBean.class, e1.id);
		Assert.assertEquals(e2.firstName, e1.firstName);
		Assert.assertTrue(e2.getFirstName().equals(e1.getFirstName()));
		
		logger.info("e2:" + e2.toString());

		String name2 = name + "append";
		//Update
		em.getTransaction().begin();
		e2.setFirstName(name2);
		em.getTransaction().commit();
		TestBean e3 = em.find(TestBean.class, e1.id);
		Assert.assertEquals(e3.firstName, name2);
		Assert.assertFalse(e3.getFirstName().equals(name));
		
		logger.info("e3:" + e3.toString());

	
		//they are all pointer to  same object
		Assert.assertTrue(e1 == e2);
		Assert.assertTrue(e2 == e3);
		
		//Delete
		em.getTransaction().begin();
		em.remove(e3);
		em.getTransaction().commit();
		TestBean e4 = em.find(TestBean.class, e1.id);
		Assert.assertEquals(e4, null);
		
		
		
	}

}
