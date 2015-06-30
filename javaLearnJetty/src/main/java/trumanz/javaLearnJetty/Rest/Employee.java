package trumanz.javaLearnJetty.Rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {
	private String name="";
	private int id = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
