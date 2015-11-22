package trumanz.HibernateLearn.realiation;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class ORMTest {

	private Session session = null;
	private SessionFactory sessionFactory = null;
	private EntityManager entityManager = null;
	private static Logger logger = Logger.getLogger(ORMTest.class);

	private static Set<Class<?>> getAllEntityClass() {
		Reflections reflections = new Reflections(ORMTest.class.getCanonicalName());
		return reflections.getTypesAnnotatedWith(Entity.class);
	}

	@Before
	public void createH2Session() throws IOException {

		Configuration cfg = new Configuration();
		Properties p = new Properties();
		// p.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
		p.put("dialect", "org.hibernate.dialect.H2Dialect");
		p.put("hibernate.connection.driver_class", "org.h2.Driver");
		p.put("hibernate.connection.url", "jdbc:h2:mem:db1");
		p.put("hibernate.connection.username", "sa");
		p.put("hibernate.connection.password", "");
		p.put("hibernate.hbm2ddl.auto", "update");
		p.put("show_sql", "true");
		cfg.setProperties(p);

		for (Class<?> c : getAllEntityClass()) {
			System.out.println("add: " + c.getName());
			cfg.addAnnotatedClass(c);
		}

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		sessionFactory = cfg.buildSessionFactory(builder.build());
		session = sessionFactory.openSession();

		logger.info("Session opened");

		// EntityManagerFactory emFactory =
		// Persistence.createEntityManagerFactory("dummyPersistenUnitName", p);
		// entityManager = emFactory.createEntityManager();
	}

	@After
	public void closeH2Session() throws IOException {
		session.close();
		sessionFactory.close();
		logger.info("session.isOpen(): " + session.isOpen());
		logger.info("Session closed");
	}

	@Entity
	@Table(name = "AirtportTrans", indexes = { @Index(name = "countrIndex", columnList = "country") })
	public static class Position {
		@Id
		public Integer id = 0;
		public String country;
		public String city;

		public Position(Integer id, String country, String city) {
			this.id = id;
			this.country = country;
			this.city = city;
		}

		public Position(Integer id) {
			this.id = id;
			this.country = createRandomString(10);
			this.city = createRandomString(10);
		}
	}

	@Entity
	public static class Cost {

		public Cost(Position a, Position b, Integer cost) {
			this.positionA = a;
			this.positionB = b;
			this.cost = cost;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public Integer id;

		public Integer cost;
		@ManyToOne
		@JoinColumn(name = "positionAID", referencedColumnName = "id", nullable = false)
		public Position positionA;
		@ManyToOne
		@JoinColumn(name = "positionBID", referencedColumnName = "id", nullable = false)
		public Position positionB;
	}

	@Test
	public void testPrimaryKey() {
		Transaction trx = null;
		boolean nonUniqueObjectExceptionCatched = false;

		trx = session.beginTransaction();
		session.save(new Position(0));
		session.save(new Position(1));
		trx.commit();

		try {
			trx = session.beginTransaction();
			session.save(new Position(1));
			trx.commit();
		} catch (org.hibernate.NonUniqueObjectException e) {
			nonUniqueObjectExceptionCatched = true;
			trx.rollback();
		}
		Assert.assertEquals(nonUniqueObjectExceptionCatched, true);

	}

	private static String createRandomString(int length) {
		Random random = new Random();
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int c = random.nextInt() % ('Z' - 'A' + 1) + 'A';
			strBuilder.append(c);
		}
		return strBuilder.toString();
	}

	@Test
	public void testIndexPerf() {

		String countryName = null;
		String cityName = null;
		Transaction trx = null;
		trx = session.beginTransaction();
		int countryCount = 200;
		int cityCount = countryCount;
		for (int i = 0; i < countryCount; i++) {
			countryName = createRandomString(10);
			for (int j = 0; j < cityCount; j++) {
				cityName = createRandomString(10);
				int index = cityCount * i + j;
				int index2 = cityCount * countryCount + index;
				session.save(new Position(index, countryName, cityName));
				session.save(new Position(index2, cityName, countryName));
			}
		}

		trx.commit();

		long mills = System.currentTimeMillis();

		Criteria criteria = session.createCriteria(Position.class);
		criteria.add(Restrictions.eq("country", countryName));

		Assert.assertEquals(countryCount, criteria.list().size());

		mills = System.currentTimeMillis() - mills;

		long mills2 = System.currentTimeMillis();
		criteria = session.createCriteria(Position.class);
		criteria.add(Restrictions.eq("city", countryName));

		Assert.assertEquals(countryCount, criteria.list().size());

		mills2 = System.currentTimeMillis() - mills2;

		System.out.println("mills: " + mills + ", mills2: " + mills2);
		Assert.assertTrue(mills2 > mills);
	}

	@Test
	public void testForeignKey() {

		boolean transientPropertyValueExceptionCatched = false;

		Transaction trx = null;
		Position a = new Position(0);
		Position b = new Position(1);

		trx = session.beginTransaction();
		session.save(a);
		session.save(b);
		trx.commit();

		trx = session.beginTransaction();
		session.save(new Cost(a, b, 100));
		trx.commit();

		try {
			trx = session.beginTransaction();
			session.save(new Cost(a, new Position(3), 100));
			trx.commit();
		} catch (org.hibernate.TransientPropertyValueException e) {
			transientPropertyValueExceptionCatched = true;
			trx.rollback();
		}

		Assert.assertEquals(transientPropertyValueExceptionCatched, true);
	}

	@Test
	public void tesePerf1() {
		Transaction trx = null;
		int count = 1000;
		for (int i = 0; i < count; i++) {
			trx = session.beginTransaction();
			session.save(new Position(i));
			trx.commit();
		}
	}

	@Test
	public void tesePerf2() {
		Transaction trx = null;
		int count = 1000;
		trx = session.beginTransaction();
		for (int i = 0; i < count; i++) {
			session.save(new Position(i));
		}
		trx.commit();
	}

}