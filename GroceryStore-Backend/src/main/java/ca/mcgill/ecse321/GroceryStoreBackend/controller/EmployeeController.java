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

import ca.mcgill.ecse321.GroceryStoreBackend.dto.EmployeeDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.service.EmployeeService;

@CrossOrigin(origins = "*")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	/**
	 * Return a list of employee DTOs with the employees in the system
	 * 
	 * @author anaelle.drai
	 * @return employees
	 */
	@GetMapping(value = { "/view_employees", "/view_employees/" })
	public List<EmployeeDto> getAllEmployees() {
		return service.getAllEmployees().stream().map(e -> convertToDto(e)).collect(Collectors.toList());
	}

	/**
	 * Return the employee with the given email as a DTO
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @return
	 */
	@GetMapping(value = { "/get_employee", "/get_employee/" })
	public EmployeeDto getEmployee(@RequestParam("email") String email) {
		return convertToDto(service.getEmployee(email));
	}

	/**
	 * Create an employee with the given arguments and return the DTOs
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @param name
	 * @param salary
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/create_employee", "/create_employee/" })
	public ResponseEntity<?> createEmployee(@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("salary") Double salary) throws IllegalArgumentException {
		try {
			Employee employee = service.createEmployee(email, password, name, salary);
			return new ResponseEntity<>(convertToDto(employee), HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update an employee with the given arguments and return the DTOs
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @param name
	 * @param salary
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = { "/update_employee", "/update_employees/" })
	public ResponseEntity<?> updateEmployee(@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("salary") Double salary) throws IllegalArgumentException {
		try {
			Employee employee = service.updateEmployee(email, password, name, salary);
			return new ResponseEntity<>(convertToDto(employee), HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete the customer with the given email
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @return
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/delete_employee", "/delete_employee/" })
	public ResponseEntity<?> deleteEmployee(@RequestParam("email") String email) throws IllegalArgumentException {
		try {
			service.deleteEmployee(email);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Adds a DailySchedule to the employee with the given email
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param id
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/add_dailySchedule", "/add_dailySchedule/" })
	public void addDailySchedule(@RequestParam("email") String email, @RequestParam("id") Long id)
			throws IllegalArgumentException {
		service.addDailySchedule(email, id);
	}

	/**
	 * Deletes a DailySchedule fromn the employee with the given email
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param id
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/remove_dailySchedule", "/remove_dailySchedule/" })
	public void removeDailySchedule(@RequestParam("email") String email, @RequestParam("id") Long id)
			throws IllegalArgumentException {
		service.removeDailySchedule(email, id);
	}

	/**
	 * Convert an employee instance into an employee DTO
	 * 
	 * @author anaelle.drai
	 * @param e
	 * @return
	 */
	private EmployeeDto convertToDto(Employee e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such Employee!");
		}
		EmployeeDto employeeDto = new EmployeeDto(e.getEmail(), e.getPassword(), e.getName(), e.getSalary(),
				e.getDailySchedules());
		return employeeDto;
	}
}
