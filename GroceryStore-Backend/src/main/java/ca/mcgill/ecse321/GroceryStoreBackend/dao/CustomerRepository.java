package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;


public interface CustomerRepository extends CrudRepository<Customer, String>{

	Customer findByEmail(String email);
	boolean existsByEmail(String email);
	void deleteByEmail(String email);

}