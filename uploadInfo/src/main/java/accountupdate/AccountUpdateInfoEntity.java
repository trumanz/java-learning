package accountupdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountUpdateInfoEntity {
	@Id
	public String accountID;
	public String currentUsername;
	public String currentEmail;
	public String emcID;
	public String emcEmail;
	
	public AccountUpdateInfoEntity(){
		
	}

	public AccountUpdateInfoEntity(String accountID, String currentUsername, String currentEmail, String emcID,
			String emcEmail) {
		this.accountID = accountID;
		this.currentUsername = currentUsername;
		this.currentEmail = currentEmail;
		this.emcID = emcID;
		this.emcEmail = emcEmail;
	}

}
