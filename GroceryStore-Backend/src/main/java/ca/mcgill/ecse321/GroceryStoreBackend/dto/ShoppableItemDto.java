package ca.mcgill.ecse321.GroceryStoreBackend.dto;

public class ShoppableItemDto extends ItemDto {
	
	
	private Integer quantityAvailable;
	
	public ShoppableItemDto (String name, Double price, Integer quantityAvailable) {
		super(name,price);
		this.quantityAvailable=quantityAvailable;
	}
	
	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}
	
	
}
