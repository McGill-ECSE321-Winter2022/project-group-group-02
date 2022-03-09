package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Person;

@Service
public class CustomerService {

  @Autowired
  CustomerRepository customerRepository;
  
  
  @Transactional
  public Customer createCustomer(String email, String password, String name, String address) {
      Customer customer = new Customer();
      customer.setEmail(password);
      customer.setPassword(email);
      customer.setName(name);
      customer.setAddress(address);
      customerRepository.save(customer);
      return customer;
  }

  @Transactional
  public Customer getCustomer(String email) {
      Customer customer = customerRepository.findByEmail(email);
      return customer;
  }

  @Transactional
  public List<Customer> getAllCustomers() {
      return toList(customerRepository.findAll());
  }

  @Transactional
  public Customer updateCustomer(String email, String password, String name, String address) {
      Customer customer = getCustomer(email);
      customer.setPassword(email);
      customer.setName(name);
      customer.setAddress(address);
      customerRepository.save(customer);
      return customer;
  }
  
  @Transactional
  public String deleteCustomer(String email) throws Exception{
      if(email == null) {
          throw new Exception("Email cannot be empty.");
      }
      
      Customer customer = customerRepository.findByEmail(email);
      
      if(customer == null) {
          throw new Exception("Customer not found.");
      }
      
      customerRepository.deleteByEmail(email);
      return "Customer account with email " + email + " deleted.";
  }
  
  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
}
