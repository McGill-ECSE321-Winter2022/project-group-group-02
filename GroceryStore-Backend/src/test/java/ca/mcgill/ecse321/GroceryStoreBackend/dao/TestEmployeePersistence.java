package ca.mcgill.ecse321.GroceryStoreBackend.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEmployeePersistence {

  
  @Autowired
  EntityManager entityManager;
  
  @Autowired
  private EmployeeRepository employeeRepository;
  
  @AfterEach
  public void clearDatabase() {
    employeeRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadCustomer() {
		
	  // Creation of an Employee instance with test attributes
      String name = "test employee";
      String email = "testEmployee@mail.com";
      String password = "testPassword";
      int salary = 14;
      Employee employee = new Employee();
      employee.setName(name);
      employee.setEmail(email);
      employee.setPassword(password);
      employee.setSalary(salary);
      
      // Save the created Employee instance
      employeeRepository.save(employee);

	  // Set the variable to null, and then try retrieving the saved instance using its email
      employee = null;
      employee = employeeRepository.findByEmail(email);
      
      // Determine whether the instance is null and if the name matches.
      assertNotNull(employee);
      assertEquals(name, employee.getName());
  }
  
}
