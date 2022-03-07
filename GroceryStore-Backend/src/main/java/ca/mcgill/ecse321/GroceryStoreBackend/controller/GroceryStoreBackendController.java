package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GroceryStoreBackend.dto.CustomerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.EmployeeDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.service.GroceryStoreBackendService;

@CrossOrigin(origins = "*")
@RestController
public class GroceryStoreBackendController {
	
	@Autowired
	private GroceryStoreBackendService service;
	
	
	@GetMapping(value = { "/customers", "/customers/" })
	public List<CustomerDto> getAllCustomers() {
		return service.getAllCustomers().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/customers/{email}", "/customers/{email}/" })
	public CustomerDto createCustomer(@PathVariable("email") String email, @RequestParam String password, @RequestParam String name, @RequestParam String address) throws IllegalArgumentException {
		Customer customer = service.createCustomer(email, password, name, address);
		return convertToDto(customer);
	}
	
	private CustomerDto convertToDto(Customer c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Customer!");
		}
		CustomerDto customerDto = new CustomerDto(c.getEmail(), c.getPassword(), c.getName(), c.getAddress());
		return customerDto;
	}
	
	@GetMapping(value = { "/employees", "/employees/" })
	public List<EmployeeDto> getAllEmployees() {
		return service.getAllEmployees().stream().map(e -> convertToDto(e)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/employees/{email}", "/employees/{email}/" })
	public EmployeeDto createEmployee(@PathVariable("email") String email, @RequestParam String password, @RequestParam String name, @RequestParam Double salary, @RequestParam List<DailySchedule> dailySchedules) throws IllegalArgumentException {
		Employee employee = service.createEmployee(email, password, name, salary, dailySchedules);
		return convertToDto(employee);
	}
	
	private EmployeeDto convertToDto(Employee e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such Employee!");
		}
		EmployeeDto employeeDto = new EmployeeDto(e.getEmail(), e.getPassword(), e.getName(), e.getSalary(), e.getDailySchedules());
		return employeeDto;
	}
}
