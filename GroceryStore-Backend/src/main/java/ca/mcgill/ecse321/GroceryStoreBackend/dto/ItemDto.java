package ca.mcgill.ecse321.GroceryStoreBackend.dto;

public abstract class ItemDto {
	
	private String name;
	private Double price;
	
	public ItemDto (String name, Double price) {
		this.name=name;
		this.price=price;
	}
	
	public String getName() {
		return name;
	}
	
	public Double getPrice() {
		return price;
	}
	
}
