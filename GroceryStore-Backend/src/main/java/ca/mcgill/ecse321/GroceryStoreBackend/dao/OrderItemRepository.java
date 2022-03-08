package ca.mcgill.ecse321.GroceryStoreBackend.dao;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;


public interface OrderItemRepository extends CrudRepository<OrderItem, Long>  {
    
  OrderItem findOrderItemById(Long orderItemId);
  List <OrderItem> findOrderItemByOrder(Order order);

    
}
