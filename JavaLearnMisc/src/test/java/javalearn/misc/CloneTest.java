package javalearn.misc;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class CloneTest {
	
	static class MutableObj {
		public int value = 1;
	}

	static class MyObj implements Cloneable {
		public Object clone() throws CloneNotSupportedException {
			return super.clone();

		}

		public MutableObj muobj = new MutableObj();
	}

	@Test
	public void sameMutalbeObjInWrongClone() throws CloneNotSupportedException {

		MyObj o1 = new MyObj();
		MyObj o2 = (MyObj) o1.clone();
		
		Assert.assertEquals(1, o2.muobj.value);
		 
		//chagne o1
		o1.muobj.value = 2;
		//then o2 chanaged
		Assert.assertEquals(2, o2.muobj.value);
		
		//o1 and o2 is different obj; but o1.muobj and o2.muobj are same
		Assert.assertFalse(o1.equals(o2));

		Assert.assertTrue(o1.muobj.equals(o2.muobj));
		 
	}
}
