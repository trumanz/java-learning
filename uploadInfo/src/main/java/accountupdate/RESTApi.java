package accountupdate;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/")
public class RESTApi {
	@POST
	@Path("/accountinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountUpdateResponse recordUpdateInfo(AccountUpdateInfoEntity accountInfo){
		//concurrency!!! multi-thread will call this function!
		//now, serial the calls, avoid mulit thread use mysql session
		
		//c3p0 hibernate-c3p0 ??
		/*
		 * this.setProperty( "hibernate.c3p0.minSize", "0");
		this.setProperty( "hibernate.c3p0.maxSize", "64");
		this.setProperty( "hibernate.c3p0.acquireIncrement", "1");
		this.setProperty( "hibernate.c3p0.idleTestPeriod", "500");
		this.setProperty( "hibernate.c3p0.maxStatements", "0");
		this.setProperty( "hibernate.c3p0.timeout", "100");
		 */
		
		
		Logger.getLogger(RESTApi.class).info(this.toString() + "threadId=" + Thread.currentThread().getId());
		
		return new AccountUpdateResponse(AccountDBRecorder.update(accountInfo));
		
	}
	
	
	@GET
	@Path("/allAccount")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AccountUpdateInfoEntity>  getAllAccount(){
		return  AccountDBRecorder.getAllAccount();
	}
	
}
