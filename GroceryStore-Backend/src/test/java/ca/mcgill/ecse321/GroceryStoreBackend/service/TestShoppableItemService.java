package ca.mcgill.ecse321.GroceryStoreBackend.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.ShoppableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;

import org.mockito.stubbing.OngoingStubbing;


@ExtendWith(MockitoExtension.class)
public class TestShoppableItemService {
	
	 @Mock
	  private ShoppableItemRepository shoppableItemRepository;
	  @InjectMocks
	  private ShoppableItemService shoppableItemService;
	  
	  private static final String SHOPPABLE_ITEM_NAME = "TestShoppableItem";
	  private static final double SHOPPABLE_ITEM_PRICE = 321.2;
	  private static final int SHOPPABLE_ITEM_QUANTITY_AVAILABLE = 100;
	  
	  
	  
	  @BeforeEach
	  public void setMockOutput() {

	    lenient().when(shoppableItemRepository.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
	      if (invocation.getArgument(0).equals(SHOPPABLE_ITEM_NAME)) {
	        ShoppableItem shoppableItem = new ShoppableItem();
	        shoppableItem.setName(SHOPPABLE_ITEM_NAME);
	        shoppableItem.setPrice(SHOPPABLE_ITEM_PRICE);
	        shoppableItem.setQuantityAvailable(SHOPPABLE_ITEM_QUANTITY_AVAILABLE);
	        
	        return shoppableItem;
	      } else {
	        return null;
	      }
	    });
	    
	    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
	        return invocation.getArgument(0);
	      };
	      lenient().when(shoppableItemRepository.save(any(ShoppableItem.class))).thenAnswer(returnParameterAsAnswer);
	    }
	  
	  
	  @Test
	  public void testCreateShoppableItem() {
	    String name = "Battikha";
	    double price = 1000;
	    int quantityAvailable = 251;
	    ShoppableItem shoppableItem = null;
	    try {
	    	shoppableItem = shoppableItemService.createShoppableItem(name, price, quantityAvailable);
	    } catch (IllegalArgumentException e) {
	      fail();
	    }
	    assertNotNull(shoppableItem);
	    assertEquals(name, shoppableItem.getName());
	    assertEquals(price, shoppableItem.getPrice());
	    assertEquals(quantityAvailable, shoppableItem.getQuantityAvailable());
	  }
	  
	  @Test
	  public void testFindShoppableItem() {
	    

	  }
	  
	  @Test
	  public void testCreateShoppableItemErrorNegativePrice() {
	    String name = "lollar";
	    double price = -1;
	    int quantity = 3900;
	    ShoppableItem shoppableItem = null;
	    String error = "";
	    try {
	    	shoppableItem = shoppableItemService.createShoppableItem(name, price, quantity);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }
	    assertNull(shoppableItem);
	    assertEquals("Item price cannot be negative", error);
	    
	  }
	  
	  @Test
	  public void testCreateShoppableItemErrorNegativeQuantityAvailable() {
	    String name = "lollar";
	    double price = 3900;
	    int quantity = -1;
	    ShoppableItem shoppableItem = null;
	    String error = "";
	    try {
	    	shoppableItem = shoppableItemService.createShoppableItem(name, price, quantity);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }
	    assertNull(shoppableItem);
	    assertEquals("Item quantity cannot be negative", error);
	    
	  }
	  
	  @Test
	  public void testCreateShoppableItemErrorItemAlreadyInTheSystem() {
	    String name = "dollar";
	    double price = 23550;
	    int quantity = 0;
	    shoppableItemService.createShoppableItem(name, price, quantity);
	    ShoppableItem shoppableItem = null;
	    String error = "";
	    try {
	    	shoppableItem = shoppableItemService.createShoppableItem(name, price, quantity);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }
	    assertNull(shoppableItem);
	    assertEquals("Item already in the system", error);
	    
	  }
	  
	  @Test
	  public void testCreateShoppableItemNameBlank() {
	    
	    String name = "";
	    double price = 12;
	    int quantity = 3;
	    ShoppableItem shoppableItem = null;
	    String error = "";
	    try {
	    	shoppableItem = shoppableItemService.createShoppableItem(name, price, quantity);
	    } catch (IllegalArgumentException e) {
	      error = e.getMessage();
	    }
	    assertNull(shoppableItem);
	    assertEquals("Item name cannot be blank", error);
	    
	  }
	  
	  @Test
	  public void testUpdatePrice() {
		  double newPrice = 12;
		  ShoppableItem shoppableItem = null;
		  try {
			  shoppableItem = shoppableItemService.updatePrice(SHOPPABLE_ITEM_NAME, newPrice);
		  } catch(IllegalArgumentException e) {
		      fail();
		  }
		  assertNotNull(shoppableItem);
		  assertEquals(SHOPPABLE_ITEM_NAME,shoppableItem.getName());
		  assertEquals(newPrice,shoppableItem.getPrice());
		  
		  
	  }
	  
	  
	  
	  
	  
	  
	  
	  
}
