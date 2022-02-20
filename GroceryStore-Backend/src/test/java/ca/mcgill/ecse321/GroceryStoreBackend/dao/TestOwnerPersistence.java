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
public class TestOwnerPersistence {

  
  @Autowired
  EntityManager entityManager;
  
  @Autowired
  private OwnerRepository ownerRepository;
  
  @AfterEach
  public void clearDatabase() {
      ownerRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadOwner() {
      String name = "owner";
      String email = "admin@grocerystore.com";
      String password = "1234";
      Owner owner = new Owner();
      
      owner.setName(name);
      owner.setEmail(email);
      owner.setPassword(password);
      
      
      ownerRepository.save(owner);


      owner = null;

      owner = ownerRepository.findByEmail(email);
      assertNotNull(owner);
      assertEquals(email, owner.getEmail());
  }
  
}
