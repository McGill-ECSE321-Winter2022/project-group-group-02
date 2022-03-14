package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;

@Service
public class StoreService {

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	DailyScheduleRepository dailyScheduleRepository;

	@Transactional
	public Store createStore(Long id, String town, Double deliveryFee) {

		if (town == null || town.isEmpty()) {
			throw new IllegalArgumentException("Please enter a town");
		}

		if (deliveryFee == null) {
			throw new IllegalArgumentException("Please enter a delivery fee");
		}

		if (deliveryFee < 0) {
			throw new IllegalArgumentException("The delivery fee cannot be negative");
		}

		Store storee = storeRepository.findStoreById(id);

		if (storee != null)
			throw new IllegalArgumentException("Store with the same id already exists.");

		Store store = new Store();
		store.setTown(town);
		store.setId(id);
		store.setDeliveryFee(deliveryFee);
		storeRepository.save(store);
		return store;

	}

	@Transactional
	public Store updateStore(Long id, String town, Double deliveryFee) {

		Store store = storeRepository.findStoreById(id);

		if (store == null) {
			throw new IllegalArgumentException("No store found");
		}

		if (town == null || town.isEmpty()) {
			throw new IllegalArgumentException("Please enter a town");
		}

		if (deliveryFee == null) {
			throw new IllegalArgumentException("Please enter a delivery fee");
		}

		if (deliveryFee < 0) {
			throw new IllegalArgumentException("The delivery fee cannot be negative");
		}

		store.setTown(town);
		store.setDeliveryFee(deliveryFee);
		storeRepository.save(store);

		return store;

	}
    @javax.transaction.Transactional
    public boolean addDailyScheduleToStore(Long sid, Long did) throws IllegalArgumentException{


		boolean success = false;
		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(did);
		if (dailySchedule == null)
			throw new IllegalArgumentException("Daily schedule not found");

		Store store = storeRepository.findStoreById(sid);
		if (store == null)
			throw new IllegalArgumentException("Store not found");

		success = store.addDailySchedule(dailySchedule);

		return success;

	}


    @javax.transaction.Transactional
    public boolean deleteDailyScheduleToStore(Long sid, Long did) throws IllegalArgumentException{

		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(did);
		if (dailySchedule == null)
			throw new IllegalArgumentException("Daily schedule not found");

		Store store = storeRepository.findStoreById(sid);
		if (store == null)
			throw new IllegalArgumentException("Store not found");

		store.removeDailySchedule(dailySchedule);
		return true;

	}

	@Transactional
	public Store getStore(Long id) {
		Store store = storeRepository.findStoreById(id);
		return store;
	}

	@Transactional
	public boolean deleteStore(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be empty");
		}

		Store store = storeRepository.findStoreById(id);
		if (store == null)
			throw new IllegalArgumentException("Store not found");

		storeRepository.delete(store);
		store.delete();
		return true;

	}

	@Transactional
	public List<Store> getAllStores() {
		return toList(storeRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
}