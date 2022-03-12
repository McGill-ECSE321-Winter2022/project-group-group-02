package ca.mcgill.ecse321.GroceryStoreBackend.service;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.ShoppableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;




@ExtendWith(MockitoExtension.class)
public class TestOrderItemService {


    @Mock
    private OrderItemRepository orderItemRepo;
    @Mock
    private ShoppableItemRepository shoppableItemRepo;
    @Mock
    private OrderRepository orderRepo;
    @Mock
    private CustomerRepository customerRepo;
    
    @InjectMocks
    private OrderItemService orderItemService;
    @InjectMocks
    private ShoppableItemService shoppableItemService;
    
    


    private static final String SHOPPABLE_ITEM_NAME = "FRESH LOLLAR";
    private static final double SHOPPABLE_ITEM_PRICE = 22900;
    private static final int SHOPPABLE_ITEM_QUANTITY = 20;

    private static final int ORDER_ITEM_QUANTITY = 1;
    private static final long ORDER_ITEM_ID = 123456L;
    
    private static final String CUSTOMER_EMAIL = "theBestValuableCustomer@lollar.com";
    private static final String CUSTOMER_NAME = "Bank";
    private static final String CUSTOMER_ADDRESS = "Beirut, the land of opportunities";
    private static final String CUSTOMER_PASSWORD = "Audi2019";

    
    private static final Long ORDER_ID = 1234567L;
    private static final OrderType ORDER_TYPE = OrderType.PickUp;
    private static final OrderStatus ORDER_STATUS = OrderStatus.Confirmed;
    private static final Date ORDER_DATE = Date.valueOf("2022-03-10");
    private static final Time ORDER_TIME = Time.valueOf("10:09:00");;



    @BeforeEach
    public void setMockOutput() {

      lenient().when(orderRepo.findOrderById(any(Long.class)))
      .thenAnswer((InvocationOnMock invocation) -> {
        if (invocation.getArgument(0).equals(ORDER_ID)) {

          Customer customer = new Customer();
          customer.setEmail(CUSTOMER_EMAIL);
          customer.setName(CUSTOMER_NAME);
          customer.setAddress(CUSTOMER_ADDRESS);
          customer.setPassword(CUSTOMER_PASSWORD);

          Order order = new Order();
          order.setCustomer(customer);
          order.setId(ORDER_ID);
          order.setOrderStatus(ORDER_STATUS);
          order.setOrderType(ORDER_TYPE);
          order.setDate(ORDER_DATE);
          order.setTime(ORDER_TIME);

          return order;
        } else {
          return null;
        }

      });
      

        lenient().when(shoppableItemRepo.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(SHOPPABLE_ITEM_NAME)) {
                ShoppableItem shoppableItem = new ShoppableItem();
                shoppableItem.setName(SHOPPABLE_ITEM_NAME);
                shoppableItem.setPrice(SHOPPABLE_ITEM_PRICE);
                shoppableItem.setQuantityAvailable(SHOPPABLE_ITEM_QUANTITY);

                return shoppableItem;
            } else {
                return null;
            }
        });



        lenient().when(orderItemRepo.findOrderItemById(any(Long.class)))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(ORDER_ITEM_ID)) {

                        ShoppableItem item = new ShoppableItem();
                        item.setName(SHOPPABLE_ITEM_NAME);
                        item.setQuantityAvailable(SHOPPABLE_ITEM_QUANTITY);
                        item.setPrice(SHOPPABLE_ITEM_PRICE);


                        OrderItem orderItem = new OrderItem();

                        orderItem.setItem(item);
                        orderItem.setId(ORDER_ITEM_ID);
                        orderItem.setQuantity(ORDER_ITEM_QUANTITY);

                        return orderItem;
                    } else {
                        return null;
                    }

                });
        lenient().when(orderItemRepo.findAll())
                .thenAnswer((InvocationOnMock invocation) -> {

                    List<OrderItem> list = new ArrayList<>();

                    ShoppableItem item = new ShoppableItem();
                    item.setName(SHOPPABLE_ITEM_NAME);
                    item.setQuantityAvailable(SHOPPABLE_ITEM_QUANTITY);
                    item.setPrice(SHOPPABLE_ITEM_PRICE);


                    OrderItem orderItem = new OrderItem();

                    orderItem.setItem(item);
                    orderItem.setId(ORDER_ITEM_ID);
                    orderItem.setQuantity(ORDER_ITEM_QUANTITY);

                    list.add(orderItem);
                    return list;

                });



        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        lenient().when(orderItemRepo.save(any(OrderItem.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(shoppableItemRepo.save(any(ShoppableItem.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);

    }

    /**
     * Test for create order item
     */
    @Test
    public void testCreateOrderItem(){

        String name = "mafi fresh";
        double price = 34;
        int quantity = 4;



        ShoppableItem item = shoppableItemService.createShoppableItem(name, price, quantity);
        lenient().when(shoppableItemRepo.findByName(name)).thenReturn(item);

        int quantityOrder = 1;
        long itemId = 234L;
        OrderItem orderItem = null;
         try{
             orderItem = orderItemService.createOrderItem(itemId, quantityOrder, name, ORDER_ID);


         }catch (IllegalArgumentException e){
             fail();
         }

         assertNotNull(orderItem);
         assertEquals(itemId, orderItem.getId());
        assertEquals(quantityOrder, orderItem.getQuantity());
        assertEquals(name, orderItem.getItem().getName());

    }
    @Test
    public void testCreateOrderItemInvalidQuantity(){

        String name = "mafi fresh";
        double price = 34;
        int quantity = 4;



        ShoppableItem item = shoppableItemService.createShoppableItem(name, price, quantity);
        lenient().when(shoppableItemRepo.findByName(name)).thenReturn(item);

        int quantityOrder = -1;
        long itemId = 234L;
        OrderItem orderItem = null;
        String error = null;
        try{
            orderItem = orderItemService.createOrderItem(itemId, quantityOrder, name, ORDER_ID);

        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(orderItem);
        assertEquals(error, "Please enter a valid quantity. ");


    }
    @Test
    public void testCreateOrderItemInvalidItem(){

        String name = "mafi item";

        int quantityOrder = 1;
        long itemId = 234L;
        OrderItem orderItem = null;
        String error = null;
        try{
            orderItem = orderItemService.createOrderItem(itemId, quantityOrder, name, ORDER_ID);

        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(orderItem);
        assertEquals(error, "Please enter a valid item. ");

    }
    @Test
    public void testCreateOrderItemInvalidOrder(){
        String name = "mafi fresh";
        double price = 34;
        int quantity = 4;

        ShoppableItem item = shoppableItemService.createShoppableItem(name, price, quantity);
        lenient().when(shoppableItemRepo.findByName(name)).thenReturn(item);

        int quantityOrder = 1;
        long itemId = 234L;
        OrderItem orderItem = null;
        Long fakeOrderId = 1L;
        String error = null;
        try{
            orderItem = orderItemService.createOrderItem(itemId, quantityOrder, name, fakeOrderId);

        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(orderItem);
        assertEquals(error, "Order Item cannot exist without an order. ");

    }


    /**
     * test for update order item
     */

    @Test
    public void testUpdateOrderItem(){

        int quantityOrdered = 12;
        OrderItem item = null;

        try{
            item = orderItemService.updateOrderItem(ORDER_ITEM_ID, quantityOrdered, SHOPPABLE_ITEM_NAME, ORDER_ID);

        }catch (IllegalArgumentException e){

            fail();
        }

        assertNotNull(item);
        assertEquals(ORDER_ITEM_ID, item.getId());
        assertEquals(quantityOrdered, item.getQuantity());
        assertEquals(SHOPPABLE_ITEM_NAME, item.getItem().getName());

    }
    @Test
    public void testUpdateOrderItemInvalidQuantity(){

        int quantityOrdered = -1;
        OrderItem item = null;
        String error = null;
        try{
            item = orderItemService.updateOrderItem(ORDER_ITEM_ID, quantityOrdered, SHOPPABLE_ITEM_NAME, ORDER_ID);

        }catch (IllegalArgumentException e){

            error = e.getMessage();
        }

        assertEquals(error, "Please enter a valid quantity. ");

    }
    @Test
    public void testUpdateOrderItemInvalidItem(){

        int quantityOrdered = 12;
        OrderItem item = null;
        String fakeName = "bank audi";
        String error = null;
        try{
            item = orderItemService.updateOrderItem(ORDER_ITEM_ID, quantityOrdered, fakeName, ORDER_ID);

        }catch (IllegalArgumentException e){

            error = e.getMessage();
        }

        assertEquals(error, "Please enter a valid item. ");

    }
    @Test
    public void testUpdateOrderItemInvalidOrderItemId(){

        Long fakeId = 87L;
        int quantityOrdered = 12;
        OrderItem item = null;

        String error = null;
        try{
            item = orderItemService.updateOrderItem(fakeId, quantityOrdered, SHOPPABLE_ITEM_NAME, ORDER_ID);

        }catch (IllegalArgumentException e){

            error = e.getMessage();
        }

        assertNull(item);
        assertEquals(error, "Please enter a valid order item. ");

    }
    @Test
    public void testUpdateOrderItemInvalidOrder(){
        String name = "mafi fresh";
        double price = 34;
        int quantity = 4;

        ShoppableItem item = shoppableItemService.createShoppableItem(name, price, quantity);
        lenient().when(shoppableItemRepo.findByName(name)).thenReturn(item);

        int quantityOrder = 1;
        long itemId = 234L;
        OrderItem orderItem = null;
        Long fakeOrderId = 1L;
        String error = null;
        try{
            orderItem = orderItemService.updateOrderItem(itemId, quantityOrder, name, fakeOrderId);

        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(orderItem);
        assertEquals(error, "Order Item cannot exist without an order. ");

    }

    /**
     * test for delete order item
     */

    @Test
    public void testDeleteOrderItem(){

        String name = "mafi fresh";
        double price = 34;
        int quantity = 4;

        ShoppableItem item = shoppableItemService.createShoppableItem(name, price, quantity);
        lenient().when(shoppableItemRepo.findByName(name)).thenReturn(item);

        int quantityOrder = 1;
        long itemId = 234L;
        OrderItem orderItem = orderItemService.createOrderItem(itemId, quantityOrder, name, ORDER_ID);
        lenient().when(orderItemRepo.findOrderItemById(itemId)).thenReturn(orderItem);

        boolean deleted = false;
        try{
            deleted = orderItemService.deleteOrderItem(itemId);

        }catch (IllegalArgumentException e){
            fail();

        }
        assertTrue(deleted);

    }
    @Test
    public void testDeleteOrderItemThatDoesNotExist(){


        long itemId = 234L;
        String error = null;
        try{
            orderItemService.deleteOrderItem(itemId);

        }catch (IllegalArgumentException e){
            error = e.getMessage();

        }
        assertEquals(error, "Please enter a valid order item. ");

    }

    /**
     * test for get order item by id
     */
    @Test
    public void testGetOrderItemById() {

        OrderItem orderItem = null;

        try {
            orderItem = orderItemService.getOrderItemById(ORDER_ITEM_ID);

        } catch (IllegalArgumentException e) {

            fail();
        }

        assertEquals(orderItem.getId(), ORDER_ITEM_ID);
        assertNotNull(orderItem);

    }
    @Test
    public void testGetOrderByIdButInvalid() {

        OrderItem orderItem = null;
        Long fakeId = 98L;
        String error = null;
        try {
            orderItem = orderItemService.getOrderItemById(fakeId);

        } catch (IllegalArgumentException e) {

            error = e.getMessage();
        }

        assertEquals(error, "Please enter a valid order item by providing a valid order item ID. ");
        assertNull(orderItem);

    }


    /**
     * test for get all order item
     */
    @Test
    public void testGetAllOrderItems(){

        List<OrderItem> allOrderItems = null;

        try {
            allOrderItems = orderItemService.getAllOrderItem();

        }catch (IllegalArgumentException e){
            fail();
        }
        assertNotNull(allOrderItems);
        assertEquals(1, allOrderItems.size());

    }

}
