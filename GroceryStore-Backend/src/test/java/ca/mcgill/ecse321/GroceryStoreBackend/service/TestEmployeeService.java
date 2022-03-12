package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.DailyScheduleRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.EmployeeRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Employee;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;

@ExtendWith(MockitoExtension.class)
public class TestEmployeeService {
	@Mock
	private EmployeeRepository employeeDao;

	@Mock
	private DailyScheduleRepository dailyScheduleDao;
	
	@InjectMocks
	private EmployeeService service;
	
	@InjectMocks
	private DailyScheduleService dailyScheduleService;

	static final String EMPLOYEE_KEY = "test@mail.ca";
	private static final String EMPLOYEE_NAME = "Test";
	private static final Double EMPLOYEE_SALARY = 1500.0;
	private static final String EMPLOYEE_PASSWORD = "2222";
	

	static final long DAILYSCHEDULE_KEY = (long) 321;
	private static final Time DAILYSCHEDULE_STARTTIME = Time.valueOf("08:00:00");
	private static final Time DAILYSCHEDULE_ENDTIME = Time.valueOf("20:00:00");
	private static final DayOfWeek DAILYSCHEDULE_DAYOFWEEK = DayOfWeek.Monday;

	@BeforeEach
	public void setMockOutput() {
		lenient().when(employeeDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(EMPLOYEE_KEY)) {
				
				DailySchedule dailySchedule = new DailySchedule();
				dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK);
				dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME);
				dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME);
				dailySchedule.setId(DAILYSCHEDULE_KEY);
				List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
				
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
		
		lenient().when(dailyScheduleDao.findById((long) anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(DAILYSCHEDULE_KEY)) {
				
				DailySchedule dailySchedule = new DailySchedule();
				dailySchedule.setDayOfWeek(DAILYSCHEDULE_DAYOFWEEK);
				dailySchedule.setStartTime(DAILYSCHEDULE_STARTTIME);
				dailySchedule.setEndTime(DAILYSCHEDULE_ENDTIME);
				dailySchedule.setId(DAILYSCHEDULE_KEY);

				return dailySchedule;
			} else {
				return null;
			}
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(employeeDao.save(any(Employee.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(dailyScheduleDao.save(any(DailySchedule.class))).thenAnswer(returnParameterAsAnswer);

	}

	@Test
	public void testCreateEmployee() {
		assertEquals(0, service.getAllEmployees().size());

		String email = "testemployee@mail.ca";
		String password = "1234";
		String name = "Test Employee";
		Double salary = 1400.0;
		Employee employee = null;
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(employee);
		assertEquals(email, employee.getEmail());
		assertEquals(password, employee.getPassword());
		assertEquals(name, employee.getName());
		assertEquals(salary, employee.getSalary());
		
		assertNotNull(employee.getDailySchedules().get(0));
		assertEquals(dayOfWeek, employee.getDailySchedules().get(0).getDayOfWeek());
		assertEquals(startTime, employee.getDailySchedules().get(0).getStartTime());
		assertEquals(endTime, employee.getDailySchedules().get(0).getEndTime());
	}

	@Test
	public void testCreateEmployeeNull() {
		String error = null;
		String email = null;
		String password = "1234";
		String name = "Test Employee";
		Double salary = 1400.0;
		Employee employee = null;

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee email cannot be empty!", error);
	}

	@Test
	public void testCreateEmployeeEmpty() {
		String error = null;
		String email = "";
		String password = "1234";
		String name = "Test Employee";
		Double salary = 1400.0;
		Employee employee = null;

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee email cannot be empty!", error);
	}
	
	@Test
	public void testCreateEmployeeEmailAlreadyInUse() {
		String error = null;
		String password = "1234";
		String name = "Test Employee";
		Double salary = 1400.0;
		Employee employee = null;

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(EMPLOYEE_KEY, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("An employee with this email already exists.", error);
	}
	
	@Test
	public void testCreateEmployeeEmailInvalid() {
		String error = null;
		String email = "email";
		String password = "1234";
		String name = "Test Employee";
		Double salary = 1400.0;
		Employee employee = null;


		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee email must contain an @!", error);
	}

	@Test
	public void testCreateEmployeeEmptyPassword() {
		String error = null;
		String email = "testemployee@mail.ca";
		String password = "";
		String name = "Test Employee";
		Double salary = 1400.0;
		Employee employee = null;

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee password must not be empty!", error);
	}

	@Test
	public void testCreateEmployeeNullPassword() {
		String error = null;
		String email = "testemployee@mail.ca";
		String password = null;
		String name = "Test Employee";
		Double salary = 1400.0;
		Employee employee = null;

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee password must not be empty!", error);
	}

	@Test
	public void testCreateEmployeeEmptyName() {
		String error = null;
		String email = "testemployee@mail.ca";
		String password = "1234";
		String name = "";
		Double salary = 1400.0;
		Employee employee = null;

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee name must not be empty!", error);
	}

	@Test
	public void testCreateEmployeeNullName() {
		String error = null;
		String email = "testemployee@mail.ca";
		String password = "1234";
		String name = null;
		Double salary = 1400.0;
		Employee employee = null;


		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee name must not be empty!", error);
	}

	@Test
	public void testCreateEmployeeNegativeSalary() {
		String error = null;
		String email = "testemployee@mail.ca";
		String password = "1234";
		String name = "Test Employee";
		Double salary = -1400.0;
		Employee employee = null;

		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("08:00:00");
		Time endTime = Time.valueOf("20:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> dailySchedules = new ArrayList<DailySchedule>();
			dailySchedules.add(dailySchedule);
			employee = service.createEmployee(email, password, name, salary, dailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee salary must be positive!", error);
	}


	@Test
	public void testUpdateEmployee() {
		String error = null;
		Employee employee = null;
		String newPassword = "5678";
		String newName = "Test Employee2";
		Double newSalary = 2000.0;

		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			employee = service.updateEmployee(EMPLOYEE_KEY, newPassword, newName, newSalary, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(employee);
		assertEquals(EMPLOYEE_KEY, employee.getEmail());
		assertEquals(newSalary, employee.getSalary());
		assertEquals(newName, employee.getName());
		assertEquals(newPassword, employee.getPassword());
		
		assertNotNull(employee.getDailySchedules().get(0));
		assertEquals(dayOfWeek, employee.getDailySchedules().get(0).getDayOfWeek());
		assertEquals(startTime, employee.getDailySchedules().get(0).getStartTime());
		assertEquals(endTime, employee.getDailySchedules().get(0).getEndTime());
	}

	@Test
	public void testUpdateEmployeeEmptyPassword() {
		String error = null;
		Employee employee = null;
		String newPassword = "";
		String newName = "Test Employee2";
		Double newSalary = 2000.0;
		
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			employee = service.updateEmployee(EMPLOYEE_KEY, newPassword, newName, newSalary, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee password must not be empty!", error);
	}

	@Test
	public void testUpdateEmployeeNullPassword() {
		String error = null;
		Employee employee = null;
		String newPassword = null;
		String newName = "Test Employee2";
		Double newSalary = 2000.0;
		
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			employee = service.updateEmployee(EMPLOYEE_KEY, newPassword, newName, newSalary, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee password must not be empty!", error);
	}

	@Test
	public void testUpdateEmployeeEmptyName() {
		String error = null;
		Employee employee = null;
		String newPassword = "5678";
		String newName = "";
		Double newSalary = 2000.0;
		
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			employee = service.updateEmployee(EMPLOYEE_KEY, newPassword, newName, newSalary, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee name must not be empty!", error);
	}

	@Test
	public void testUpdateEmployeeNullName() {
		String error = null;
		Employee employee = null;
		String newPassword = "5678";
		String newName = null;
		Double newSalary = 2000.0;
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			employee = service.updateEmployee(EMPLOYEE_KEY, newPassword, newName, newSalary, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee name must not be empty!", error);
	}

	@Test
	public void testUpdateEmployeeNegativeSalary() {
		String error = null;
		Employee employee = null;
		String newPassword = "5678";
		String newName = "Test Emplpoyee2";
		Double newSalary = -2000.0;
		
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			employee = service.updateEmployee(EMPLOYEE_KEY, newPassword, newName, newSalary, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals("Employee salary must be positive!", error);
	}

	@Test
	public void testUpdateEmployeeNotFound() {
		String error = null;
		String newPassword = "5678";
		String newName = "Test Emplpoyee2";
		Double newSalary = 2000.0;
		DayOfWeek dayOfWeek = DayOfWeek.Tuesday;
		Time startTime = Time.valueOf("09:00:00");
		Time endTime = Time.valueOf("21:00:00");
		Long id = (long) 321;
		DailySchedule dailySchedule = null;
		
		try {
			dailySchedule = dailyScheduleService.createDailySchedule(dayOfWeek, startTime, endTime);
			List<DailySchedule> newDailySchedules = new ArrayList<DailySchedule>();
			newDailySchedules.add(dailySchedule);
			service.updateEmployee("doesntexist@mail.ca", newPassword, newName, newSalary, newDailySchedules);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Employee not found.", error);
	}

	@Test
	public void testDeleteEmployee() {

		String error = null;
		boolean success = false;
		try {
			success = service.deleteEmployee(EMPLOYEE_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertTrue(success);
	}

	@Test
	public void testDeleteEmployeeEmpty() {
		String error = null;
		try {
			service.deleteEmployee("");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Employee email cannot be empty!", error);
	}

	@Test
	public void testDeleteEmployeeNull() {
		String error = null;
		try {
			service.deleteEmployee(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Employee email cannot be empty!", error);
	}

	@Test
	public void testDeleteEmployeeNotFound() {
		String error = null;
		try {
			service.deleteEmployee("doesntexist@mail.ca");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Employee not found.", error);
	}
}
