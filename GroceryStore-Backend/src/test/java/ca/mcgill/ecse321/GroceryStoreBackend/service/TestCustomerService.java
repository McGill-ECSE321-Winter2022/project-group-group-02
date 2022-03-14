package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
	@Mock
	private CustomerRepository customerDao;

	@InjectMocks
	private CustomerService service;

	private static final String CUSTOMER_KEY = "test@mail.ca";
	private static final String CUSTOMER_NAME = "Test";
	private static final String CUSTOMER_ADDRESS = "35 St Catherine O, Montreal";
	private static final String CUSTOMER_PASSWORD = "2222";

	@BeforeEach
	public void setMockOutput() {
		lenient().when(customerDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
				Customer customer = new Customer();
				customer.setEmail(CUSTOMER_KEY);
				customer.setName(CUSTOMER_NAME);
				customer.setAddress(CUSTOMER_ADDRESS);
				customer.setPassword(CUSTOMER_PASSWORD);
				return customer;
			} else {
				return null;
			}

		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateCustomer() {
		assertEquals(0, service.getAllCustomers().size());

		String email = "testcustomer@mail.ca";
		String password = "1234";
		String name = "Test Customer";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(customer);
		assertEquals(email, customer.getEmail());
		assertEquals(password, customer.getPassword());
		assertEquals(name, customer.getName());
		assertEquals(address, customer.getAddress());
	}
	
	@Test
	public void testCreateCustomerNull() {
		String error = null;
		String email = null;
		String password = "1234";
		String name = "Test Customer";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer email cannot be empty!", error);
	}

	@Test
	public void testCreateCustomerEmpty() {
		String error = null;
		String email = "";
		String password = "1234";
		String name = "Test Customer";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer email cannot be empty!", error);
	}

	@Test
	public void testCreateCustomerEmailAlreadyInUse() {
		String error = null;
		String password = "1234";
		String name = "Test Customer";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(CUSTOMER_KEY, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("This email is already in use for a customer.", error);
	}
	
	@Test
	public void testCreateCustomerEmailInvalid() {
		String error = null;
		String email = "email";
		String password = "1234";
		String name = "Test Customer";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer email must contain an @!", error);
	}

	@Test
	public void testCreateCustomerEmptyPassword() {
		String error = null;
		String email = "testcustomer@mail.ca";
		String password = "";
		String name = "Test Customer";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer password must not be empty!", error);
	}

	@Test
	public void testCreateCustomerNullPassword() {
		String error = null;
		String email = "testcustomer@mail.ca";
		String password = null;
		String name = "Test Customer";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer password must not be empty!", error);
	}

	@Test
	public void testCreateCustomerEmptyName() {
		String error = null;
		String email = "testcustomer@mail.ca";
		String password = "1234";
		String name = "";
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer name must not be empty!", error);
	}

	@Test
	public void testCreateCustomerNullName() {
		String error = null;
		String email = "testcustomer@mail.ca";
		String password = "1234";
		String name = null;
		String address = "3712 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer name must not be empty!", error);
	}

	@Test
	public void testCreateCustomerNullAddress() {
		String error = null;
		String email = "testcustomer@mail.ca";
		String password = "1234";
		String name = "Test Customer";
		String address = null;
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer address must not be empty!", error);
	}

	@Test
	public void testCreateCustomerEmptyAddress() {
		String error = null;
		String email = "testcustomer@mail.ca";
		String password = "1234";
		String name = "Test Customer";
		String address = "";
		Customer customer = null;
		try {
			customer = service.createCustomer(email, password, name, address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer address must not be empty!", error);
	}

	@Test
	public void testUpdateCustomer() {
		String error = null;
		Customer customer = null;
		String newPassword = "5678";
		String newName = "Test Customer2";
		String newAddress = "3600 McGill Street, Montréal";

		try {
			customer = service.updateCustomer(CUSTOMER_KEY, newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(customer);
		assertEquals(CUSTOMER_KEY, customer.getEmail());
		assertEquals(newAddress, customer.getAddress());
		assertEquals(newName, customer.getName());
		assertEquals(newPassword, customer.getPassword());
	}

	@Test
	public void testUpdateCustomerEmptyPassword() {
		String error = null;
		Customer customer = null;
		String newPassword = "";
		String newName = "Test Customer2";
		String newAddress = "3600 McGill Street, Montréal";

		try {
			customer = service.updateCustomer(CUSTOMER_KEY, newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer password must not be empty!", error);
	}

	@Test
	public void testUpdateCustomerNullPassword() {
		String error = null;
		String newPassword = null;
		String newName = "Test Customer2";
		String newAddress = "3600 McGill Street, Montréal";
		Customer customer = null;
		try {
			customer = service.updateCustomer(CUSTOMER_KEY, newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer password must not be empty!", error);
	}

	@Test
	public void testUpdateCustomerEmptyName() {
		String error = null;
		Customer customer = null;
		String newPassword = "5678";
		String newName = "";
		String newAddress = "3600 McGill Street, Montréal";

		try {
			customer = service.updateCustomer(CUSTOMER_KEY, newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer name must not be empty!", error);
	}

	@Test
	public void testUpdateCustomerNullName() {
		String error = null;
		Customer customer = null;
		String newPassword = "5678";
		String newName = null;
		String newAddress = "3600 McGill Street, Montréal";

		try {
			customer = service.updateCustomer(CUSTOMER_KEY, newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer name must not be empty!", error);
	}

	@Test
	public void testUpdateCustomerNullAddress() {
		String error = null;
		String newPassword = "5678";
		String newName = "Test Customer2";
		String newAddress = null;
		Customer customer = null;
		try {
			customer = service.updateCustomer(CUSTOMER_KEY, newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer address must not be empty!", error);
	}

	@Test
	public void testUpdateCustomerEmptyAddress() {
		String error = null;
		String newPassword = "5678";
		String newName = "Test Customer2";
		String newAddress = "";
		Customer customer = null;
		try {
			customer = service.updateCustomer(CUSTOMER_KEY, newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals("Customer address must not be empty!", error);
	}

	@Test
	public void testUpdateCustomerNotFound() {
		String error = null;
		String newPassword = "5678";
		String newName = "Test Customer2";
		String newAddress = "Test Address";
		try {
			service.updateCustomer("doesntexist@mail.ca", newPassword, newName, newAddress);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Customer not found.", error);
	}

	@Test
	public void testDeleteCustomer() {

		String error = null;
		boolean success = false;
		try {
			success = service.deleteCustomer(CUSTOMER_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertTrue(success);
	}

	@Test
	public void testDeleteCustomerEmpty() {
		String error = null;
		try {
			service.deleteCustomer("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Customer email cannot be empty!", error);
	}

	@Test
	public void testDeleteCustomerNull() {
		String error = null;
		try {
			service.deleteCustomer(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Customer email cannot be empty!", error);
	}

	@Test
	public void testDeleteCustomerNotFound() {
		String error = null;
		try {
			service.deleteCustomer("doesntexist@mail.ca");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Customer not found.", error);
	}
}