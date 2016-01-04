package trumanz.c3p0;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class BasicTest {

	
	
	
	@Test
	public void test() throws InterruptedException {
		SessionFactory sessionFactory = createMysqlSession();

		List<Session> sessions = new LinkedList<Session>();
		
		Thread.sleep(10*1000);
		Assert.assertEquals(5, sessionFactory.getStatistics().getConnectCount());
		
		
		for (int i = 0; i < 10; i++) {
			System.out.println("========" + i);
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			
			sessions.add(session);
		}
		
		Assert.assertEquals(11, sessionFactory.getStatistics().getConnectCount());
		
		for(Session session : sessions ){
			session.close();
		}

		Assert.assertEquals(11, sessionFactory.getStatistics().getConnectCount());
	
		Thread.sleep(100*1000);


	}

	private static SessionFactory createMysqlSession() {

		Configuration cfg = new Configuration();
		Properties p = new Properties();

		// http://www.mchange.com/projects/c3p0/#hibernate-specific
		//http://www.mchange.com/projects/c3p0/#configuration_properties
		p.put("hibernate.c3p0.min_size", "5");
		p.put("hibernate.c3p0.max_size", "20");
		p.put("hibernate.c3p0.timeout", "1800");
		p.put("hibernate.c3p0.max_statements", "50");
		p.put("hibernate.c3p0.acquireIncrement", "2");
		p.put("hibernate.c3p0.idleTestPeriod", "500");

		// p.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
		p.put("dialect", "org.hibernate.dialect.MySQLDialect");
		p.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		p.put("hibernate.connection.url", "jdbc:mysql://10.29.113.20:3306/testdb");
		p.put("hibernate.connection.username", "root");
		p.put("hibernate.connection.password", "root");
		p.put("hibernate.hbm2ddl.auto", "update");
		p.put("show_sql", "true");
		cfg.setProperties(p);

		// cfg.addAnnotatedClass(c);

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory sessionFactory = cfg.buildSessionFactory(builder.build());

		return sessionFactory;
		// return sessionFactory.openSession();
	}

}
