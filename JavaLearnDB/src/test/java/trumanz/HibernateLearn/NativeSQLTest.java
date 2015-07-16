package trumanz.HibernateLearn;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NativeSQLTest {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static Logger logger = Logger.getLogger(HQLTest.class);
	private static Session session = null;

	@BeforeClass
	public static void openSession() {
		session = factory.openSession();

		Transaction tx = session.beginTransaction();
		session.createQuery("DELETE FROM  EmployeeWithXmlMap").executeUpdate();
		tx.commit();

		tx = session.beginTransaction();
		Integer aemployeeID = (Integer) session.save(new EmployeeWithXmlMap("a", "A", 1000));
		Integer bemployeeID = (Integer) session.save(new EmployeeWithXmlMap("b", "B", 2000));
		Integer cemployeeID = (Integer) session.save(new EmployeeWithXmlMap("c", "C", 3000));

		session.save(new EmployeeWithXmlMap("d", "D", 4000));
		session.save(new EmployeeWithXmlMap("e", "E", 5000));
		session.save(new EmployeeWithXmlMap("f", "F", 6000));

		tx.commit();

	}

	@AfterClass
	public static void closeSession() {
		Transaction tx = session.beginTransaction();
		session.createQuery("DELETE FROM  EmployeeWithXmlMap").executeUpdate();
		tx.commit();
		session.close();
		session = null;
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void scalarQueryTest() {
		String sql = "SELECT first_name, salary FROM EMPLOYEEWithXmlMap";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		Assert.assertEquals(6, results.size());
		Assert.assertEquals("a", ((Map) results.get(0)).get("first_name"));

	}

	@Test
	public void entityQueryTest() {
		String sql = "SELECT * FROM EMPLOYEEWithXmlMap";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(EmployeeWithXmlMap.class);
		@SuppressWarnings("rawtypes")
		List results = query.list();
		Assert.assertEquals(6, results.size());
		Assert.assertEquals("a", ((EmployeeWithXmlMap) results.get(0)).getFirstName());
	}

	@Test
	public void namedSQLQueryTest() {
		String sql = "SELECT * FROM EMPLOYEEWithXmlMap WHERE salary = :salary";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("salary", 2000);
		query.addEntity(EmployeeWithXmlMap.class);
		@SuppressWarnings("rawtypes")
		List results = query.list();
		Assert.assertEquals(1, results.size());
		Assert.assertEquals("b", ((EmployeeWithXmlMap) results.get(0)).getFirstName());
	}
}
