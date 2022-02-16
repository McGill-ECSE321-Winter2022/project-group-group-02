package ca.mcgill.ecse321.GroceryStoreBackend.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
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
public class TestCustomerPersistence {

  
  @Autowired
  EntityManager entityManager;
  
  @Autowired
  private CustomerRepository customerRepository;
  
  @AfterEach
  public void clearDatabase() {
      customerRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadCustomer() {
      String name = "testCustomer";
      String email = "testCustomer@mail.com";
      String password = "testPassword";
      String address = "town";
      Customer customer = new Customer(email, password, name, address);
      
      customerRepository.save(customer);


      customer = null;

      customer = customerRepository.findCustomerByEmail(email);
      assertNotNull(customer);
      assertEquals(email, customer.getEmail());
  }
  
}
