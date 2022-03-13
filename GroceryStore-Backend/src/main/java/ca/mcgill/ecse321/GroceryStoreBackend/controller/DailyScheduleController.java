package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Time;
import java.time.LocalTime;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.service.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;


@CrossOrigin(origins = "*")
@RestController
public class DailyScheduleController {

    @Autowired
    private DailyScheduleService dailyScheduleService;
    
    @GetMapping(value = { "/view_dailyschedules" })
    public List<DailyScheduleDto> getAllStores() {
        return dailyScheduleService.getAllDailySchedules().stream().map(dailySchedule -> convertToDTO(dailySchedule)).collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/view_dailyschedules/{id}"})
    public DailyScheduleDto viewStore(@RequestParam("id") String id) {
        
      
      return convertToDTO(dailyScheduleService.getDailySchedule(Long.parseLong(id)));
    }

    
   
  @PostMapping(value = {"/create_dailyschedule"})
  public ResponseEntity<?> createDailySchedule( @RequestParam("DailyScheduleId") Long DailyScheduleId, @RequestParam("Dayofweek") DayOfWeek dayOfWeek,
                                                @RequestParam("startTime") String startTime,
                                                @RequestParam("endTime") String endTime
                                                ) {


        DailySchedule dailySchedule = null;

    try {
        dailySchedule = dailyScheduleService.createDailySchedule(DailyScheduleId, dayOfWeek,
               Time.valueOf(startTime), Time.valueOf(endTime)
                 );
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(dailySchedule), HttpStatus.CREATED);
  }
    
    
    @PostMapping(value = {"/update_dailyschedule"})
    public ResponseEntity<?> updateDailySchedule (@RequestParam("DailyScheduleId") String DailyScheduleId, @RequestParam("Dayofweek") DayOfWeek dayOfWeek,
                                                  @RequestParam("startTime") String startTime,
                                                  @RequestParam("endTime") String endTime
    ) {
  
      
        DailySchedule dailySchedule = null;
  
      try {
        dailySchedule = dailyScheduleService.updateDailySchedule(Long.parseLong(DailyScheduleId), dayOfWeek, Time.valueOf(startTime), Time.valueOf(endTime));
      } catch (IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(convertToDTO(dailySchedule), HttpStatus.CREATED);
    }
    

    @PostMapping(value = {"/delete_dailyschedule"})
    public boolean deleteStore( @RequestParam("DailyScheduleId") String DailyScheduleId) {
  
        return dailyScheduleService.deleteDailySchedule(Long.parseLong(DailyScheduleId));
    
    }
    
     
    public static DailyScheduleDto convertToDTO(DailySchedule dailySchedule) {
      if (dailySchedule == null)
        throw new IllegalArgumentException("Daily schedule not found.");
      
        DailyScheduleDto dailyScheduleDto = new DailyScheduleDto(dailySchedule.getDayOfWeek(),dailySchedule.getStartTime(),dailySchedule.getEndTime());
        return dailyScheduleDto;
      
     
    }

}

