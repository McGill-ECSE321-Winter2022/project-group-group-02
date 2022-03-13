package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class OwnerController {

	@Autowired
	private OwnerService ownerService;

	@GetMapping(value = { "/view_owner/", "/view_owner" })
	public OwnerDto viewOwner() {
		return convertToDTO(ownerService.getOwner());
	}

	@PostMapping(value = { "/create_owner/", "/create_owner" })
	public ResponseEntity<?> createOwner(@RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam("email") String email) {

		Owner owner = null;

		try {
			owner = ownerService.createOwner(email, password, name);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(owner), HttpStatus.CREATED);
	}

	@PostMapping(value = { "/update_owner_password/", "/update_owner_password" })
	public OwnerDto updateOwnerPassword(@RequestParam("newPassword") String newPassword) {
		Owner owner = ownerService.updateOwnerPassword(newPassword);
		return convertToDTO(owner);
	}

	@PostMapping(value = { "/update_owner_name/", "/update_owner_name" })
	public OwnerDto updateOwnerName(@RequestParam("newName") String newName) {
		Owner owner = ownerService.updateOwnerName(newName);
		return convertToDTO(owner);
	}

	@PostMapping(value = { "/delete_owner", "/delete_owner/" })
	public void deleteCustomer() throws Exception {
		try {
			ownerService.deleteOwner();
		} catch (Exception e) {
			String error = e.getMessage();
		}
	}

	public static OwnerDto convertToDTO(Owner owner) {
		if (owner == null)
			return null;
		return new OwnerDto(owner.getEmail(), owner.getPassword(), owner.getName());
	}

}
