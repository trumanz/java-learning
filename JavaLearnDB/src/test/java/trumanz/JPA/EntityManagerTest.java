package trumanz.JPA;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntityManagerTest {

	// http://docs.oracle.com/javaee/6/tutorial/doc/bnbqw.html
	// EntityManager is not thread-safe
	// EntityManagerFactory is thread-safe
	public static EntityManager createEntityManager() {
		logger.info("create EntityManager");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("testUnit4jpa");

		return emf.createEntityManager();
	}

	static Logger logger = Logger.getLogger(EntityManagerTest.class);
	 

	@Entity
	public static class TestBean {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public Long id;
		public String firstName;

		public TestBean() {
		}

		public TestBean(String name) {
			this.firstName = name;
		}
	}

	@Test
	public void CRUD_TEST() {

		EntityManager em = createEntityManager();

		final String TESTNAME = "TestName";
		final String TESTNAME_UPDATE = TESTNAME + "append";
		TestBean beanCreate = new TestBean(TESTNAME);
		TestBean beanFind = null;

		// Create
		em.getTransaction().begin();
		em.persist(beanCreate);
		em.getTransaction().commit();
		logger.info("e1:" + beanCreate.toString());

		// Retrieval
		beanFind = em.find(TestBean.class, beanCreate.id);
		Assert.assertTrue(beanFind.firstName.equals(TESTNAME));
		logger.info("e2:" + beanFind.toString());

		// Update
		em.getTransaction().begin();
		beanFind.firstName = TESTNAME_UPDATE;
		em.getTransaction().commit();
		TestBean e3 = em.find(TestBean.class, beanCreate.id);
		Assert.assertTrue(e3.firstName.equals(TESTNAME_UPDATE));

		logger.info("e3:" + e3.toString());

		// they are all pointer to same object
		Assert.assertTrue(beanCreate == beanFind);
		Assert.assertTrue(beanFind == e3);

		// Delete
		em.getTransaction().begin();
		em.remove(e3);
		em.getTransaction().commit();
		TestBean e4 = em.find(TestBean.class, beanFind.id);
		Assert.assertEquals(e4, null);

	}
	
	
}
