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
      double deliveryfee = 5;
      String town = "townn";
      Long id = (long) 76;
      Store store = new Store(deliveryfee, town);
      
      DailySchedule dailySchedule = new DailySchedule();
      dailySchedule.setId((long) 54);
      dailySchedule.setDayOfWeek(DayOfWeek.Monday);
      dailySchedule.setStartTime(Time.valueOf("08:00:00"));
      dailySchedule.setEndTime(Time.valueOf("20:00:00"));
      store.setId(id);
      
      dailyScheduleRepository.save(dailySchedule);


      ArrayList<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
      dailySchedules.add(dailySchedule);
      store.setDailySchedules(dailySchedules);

      storeRepository.save(store);



      store = null;

      store = storeRepository.findStoreById(id);
      assertNotNull(store);
      assertEquals(deliveryfee, store.getDeliveryFee());
  }
  
}
