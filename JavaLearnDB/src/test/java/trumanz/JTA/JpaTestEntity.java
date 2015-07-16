package trumanz.JTA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class JpaTestEntity {
	@Id @GeneratedValue
	private Long id;
	private String text;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		JpaTestEntity that = (JpaTestEntity)o;
		if(id != null ? !id.equals(that.id) : that.id != null){
			return false;
		}
		if(text != null ? !text.equals(that.text) : that.text != null){
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode(){
		int result  = id != null ? id.hashCode() : 0;
		result  = 31 * result + (text != null ? text.hashCode() : 0);
		return  result;
	}
	
	@Override
	public String toString(){
		return "id=" + id + ", text=" + text;
		
	}

}
