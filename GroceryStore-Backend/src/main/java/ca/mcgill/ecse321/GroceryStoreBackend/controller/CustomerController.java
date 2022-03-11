package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GroceryStoreBackend.dto.CustomerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.service.CustomerService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {

  
  @Autowired
  private CustomerService service;
  
  
  @GetMapping(value = { "/view_customers", "/view_customers/" })
  public List<CustomerDto> getAllCustomers() {
      return service.getAllCustomers().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
  }
  
  @PostMapping(value = { "/create_customer", "/create_customer/" })
  public CustomerDto createCustomer(@RequestParam("email") String email, @RequestParam String password, @RequestParam String name, @RequestParam String address) throws IllegalArgumentException {
      Customer customer = service.createCustomer(email, password, name, address);
      return convertToDto(customer);
  }
  
  @PostMapping(value = { "/update_customer/{email}", "/update_customer/{email}/" })
  public CustomerDto updateCustomer(@PathVariable("email") String email, @RequestParam String password, @RequestParam String name, @RequestParam String address) throws IllegalArgumentException {
      Customer customer = service.updateCustomer(email, password, name, address);
      return convertToDto(customer);
  }
  
  @PostMapping(value = { "/delete_customer/{email}", "/delete_customer/{email}/" })
  public void deleteCustomer(@PathVariable("email") String email) throws Exception {
      try {
          service.deleteCustomer(email);
      } catch (Exception e) {
          String error = e.getMessage();
      }
  }
  
  public static CustomerDto convertToDto(Customer c) {
      if (c == null) {
          throw new IllegalArgumentException("There is no such Customer!");
      }
      CustomerDto customerDto = new CustomerDto(c.getEmail(), c.getPassword(), c.getName(), c.getAddress());
      return customerDto;
  }
}
