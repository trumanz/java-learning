package javalearn.misc.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

public class Match {

	@Test
	public void  regexGetData() {
		CharSequence str1 = "Step-1/2, CreateVM-34/56";
		
		Pattern dataPatt = Pattern.compile("^Step-(\\d+)/(\\d+), (\\S+)-(\\d+)/(\\d+)$");
		Matcher m = dataPatt.matcher(str1);
		
		Assert.assertTrue(m.matches());
		Assert.assertEquals(5, m.groupCount());
		Assert.assertEquals("1", m.group(1));
		Assert.assertEquals("2", m.group(2));
		Assert.assertEquals("CreateVM", m.group(3));
		Assert.assertEquals("34", m.group(4));
		Assert.assertEquals("56", m.group(5));
		 
 
	}

}
