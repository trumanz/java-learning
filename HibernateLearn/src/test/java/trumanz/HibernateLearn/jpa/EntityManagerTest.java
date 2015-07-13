package trumanz.HibernateLearn.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;



public class EntityManagerTest {

	@Test
	public void test() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testUnit");
		
		
	}

}
