package ca.mcgill.ecse321.GroceryStoreBackend.dto;

public class CustomerDto {

	private String address;
	private String email;
	private String password;
	private String name;
	private String userType;

	public CustomerDto() {
		}

	public CustomerDto(String email, String password, String name, String address) {
			this.email = email;
			this.password = password;
			this.name = name;
			this.address = address;
			this.userType = "customer";
		}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}

}
