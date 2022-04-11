package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;

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
public class TestDailySchedulePersistence {

	@Autowired
	EntityManager entityManager;

	@Autowired
	private DailyScheduleRepository DailyScheduleRepository;

	@AfterEach
	public void clearDatabase() {
		DailyScheduleRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadDailySchedule() {

		// Creation of an UnavailableItem instance, and set its attributes to test
		// attributes
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		DailySchedule dailySchedule = new DailySchedule();
		dailySchedule.setDayOfWeek(dayOfWeek);
		dailySchedule.setStartTime(startTime);
		dailySchedule.setEndTime(endTime);

		// Save the created instance
		DailySchedule newSchedule = DailyScheduleRepository.save(dailySchedule);

		// Set the variable to null, and then try retrieving the saved instance using
		// its id
		dailySchedule = null;
		dailySchedule = DailyScheduleRepository.findDailyScheduleById(newSchedule.getId());

		// Determine whether the instance is null and if the attribute dayOfWeek
		// matches.
		assertNotNull(dailySchedule);
		assertEquals(dayOfWeek, dailySchedule.getDayOfWeek());
	}

}
