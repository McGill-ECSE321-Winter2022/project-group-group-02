package ca.mcgill.ecse321.GroceryStoreBackend.model;

import javax.persistence.*;

@Entity
public class UnavailableItem extends Item {
  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public UnavailableItem(String aName, double aPrice) {
    super(aName, aPrice);
  }

  public UnavailableItem() {

  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}