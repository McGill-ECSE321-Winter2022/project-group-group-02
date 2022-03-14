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

	/**
	 * @author Cora Cheung
	 *         This method creates a store
	 * @param id
	 * @param town
	 * @param deliveryFee
	 * @return store
	 */
	@Transactional
	public Store createStore(Long id, String town, Double deliveryFee) {

		// check all inputs are valid
		if (town == null || town.isEmpty()) {
			throw new IllegalArgumentException("Please enter a town");
		}

		if (deliveryFee == null) {
			throw new IllegalArgumentException("Please enter a delivery fee");
		}

		if (deliveryFee < 0) {
			throw new IllegalArgumentException("The delivery fee cannot be negative");
		}

		// check the uniqueness of id
		Store storee = storeRepository.findStoreById(id);

		if (storee != null)
			throw new IllegalArgumentException("Store with the same id already exists.");

		// create store
		Store store = new Store();
		store.setTown(town);
		store.setId(id);
		store.setDeliveryFee(deliveryFee);
		storeRepository.save(store);
		return store;

	}

	/**
	 * @author Cora Cheung
	 *         This method updates a store
	 * @param id
	 * @param town
	 * @param deliveryFee
	 * @return store
	 */
	@Transactional
	public Store updateStore(Long id, String town, Double deliveryFee) {

		// check if the store exists
		Store store = storeRepository.findStoreById(id);

		if (store == null) {
			throw new IllegalArgumentException("No store found");
		}

		// check all inputs are valid
		if (town == null || town.isEmpty()) {
			throw new IllegalArgumentException("Please enter a town");
		}

		if (deliveryFee == null) {
			throw new IllegalArgumentException("Please enter a delivery fee");
		}

		if (deliveryFee < 0) {
			throw new IllegalArgumentException("The delivery fee cannot be negative");
		}

		// create store
		store.setTown(town);
		store.setDeliveryFee(deliveryFee);
		storeRepository.save(store);

		return store;

	}

	/**
	 * @author Cora Cheung
	 *         This method adds a daily schedule to a store
	 * @param sid
	 * @param did
	 * @return true if daily schedule has been added
	 */
	@javax.transaction.Transactional
	public boolean addDailyScheduleToStore(Long sid, Long did) throws IllegalArgumentException {

		boolean success = false;
		// search for the dailyschedule
		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(did);
		if (dailySchedule == null)
			throw new IllegalArgumentException("Daily schedule not found");

		// search for the store
		Store store = storeRepository.findStoreById(sid);
		if (store == null)
			throw new IllegalArgumentException("Store not found");

		success = store.addDailySchedule(dailySchedule);
		// return true if daily schedule has been added
		return success;

	}

	/**
	 * @author Cora Cheung
	 *         This method adds a daily schedule to a store
	 * @param sid
	 * @param did
	 * @return true if daily schedule has been deleted
	 */
	@javax.transaction.Transactional
	public boolean deleteDailyScheduleToStore(Long sid, Long did) throws IllegalArgumentException {

		// search for the dailyschedule
		DailySchedule dailySchedule = dailyScheduleRepository.findDailyScheduleById(did);
		if (dailySchedule == null)
			throw new IllegalArgumentException("Daily schedule not found");

		// search for the store
		Store store = storeRepository.findStoreById(sid);
		if (store == null)
			throw new IllegalArgumentException("Store not found");

		// return true if daily schedule has been deleted
		store.removeDailySchedule(dailySchedule);
		return true;

	}

	/**
	 * @author Cora Cheung
	 *         This method gets a store given the id
	 * @return store
	 */
	@Transactional
	public Store getStore(Long id) {
		Store store = storeRepository.findStoreById(id);
		return store;
	}

	/**
	 * @author Cora Cheung
	 *         This method deletes a store
	 * @param id
	 * @return true if store has been deleted
	 */
	@Transactional
	public boolean deleteStore(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be empty");
		}

		//search for the store
		Store store = storeRepository.findStoreById(id);
		if (store == null)
			throw new IllegalArgumentException("Store not found");

		//return true if the store is deleted
		storeRepository.delete(store);
		store.delete();
		return true;

	}

	/**
	 * @author Cora Cheung
	 *         This method gets all stores
	 * @return List<Store>
	 */
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