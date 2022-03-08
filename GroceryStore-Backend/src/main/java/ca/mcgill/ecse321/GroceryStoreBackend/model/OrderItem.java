package ca.mcgill.ecse321.GroceryStoreBackend.model;

import javax.persistence.*;


@Entity
@Table(name = "OrderedItems")
public class OrderItem {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// OrderItem Attributes
	private Long id;
	private int quantity;

	// OrderItem Associations
	private ShoppableItem item;
	// private Order order;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public OrderItem() {
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setId(Long aId) {
		boolean wasSet = false;
		id = aId;
		wasSet = true;
		return wasSet;
	}

	public boolean setQuantity(int aQuantity) {
		boolean wasSet = false;
		quantity = aQuantity;
		wasSet = true;
		return wasSet;
	}

	@Id
	public Long getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	/* Code from template association_GetOne */
	@ManyToOne(optional = false)
	public ShoppableItem getItem() {
		return item;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setItem(ShoppableItem aNewItem) {
		boolean wasSet = false;
		if (aNewItem != null) {
			item = aNewItem;
			wasSet = true;
		}
		return wasSet;
	}

	public void delete() {
		item = null;

	}

	public String toString() {
		return super.toString() + "[" + "id" + ":" + getId() + "," + "quantity" + ":" + getQuantity() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "item = "
				+ (getItem() != null ? Integer.toHexString(System.identityHashCode(getItem())) : "null");

	}
}