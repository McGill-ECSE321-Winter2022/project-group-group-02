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

import ca.mcgill.ecse321.GroceryStoreBackend.dto.EmployeeDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
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
  
  @PostMapping(value = { "/create_employee/", "/create_employee/" })
  public EmployeeDto createEmployee(@PathVariable("email") String email, @RequestParam String password, @RequestParam String name, @RequestParam Double salary, @RequestParam List<DailySchedule> dailySchedules) throws IllegalArgumentException {
      Employee employee = service.createEmployee(email, password, name, salary, dailySchedules);
      return convertToDto(employee);
  }
  
  @PostMapping(value = { "/update_employee/{email}", "/update_employees/{email}/" })
  public EmployeeDto updateEmployee(@PathVariable("email") String email, @RequestParam String password, @RequestParam String name, @RequestParam Double salary, @RequestParam List<DailySchedule> dailySchedules) throws IllegalArgumentException {
      Employee employee = service.updateEmployee(email, password, name, salary, dailySchedules);
      return convertToDto(employee);
  }
  
  @PostMapping(value = { "/delete_employees/{email}", "/delete_employees/{email}/" })
  public void deleteEmployee(@PathVariable("email") String email) throws Exception {
      try {
          service.deleteEmployee(email);
      } catch (Exception e) {
          String error = e.getMessage();
      }
  }
  
  private EmployeeDto convertToDto(Employee e) {
      if (e == null) {
          throw new IllegalArgumentException("There is no such Employee!");
      }
      EmployeeDto employeeDto = new EmployeeDto(e.getEmail(), e.getPassword(), e.getName(), e.getSalary(), e.getDailySchedules());
      return employeeDto;
  }
}
