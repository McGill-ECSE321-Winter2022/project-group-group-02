package ca.mcgill.ecse321.GroceryStoreBackend.dto;

public class OwnerDto {

	private String email;
	private String password;
	private String name;

	public OwnerDto() {
		}

	public OwnerDto(String email, String password, String name) {
			this.email = email;
			this.password = password;
			this.name = name;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}

}
