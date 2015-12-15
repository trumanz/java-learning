package accountupdate;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.reflections.Reflections;

public class AccountDBRecorder {

	static Session session = createMysqlSession();
	static Logger logger = Logger.getLogger(AccountDBRecorder.class);
	
	AccountDBRecorder() throws IOException{
		logger.info("init");
		
	}
	//TODO
	//session/connection pool for performance
	synchronized
	public static String update(AccountUpdateInfoEntity accountInfo) {

		Criteria cr = session.createCriteria(AccountUpdateInfoEntity.class);
		cr.add(Restrictions.eq("accountID", accountInfo.accountID));
		
		boolean update = false;
		
		if( cr.list().size() == 1){
			logger.info("update");
			update = true;
		}
		
		try{
		     session.beginTransaction();
		     session.saveOrUpdate(session.merge(accountInfo));
		     session.getTransaction().commit();
		} catch (Exception e){
			//e.printStackTrace();
			session.getTransaction().rollback();
			logger.warn(e.getMessage());
			return e.getMessage();
		}

		logger.info("update finished");
		
		return "Success";

	}

	private static Session createMysqlSession() {

		Configuration cfg = new Configuration();
		Properties p = new Properties();
		// p.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
		p.put("dialect", "org.hibernate.dialect.MySQLDialect");
		p.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		p.put("hibernate.connection.url", "jdbc:mysql://10.29.98.2:3306/gerritaccount");
		p.put("hibernate.connection.username", "root");
		p.put("hibernate.connection.password", "root");
		p.put("hibernate.hbm2ddl.auto", "update");
		p.put("show_sql", "true");
		cfg.setProperties(p);

		for (Class<?> c : getAllEntityClass()) {
			System.out.println("add: " + c.getName());
			cfg.addAnnotatedClass(c);
		}

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory sessionFactory = cfg.buildSessionFactory(builder.build());
		
		return sessionFactory.openSession();		
	}

	public void closeMysqlSession() throws IOException {
		session.close();
		logger.info("session.isOpen(): " + session.isOpen());
		logger.info("Session closed");
	}

	private static Set<Class<?>> getAllEntityClass() {
		Reflections reflections = new Reflections(AccountUpdateInfoEntity.class.getCanonicalName());
		return reflections.getTypesAnnotatedWith(Entity.class);
	}

}
