package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    
  
  Order findOrderById(Long orderId);
  
}
