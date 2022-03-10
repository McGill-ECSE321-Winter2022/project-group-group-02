package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Person;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Transactional
	public Customer createCustomer(String email, String password, String name, String address) {
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

		Customer customer = new Customer();
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setName(name);
		customer.setAddress(address);
		customerRepository.save(customer);
		return customer;
	}

	@Transactional
	public Customer getCustomer(String email) {
		Customer customer = customerRepository.findByEmail(email);
		return customer;
	}

	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(customerRepository.findAll());
	}

	@Transactional
	public Customer updateCustomer(String email, String password, String name, String address) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Customer password must not be empty!");
		} else if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Customer name must not be empty!");
		} else if (address == null || address.isEmpty()) {
			throw new IllegalArgumentException("Customer address must not be empty!");
		}
		
		Customer customer = customerRepository.findByEmail(email);

		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");	
		}
		customer.setPassword(password);
		customer.setName(name);
		customer.setAddress(address);
		customerRepository.save(customer);
		return customer;
	}

	@Transactional
	public boolean deleteCustomer(String email) throws IllegalArgumentException {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Customer email cannot be empty!");
		}

		Customer customer = customerRepository.findByEmail(email);

		if (customer == null) {
			throw new IllegalArgumentException("Customer not found.");
		}

		customerRepository.deleteByEmail(email);
		return true;
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
