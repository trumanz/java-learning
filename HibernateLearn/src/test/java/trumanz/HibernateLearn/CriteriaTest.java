package trumanz.HibernateLearn;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
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

public class CriteriaTest  {
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
	private static Logger logger = Logger.getLogger(HQLTest.class);
	private static Session session = null;
	
	
	@BeforeClass
	public static void openSession(){
		session = factory.openSession();
		
		Transaction tx = session.beginTransaction();
		Integer aemployeeID = (Integer) session.save(new EmployeeWithXmlMap("a", "A", 1000));
		Integer bemployeeID = (Integer) session.save(new EmployeeWithXmlMap("b", "B", 2000));
		Integer cemployeeID = (Integer) session.save(new EmployeeWithXmlMap("c", "C", 3000));
		
		session.save(new EmployeeWithXmlMap("d", "D", 4000));
		session.save(new EmployeeWithXmlMap("e", "E", 5000));
		session.save(new EmployeeWithXmlMap("f", "F", 6000));
		
		tx.commit();
		
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
	public void equalTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		cr.add(Restrictions.eq("salary", 2000));
		
		@SuppressWarnings("rawtypes")
		List results = cr.list();
		for(Object obj : results){
			EmployeeWithXmlMap e = (EmployeeWithXmlMap)obj;
			Assert.assertEquals("b", e.getFirstName());
		}
	}
	
	@Test
	public void  lessThanTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		cr.add(Restrictions.lt("salary", 2001));
		
		@SuppressWarnings("rawtypes")
		List results = cr.list();
		
		Assert.assertEquals(2, results.size());
	}
	
	
	@Test
	public void  notNULLTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		cr.add(Restrictions.isNotNull("salary"));
		
		@SuppressWarnings("rawtypes")
		List results = cr.list();
		
		Assert.assertEquals(6, results.size());
	}

	
	@Test
	public void andTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		
		
		Criterion salay  = Restrictions.gt("salary", 1999);
		Criterion name = Restrictions.ilike("firstName", "c");
		LogicalExpression  andExp = Restrictions.and(salay, name);
		
		cr.add(andExp);
		
		logger.info(cr.toString());
		Assert.assertEquals(1, cr.list().size());
		
	}
	
	

	@Test
	public void  paginationTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		//cr.add(Restrictions.isNotNull("salary"));
		
		cr.setFirstResult(0);
		cr.setMaxResults(2);
		
		@SuppressWarnings("rawtypes")
		List results = cr.list();
		
		Assert.assertEquals(2, results.size());
		Assert.assertEquals("a", ((EmployeeWithXmlMap)results.get(0)).getFirstName());
		Assert.assertEquals("b", ((EmployeeWithXmlMap)results.get(1)).getFirstName());
		
		cr.setFirstResult(2);
		results = cr.list();

		Assert.assertEquals(2, results.size());
		Assert.assertEquals("c", ((EmployeeWithXmlMap)results.get(0)).getFirstName());
		Assert.assertEquals("d", ((EmployeeWithXmlMap)results.get(1)).getFirstName());
		
	}
	
	
	@Test
	public void sortTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		cr.add(Restrictions.lt("salary", 3000));
		cr.addOrder(Order.desc("salary"));
		
		@SuppressWarnings("rawtypes")
		List results = cr.list();
		Assert.assertEquals(2, results.size());
		Assert.assertEquals("b", ((EmployeeWithXmlMap)results.get(0)).getFirstName());
		Assert.assertEquals("a", ((EmployeeWithXmlMap)results.get(1)).getFirstName());
	}
	
	
	@Test
	public void projectionCountTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		cr.setProjection(Projections.rowCount());
		
		Assert.assertEquals(new Long(6),cr.list().get(0));
		
	}
	
	@Test
	public void projectionMaxTest(){
		Criteria cr = session.createCriteria(EmployeeWithXmlMap.class);
		cr.setProjection(Projections.max("salary"));
		
		Assert.assertEquals(new Integer(6000),cr.list().get(0));
		
	}
	
	
	
}
