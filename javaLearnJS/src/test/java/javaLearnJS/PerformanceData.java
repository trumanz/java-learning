package javaLearnJS;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PerformanceData {
	
	public PerformanceData(String vm_name, List<PerfCount> cpu_count_list){
		this.vm_name = vm_name;
		this.cpu_count_list = cpu_count_list;
	}
	
	public String vm_name;
	public List<PerfCount> cpu_count_list;
	
	public static List<PerformanceData> GetDummyDatasForTest(){
		 List<PerformanceData>  pds = new LinkedList<PerformanceData>();
		for(char c = 'a' ; c <= 'c'; c++){
			PerformanceData pd =  GetDummyDataForTest(String.valueOf(c));	
			pds.add(pd);
		}
		return pds;
	}
	
	public static PerformanceData GetDummyDataForTest(String vm_name){
		long msNow = new Date().getTime();
		Random random = new Random();
		long count = 3;
		long interval = 5;
		List<PerfCount> cpu_count_list = new LinkedList<PerfCount>();
		for(int i = 0; i < count; i++){
			Date d = new Date(msNow + (count - i)*interval);
			Double v = random.nextDouble();
			PerfCount pc = new PerfCount(d, v);
			cpu_count_list.add(pc);
		}
		
		return new PerformanceData(vm_name, cpu_count_list);
		
	}
	

}
