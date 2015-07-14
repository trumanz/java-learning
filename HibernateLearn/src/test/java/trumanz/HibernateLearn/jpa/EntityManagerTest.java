package trumanz.HibernateLearn.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;

import org.hibernate.cfg.Configuration;
import org.junit.Test;

import trumanz.HibernateLearn.EmployeeWithXmlMap;


public class EntityManagerTest {

	
	
	
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
