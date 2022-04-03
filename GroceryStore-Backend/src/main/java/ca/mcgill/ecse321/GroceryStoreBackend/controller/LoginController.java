package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GroceryStoreBackend.dto.CustomerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.EmployeeDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.OwnerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Person;
import ca.mcgill.ecse321.GroceryStoreBackend.service.CustomerService;
import ca.mcgill.ecse321.GroceryStoreBackend.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	/*** Login controller method to login a user in the system.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @return
	 */
	@PostMapping(value = {"/login", "/login/"})
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
		Person user = null;
		try {
			user = loginService.login(email, password);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Determine what type of user is login in in the system.
		if(user instanceof Customer) {

			return new ResponseEntity<>(convertToCustomerDto((Customer) user), HttpStatus.OK);
		}
		
		if(user instanceof Employee) {
			return new ResponseEntity<>(convertToEmployeeDto((Employee) user), HttpStatus.OK);
		}
		
		if(user instanceof Owner) {
			return new ResponseEntity<>(convertToOwnerDTO((Owner) user), HttpStatus.OK);
		}
		return null;
	}
	
	public static CustomerDto convertToCustomerDto(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("There is no such Customer!");
		}
		CustomerDto customerDto = new CustomerDto(customer.getEmail(), customer.getPassword(), customer.getName(), customer.getAddress());
		return customerDto;
	}
	
	private EmployeeDto convertToEmployeeDto(Employee employee) {
		if (employee == null) {
			throw new IllegalArgumentException("There is no such Employee!");
		}
		EmployeeDto employeeDto = new EmployeeDto(employee.getEmail(), employee.getPassword(), employee.getName(), employee.getSalary(),
				employee.getDailySchedules());
		return employeeDto;
	}
	
	public static OwnerDto convertToOwnerDTO(Owner owner) {
		if (owner == null)
			return null;
		return new OwnerDto(owner.getEmail(), owner.getPassword(), owner.getName());
	}
}
