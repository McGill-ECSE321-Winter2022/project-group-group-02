package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OwnerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;

@ExtendWith(MockitoExtension.class)
public class TestOwnerService {

	@Mock
	private OwnerRepository ownerRepo;
	@InjectMocks
	private OwnerService ownerService;

	private static final String OWNER_USERNAME = "TestOwner";
	private static final String OWNER_PASSWORD = "TestPassword1";
	private static final String OWNER_EMAIL = "admin@grocerystore.com";

	@BeforeEach
	public void setMockOutput() {

		lenient().when(ownerRepo.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(OWNER_EMAIL)) {
				Owner owner = new Owner();
				owner.setName(OWNER_USERNAME);
				owner.setPassword(OWNER_PASSWORD);
				owner.setEmail(OWNER_EMAIL);
				return owner;
			} else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(ownerRepo.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateOwner() {

		String username = "nameTest";
		String password = "passwordTest1";
		String email = "admin@grocerystore.com";

		// To remove existing owner
		lenient().when(ownerRepo.findByEmail(anyString())).thenReturn(null);

		Owner owner = null;
		try {
			owner = ownerService.createOwner(email, password, username);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(owner);
		assertEquals(username, owner.getName());
		assertEquals(password, owner.getPassword());
		assertEquals(email, owner.getEmail());
	}

	@Test
	public void testCreateOwnerErrorBlankName() {
		String username = " ";
		String password = "passwordTest1";
		String email = "admin@grocerystore.com";

		// To remove existing owner
		lenient().when(ownerRepo.findByEmail(anyString())).thenReturn(null);

		Owner owner = null;
		String error = "";
		try {
			owner = ownerService.createOwner(email, password, username);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Name cannot be blank", error);
	}

	@Test
	public void testCreateOwnerErrorNullName() {
		String username = null;
		String password = "passwordTest1";
		String email = "admin@grocerystore.com";

		// To remove existing owner
		lenient().when(ownerRepo.findByEmail(anyString())).thenReturn(null);

		Owner owner = null;
		String error = "";
		try {
			owner = ownerService.createOwner(email, password, username);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Name cannot be null", error);
	}

	@Test
	public void testCreateOwnerErrorNullPassword() {
		String username = "nameTest";
		String password = null;
		String email = "admin@grocerystore.com";

		// To remove existing owner
		lenient().when(ownerRepo.findByEmail(anyString())).thenReturn(null);

		Owner owner = null;
		String error = "";
		try {
			owner = ownerService.createOwner(email, password, username);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password cannot be null", error);
	}

	@Test
	public void testCreateOwnerErrorBlankPassword() {
		String username = "nameTest";
		String password = "  ";
		String email = "admin@grocerystore.com";

		// To remove existing owner
		lenient().when(ownerRepo.findByEmail(anyString())).thenReturn(null);

		Owner owner = null;
		String error = "";
		try {
			owner = ownerService.createOwner(email, password, username);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Password cannot be blank", error);
	}

	@Test
	public void testCreateOwnerWrongEmail() {
		String username = "nameTest";
		String password = "passwordTest1";
		String email = "fraud@grocerystore.com";

		// To remove existing owner
		lenient().when(ownerRepo.findByEmail(anyString())).thenReturn(null);

		Owner owner = null;
		String error = "";
		try {
			owner = ownerService.createOwner(email, password, username);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Invalid email, use system email \"admin@grocerystore.com\"", error);
	}

	@Test
	public void testCreateOwnerAlreadyExisting() {

		Owner owner = null;
		String error = "";
		try {
			owner = ownerService.createOwner(OWNER_EMAIL, OWNER_PASSWORD, OWNER_USERNAME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(owner);
		assertEquals("Owner already exists", error);
	}

	@Test
	public void testUpdateOwnerPassword() {
		Owner owner = null;

		try {
			owner = ownerService.updateOwnerPassword("newPassword123");
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(owner);
		assertEquals(OWNER_USERNAME, owner.getName());
		assertEquals("newPassword123", owner.getPassword());
		assertEquals(OWNER_EMAIL, owner.getEmail());
	}

	@Test
	public void testUpdateOwnerPasswordBlank() {

		String error = null;
		try {
			ownerService.updateOwnerPassword(" ");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Password cannot be blank", error);
	}

	@Test
	public void testUpdateOwnerPasswordNull() {

		String error = null;
		try {
			ownerService.updateOwnerPassword(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Password cannot be null", error);
	}

	@Test
	public void testUpdateOwnerNameNull() {

		String error = null;
		try {
			ownerService.updateOwnerName(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Name cannot be null", error);
	}

	@Test
	public void testUpdateOwnerNameBlank() {

		String error = null;
		try {
			ownerService.updateOwnerName("  ");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Name cannot be blank", error);
	}

	@Test
	public void testGetOwner() {

		Owner owner = null;
		try {
			owner = ownerService.getOwner();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(owner);
		assertEquals(owner.getName(), OWNER_USERNAME);
		assertEquals(owner.getPassword(), OWNER_PASSWORD);
		assertEquals(owner.getEmail(), OWNER_EMAIL);

	}

	@Test
	public void testDeleteOwner() {

		boolean success = false;
		try {
			success = ownerService.deleteOwner();
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(success);
	}

	@Test
	public void testDeleteOwnerNotFound() {

		String error = null;
		lenient().when(ownerRepo.findByEmail(anyString())).thenReturn(null);

		try {
			ownerService.deleteOwner();
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Owner not found.", error);
	}

}
