package ca.mcgill.ecse321.GroceryStoreBackend.model;

import javax.persistence.*;

@Entity
public class Customer extends Person {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Customer Attributes
	private String address;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Customer(String aEmail, String aPassword, String aName, String aAddress) {
		super(aEmail, aPassword, aName);
		address = aAddress;
	}

	public Customer() {
		super();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setAddress(String aAddress) {
		boolean wasSet = false;
		address = aAddress;
		wasSet = true;
		return wasSet;
	}

	public String getAddress() {
		return address;
	}

	public void delete() {
		super.delete();
	}

	public String toString() {
		return super.toString() + "[" + "address" + ":" + getAddress() + "]";
	}
}