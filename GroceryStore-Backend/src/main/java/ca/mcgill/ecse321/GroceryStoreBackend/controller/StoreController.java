package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.StoreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.service.*;

@CrossOrigin(origins = "*")
@RestController
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	StoreRepository storeRepository;

	@GetMapping(value = { "/view_stores" })
	public List<StoreDto> getAllStores() {
		return storeService.getAllStores().stream().map(store -> convertToDTO(store)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/view_store/{id}" })
	public StoreDto viewStore(@RequestParam("id") String id) {

		return convertToDTO(storeService.getStore(Long.parseLong(id)));
	}

	@PostMapping(value = { "/create_store" })
	public ResponseEntity<?> createStore(@RequestParam("StoreId") Long StoreId, @RequestParam("town") String town,
			@RequestParam("delivery_fee") Double deliveryFee) {

		Store store = null;

		try {
			store = storeService.createStore(StoreId, town, deliveryFee);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(store), HttpStatus.CREATED);
	}

	@PostMapping(value = { "/update_store" })
	public ResponseEntity<?> updateStore(@RequestParam("town") String town,
			@RequestParam("delivery_fee") Double deliveryFee, @RequestParam("StoreId") String StoreId) {

		Store store = null;

		try {
			store = storeService.updateStore(Long.parseLong(StoreId), town, deliveryFee);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(store), HttpStatus.CREATED);
	}

	@PostMapping(value = { "/delete_store" })
	public boolean deleteStore(@RequestParam("StoreId") String StoreId) {

		return storeService.deleteStore(Long.parseLong(StoreId));

	}

	@PostMapping(value = { "/add_dailyschedule_for_store" })
	public ResponseEntity<?> createStore(@RequestParam("StoreId") Long StoreId,
			@RequestParam("DailyScheduleId") Long DailyScheduleId) {
		Store store = storeRepository.findStoreById(StoreId);
		try {
			storeService.addDailyScheduleToOrder(StoreId, DailyScheduleId);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(store), HttpStatus.CREATED);
	}

	@PostMapping(value = { "/delete_dailyschedule_for_store" })
	public boolean updateStore(@RequestParam("StoreId") Long StoreId,
			@RequestParam("DailyScheduleId") Long DailyScheduleId) {

		return storeService.deleteDailySchedulesToOrder(StoreId, DailyScheduleId);
	}

	public static StoreDto convertToDTO(Store store) {
		if (store == null)
			throw new IllegalArgumentException("Store not found.");

		StoreDto storeDto = new StoreDto(store.getDeliveryFee(), store.getTown(), store.getId(),
				store.getDailySchedules());
		return storeDto;

	}

}
