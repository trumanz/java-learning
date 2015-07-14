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
import org.junit.BeforeClass;
import org.junit.Test;

import trumanz.HibernateLearn.EmployeeWithXmlMap;


public class EntityManagerTest {

	
	@Entity
	public static class TestBean {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

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
	public void test() {
	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testUnit");
		

		EntityManager em = emf.createEntityManager();
		
	
		em.getTransaction().begin();
		TestBean e = new TestBean();
		em.persist(e);
		em.getTransaction().commit();

	}

}
