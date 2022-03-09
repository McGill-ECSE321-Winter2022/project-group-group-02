package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

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
    
    @GetMapping(value = { "/view_stores" })
    public List<StoreDto> getAllStores() {
        return storeService.getAllStores().stream().map(store -> convertToDTO(store)).collect(Collectors.toList());
    }
    
    @GetMapping(value = {"/view_store/{id}"})
    public StoreDto viewStore(@RequestParam("id") String id) {
        
      
      return convertToDTO(storeService.getStore(Long.parseLong(id)));
    }

    
   
  @PostMapping(value = {"/create_store"})
  public ResponseEntity<?> createStore(@RequestParam("town") String town,
      @RequestParam("delivery_fee") Double deliveryFee, @RequestParam("daily_schedule") List<DailySchedule> dailySchedules) {

    Store store = null;

    try {
      store = storeService.createStore(town, deliveryFee, dailySchedules);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(store), HttpStatus.CREATED);
  }
    
    
    @PostMapping(value = {"/update_store"})
    public ResponseEntity<?> updateStore(@RequestParam("town") String town,
    @RequestParam("delivery_fee") Double deliveryFee, @RequestParam("daily_schedule") List<DailySchedule> dailySchedules, @RequestParam("id") String id) {
  
      
        Store store = null;
  
      try {
        store = storeService.updateStore(Long.parseLong(id), town, deliveryFee, dailySchedules);
      } catch (IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>(convertToDTO(store), HttpStatus.CREATED);
    }
    

    @PostMapping(value = {"/delete_store"})
    public boolean deleteStore( @RequestParam("id") String id) {
  
      
        return storeService.deleteStore(Long.parseLong(id));
    
    }
    
     
    public static StoreDto convertToDTO(Store store) {
      if (store == null)
        throw new IllegalArgumentException("Store not found.");
      
        StoreDto storeDto = new StoreDto(store.getDeliveryFee(),store.getTown(),store.getId(),store.getDailySchedules());
        return storeDto;
      
     
    }

}
