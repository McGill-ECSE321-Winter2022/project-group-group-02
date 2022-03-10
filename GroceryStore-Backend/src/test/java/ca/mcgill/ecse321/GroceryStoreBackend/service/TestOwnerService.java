package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
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
  public void testFindOwner() {

    Owner owner = null;
    try {
      owner = ownerService.getOwner(OWNER_EMAIL);
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(owner);
    assertEquals(owner.getName(), OWNER_USERNAME);
    assertEquals(owner.getPassword(), OWNER_PASSWORD);
    assertEquals(owner.getEmail(),OWNER_EMAIL);

  }

  @Test
  public void testCreateOwnerErrorBlankUsername() {
    String username = "";
    String password = "Password123";
    String email = "admin@grocerystore.com";
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
  public void testCreateOwnerErrorBlankPassword() {
    String username = "newUsername1";
    String password = "";
    String email = "admin@grocerystore.com";
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
  public void testUpdateOwnerPassword() {
  Owner owner = null;

  try {
      owner = ownerService.updateOwner(OWNER_EMAIL,"newPassword123");
  }catch(IllegalArgumentException e) {
      fail();
  }
  assertNotNull(owner);
  assertEquals(OWNER_USERNAME,owner.getName());
  assertEquals("newPassword123",owner.getPassword());
  assertEquals(OWNER_EMAIL,owner.getEmail());
}
  
  @Test
  public void testUpdateSamePassword() {
  Owner owner = null;

  try {
      owner = ownerService.updateOwner(OWNER_EMAIL,OWNER_PASSWORD);
  }catch(IllegalArgumentException e) {
      fail();
  }
  assertNotNull(owner);
  assertEquals(OWNER_USERNAME,owner.getName());
  assertEquals(OWNER_PASSWORD,owner.getPassword());
  assertEquals(OWNER_EMAIL,owner.getEmail());
  }
  
  
  @Test
  public void testGetExistingOwner() {
      assertEquals(OWNER_USERNAME, ownerService.getOwner(OWNER_EMAIL).getName());
  }

  @Test
  public void testGetNonExistingOwner() {
      assertNull(ownerService.getOwner("nonExistingOwner@shop.com"));
  }
}
