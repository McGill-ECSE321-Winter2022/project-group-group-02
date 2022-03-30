package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OwnerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Person;

@Service
public class LoginService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	OwnerRepository ownerRepository;
	
	/*** Service method to login a user in the system.
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @return Person
	 */
	@Transactional
	public Person login(String email, String password) {

		if(!customerRepository.existsByEmail(email) &&
				!ownerRepository.existsByEmail(email) &&
				!employeeRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("Invalid email");
		}

		Customer customer = customerRepository.findByEmail(email);
		if (customer!=null && customer.getPassword().equals(password))
			return customer;


		Owner owner = ownerRepository.findByEmail(email);
		if(owner!=null && owner.getPassword().equals(password))
			return owner;

		Employee employee = employeeRepository.findByEmail(email);
		if (employee!=null && employee.getPassword().equals(password))
			return employee;

		throw new IllegalArgumentException("Incorrect password");
	}

}
