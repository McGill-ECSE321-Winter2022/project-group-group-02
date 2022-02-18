package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;

public interface DailyScheduleRepository extends CrudRepository<DailySchedule, Long>{

    DailySchedule findDailyScheduleById(Long id);
    
}