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
	
	@BeforeClass
	public static void setUpTable(){
		Configuration conf = new Configuration().configure().addAnnotatedClass(TestBean.class);
		SessionFactory factory = conf.buildSessionFactory();
		factory.close();
	}
	
	
	@Test
	public void CRUD_TEST() {
	
	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testUnit");
		
		EntityManager em = emf.createEntityManager();
		
		String name = "TestName";
		
		//Create
		em.getTransaction().begin();
		TestBean e = new TestBean();
		e.setFirstName(name);
		em.persist(e);
		em.getTransaction().commit();
		
		//Retrieval
		TestBean e2 = em.find(TestBean.class, e.id);
		Assert.assertEquals(e2.firstName, e.firstName);
		Assert.assertTrue(e.getFirstName().equals(e.getFirstName()));

		String name2 = name + "append";
		//Update
		em.getTransaction().begin();
		e2.setFirstName(name2);
		em.getTransaction().commit();
		TestBean e3 = em.find(TestBean.class, e.id);
		Assert.assertEquals(e3.firstName, name2);
		Assert.assertFalse(e3.getFirstName().equals(name));
		
	
		//Delete
		em.getTransaction().begin();
		em.remove(e3);
		em.getTransaction().commit();
		TestBean e4 = em.find(TestBean.class, e.id);
		Assert.assertEquals(e4, null);
	}

}
