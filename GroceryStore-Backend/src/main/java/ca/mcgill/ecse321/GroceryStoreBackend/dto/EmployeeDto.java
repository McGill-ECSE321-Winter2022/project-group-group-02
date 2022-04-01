package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import java.util.List;

import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;

public class EmployeeDto {

	private double salary;
	private String email;
	private String password;
	private String name;
	private List<DailySchedule> dailySchedules;
	private String userType;

	public EmployeeDto() {
		}

	public EmployeeDto(String email, String password, String name, double salary, List<DailySchedule> dailySchedules) {
			this.email = email;
			this.password = password;
			this.name = name;
			this.salary = salary;
			this.dailySchedules = dailySchedules;
			this.userType = "employee";

		}

	public String getEmail() {
		return email;
	}
	
	public String getUserType() {
		return userType;
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
	
	public List<DailySchedule> getDailySchedules() {
		return dailySchedules;
	}

	public void setDailySchedules(List<DailySchedule> dailySchedules) {
		this.dailySchedules = dailySchedules;
	}

}
