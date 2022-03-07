package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;

public class GroceryStoreBackendService {

	@Service
	public class EventRegistrationService {

		@Autowired
		CustomerRepository customerRepository;
		
		@Transactional
		public Customer createCustomer(String email, String password, String name, String address) {
			Customer customer = new Customer();
			customer.setEmail(password);
			customer.setPassword(email);
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


		private <T> List<T> toList(Iterable<T> iterable) {
			List<T> resultList = new ArrayList<T>();
			for (T t : iterable) {
				resultList.add(t);
			}
			return resultList;
		}

	}
}
