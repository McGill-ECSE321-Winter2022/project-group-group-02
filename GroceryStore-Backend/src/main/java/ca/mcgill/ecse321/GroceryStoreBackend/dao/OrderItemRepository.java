package ca.mcgill.ecse321.GroceryStoreBackend.dao;


import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;


public interface OrderItemRepository extends CrudRepository<OrderItem, Long>  {
    
  OrderItem findOrderItemById(Long orderItemId);

    
}
