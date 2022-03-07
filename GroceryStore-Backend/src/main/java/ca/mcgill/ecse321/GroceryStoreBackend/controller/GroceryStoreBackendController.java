package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GroceryStoreBackend.service.GroceryStoreBackendService;

@CrossOrigin(origins = "*")
@RestController
public class GroceryStoreBackendController {
	
	@Autowired
	private GroceryStoreBackendService service;
	
	
	@GetMapping(value = { "/persons", "/persons/" })
	public List<CustomerDto> getAllPersons() {
		return service.getAllCustomers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
}
