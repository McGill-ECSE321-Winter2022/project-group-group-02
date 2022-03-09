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
public class StoreService {

  @Autowired
  StoreRepository storeRepository;
  
  @Transactional
	public  Store createStore(String town, Double deliveryFee, List<DailySchedule> dailySchedules) {

          if (town == null) {
            throw new IllegalArgumentException("Please enter a town");
          }
      
          if (deliveryFee == null) {
            throw new IllegalArgumentException("Please enter a delivery fee");
          }
      
          Store store = new Store();
          store.setTown(town);
          store.setDeliveryFee(deliveryFee);
          store.setDailySchedules(dailySchedules);
          storeRepository.save(store);
		return store;
       
    }

    @Transactional
    public Store updateStore(Long id, String town, Double deliveryFee, List<DailySchedule> dailySchedules) {
  
        Store store =  storeRepository.findStoreById(id);
        if (store == null) {
          throw new IllegalArgumentException("No store found");
        }

        if (town == null) {
            throw new IllegalArgumentException("Please enter a town");
          }
      
          if (deliveryFee == null) {
            throw new IllegalArgumentException("Please enter a delivery fee");
          }
  
          store.setTown(town);
          store.setDeliveryFee(deliveryFee);
          store.setDailySchedules(dailySchedules);
          storeRepository.save(store);
		return store;
       
    }
    
    @Transactional
	public Store getStore(Long id) {
		Store store = storeRepository.findStoreById(id);
		return store;
	}

    @Transactional
    public boolean deleteStore(Long id) {
      Store store = storeRepository.findStoreById(id);
      if(store.equals(null)) throw new IllegalArgumentException ("Please enter a valid id");
      
      storeRepository.delete(store);
      store.delete();
      return true;
      
    }

    @Transactional
    public List<Store> getAllStores() {
        return toList(storeRepository.findAll());
    }
    
  
    private <T> List<T> toList(Iterable<T> iterable){
      List<T> resultList = new ArrayList<T>();
      for (T t : iterable) {
          resultList.add(t);
      }
      return resultList;

}
}