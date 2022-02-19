package ca.mcgill.ecse321.GroceryStoreBackend.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOrderItemPersistence {

  
  @Autowired
  EntityManager entityManager;
  
  @Autowired
  private OrderItemRepository orderItemRepository;
  
  
  @Autowired
  private ShoppableItemRepository shoppableItemRepository;
  
  @AfterEach
  public void clearDatabase() {
    orderItemRepository.deleteAll();
    shoppableItemRepository.deleteAll();
    
  }
  
  @Test
  public void testPersistAndLoadOrderItem() {
    
    

    String itemName = "Milk";
    double price = 4.65;
    int quantityAvailable = 10;
    ShoppableItem shoppableItem = new ShoppableItem (itemName, price, quantityAvailable);
    
    shoppableItemRepository.save(shoppableItem);
    
    
    Long orderItemId = (long) 1234;
    int quantityWanted = 2;
    OrderItem orderItem = new OrderItem();
    
    orderItem.setQuantity(quantityWanted);
    orderItem.setItem(shoppableItem);
    orderItem.setId(orderItemId);
    
 
   
    orderItemRepository.save(orderItem);

    orderItem = null;

    orderItem = orderItemRepository.findOrderItemById(orderItemId);
    assertNotNull(orderItem);
    assertEquals(orderItemId, orderItem.getId());
  }
  
}
