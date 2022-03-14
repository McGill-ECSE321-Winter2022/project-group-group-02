package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.StoreRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Store;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.DailyScheduleRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;

@ExtendWith(MockitoExtension.class)
public class TestStoreService {
	@Mock
	private StoreRepository storeRepository;
	@InjectMocks
	private StoreService storeService;

	@Mock
	private DailyScheduleRepository dailyScheduleDao;
	@InjectMocks
	private DailyScheduleService dailyScheduleService;

	private static final String STORE_TOWN = "TestTown";
	static final Long STORE_KEY = 12l;
	private static final Double STORE_DELIVERY_FEE = 20d;

	static final Long DAILYSCHEDULE_KEY = 321l;
	private static final Time DAILYSCHEDULE_STARTTIME = Time.valueOf("08:00:00");
	private static final Time DAILYSCHEDULE_ENDTIME = Time.valueOf("20:00:00");
	private static final DayOfWeek DAILYSCHEDULE_DAYOFWEEK = DayOfWeek.Monday;

	static final long DAILYSCHEDULE_KEY2 = 123l;
	private static final Time DAILYSCHEDULE_STARTTIME2 = Time.valueOf("07:00:00");
	private static final Time DAILYSCHEDULE_ENDTIME2 = Time.valueOf("21:00:00");
	private static final DayOfWeek DAILYSCHEDULE_DAYOFWEEK2 = DayOfWeek.Tuesday;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(storeRepository.findStoreById(any(Long.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STORE_KEY)) {
				Store store = new Store();
				store.setTown(STORE_TOWN);
				store.setDeliveryFee(STORE_DELIVERY_FEE);
				store.setId(STORE_KEY);

				DailySchedule dailySchedule = new DailySchedule();
				dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK);
				dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME);
				dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME);
				dailySchedule.setId(DAILYSCHEDULE_KEY);
				List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
				store.setDailySchedules(dailySchedules);

				return store;
			} else {
				return null;
			}
		});

		lenient().when(dailyScheduleDao.findDailyScheduleById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(DAILYSCHEDULE_KEY)) {

				DailySchedule dailySchedule = new DailySchedule();
				dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK);
				dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME);
				dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME);
				dailySchedule.setId(DAILYSCHEDULE_KEY);
	

				return dailySchedule;
			} else if (invocation.getArgument(0).equals(DAILYSCHEDULE_KEY2)) {
				DailySchedule dailySchedule = new DailySchedule();
				dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK2);
				dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME2);
				dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME2);
				dailySchedule.setId(DAILYSCHEDULE_KEY2);

				return dailySchedule;
			} else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(storeRepository.save(any(Store.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(dailyScheduleDao.save(any(DailySchedule.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateStore() { //normal use case
		assertEquals(0, storeService.getAllStores().size());
		String town = "FakeTown";
		Double delifee = 45d;
		Store store = null;
		Long sid = 222L;

      try {

        store = storeService.createStore(sid, town,delifee);
    } catch (IllegalArgumentException e) {
        // Check that no error occurred
        fail();
    }

	//check the inputs are correctly passed
    assertNotNull(store);
    assertEquals(town, store.getTown());
    assertEquals(delifee, store.getDeliveryFee());
    
}
   
@Test
	public void testCreateStoreNullTown() {
        String error = null;
        String town = null;
	    Double delifee = 45d;
        Store store = null;
		Long sid = 222L;

		try {
			store = storeService.createStore(sid, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not created
		assertNull(store);
		// check error
		assertEquals("Please enter a town", error);
	}

    @Test
	public void testCreateStoreEmptyTown() {
        String error = null;
        String town = "";
	    Double delifee = 45d;
        Store store = null;
		Long sid = 222L;

		try {
			store = storeService.createStore(sid, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not created
		assertNull(store);
		// check error
		assertEquals("Please enter a town", error);
	}

	@Test
	public void testCreateStoreNullDeliveryFee() {
		String error = null;
		String town = "FakeTown";
		Double delifee = null;
		Store store = null;
		Long sid = 222L;

		try {
			store = storeService.createStore(sid, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not created
		assertNull(store);
		// check error
		assertEquals("Please enter a delivery fee", error);
	}

	@Test
	public void testCreateStoreNegativeDeliveryFee() {
		String error = null;
		String town = "FakeTown";
		Double delifee = -7d;
		Store store = null;
		Long sid = 222L;

		try {
			store = storeService.createStore(sid, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not created
		assertNull(store);
		// check error
		assertEquals("The delivery fee cannot be negative", error);
	}

	@Test
	public void testUpdateStore() { //Normal use case
		String error = null;
		String town = "FakeTown";
		Double delifee = 45d;
		Store store = null;


		try {
       
			store = storeService.updateStore(STORE_KEY, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check the inputs are correctly passed
		assertNotNull(store);
		assertEquals(STORE_KEY, store.getId());
		assertEquals(town, store.getTown());
		assertEquals(delifee, store.getDeliveryFee());

	}

	@Test
	public void testUpdateStoreNotFound() {
		String error = null;
		String town = "FakeTown";
		Double delifee = 45d;
		Long sid = 000L;
		Store store = null;


		
		try {
           
			store = storeService.updateStore(sid, town, delifee);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not updated
		assertNull(store);
		//check error message
		assertEquals("No store found", error);
	}

	@Test
	public void testUpdateStoreNullTown() {
		String error = null;
		String town = null;
		Double delifee = 45d;
		Store store = null;

		try {
			store = storeService.updateStore(STORE_KEY, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not updated
		assertNull(store);
		//check error message
		assertEquals("Please enter a town", error);
	}

	@Test
	public void testUpdateStoreEmptyTown() {
		String error = null;
		String town = "";
		Double delifee = 45d;
		Store store = null;

		try {
			store = storeService.updateStore(STORE_KEY, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not updated
		assertNull(store);
		//check error message
		assertEquals("Please enter a town", error);
	}

	@Test
	public void testUpdateStoreNullDeliveryFee() {
		String error = null;
		String town = "FakeTown";
		Double delifee = null;
		Store store = null;

		try {
			store = storeService.updateStore(STORE_KEY, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not updated
		assertNull(store);
		//check error message
		assertEquals("Please enter a delivery fee", error);
	}

	@Test
	public void testUpdateStoreNegativeDeliveryFee() {
		String error = null;
		String town = "FakeTown";
		Double delifee = -21D;
		Store store = null;

		try {
			store = storeService.updateStore(STORE_KEY, town, delifee);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is not updated
		assertNull(store);
		//check error message
		assertEquals("The delivery fee cannot be negative", error);
	}

	@Test
	public void testDeleteStore() { //normal use case

		String error = null;
		boolean success = false;

		try {
			success = storeService.deleteStore(STORE_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if the store is deleted
		assertTrue(success);
	}

	@Test
	public void testDeleteStoreNullId() {
		String error = null;
		try {
			storeService.deleteStore(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check error message
		assertEquals("Id cannot be empty", error);
	}

	@Test
	public void testDeleteStoreNotFound() {
		String error = null;
		Long Id = (long) 9;

		try {
			storeService.deleteStore(Id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check error message
		assertEquals("Store not found", error);
	}

	@Test
	public void testAddDailyScheduleForStore() { //normal use case
		String error = null;
	
		boolean success = false;
		try {
			success = storeService.addDailyScheduleToStore(STORE_KEY,DAILYSCHEDULE_KEY2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		//check if daily schedule is added to the store
		assertTrue(success);
	}

	@Test
	public void testAddDailyScheduleForStoreStoreNotFound() {
		String error = null;
		boolean success = false;
		try {
			success = storeService.addDailyScheduleToStore(77L ,DAILYSCHEDULE_KEY2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(success);
		//check error message
		assertEquals("Store not found", error);
	}

	@Test
	public void testAddDailyScheduleForStoreDailyScheduleNotFound() {
		String error = null;
	
		boolean success = false;
		try {
			success = storeService.addDailyScheduleToStore(STORE_KEY ,77L);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(success);
		//check error message
		assertEquals("Daily schedule not found", error);
	}

	@Test
	public void testDeleteDailyScheduleForStore() { //normal use case
		String error = null;
		boolean success = false;
		try {
			success = storeService.deleteDailyScheduleToStore(STORE_KEY ,DAILYSCHEDULE_KEY2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		//check if the dailyschedule is deleted
		assertTrue(success);
	}

	@Test
	public void testDeleteDailyScheduleForStoreStoreNotFound() {
		String error = null;
		boolean success = false;
		try {
			success = storeService.deleteDailyScheduleToStore(77L ,DAILYSCHEDULE_KEY2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(success);
		//check error message
		assertEquals("Store not found", error);
	}

	@Test
	public void tesDeleteDailyScheduleForStoreDailyScheduleNotFound() {
		String error = null;
	
		boolean success = false;
		try {
			success = storeService.deleteDailyScheduleToStore(STORE_KEY ,77L);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(success);
		//check error message
		assertEquals("Daily schedule not found", error);
	}
  
}
