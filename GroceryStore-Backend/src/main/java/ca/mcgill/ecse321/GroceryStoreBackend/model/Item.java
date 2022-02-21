package ca.mcgill.ecse321.GroceryStoreBackend.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Items")
public abstract class Item {

	// ------------------------
	// STATIC VARIABLES
	// ------------------------

	private static Map<String, Item> itemsByName = new HashMap<String, Item>();

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Item Attributes
	private String name;
	private double price;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Item(String aName, double aPrice) {
		price = aPrice;
		if (!setName(aName)) {
			throw new RuntimeException(
					"Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
		}
	}

	public Item() {
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setName(String aName) {
		this.name = aName;
		return true;
	}

	public boolean setPrice(double aPrice) {
		boolean wasSet = false;
		price = aPrice;
		wasSet = true;
		return wasSet;
	}

	@Id
	public String getName() {
		return name;
	}

	/* Code from template attribute_GetUnique */
	public static Item getWithName(String aName) {
		return itemsByName.get(aName);
	}

	/* Code from template attribute_HasUnique */
	public static boolean hasWithName(String aName) {
		return getWithName(aName) != null;
	}

	public double getPrice() {
		return price;
	}

	public void delete() {
		itemsByName.remove(getName());
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "," + "price" + ":" + getPrice() + "]";
	}
}