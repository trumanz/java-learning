package trumanz.JTA;

import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.jpa.JpaPersistModule;

public class JTATest {

	private static Injector injector;
	private static Logger logger = Logger.getLogger(JTATest.class);

	@BeforeClass
	public static void startPersistService() {
		injector = Guice.createInjector(new JpaPersistModule("testUnit4jta"));
		injector.getInstance(PersistService.class).start();
	}

	@AfterClass
	public static void stopPersistService() {
		injector.getInstance(PersistService.class).stop();
	}

	@Test
	public void sameEntityManagerTest() {
		TransactionalObject txnsObj1 = injector.getInstance(TransactionalObject.class);
		TransactionalObject txnsObj2 = injector.getInstance(TransactionalObject.class);
		
		Assert.assertNotNull(txnsObj1);
		Assert.assertTrue(txnsObj1 != txnsObj2);
		
		Assert.assertNotNull(txnsObj1.em);
		Assert.assertTrue(txnsObj1.em == txnsObj1.em);

	}
	
	
	@Test
	public void sameObjTest2() {
		TransactionalObject txnsObj1 = injector.getInstance(TransactionalObject.class);
		
		//1 clear all data;
		txnsObj1.em.getTransaction().begin();
		txnsObj1.em.createQuery("DELETE FROM JpaTestEntity").executeUpdate();
		txnsObj1.em.getTransaction().commit();
		
	//	EntityManager em;
//		em.createQuery("DELETE FROM JpaTestEntity");
		
		txnsObj1.createObjInTxnWithRollBack();
		Assert.assertEquals(0, txnsObj1.em.createQuery("SELECT x FROM JpaTestEntity x").getResultList().size());
		
		txnsObj1.createObjInTxn();
		Assert.assertEquals(2, txnsObj1.em.createQuery("SELECT x FROM JpaTestEntity x").getResultList().size());
		
	}
	
	
	public static class  TransactionalObject{
		@Inject
		EntityManager em;
		
		@Transactional
		public void  createObjInTxn(){
			
			
			
			JpaTestEntity entity = new JpaTestEntity();
			logger.info(entity.toString());
			em.persist(entity);
			logger.info(entity.toString());
			
			entity = new JpaTestEntity();
			logger.info(entity.toString());
			em.persist(entity);
			logger.info(entity.toString());
			
			//
			//em.getTransaction().rollback();
			//em.getTransaction().begin();
		}
		
		
		@Transactional
		public void  createObjInTxnWithRollBack(){
		
			JpaTestEntity entity = new JpaTestEntity();
			logger.info(entity.toString());
			em.persist(entity);
			logger.info(entity.toString());
			
			entity = new JpaTestEntity();
			logger.info(entity.toString());
			em.persist(entity);
			logger.info(entity.toString());
			
			//rollback, and must begin again, for continuous commit 
			em.getTransaction().rollback();
			em.getTransaction().begin();
		}
		
	}

}




