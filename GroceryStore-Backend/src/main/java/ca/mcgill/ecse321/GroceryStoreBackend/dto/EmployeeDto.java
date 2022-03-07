package ca.mcgill.ecse321.GroceryStoreBackend.dto;

public class EmployeeDto {

	private double salary;
	private String email;
	private String password;
	private String name;

	public EmployeeDto() {
		}

	public EmployeeDto(String email, String password, String name, double salary) {
			this.email = email;
			this.password = password;
			this.name = name;
			this.salary = salary;
		}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public String getName() {
		return name;
	}

}
