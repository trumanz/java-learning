package trumanz.HibernateLearn;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InterceptorTest {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static Logger logger = Logger.getLogger(HQLTest.class);
	private static Session session = null;

	@BeforeClass
	public static void openSession() {
		session = factory.openSession();

	}

	@AfterClass
	public static void closeSession() {
		Transaction tx = session.beginTransaction();
		session.createQuery("DELETE FROM  Employee").executeUpdate();
		tx.commit();
		session.close();
		session = null;
	}

	int count = 0;

	@Test
	public void createCountTest() {

		@SuppressWarnings("serial")
		Session session = factory.openSession(new EmptyInterceptor() {
			public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
					Type[] types) {
				count++;
				return true;
			}
		});

		Transaction tx = session.beginTransaction();
		for (int i = 0; i < 10; i++) {
			session.save(new Employee("b", "B", 2000));
		}
		tx.commit();
		
		Assert.assertEquals(10, count);

	}

}
