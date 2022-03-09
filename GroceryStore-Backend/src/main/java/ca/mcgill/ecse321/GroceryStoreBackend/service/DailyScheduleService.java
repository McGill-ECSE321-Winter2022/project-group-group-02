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
	public  DailySchedule createDailySchedule(DayOfWeek dayOfWeek, Time startTime, Time endTime) {

          if (dayOfWeek == null) {
            throw new IllegalArgumentException("Please enter a valid day of week");
          }
      
          if (startTime == null) {
            throw new IllegalArgumentException("Please enter a valid start time");
          }
      
          if (endTime == null) {
            throw new IllegalArgumentException("Please enter a valid end time");
          }

          DailySchedule dailySchedule = new DailySchedule();
          dailySchedule.setDayOfWeek(dayOfWeek);
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
            throw new IllegalArgumentException("Please enter a valid day of weekk");
          }
      
          if (startTime == null) {
            throw new IllegalArgumentException("Please enter a valid start time");
          }
      
          if (endTime == null) {
            throw new IllegalArgumentException("Please enter a valid end time");
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
    public void deleteDailySchedule(Long id) {
    DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(id);
      if(dailySchedule.equals(null)) throw new IllegalArgumentException ("Please enter a valid id");
      
      dailyScheduleRepository.delete(dailySchedule);
      dailySchedule.delete();
      
    }

    @Transactional
    public List<DailySchedule> getAllDailySchedules() {
        return toList(dailyScheduleRepository.findAll());
    }
    
  
    private <T> List<T> toList(Iterable<T> iterable){
      List<T> resultList = new ArrayList<T>();
      for (T t : iterable) {
          resultList.add(t);
      }
      return resultList;

}
}
  