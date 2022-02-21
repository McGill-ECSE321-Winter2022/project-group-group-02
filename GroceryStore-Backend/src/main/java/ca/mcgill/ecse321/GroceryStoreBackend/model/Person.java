package ca.mcgill.ecse321.GroceryStoreBackend.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Persons")
public abstract class Person {

	// ------------------------
	// STATIC VARIABLES
	// ------------------------

	private static Map<String, Person> personsByEmail = new HashMap<String, Person>();

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Person Attributes
	private String email;
	private String password;
	private String name;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Person(String aEmail, String aPassword, String aName) {
		password = aPassword;
		name = aName;
		if (!setEmail(aEmail)) {
			throw new RuntimeException(
					"Cannot create due to duplicate email. See http://manual.umple.org?RE003ViolationofUniqueness.html");
		}
	}

	public Person() {
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setEmail(String aEmail) {
		this.email = aEmail;

		return true;
	}

	public boolean setPassword(String aPassword) {
		boolean wasSet = false;
		password = aPassword;
		wasSet = true;
		return wasSet;
	}

	public boolean setName(String aName) {
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	@Id
	public String getEmail() {
		return email;
	}

	/* Code from template attribute_GetUnique */
	public static Person getWithEmail(String aEmail) {
		return personsByEmail.get(aEmail);
	}

	/* Code from template attribute_HasUnique */
	public static boolean hasWithEmail(String aEmail) {
		return getWithEmail(aEmail) != null;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public void delete() {
		personsByEmail.remove(getEmail());

	}

	public String toString() {
		return super.toString() + "[" + "email" + ":" + getEmail() + "," + "password" + ":" + getPassword() + ","
				+ "name" + ":" + getName() + "]";
	}
}