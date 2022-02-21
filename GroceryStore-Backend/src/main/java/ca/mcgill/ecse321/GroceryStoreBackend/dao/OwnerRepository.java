package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, String> {

	Owner findByEmail(String ownerEmail);

	boolean existsByEmail(String email);
}
