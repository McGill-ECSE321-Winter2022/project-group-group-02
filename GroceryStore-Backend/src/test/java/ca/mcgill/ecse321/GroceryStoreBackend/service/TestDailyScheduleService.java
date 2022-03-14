package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.DailyScheduleRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;

@ExtendWith(MockitoExtension.class)
public class TestDailyScheduleService {

	@Mock
	private DailyScheduleRepository dailyScheduleRepository;
	@InjectMocks
	private DailyScheduleService dailyScheduleService;

	static final Long DAILYSCHEDULE_KEY = 321l;
	private static final Time DAILYSCHEDULE_STARTTIME = Time.valueOf("08:00:00");
	private static final Time DAILYSCHEDULE_ENDTIME = Time.valueOf("20:00:00");
	private static final DayOfWeek DAILYSCHEDULE_DAYOFWEEK = DayOfWeek.Monday;
	

	@BeforeEach
	public void setMockOutput() {

		lenient().when(dailyScheduleRepository.findDailyScheduleById(any(Long.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(DAILYSCHEDULE_KEY)) {

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
		lenient().when(dailyScheduleRepository.save(any(DailySchedule.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateDailySchedule() {
		assertEquals(0, dailyScheduleService.getAllDailySchedules().size());

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long did = 220L;
		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(did, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		assertNotNull(dailySchedule);
		assertEquals(dayOfWeek, dailySchedule.getDayOfWeek());
		assertEquals(startTime, dailySchedule.getStartTime());
		assertEquals(endTime, dailySchedule.getEndTime());
	}

	@Test
	public void testCreateDailyScheduleNullDOW() {
		String error = null;
		DayOfWeek dayOfWeek = null;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		DailySchedule dailySchedule = null;
		Long did = 222L;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(did, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		// check error
		assertEquals("Please enter a valid day of week", error);
	}

	@Test
	public void testCreateDailyScheduleNullStartTime() {
		String error = null;
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = null;
		Time endTime = Time.valueOf("20:00:00");
		DailySchedule dailySchedule = null;
		Long did = 222L;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(did, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		// check error
		assertEquals("Please enter a valid start time", error);
	}

	@Test
	public void testCreateDailyScheduleNullEndTime() {
		String error = null;
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = null;
		DailySchedule dailySchedule = null;
		Long did = 222L;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(did, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		// check error
		assertEquals("Please enter a valid end time", error);
	}
 


    @Test
	public void testCreateDailyScheduleInvalidTimePeriod() {
        String error = null;
        DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("20:00:00");
		Time endTime = Time.valueOf("08:00:00");
		DailySchedule dailySchedule = null;
		Long did = 222L;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(did,dayOfWeek, startTime, endTime);

    } catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		// check error
		assertEquals("Daily Schedule end time cannot be before event start time", error);
	}




	@Test
	public void testUpdateDailySchedule() {
		String error = null;
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		DailySchedule dailySchedule = null;
		Long did = 222L;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(did, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(dailySchedule);
		assertEquals(dayOfWeek, dailySchedule.getDayOfWeek());
		assertEquals(startTime, dailySchedule.getStartTime());
		assertEquals(endTime, dailySchedule.getEndTime());
	}

	@Test
	public void testUpdateStoreNotFound() {
		String error = null;
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.updateDailySchedule(87L, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		assertEquals("No daily schedule found", error);
	}

	@Test
	public void testUpdateStoreNullDOW() {
		String error = null;
		DayOfWeek dayOfWeek = null;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.updateDailySchedule(DAILYSCHEDULE_KEY, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		assertEquals("Please enter a valid day of week", error);
	}

	@Test
	public void testUpdateStoreNullStartTime() {
		String error = null;
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = null;
		Time endTime = Time.valueOf("21:00:00");
		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.updateDailySchedule(DAILYSCHEDULE_KEY, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		assertEquals("Please enter a valid start time", error);
	}

	@Test
	public void testUpdateStoreNullEndTime() {
		String error = null;
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = null;
		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.updateDailySchedule(DAILYSCHEDULE_KEY, dayOfWeek, startTime, endTime);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		assertEquals("Please enter a valid end time", error);
	}

	@Test
	public void testUpdateDailyScheduleInvalidTimePeriod() {
        String error = null;
        DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("20:00:00");
		Time endTime = Time.valueOf("08:0:00");
		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(DAILYSCHEDULE_KEY,dayOfWeek, startTime, endTime);

    } catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(dailySchedule);
		// check error
		assertEquals("Daily Schedule end time cannot be before start time", error);
	}


    @Test
	public void testDeleteDailySchedule() {

		String error = null;
		boolean success = false;

		try {
			success = dailyScheduleService.deleteDailySchedule(DAILYSCHEDULE_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertTrue(success);
	}

	@Test
	public void testDeletedDailyScheduleNullId() {
		String error = null;
		try {
			dailyScheduleService.deleteDailySchedule(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Id cannot be empty", error);
	}

	@Test
	public void testDeleteDailyScheduleNotFound() {
		String error = null;
		Long Id = (long) 9;

		try {
			dailyScheduleService.deleteDailySchedule(Id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Daily schedule not found", error);
	}
}
