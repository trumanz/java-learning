package trumanz.JavaLearnMisc.InjectLearn;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TestRunnable implements Runnable{
	private Object obj;
	public void run() {}
	@Inject
	public TestRunnable(@Named("ObjType") Object obj){ this.obj = obj;}
	public Object getObj(){return obj;}
}
