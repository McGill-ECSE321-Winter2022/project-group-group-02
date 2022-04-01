package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GroceryStoreBackend.dto.CustomerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.EmployeeDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.service.EmployeeService;

@CrossOrigin(origins = "*")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@GetMapping(value = { "/view_employees", "/view_employees/" })
	public List<EmployeeDto> getAllEmployees() {
		return service.getAllEmployees().stream().map(e -> convertToDto(e)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/get_employee", "/get_employee/" })
	public EmployeeDto getEmployee(@RequestParam("email") String email) {
		return convertToDto(service.getEmployee(email));
	}
	
	@PostMapping(value = { "/create_employee", "/create_employee/" })
	public EmployeeDto createEmployee(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("salary") Double salary) throws IllegalArgumentException {
		Employee employee = service.createEmployee(email, password, name, salary);
		return convertToDto(employee);
	}

	@PutMapping(value = { "/update_employee", "/update_employees/" })
	public EmployeeDto updateEmployee(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("salary") Double salary) throws IllegalArgumentException {
		Employee employee = service.updateEmployee(email, password, name, salary);
		return convertToDto(employee);
	}

	@DeleteMapping(value = { "/delete_employee", "/delete_employee/" })
	public void deleteEmployee(@RequestParam("email") String email) throws IllegalArgumentException {
		service.deleteEmployee(email);
	}

	@PostMapping(value = { "/add_dailySchedule", "/add_dailySchedule/" })
	public void addDailySchedule(@RequestParam("email") String email, @RequestParam("id") long id)
			throws IllegalArgumentException {
		service.addDailySchedule(email, id);
	}

	@DeleteMapping(value = { "/remove_dailySchedule", "/remove_dailySchedule/" })
	public void removeDailySchedule(@RequestParam("email") String email, @RequestParam("id") long id)
			throws IllegalArgumentException {
		service.removeDailySchedule(email, id);
	}

	private EmployeeDto convertToDto(Employee e) {
		if (e == null) {
			throw new IllegalArgumentException("There is no such Employee!");
		}
		EmployeeDto employeeDto = new EmployeeDto(e.getEmail(), e.getPassword(), e.getName(), e.getSalary(),
				e.getDailySchedules());
		return employeeDto;
	}
}
