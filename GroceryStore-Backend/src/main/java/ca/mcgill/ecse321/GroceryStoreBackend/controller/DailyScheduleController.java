package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Time;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	/**
     * @author Cora Cheung
     * This method gets all daily schedules
     * @return List<DailyScheduleDto>
     */
	@GetMapping(value = { "/view_dailyschedules" })
	public List<DailyScheduleDto> getAllStores() {
		return dailyScheduleService.getAllDailySchedules().stream().map(dailySchedule -> convertToDTO(dailySchedule))
				.collect(Collectors.toList());
	}

	/**
     * @author Cora Cheung
     * This method gets a specific daily schedule given the corresponding id
	 * @param id
     * @return DailyScheduleDto
     */
	@GetMapping(value = { "/view_dailyschedule/{id}" })
	public DailyScheduleDto viewStore(@RequestParam("id") String id) {

		return convertToDTO(dailyScheduleService.getDailySchedule(Long.parseLong(id)));
	}

	/**
     * @author Cora Cheung
     * This method creates a daily schedule 
	 * @param DailyScheduleId
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
     * @return DailyScheduleDto
     */
	@PostMapping(value = { "/create_dailyschedule" })
	public ResponseEntity<?> createDailySchedule(@RequestParam("dayofweek") DayOfWeek dayOfWeek,
	    @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) {

		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek,
					Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(dailySchedule), HttpStatus.CREATED);
		
	}

    /**
     * @author Cora Cheung
     * This method updates a daily schedule 
	 * @param DailyScheduleId
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
     * @return DailyScheduleDto
     */
	@PutMapping(value = { "/update_dailyschedule" })
	public ResponseEntity<?> updateDailySchedule(@RequestParam("DailyScheduleId") String DailyScheduleId,
			@RequestParam("dayofweek") DayOfWeek dayOfWeek, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) {

		DailySchedule dailySchedule = null;

		try {
			dailySchedule = dailyScheduleService.updateDailySchedule(Long.parseLong(DailyScheduleId), dayOfWeek,
					Time.valueOf(startTime), Time.valueOf(endTime));
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(dailySchedule), HttpStatus.CREATED);
	}
   
	/**
     * @author Cora Cheung
     * This method deletes a daily schedule 
	 * @param DailyScheduleId
     * @return true if daily schedule has been deleted
     */
	@DeleteMapping(value = { "/delete_dailyschedule" })
	public boolean deleteStore(@RequestParam("DailyScheduleId") String DailyScheduleId) {

		return dailyScheduleService.deleteDailySchedule(Long.parseLong(DailyScheduleId));

	}

	/**
     * @author Cora Cheung
     * Converts DailySchedule object to DailyScheduleDto
     * @return DailyScheduleDto
     */
	public static DailyScheduleDto convertToDTO(DailySchedule dailySchedule) {
		if (dailySchedule == null)
			throw new IllegalArgumentException("Daily schedule not found.");

		DailyScheduleDto dailyScheduleDto = new DailyScheduleDto(dailySchedule.getDayOfWeek(),
				dailySchedule.getStartTime(), dailySchedule.getEndTime(), dailySchedule.getId());
		return dailyScheduleDto;

	}

}
