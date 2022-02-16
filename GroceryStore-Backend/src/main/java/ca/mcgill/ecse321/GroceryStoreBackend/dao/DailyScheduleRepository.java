package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;

public interface DailyScheduleRepository extends CrudRepository<DailySchedule, Integer>{

    DailySchedule findDailyScheduleById(Integer id);
  
}