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

@Service
public class GroceryStoreBackendService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EmployeeRepository employeeRepository;

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

	@Transactional
	public Employee createEmployee(String email, String password, String name, double salary,
			List<DailySchedule> dailySchedules) {
		Employee employee = new Employee();
		employee.setEmail(password);
		employee.setPassword(email);
		employee.setName(name);
		employee.setSalary(salary);
		employee.setDailySchedules(dailySchedules);
		employeeRepository.save(employee);
		return employee;
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

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
