package javaLearnJS;

import java.util.Date;

public class PerfCount {
	public PerfCount(Date timeStamp, double cpu_value, double  net_value){
		this.timeStamp = timeStamp;
		this.cpu_value = cpu_value;
		this.net_value = net_value;
	}
	public Date  timeStamp;
	public double cpu_value;
	public double net_value;

}
