package trumanz.JPA.Spring;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.repository.CrudRepository;

public class AccessDataWithJPA {

	@Entity
	public static class Customer {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id;
		private String firstName;
		private String lastName;

		protected Customer() {
		}

		public Customer(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override
		public String toString() {
			return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
		}
	}
	
	public static interface CustomerRepository extends CrudRepository<Customer, Long> {
		List<Customer> findByLastName(String lastName);
	}

	@Test
	public void demo(){
		
	}

}
