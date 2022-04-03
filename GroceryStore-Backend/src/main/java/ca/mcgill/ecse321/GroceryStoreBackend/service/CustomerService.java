package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OwnerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	OwnerRepository ownerRepository;


	/*** Service method for the creation of a customer. Returns the created instance if there was no error.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @param name
	 * @param address
	 * @return customer
	 */
	@Transactional
	public Customer createCustomer(String email, String password, String name, String address) {
		
		// Checking all the inputs are valid
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Customer email cannot be empty!");
		} else if (!email.contains("@")) {
			throw new IllegalArgumentException("Customer email must contain an @!");
		} else if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Customer password must not be empty!");
		} else if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Customer name must not be empty!");
		} else if (address == null || address.isEmpty()) {
			throw new IllegalArgumentException("Customer address must not be empty!");
		}

		if (employeeRepository.existsByEmail(email) ) {
			throw new IllegalArgumentException("This email is already in use for an employee.");
		}
		
		if (ownerRepository.existsByEmail(email) ) {
			throw new IllegalArgumentException("This is the owner's email.");
		}
		
		// Check the email is not already associated to a customer account
		Customer customer = customerRepository.findByEmail(email);
		
		
		if (customer != null) {
			throw new IllegalArgumentException("This email is already in use for a customer.");
		}
		
		// Create the customer
		customer = new Customer();
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setName(name);
		customer.setAddress(address);
		customerRepository.save(customer);
		return customer;
	}

	/*** Returns a list of all the custoners in the system.
	 * 
	 * @author anaelle.drai
	 * @return customers
	 */
	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(customerRepository.findAll());
	}

	/*** Return the customer associated with the email if it exists in the system.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @return customer
	 */
	@Transactional
	public Customer getCustomer(String email) {
		// Checking the customer exists in the system
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}
		
		return customer;
	}

	
	/*** Updates the customer information, and returns the updated instance if there was no error.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @param name
	 * @param address
	 * @return customer
	 */
	@Transactional
	public Customer updateCustomer(String email, String password, String name, String address) {
		
		// Checking all the inputs are valid
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Customer password must not be empty!");
		} else if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Customer name must not be empty!");
		} else if (address == null || address.isEmpty()) {
			throw new IllegalArgumentException("Customer address must not be empty!");
		}

		// Checking the customer exists in the system
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}
		
		// Update the customer
		customer.setPassword(password);
		customer.setName(name);
		customer.setAddress(address);
		customerRepository.save(customer);
		return customer;
	}

	/*** Delete the customer if there is no error and returns true in that case.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @return true
	 */
	@Transactional
	public boolean deleteCustomer(String email) {
		
		// Checking the input is valid
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Customer email cannot be empty!");
		}

		// Checking the customer exists in the system
		Customer customer = customerRepository.findByEmail(email);

		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}

		// Delete the customer
		customerRepository.deleteByEmail(email);
		return true;
	}

	/*** Converts an iterable into a list.
	 * 
	 * @author anaelle.drai
	 * @param <T>
	 * @param iterable
	 * @return resultList
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
