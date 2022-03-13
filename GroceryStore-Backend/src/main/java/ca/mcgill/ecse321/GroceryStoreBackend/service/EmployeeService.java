package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.DailyScheduleRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Person;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	DailyScheduleRepository dailyScheduleRepository;

	@Transactional
	public Employee createEmployee(String email, String password, String name, double salary) {
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
		
		Employee employee = employeeRepository.findByEmail(email);

		if (employee != null) {
			throw new IllegalArgumentException("An employee with this email already exists.");	
		}
		
		employee = new Employee();
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setName(name);
		employee.setSalary(salary);
		employeeRepository.save(employee);
		return employee;
	}

	@Transactional
	public Employee updateEmployee(String email, String password, String name, double salary) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Employee password must not be empty!");
		} else if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Employee name must not be empty!");
		} else if (salary < 0) {
			throw new IllegalArgumentException("Employee salary must be positive!");
		}

		Employee employee = employeeRepository.findByEmail(email);

		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");	
		}
		
		employee.setPassword(password);
		employee.setName(name);
		employee.setSalary(salary);
		employeeRepository.save(employee);
		return employee;
	}

	@Transactional
	public boolean addDailySchedule(String email, long id) {
		Employee employee;
		DailySchedule dailySchedule;
		
		employee = employeeRepository.findByEmail(email);
		dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);

		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");	
		} else if (dailySchedule == null) {
			throw new IllegalArgumentException("Daily Schedule not found.");	
		}
		
		List<DailySchedule> dailySchedules = employee.getDailySchedules();
		
		for (DailySchedule d: dailySchedules) {
			if (d.getId() == id) {
				throw new IllegalArgumentException("Daily Schedule is already assigned to the employee.");		
			}
		}
		dailySchedules.add(dailySchedule);
		employee.setDailySchedules(dailySchedules);
		employeeRepository.save(employee);
		return true;
	}
	
	@Transactional
	public boolean removeDailySchedule(String email, long id) {
		Employee employee;
		DailySchedule dailySchedule;
		
		employee = employeeRepository.findByEmail(email);
		dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);

		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");	
		} else if (dailySchedule == null) {
			throw new IllegalArgumentException("Daily Schedule not found.");	
		}
		
		List<DailySchedule> dailySchedules = employee.getDailySchedules();
		
		for (DailySchedule d: dailySchedules) {
			if (d.getId() == id) {
				dailySchedules.remove(d);
				employee.setDailySchedules(dailySchedules);
				employeeRepository.save(employee);
				return true;
			}
		}
		throw new IllegalArgumentException("Daily Schedule is not assigned to the employee.");	
	}
	
	@Transactional
	public Employee getEmployee(String email) {
		Employee employee = employeeRepository.findByEmail(email);
		return employee;
	}

	@Transactional
	public List<Employee> getAllEmployees() {
		return toList(employeeRepository.findAll());
	}

	@Transactional
	public boolean deleteEmployee(String email) throws IllegalArgumentException {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Employee email cannot be empty!");
		}

		Employee employee = employeeRepository.findByEmail(email);

		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");
		}

		employeeRepository.deleteByEmail(email);
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
