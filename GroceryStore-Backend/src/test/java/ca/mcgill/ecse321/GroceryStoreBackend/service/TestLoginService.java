package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.DailyScheduleRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OwnerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Person;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;

@ExtendWith(MockitoExtension.class)
public class TestLoginService {
	@Mock
	private CustomerRepository customerDao;

	@Mock
	private EmployeeRepository employeeDao;

	@Mock
	private OwnerRepository ownerDao;
	
	@Mock
	private DailyScheduleRepository dailyScheduleDao;

	@InjectMocks
	private LoginService service;

	@InjectMocks
	private DailyScheduleService dailyScheduleService;
	
	private static final String CUSTOMER_KEY = "testcustomer@mail.ca";
	private static final String CUSTOMER_NAME = "Test";
	private static final String CUSTOMER_ADDRESS = "35 St Catherine O, Montreal";
	private static final String CUSTOMER_PASSWORD = "2222";
	
	private static final String EMPLOYEE_KEY = "testemployee@mail.ca";
	private static final String EMPLOYEE_NAME = "Test";
	private static final Double EMPLOYEE_SALARY = 1250.0;
	private static final String EMPLOYEE_PASSWORD = "1234";

	private static final String OWNER_KEY = "testowner@mail.ca";
	private static final String OWNER_NAME = "Test";
	private static final String OWNER_PASSWORD = "1234";
	
	static final long DAILYSCHEDULE_KEY = (long) 321;
	private static final Time DAILYSCHEDULE_STARTTIME = Time.valueOf("08:00:00");
	private static final Time DAILYSCHEDULE_ENDTIME = Time.valueOf("20:00:00");
	private static final DayOfWeek DAILYSCHEDULE_DAYOFWEEK = DayOfWeek.Monday;

	static final long DAILYSCHEDULE_KEY2 = (long) 123;
	private static final Time DAILYSCHEDULE_STARTTIME2 = Time.valueOf("07:00:00");
	private static final Time DAILYSCHEDULE_ENDTIME2 = Time.valueOf("21:00:00");
	private static final DayOfWeek DAILYSCHEDULE_DAYOFWEEK2 = DayOfWeek.Tuesday;
	
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
		lenient().when(customerDao.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
				return true;
			} else {
				return false;
			}

		});
		
		lenient().when(employeeDao.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(EMPLOYEE_KEY)) {
				return true;
			} else {
				return false;
			}

		});
		lenient().when(ownerDao.existsByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(OWNER_KEY)) {
				return true;
			} else {
				return false;
			}
		});
		lenient().when(employeeDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(EMPLOYEE_KEY)) {

				DailySchedule dailySchedule = dailyScheduleDao.findDailyScheduleById(DAILYSCHEDULE_KEY);
				List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();

				dailySchedules.add(dailySchedule);
				Employee employee = new Employee();
				employee.setEmail(EMPLOYEE_KEY);
				employee.setName(EMPLOYEE_NAME);
				employee.setSalary(EMPLOYEE_SALARY);
				employee.setPassword(EMPLOYEE_PASSWORD);
				employee.setDailySchedules(dailySchedules);

				return employee;
			} else {
				return null;
			}

		});

		lenient().when(dailyScheduleDao.findDailyScheduleById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(DAILYSCHEDULE_KEY)) {

				DailySchedule dailySchedule = new DailySchedule();
				dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK);
				dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME);
				dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME);
				dailySchedule.setId(DAILYSCHEDULE_KEY);

				return dailySchedule;
			} else if (invocation.getArgument(0).equals(DAILYSCHEDULE_KEY2)) {
				DailySchedule dailySchedule = new DailySchedule();
				dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK2);
				dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME2);
				dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME2);
				dailySchedule.setId(DAILYSCHEDULE_KEY2);

				return dailySchedule;
			} else {
				return null;
			}
		});

		lenient().when(ownerDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(OWNER_KEY)) {
				Owner owner = new Owner();
				owner.setName(OWNER_NAME);
				owner.setPassword(OWNER_PASSWORD);
				owner.setEmail(OWNER_KEY);
				return owner;
			} else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(employeeDao.save(any(Employee.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(dailyScheduleDao.save(any(DailySchedule.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(ownerDao.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testLoginCustomer() {
		Customer customer= null;
		try {
			customer = (Customer) service.login(CUSTOMER_KEY, CUSTOMER_PASSWORD);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// Check the arguments of the created customer match the ones we passed as argument.
		assertNotNull(customer);
		assertEquals(CUSTOMER_KEY, customer.getEmail());
		assertEquals(CUSTOMER_PASSWORD, customer.getPassword());
		assertEquals(CUSTOMER_NAME, customer.getName());
		assertEquals(CUSTOMER_ADDRESS, customer.getAddress());
	}
	
	@Test
	public void testLoginEmployee() {
		Employee employee= null;
		try {
			employee = (Employee) service.login(EMPLOYEE_KEY, EMPLOYEE_PASSWORD);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// Check the arguments of the created customer match the ones we passed as argument.
		assertNotNull(employee);
		assertEquals(EMPLOYEE_KEY, employee.getEmail());
		assertEquals(EMPLOYEE_PASSWORD, employee.getPassword());
		assertEquals(EMPLOYEE_NAME, employee.getName());
		assertEquals(EMPLOYEE_SALARY, employee.getSalary());
	}
		
	@Test
	public void testLoginOwner() {
		Owner owner= null;
		try {
			owner = (Owner) service.login(OWNER_KEY, OWNER_PASSWORD);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		// Check the arguments of the created customer match the ones we passed as argument.
		assertNotNull(owner);
		assertEquals(OWNER_KEY, owner.getEmail());
		assertEquals(OWNER_PASSWORD, owner.getPassword());
		assertEquals(OWNER_NAME, owner.getName());
	}
	
	@Test
	public void testLoginNotFound() {
		Person test= null;
		String error = null;
		try {
			test = (Owner) service.login("doesnotexist", "1234");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		// Check the person instance is null
		assertNull(test);
		assertEquals(error, "Invalid email");
	}
	
	@Test
	public void testLoginWrongPassword() {
		Person test= null;
		String error = null;
		try {
			test = (Owner) service.login(CUSTOMER_KEY, "wrong");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		// Check the person instance is null
		assertNull(test);
		assertEquals("Incorrect password", error);

	}
	
	
}
