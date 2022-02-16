package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{

	Employee findEmployeebyEmail(String email);

}