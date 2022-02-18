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
      
    
     
      DayOfWeek dayofweek = DayOfWeek.Monday;
      Time startTime = Time.valueOf("08:00:00");
      Time endTime = Time.valueOf("20:00:00");
     
      DailySchedule dailyschedule = new DailySchedule();
      dailyschedule.setDayOfWeek(dayofweek);
      dailyschedule.setStartTime(startTime);
      dailyschedule.setEndTime(endTime);
      dailyschedule.setId((long) 568978);

      
      
      
      DailyScheduleRepository.save(dailyschedule);
      //The id is being changed after save, we have no idea how to get it
      //Except that it is increasing starting from 1
      

      dailyschedule = null;

      
      
      dailyschedule = DailyScheduleRepository.findDailyScheduleById((long) 1);
      
      assertNotNull(dailyschedule);
      assertEquals(dayofweek, dailyschedule.getDayOfWeek());
  }
  
}
