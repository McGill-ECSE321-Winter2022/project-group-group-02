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

    /**
     * @author Cora Cheung
     * This method creates a daily schedule 
	 * @param DailyScheduleId
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
     * @return dailySchedule
     */
	@Transactional
	public DailySchedule createDailySchedule(Long id, DayOfWeek dayOfWeek, Time startTime, Time endTime) {
         
		//check the uniqueness of id
		DailySchedule dailyschedulee = dailyScheduleRepository.findDailyScheduleById(id);

		if (dailyschedulee != null)
			throw new IllegalArgumentException("Daily Schedule with the same id already exists.");
		
		//check if all the inputs are valid
		if (dayOfWeek == null)
			throw new IllegalArgumentException("Please enter a valid day of week");

         if (endTime != null && startTime != null && endTime.before(startTime)) {
          throw new IllegalArgumentException("Daily Schedule end time cannot be before start time");
      }

		if (endTime == null) {
			throw new IllegalArgumentException("Please enter a valid end time");
		}

		if (startTime == null) {
			throw new IllegalArgumentException("Please enter a valid start time");
		}

		//create dailyschedule
		DailySchedule dailySchedule = new DailySchedule();
		dailySchedule.setDayOfWeek(dayOfWeek);
		dailySchedule.setId(id);
		dailySchedule.setStartTime(startTime);
		dailySchedule.setEndTime(endTime);
		dailyScheduleRepository.save(dailySchedule);
		return dailySchedule;
       
    }

	 /**
     * @author Cora Cheung
     * This method updates a daily schedule 
	 * @param DailyScheduleId
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
     * @return dailySchedule
     */
    @Transactional
    public DailySchedule updateDailySchedule(Long id, DayOfWeek dayOfWeek, Time startTime, Time endTime) {

		//check if the daily schedule exists
        DailySchedule dailySchedule =  dailyScheduleRepository.findDailyScheduleById(id);
        if (dailySchedule == null) {
          throw new IllegalArgumentException("No daily schedule found");
        }

		//check if all the inputs are vaild
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
            throw new IllegalArgumentException("Daily Schedule end time cannot be before start time");
        }
  
		//update dailyschedule
          dailySchedule.setDayOfWeek(dayOfWeek);
          dailySchedule.setStartTime(startTime);
          dailySchedule.setEndTime(endTime);
          dailyScheduleRepository.save(dailySchedule);
		return dailySchedule;
	}

	/**
     * @author Cora Cheung
     * This method gets a specific daily schedule given the corresponding id
	 * @param id
     * @return dailySchedule
     */
	@Transactional
	public DailySchedule getDailySchedule(Long id) {
		//return dailyschedule with the inputed id
		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);
		return dailySchedule;
	}

    /**
     * @author Cora Cheung
     * This method deletes a daily schedule 
	 * @param id
     * @return true if daily schedule has been deleted
     */
	@Transactional
	public boolean deleteDailySchedule(Long id) {

		//check null id
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be empty");
		}

		//check if the daily schedule exists
		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);

		if (dailySchedule == null)
			throw new IllegalArgumentException("Daily schedule not found");

		//delete dailyschedule
		dailyScheduleRepository.delete(dailySchedule);
		dailySchedule.delete();
		return true;
	}

	/**
     * @author Cora Cheung
     * This method gets all daily schedules
     * @return List<DailySchedule>
     */
	@Transactional
	public List<DailySchedule> getAllDailySchedules() {
		//return all dailyschedules in the system
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
