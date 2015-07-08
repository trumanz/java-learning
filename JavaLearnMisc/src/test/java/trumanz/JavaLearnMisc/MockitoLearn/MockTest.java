package trumanz.JavaLearnMisc.MockitoLearn;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MockTest {
	
	public static class MyImp{
		public void m1(){
			throw new RuntimeException(); 
		}
		public int  m2(int i){
			 return i;
		}
	}
	
	
	@Test
	public void test(){
		MyImp impMock = Mockito.mock(MyImp.class);
		MyImp imp  = new MyImp();
		//Logger.getLogger(this.getClass()).info(imp.m2(100));
		Assert.assertEquals(100, imp.m2(100));
		Assert.assertEquals(0, impMock.m2(100));
		impMock.m1();
		
		Mockito.when(impMock.m2(org.mockito.Matchers.anyInt())).thenReturn(66);
		Assert.assertEquals(66, impMock.m2(100));
		
		
	}

}
