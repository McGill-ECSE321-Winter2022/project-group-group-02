package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GroceryStoreBackend.service.GroceryStoreBackendService;

@CrossOrigin(origins = "*")
@RestController
public class GroceryStoreBackendController {
	
	@Autowired
	private GroceryStoreBackendService service;
	
}
