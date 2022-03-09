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
import ca.mcgill.ecse321.GroceryStoreBackend.model.Person;

@Service
public class EmployeeService {

  
  @Autowired
  EmployeeRepository employeeRepository;

  
  
  @Transactional
  public Employee createEmployee(String email, String password, String name, double salary,
          List<DailySchedule> dailySchedules) {
      Employee employee = new Employee();
      employee.setEmail(email);
      employee.setPassword(password);
      employee.setName(name);
      employee.setSalary(salary);
      employee.setDailySchedules(dailySchedules);
      employeeRepository.save(employee);
      return employee;
  }

  
  @Transactional
  public Employee updateEmployee(String email, String password, String name, double salary,
          List<DailySchedule> dailySchedules) {
      Employee employee = getEmployee(email);
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
  
  @Transactional
  public String deleteEmployee(String email) throws Exception{
      if(email == null) {
          throw new Exception("Email cannot be empty.");
      }
      
      Employee employee = employeeRepository.findByEmail(email);
      
      if(employee == null) {
          throw new Exception("Customer not found.");
      }
      
      employeeRepository.deleteByEmail(email);
      return "Employee account with email " + email + " deleted.";
  }
  
  private <T> List<T> toList(Iterable<T> iterable) {
      List<T> resultList = new ArrayList<T>();
      for (T t : iterable) {
          resultList.add(t);
      }
      return resultList;
  }
}
