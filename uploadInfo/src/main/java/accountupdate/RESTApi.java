package accountupdate;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/api/")
public class RESTApi {
	@POST
	@Path("/accountinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountUpdateResponse recordUpdateInfo(AccountUpdateInfoEntity accountInfo){
		//concurrency!!! multi-thread will call this function!
		//now, serial the calls, avoid mulit thread use mysql session
		
		
		Logger.getLogger(RESTApi.class).info(this.toString() + "threadId=" + Thread.currentThread().getId());
		
		return new AccountUpdateResponse(AccountDBRecorder.update(accountInfo));
		
	}
}
