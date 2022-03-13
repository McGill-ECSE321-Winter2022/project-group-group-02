package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;

@Service
public class DailyScheduleService {

	@Autowired
	DailyScheduleRepository dailyScheduleRepository;

	@Transactional
	public DailySchedule createDailySchedule(Long id, DayOfWeek dayOfWeek, Time startTime, Time endTime) {

		DailySchedule dailyschedulee = dailyScheduleRepository.findDailyScheduleById(id);

		if (dailyschedulee != null)
			throw new IllegalArgumentException("Daily Schedule with the same id already exists.");

		if (dayOfWeek == null)
			throw new IllegalArgumentException("Please enter a valid day of week");

         if (endTime != null && startTime != null && endTime.before(startTime)) {
          throw new IllegalArgumentException("Event end time cannot be before event start time");
      }

      // Time startTime = Time.valueOf("08:00:00");
     // Time endTime = Time.valueOf("08:00:00");

		if (endTime == null) {
			throw new IllegalArgumentException("Please enter a valid end time");
		}

		// Time startTime = Time.valueOf("08:00:00");
		// Time endTime = Time.valueOf("08:00:00");

		DailySchedule dailySchedule = new DailySchedule();
		dailySchedule.setDayOfWeek(dayOfWeek);
		dailySchedule.setId(id);
		dailySchedule.setStartTime(startTime);
		dailySchedule.setEndTime(endTime);
		dailyScheduleRepository.save(dailySchedule);
		return dailySchedule;
       
    }

    @Transactional
    public DailySchedule updateDailySchedule(Long id, DayOfWeek dayOfWeek, Time startTime, Time endTime) {


  
        DailySchedule dailySchedule =  dailyScheduleRepository.findDailyScheduleById(id);
        if (dailySchedule == null) {
          throw new IllegalArgumentException("No daily schedule found");
        }

        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Please enter a valid day of week");
          }
      
          if (startTime == null) {
            throw new IllegalArgumentException("Please enter a valid start time");
          }
      
          if (endTime == null) {
            throw new IllegalArgumentException("Please enter a valid end time");
          }

          if (endTime != null && startTime != null && endTime.before(startTime)) {
            throw new IllegalArgumentException("Event end time cannot be before event start time");
        }
  
          dailySchedule.setDayOfWeek(dayOfWeek);
          dailySchedule.setStartTime(startTime);
          dailySchedule.setEndTime(endTime);
          dailyScheduleRepository.save(dailySchedule);
		return dailySchedule;
	}

	@Transactional
	public DailySchedule getDailySchedule(Long id) {
		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);
		return dailySchedule;
	}

	@Transactional
	public boolean deleteDailySchedule(Long id) {

		if (id == null) {
			throw new IllegalArgumentException("Id cannot be empty");
		}

		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);

		if (dailySchedule == null)
			throw new IllegalArgumentException("Daily schedule not found");

		dailyScheduleRepository.delete(dailySchedule);
		dailySchedule.delete();
		return true;
	}

	@Transactional
	public List<DailySchedule> getAllDailySchedules() {
		return toList(dailyScheduleRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
}
