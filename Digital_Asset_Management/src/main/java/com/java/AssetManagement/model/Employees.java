package com.java.AssetManagement.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employees {
	private int employeeId;
	private String name;
	private String department;
	private String email;
	private String password;
}
