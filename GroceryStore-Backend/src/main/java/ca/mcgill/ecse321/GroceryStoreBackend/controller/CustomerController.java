package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import ca.mcgill.ecse321.GroceryStoreBackend.dto.CustomerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {

	@Autowired
	private CustomerService service;

	@GetMapping(value = { "/view_customers", "/view_customers/" })
	public List<CustomerDto> getAllCustomers() {
		return service.getAllCustomers().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/get_customer", "/get_customer/" })
	public CustomerDto getCustomer(@RequestParam("email") String email) {
		return convertToDto(service.getCustomer(email));
	}

	@PostMapping(value = { "/create_customer", "/create_customer/" })
	public ResponseEntity<?> createCustomer(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("address") String address)
			throws IllegalArgumentException {
		try {
			Customer customer = service.createCustomer(email, password, name, address);
			return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);

		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = { "/update_customer/", "/update_customer/" })
	public ResponseEntity<?> updateCustomer(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("address") String address)
			throws IllegalArgumentException {
		try {
			Customer customer = service.updateCustomer(email, password, name, address);
			return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);

		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = { "/delete_customer/", "/delete_customer/" })
	public ResponseEntity<?> deleteCustomer(@RequestParam("email") String email) throws IllegalArgumentException {
		try {
			service.deleteCustomer(email);
			return new ResponseEntity<>(HttpStatus.OK);

		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static CustomerDto convertToDto(Customer c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Customer!");
		}
		CustomerDto customerDto = new CustomerDto(c.getEmail(), c.getPassword(), c.getName(), c.getAddress());
		return customerDto;
	}
}
