package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCustomerPersistence {

	@Autowired
	EntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepository;

	@AfterEach
	public void clearDatabase() {
		customerRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadCustomer() {
		
		// Creation of a Customer instance with test attributes
		String name = "testCustomer";
		String email = "testCustomer@mail.com";
		String password = "testPassword";
		String address = "town";
		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setAddress(address);

		// Save the created Customer instance
		customerRepository.save(customer);

		// Set the variable to null, and then try retrieving the saved instance using its email
		customer = null;
		customer = customerRepository.findByEmail(email);
		
		// Determine whether the instance is null and if its attributes match.
		assertNotNull(customer);
		assertEquals(name, customer.getName());
		assertEquals(password, customer.getPassword());
		assertEquals(address, customer.getAddress());
		assertEquals(email, customer.getEmail());
	}

}
