package trumanz.HibernateLearn;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
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
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BatchTest  {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static Logger logger = Logger.getLogger(HQLTest.class);
	private static Session session = null;
	
	
	@BeforeClass
	public static void openSession(){
		session = factory.openSession();
		
	
		
	}
	@AfterClass
	public  static void closeSession(){
		Transaction tx = session.beginTransaction();
		session.createQuery("DELETE FROM  Employee").executeUpdate();
		tx.commit();
		session.close();
		session = null;
	}
	
	@Test
	public void batchTest(){

		Transaction tx = session.beginTransaction();
		for(int i=0; i < 200; i++){
			 session.save(new EmployeeWithXmlMap("b", "B", 2000));
			 	if(i%50==0){
			 		session.flush();
			 		session.clear();
			 		logger.info(i);
			 	}
		}
		tx.commit();
		
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		cr.setProjection(Projections.rowCount());
		Assert.assertEquals(new Long(200),cr.list().get(0));
		
		
		tx.begin();
		ScrollableResults employeeCursor  = session.createQuery("FROM EMPLOYEE").scroll();
		
		int count = 0;
		while(employeeCursor.next()){
			EmployeeWithXmlMap employee = (EmployeeWithXmlMap)employeeCursor.get(0);
			employee.setSalary(23000);
			session.update(employee);
			if(++count%50==0){
				session.flush();
				session.clear();
			}
		}
		
		tx.commit();
	
	}
	
	
	
}
