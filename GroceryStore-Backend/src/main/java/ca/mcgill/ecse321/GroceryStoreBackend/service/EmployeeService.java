package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.DailyScheduleRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DailyScheduleRepository dailyScheduleRepository;

	/*** Service method for the creation of an employee. Returns the created instance if there was no error.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @param name
	 * @param salary
	 * @return employee
	 */
	@Transactional
	public Employee createEmployee(String email, String password, String name, double salary) {
		// Checking all the inputs are valid
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Employee email cannot be empty!");
		} else if (!email.contains("@")) {
			throw new IllegalArgumentException("Employee email must contain an @!");
		} else if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Employee password must not be empty!");
		} else if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Employee name must not be empty!");
		} else if (salary < 0) {
			throw new IllegalArgumentException("Employee salary must be positive!");
		}

		// Checking the employee does not already exist in the system
		Employee employee = employeeRepository.findByEmail(email);

		if (employee != null) {
			throw new IllegalArgumentException("An employee with this email already exists.");
		}

		// Create employee
		employee = new Employee();
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setName(name);
		employee.setSalary(salary);
		employeeRepository.save(employee);
		return employee;
	}

	/*** Updates the employee information, and returns the updated instance if there was no error.
	 *
	 * @author anaelle.drai
	 * @param email
	 * @param password
	 * @param name
	 * @param salary
	 * @return employee
	 */
	@Transactional
	public Employee updateEmployee(String email, String password, String name, double salary) {
		// Checking the inputs are valid
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Employee password must not be empty!");
		} else if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Employee name must not be empty!");
		} else if (salary < 0) {
			throw new IllegalArgumentException("Employee salary must be positive!");
		}

		// Checking the employee exists in the system
		Employee employee = employeeRepository.findByEmail(email);

		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");
		}

		// Update employee
		employee.setPassword(password);
		employee.setName(name);
		employee.setSalary(salary);
		employeeRepository.save(employee);
		return employee;
	}

	
	/*** Adds a dailySchedule to the dailySchedules of an employee, returns a boolean for success.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param id
	 * @return true
	 */
	@Transactional
	public boolean addDailySchedule(String email, Long id) {
		Employee employee;
		DailySchedule dailySchedule;

		employee = employeeRepository.findByEmail(email);
		dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);

		// Checking the employee and the dailySchedule exist in the system.
		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");
		} else if (dailySchedule == null) {
			throw new IllegalArgumentException("Daily Schedule not found.");
		}

		// Checking the dailySchedule is not already in the employee's dailySchedules
		List<DailySchedule> dailySchedules = employee.getDailySchedules();

		for (DailySchedule d : dailySchedules) {
			if (d.getId() == id) {
				throw new IllegalArgumentException("Daily Schedule is already assigned to the employee.");
			}
		}
		
		// Add the dailySchedule
		dailySchedules.add(dailySchedule);
		employee.setDailySchedules(dailySchedules);
		employeeRepository.save(employee);
		return true;
	}

	/*** Removes a dailySchedule from the employee's dailySchedules and returns a boolean for success.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @param id
	 * @return true
	 */
	@Transactional
	public boolean removeDailySchedule(String email, Long id) {
		Employee employee;
		DailySchedule dailySchedule;

		employee = employeeRepository.findByEmail(email);
		dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);

		// Checking the employee and the dailySchedule exist in the system.
		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");
		} else if (dailySchedule == null) {
			throw new IllegalArgumentException("Daily Schedule not found.");
		}
		
		// Checking the dailySchedule is in the employee's dailySchedyles and in that case, remove it and return.
		List<DailySchedule> dailySchedules = employee.getDailySchedules();

		for (DailySchedule d : dailySchedules) {
			if (d.getId() == id) {
				dailySchedules.remove(d);
				employee.setDailySchedules(dailySchedules);
				employeeRepository.save(employee);
				return true;
			}
		}
		throw new IllegalArgumentException("Daily Schedule is not assigned to the employee.");
	}

	/*** Returns a list of all the employees in the system.
	 * 
	 * @author anaelle.drai
	 * @return employees
	 */
	@Transactional
	public List<Employee> getAllEmployees() {
		return toList(employeeRepository.findAll());
	}

	/*** Return the employee associated with the email if it exists in the system.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @return employee
	 */
	@Transactional
	public Employee getEmployee(String email) {
		// Checking the employee exists in the system
		Employee employee = employeeRepository.findByEmail(email);
		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");
		}
		
		return employee;
	}

	/*** Deletes the employee from the system if there is no error and returns true in this case.
	 * 
	 * @author anaelle.drai
	 * @param email
	 * @return true
	 */
	@Transactional
	public boolean deleteEmployee(String email) {
		
		// Checking the input is valid
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Employee email cannot be empty!");
		}

		// Checking the customer exists in the system
		Employee employee = employeeRepository.findByEmail(email);

		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");
		}

		// Delete the employee
		employeeRepository.deleteByEmail(email);
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
