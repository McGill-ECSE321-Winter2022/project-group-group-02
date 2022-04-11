package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.ShoppableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;

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
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		String name = "Battikha";
		double price = 1000;
		int quantityAvailable = 251;
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.createShoppableItem(name, price, quantityAvailable);
		} catch (IllegalArgumentException e) {
			fail();
		}
		// Check that the shoppable item was created and that its parameters are well set
		assertNotNull(shoppableItem);
		
		assertEquals(name, shoppableItem.getName());
		assertEquals(price, shoppableItem.getPrice());
		assertEquals(quantityAvailable, shoppableItem.getQuantityAvailable());
	}

	@Test
	public void testFindShoppableItem() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		ShoppableItem s = null;
		try {
			s = shoppableItemService.getShoppableItem(SHOPPABLE_ITEM_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		//Check that the shoppable item was found with bits expected parameters
		assertNotNull(s);
		assertEquals(s.getName(), SHOPPABLE_ITEM_NAME);
		assertEquals(s.getPrice(), SHOPPABLE_ITEM_PRICE);
		assertEquals(s.getQuantityAvailable(), SHOPPABLE_ITEM_QUANTITY_AVAILABLE);
	}

	@Test
	public void testFindShoppableItemErrorItemNotFound() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		String name = "zaatar";
		String error = "";
		ShoppableItem s = null;
		try {
			s = shoppableItemService.getShoppableItem(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check that the shoppable item was not found and that the correct error message was displayed
		assertNull(s);
		assertEquals("This item does not exist in the system", error);
	}

	@Test
	public void testCreateShoppableItemErrorNegativePrice() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
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
		//Check that the shoppable item was not created and that the correct error message was displayed
		
		assertNull(shoppableItem);
		assertEquals("Item price cannot be negative", error);

	}

	@Test
	public void testCreateShoppableItemErrorNegativeQuantityAvailable() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
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
		//Check that the shoppable item was not created and that the correct error message was displayed

		assertNull(shoppableItem);
		assertEquals("Item quantity cannot be negative", error);

	}

	@Test
	public void testCreateShoppableItemErrorItemAlreadyInTheSystem() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		double price = 23550;
		int quantity = 0;
		ShoppableItem shoppableItem = null;
		String error = "";
		try {
			shoppableItem = shoppableItemService.createShoppableItem(SHOPPABLE_ITEM_NAME, price, quantity);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check that the shoppable item was not created and that the correct error message was displayed

		assertNull(shoppableItem);
		assertEquals("Item already in the system", error);

	}

	@Test
	public void testCreateShoppableItemNameBlank() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
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
		//Check that the shoppable item was not created and that the correct error message was displayed

		assertNull(shoppableItem);
		assertEquals("Item name cannot be blank", error);

	}

	@Test
	public void testUpdatePrice() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		double newPrice = 12;
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updatePrice(SHOPPABLE_ITEM_NAME, newPrice);
		} catch (IllegalArgumentException e) {
			fail();
		}
		//Check that the shoppable item price was successfully updated and that the item price was updated

		assertNotNull(shoppableItem);
		assertEquals(SHOPPABLE_ITEM_NAME, shoppableItem.getName());
		assertEquals(newPrice, shoppableItem.getPrice());

	}

	@Test
	public void testUpdatePriceBlankName() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		double newPrice = 12;
		String name = "";
		String error = "";
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updatePrice(name, newPrice);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check that the shoppable item price was not updated and that the correct error message was displayed

		assertNull(shoppableItem);
		assertEquals("Item name cannot be blank", error);
	}

	@Test
	public void testUpdatePriceNegativePrice() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		double newPrice = -1;
		String error = "";
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updatePrice(SHOPPABLE_ITEM_NAME, newPrice);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check that the shoppable item price was not updated and that the correct error message was displayed

		assertNull(shoppableItem);
		assertEquals("Item price cannot be negative", error);
	}

	@Test
	public void testUpdatePriceItemNotFound() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		double newPrice = 14;
		String name = "coca-cola";
		String error = "";
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updatePrice(name, newPrice);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check that the shoppable item price was not updated and that the correct error message was displayed

		assertNull(shoppableItem);
		assertEquals("This item does not exist in the system", error);
	}


	// UpdateInventory

	@Test
	public void testUpdateInventory() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		int newQuantity = 12;
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updateInventory(SHOPPABLE_ITEM_NAME, newQuantity);
		} catch (IllegalArgumentException e) {
			fail();
		}
		//Check the shoppable item inventory was successfully updated
		assertNotNull(shoppableItem);
		assertEquals(SHOPPABLE_ITEM_NAME, shoppableItem.getName());
		assertEquals(newQuantity, shoppableItem.getQuantityAvailable());

	}

	@Test
	public void testUpdateInventoryBlankName() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		int newQuantity = 12;
		String name = "";
		String error = "";
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updateInventory(name, newQuantity);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check the shoppable item inventory was not updated and that the correct error message is printed
		assertNull(shoppableItem);
		assertEquals("Item name cannot be blank", error);
	}

	@Test
	public void testUpdateInventoryNegativeQuantity() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		String error = "";
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updateInventory(SHOPPABLE_ITEM_NAME, -1);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check the shoppable item inventory was not updated and that the correct error message is printed
		assertNull(shoppableItem);
		assertEquals("The quantity cannot be negative", error);
	}

	@Test
	public void testUpdateInventoryItemNotFound() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		int newQuantity = 14;
		String name = "coca-cola";
		String error = "";
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updatePrice(name, newQuantity);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check the shoppable item inventory was not updated and that the correct error message is printed
		assertNull(shoppableItem);
		assertEquals("This item does not exist in the system", error);
	}

	@Test
	public void testUpdateInventorySameQuantity() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		String error = "";
		ShoppableItem shoppableItem = null;
		try {
			shoppableItem = shoppableItemService.updateInventory(SHOPPABLE_ITEM_NAME,
					SHOPPABLE_ITEM_QUANTITY_AVAILABLE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check the shoppable item inventory was not updated and that the correct error message is printed
		assertNull(shoppableItem);
		assertEquals("The quantity is the same", error);
	}

	// delete shoppable item

	@Test
	public void testDeleteShoppableItem() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		boolean deleted = false;

		try {
			deleted = shoppableItemService.deleteShoppableItem(SHOPPABLE_ITEM_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		//Check that the item was successfully deleted from the system
		assertTrue(deleted);
	}

	@Test
	public void testDeleteShoppableItemBlankName() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		ShoppableItem shoppableItem = null;
		String error = "";
		boolean deleted = false;
		try {
			deleted = shoppableItemService.deleteShoppableItem("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check that the item was not deleted from the system and that the correct error message is displayed
		
		shoppableItem = shoppableItemRepository.findByName(SHOPPABLE_ITEM_NAME);
		assertNotNull(shoppableItem);
		assertFalse(deleted);
		assertEquals("Item name cannot be blank", error);
	}

	@Test
	public void testDeleteShoppableItemItemNotFound() {
		assertEquals(0, shoppableItemService.getAllShoppableItems().size());
		ShoppableItem shoppableItem = null;
		String error = "";
		boolean deleted = false;
		try {
			deleted = shoppableItemService.deleteShoppableItem("Famous Chicken");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//Check that the item was not deleted from the system and that the correct error message is displayed

		shoppableItem = shoppableItemRepository.findByName(SHOPPABLE_ITEM_NAME);
		assertNotNull(shoppableItem);
		assertFalse(deleted);
		assertEquals("This item does not exist in the system", error);
	}

}
