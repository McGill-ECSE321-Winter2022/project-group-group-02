package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    static final Long STORE_KEY=12l;
    private static final Double STORE_DELIVERY_FEE=20d;

    static final Long DAILYSCHEDULE_KEY = 321l;
	private static final Time DAILYSCHEDULE_STARTTIME = Time.valueOf("08:00:00");
	private static final Time DAILYSCHEDULE_ENDTIME = Time.valueOf("20:00:00");
	private static final DayOfWeek DAILYSCHEDULE_DAYOFWEEK = DayOfWeek.Monday;

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

      lenient().when(dailyScheduleDao.findById((long) anyInt())).thenAnswer((InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(DAILYSCHEDULE_KEY)) {
            
            DailySchedule dailySchedule = new DailySchedule();
            dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK);
            dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME);
            dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME);
            dailySchedule.setId(DAILYSCHEDULE_KEY);

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
	  public void testCreateStore() {
		assertEquals(0, storeService.getAllStores().size()); 
	    String town = "FakeTown";
	    Double delifee = 45d;
        Store store = null;
		Long sid = 222L;

        DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		DailySchedule dailySchedule = null;
		Long did = 202L;

      try {
        dailySchedule = dailyScheduleService.createDailySchedule(did,dayOfWeek, startTime, endTime);
        List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
        dailySchedules.add(dailySchedule);
        store = storeService.createStore(sid,town,delifee, dailySchedules);
    } catch (IllegalArgumentException e) {
        // Check that no error occurred
        fail();
    }

    assertNotNull(store);
    assertEquals(town, store.getTown());
    assertEquals(delifee, store.getDeliveryFee());
    
    assertNotNull(store.getDailySchedules().get(0));
    assertEquals(dayOfWeek, store.getDailySchedules().get(0).getDayOfWeek());
    assertEquals(startTime, store.getDailySchedules().get(0).getStartTime());
    assertEquals(endTime, store.getDailySchedules().get(0).getEndTime());
}
   
@Test
	public void testCreateStorerNullTown() {
        String error = null;
        String town = null;
	    Double delifee = 45d;
        Store store = null;
		Long sid = 222L;

		try {
			store = storeService.createStore(sid,town,delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(store);
		// check error
		assertEquals("Please enter a town", error);
	}

    @Test
	public void testCreateStorerEmptyTown() {
        String error = null;
        String town = "";
	    Double delifee = 45d;
        Store store = null;
		Long sid = 222L;

		try {
			store = storeService.createStore(sid,town,delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

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
			store = storeService.createStore(sid,town,delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

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
			store = storeService.createStore(sid,town,delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(store);
		// check error
		assertEquals("The delivery fee cannot be negative", error);
	}

    @Test
	public void testUpdateStore() {
		String error = null;
		String town = "FakeTown";
	    Double delifee = 45d;
        Store store = null;

        DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long did = 000L;
		DailySchedule dailySchedule = null;

		try {
            dailySchedule = dailyScheduleService.createDailySchedule(did, dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			store = storeService.updateStore(STORE_KEY, town, delifee, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(store);
		assertEquals(STORE_KEY, store.getId());
		assertEquals(town, store.getTown());
		assertEquals(delifee, store.getDeliveryFee());

        assertNotNull(store.getDailySchedules().get(0));
		assertEquals(dayOfWeek, store.getDailySchedules().get(0).getDayOfWeek());
		assertEquals(startTime, store.getDailySchedules().get(0).getStartTime());
		assertEquals(endTime, store.getDailySchedules().get(0).getEndTime());
	}

    @Test
	public void testUpdateStoreNotFound() {
		String error = null;
		String town = "FakeTown";
	    Double delifee = 45d;
		Long sid = 000L;
        Store store = null;

        DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long did = 000L;
		DailySchedule dailySchedule = null;
		
		
		try {
            dailySchedule = dailyScheduleService.createDailySchedule(did, dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			store = storeService.updateStore(sid, town, delifee, newDailySchedules);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
        
        assertNull(store);
		assertEquals("No store found", error);
	}

    @Test
	public void testUpdateStoreNullTown() {
		String error = null;
		String town = null;
	    Double delifee = 45d;
        Store store = null;
		
		try {
			store = storeService.updateStore(STORE_KEY, town, delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
        
        assertNull(store);
		assertEquals("Please enter a town", error);
	}

    @Test
	public void testUpdateStoreEmptyTown() {
		String error = null;
		String town = "";
	    Double delifee = 45d;
        Store store = null;
		
		try {
			store = storeService.updateStore(STORE_KEY, town, delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
        
        assertNull(store);
		assertEquals("Please enter a town", error);
	}
  
    @Test
	public void testUpdateStoreNullDeliveryFee() {
		String error = null;
		String town = "FakeTown";
	    Double delifee = null;
        Store store = null;
		
		try {
			store = storeService.updateStore(STORE_KEY, town, delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
        
        assertNull(store);
		assertEquals("Please enter a delivery fee", error);
	}

    @Test
	public void testUpdateStoreNegativeDeliveryFee() {
		String error = null;
		String town = "FakeTown";
	    Double delifee = -21D;
        Store store = null;
		
		try {
			store = storeService.updateStore(STORE_KEY, town, delifee, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
        
        assertNull(store);
		assertEquals("The delivery fee cannot be negative", error);
	}

    @Test
	public void testDeleteStore() {

		String error = null;
		boolean success = false;

		try {
			success = storeService.deleteStore(STORE_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

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

		assertEquals("Store not found", error);
	}
}