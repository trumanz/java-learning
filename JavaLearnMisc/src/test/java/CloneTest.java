import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class CloneTest implements Cloneable { 
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	
	private String name = new String();
	private Date date = new Date();
	
	@Test
	public void testCloneThis() throws CloneNotSupportedException{
		
		System.out.println(System.getProperty("ProgramFiles"));
		
		CloneTest t1 = new CloneTest();
		CloneTest t2=  (CloneTest) t1.clone();
		
 
		Assert.assertTrue( t1.name.equals(t2.name));
		Assert.assertTrue( t1.date.equals(t2.date));
		
		Assert.assertTrue(t1.name == t2.name);
		Assert.assertTrue(t1.date == t2.date);
	}
}
