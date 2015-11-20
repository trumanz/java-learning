package javaLearnJS;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PerformanceData {
	
	public PerformanceData(String vm_name, List<PerfCount> count_list){
		this.vm_name = vm_name;
		this.count_list = count_list;
	}
	
	public String vm_name;
	public List<PerfCount> count_list;
	
	
	public static List<PerformanceData> GetDummyDatasForTest(){
		 List<PerformanceData>  pds = new LinkedList<PerformanceData>();
		for(char c = 'a' ; c < 'a' + 5; c++){
			PerformanceData pd =  GetDummyDataForTest(String.valueOf(c));	
			pds.add(pd);
		}
		return pds;
	}
	
	public static PerformanceData GetDummyDataForTest(String vm_name){
		long msNow = new Date().getTime();
		Random random = new Random();
		long count = 10;
		long interval = 5;
		List<PerfCount> count_list = new LinkedList<PerfCount>();
		Double v0 = (double) (Math.abs(random.nextInt())%70);
		for(int i = 0; i < count; i++){
			Date d = new Date(msNow + (count - i)*interval);
			
			Double v1 = (double) (Math.abs(random.nextInt())%10) + v0;
			Double v2 = (double) (Math.abs(random.nextInt())%10) + v0;
			PerfCount pc = new PerfCount(d,v1, v2);
			count_list.add(pc);
		}
		
		return new PerformanceData(vm_name, count_list);
		
	}
	

}
