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
public class TestStorePersistence {

  
  @Autowired
  EntityManager entityManager;
  
  @Autowired
  private StoreRepository StoreRepository;
  
  @AfterEach
  public void clearDatabase() {
    StoreRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadStore() {
      double deliveryfee = 5;
      String town = "townn";
      Long id = (long) 1234;
      Store store = new Store(deliveryfee, town);
      
      
      store.setId(id);
      StoreRepository.save(store);


      store = null;

      store = StoreRepository.findStoreById(id);
      assertNotNull(store);
      assertEquals(id, store.getId());
  }
  
}
