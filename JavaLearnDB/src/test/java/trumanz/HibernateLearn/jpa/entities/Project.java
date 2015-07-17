package trumanz.HibernateLearn.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Project {
	@Id
	private String name;
	
	
	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(this.getClass() != o.getClass()) return false;
		Project that = (Project)o;
		if(this.name == null ? that.name != null : (!this.name.equals(that.name))) return false;
		return true;
	}
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode = 32 * hashCode + (this.name == null ? 0 : this.name.hashCode());
		return hashCode;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
