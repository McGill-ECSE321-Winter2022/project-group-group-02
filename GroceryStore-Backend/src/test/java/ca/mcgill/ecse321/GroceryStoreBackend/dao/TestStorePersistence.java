package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestStorePersistence {

	@Autowired
	EntityManager entityManager;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private DailyScheduleRepository dailyScheduleRepository;

	@AfterEach
	public void clearDatabase() {
		storeRepository.deleteAll();
		dailyScheduleRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadStore() {
		
		// Creation of a Store instance with test attributes
		double deliveryFee = 5;
		String town = "townn";
		Long id = (long) 76;
		Store store = new Store(deliveryFee, town);
		
		// Creation of a DailySchedule instance with test attributes
		DailySchedule dailySchedule = new DailySchedule();
		dailySchedule.setId((long) 54);
		dailySchedule.setDayOfWeek(DayOfWeek.Monday);
		dailySchedule.setStartTime(Time.valueOf("08:00:00"));
		dailySchedule.setEndTime(Time.valueOf("20:00:00"));
		store.setId(id);

		// Save the created DailySchedule instance
		dailyScheduleRepository.save(dailySchedule);

		// Set the dailySchedules attribute of store, because of the one-to-many association
		ArrayList<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
		dailySchedules.add(dailySchedule);
		store.setDailySchedules(dailySchedules);

		// Save the created Store instance
		storeRepository.save(store);
		
		// Set the variable to null, and then try retrieving the saved instance using its id
		store = null;
		store = storeRepository.findStoreById(id);
		
		// Determine whether the instance is null and if the attribute deliveryFee matches.
		assertNotNull(store);
		assertEquals(deliveryFee, store.getDeliveryFee());
	}

}
