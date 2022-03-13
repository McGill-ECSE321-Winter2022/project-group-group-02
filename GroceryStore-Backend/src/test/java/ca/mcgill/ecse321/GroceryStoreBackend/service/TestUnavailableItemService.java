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

import ca.mcgill.ecse321.GroceryStoreBackend.dao.UnavailableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.UnavailableItem;

@ExtendWith(MockitoExtension.class)
public class TestUnavailableItemService {

	@Mock
	private UnavailableItemRepository unavailableItemRepository;
	@InjectMocks
	private UnavailableItemService unavailableItemService;

	private static final String UNAVAILABLE_ITEM_NAME = "TestUnavailableItem";
	private static final double UNAVAILABLE_ITEM_PRICE = 321.2;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(unavailableItemRepository.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(UNAVAILABLE_ITEM_NAME)) {
				UnavailableItem UnavailableItem = new UnavailableItem();
				UnavailableItem.setName(UNAVAILABLE_ITEM_NAME);
				UnavailableItem.setPrice(UNAVAILABLE_ITEM_PRICE);

				return UnavailableItem;
			} else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(unavailableItemRepository.save(any(UnavailableItem.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateUnavailableItem() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		String name = "Battikha";
		double price = 1000;
		UnavailableItem unavailableItem = null;
		try {
			unavailableItem = unavailableItemService.createUnavailableItem(name, price);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(unavailableItem);
		assertEquals(name, unavailableItem.getName());
		assertEquals(price, unavailableItem.getPrice());
	}

	@Test
	public void testFindUnavailableItem() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		UnavailableItem u = null;
		try {
			u = unavailableItemService.getUnavailableItem(UNAVAILABLE_ITEM_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(u);
		assertEquals(u.getName(), UNAVAILABLE_ITEM_NAME);
		assertEquals(u.getPrice(), UNAVAILABLE_ITEM_PRICE);
	}

	@Test
	public void testFindShoppableItemErrorItemNotFound() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		String name = "zaatar";
		String error = "";
		UnavailableItem s = null;
		try {
			s = unavailableItemService.getUnavailableItem(name);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(s);
		assertEquals("This item does not exist in the system", error);
	}

	@Test
	public void testCreateUnavailableItemErrorNegativePrice() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		String name = "lollar";
		double price = -1;
		UnavailableItem unavailableItem = null;
		String error = "";
		try {
			unavailableItem = unavailableItemService.createUnavailableItem(name, price);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(unavailableItem);
		assertEquals("Item price cannot be negative", error);

	}

	@Test
	public void testCreateUnavailableItemErrorItemAlreadyInTheSystem() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		double price = 23550;
		UnavailableItem unavailableItem = null;
		String error = "";
		try {
			unavailableItem = unavailableItemService.createUnavailableItem(UNAVAILABLE_ITEM_NAME, price);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Item already in the system", error);
		assertNull(unavailableItem);

	}

	@Test
	public void testCreateUnavailableItemNameBlank() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		String name = "";
		double price = 12;
		UnavailableItem unavailableItem = null;
		String error = "";
		try {
			unavailableItem = unavailableItemService.createUnavailableItem(name, price);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(unavailableItem);
		assertEquals("Item name cannot be blank", error);

	}

	@Test
	public void testUpdatePrice() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		double newPrice = 12;
		UnavailableItem unavailableItem = null;
		try {
			unavailableItem = unavailableItemService.updatePrice(UNAVAILABLE_ITEM_NAME, newPrice);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(unavailableItem);
		assertEquals(UNAVAILABLE_ITEM_NAME, unavailableItem.getName());
		assertEquals(newPrice, unavailableItem.getPrice());

	}

	@Test
	public void testUpdatePriceBlankName() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		double newPrice = 12;
		String name = "";
		String error = "";
		UnavailableItem unavailableItem = null;
		try {
			unavailableItem = unavailableItemService.updatePrice(name, newPrice);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(unavailableItem);
		assertEquals("Item name cannot be blank", error);
	}

	@Test
	public void testUpdatePriceNegativePrice() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		double newPrice = -1;
		String error = "";
		UnavailableItem unavailableItem = null;
		try {
			unavailableItem = unavailableItemService.updatePrice(UNAVAILABLE_ITEM_NAME, newPrice);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(unavailableItem);
		assertEquals("Item price cannot be negative", error);
	}

	@Test
	public void testUpdatePriceItemNotFound() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		double newPrice = 16;
		String name = "coca-cola";
		String error = "";
		UnavailableItem unavailableItem = null;
		try {
			unavailableItem = unavailableItemService.updatePrice(name, newPrice);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(unavailableItem);
		assertEquals("This item does not exist in the system", error);
	}

	@Test
	public void testUpdatePriceSamePrice() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		String error = "";
		UnavailableItem unavailableItem = null;
		try {
			unavailableItem = unavailableItemService.updatePrice(UNAVAILABLE_ITEM_NAME, UNAVAILABLE_ITEM_PRICE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("The price is the same", error);
		assertNull(unavailableItem);
	}

	// delete Unavailable item

	@Test
	public void testDeleteUnavailableItem() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		boolean deleted = false;
		try {
			deleted = unavailableItemService.deleteUnavailableItem(UNAVAILABLE_ITEM_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(deleted);
	}

	@Test
	public void testDeleteUnavailableItemBlankName() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		UnavailableItem unavailableItem = null;
		String error = "";
		boolean deleted = false;
		try {
			deleted = unavailableItemService.deleteUnavailableItem("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		unavailableItem = unavailableItemRepository.findByName(UNAVAILABLE_ITEM_NAME);
		assertNotNull(unavailableItem);
		assertFalse(deleted);
		assertEquals("Item name cannot be blank", error);
	}

	@Test
	public void testDeleteUnavailableItemItemNotFound() {
		assertEquals(0, unavailableItemService.getAllUnavailableItems().size());
		UnavailableItem unavailableItem = null;
		String error = "";
		boolean deleted = false;
		try {
			deleted = unavailableItemService.deleteUnavailableItem("Famous Chicken");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		unavailableItem = unavailableItemRepository.findByName(UNAVAILABLE_ITEM_NAME);
		assertNotNull(unavailableItem);
		assertFalse(deleted);
		assertEquals("This item does not exist in the system", error);
	}

}
