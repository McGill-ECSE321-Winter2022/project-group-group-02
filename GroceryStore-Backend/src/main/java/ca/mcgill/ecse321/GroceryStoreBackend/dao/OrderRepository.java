package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    
 List<Order> findOrderByCustomer(Customer customerEmail);
  
  boolean existsByCustomer(Customer customerEmail);
  
  Order findOrderById(Long orderId);
  
}
