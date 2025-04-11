package com.java.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.AssetManagement.model.Employees;
public class EmployeesTest {

	    @Test
	    public void testGettersAndSetters() {
	        Employees emp = new Employees();
	        emp.setEmployeeId(1);
	        emp.setName("Keerthana");
	        emp.setDepartment("IT");
	        emp.setEmail("keerthana@example.com");
	        emp.setPassword("pass123");

	        assertEquals(1, emp.getEmployeeId());
	        assertEquals("Keerthana", emp.getName());
	        assertEquals("IT", emp.getDepartment());
	        assertEquals("keerthana@example.com", emp.getEmail());
	        assertEquals("pass123", emp.getPassword());
	    }

	    @Test
	    public void testConstructor() {
	        Employees emp = new Employees(2, "Ankitha", "HR", "ankitha@example.com", "secret");
	        assertEquals(2, emp.getEmployeeId());
	        assertEquals("Ankitha", emp.getName());
	        assertEquals("HR", emp.getDepartment());
	        assertEquals("ankitha@example.com", emp.getEmail());
	        assertEquals("secret", emp.getPassword());
	    }

	    @Test
	    public void testToString() {
	        Employees emp = new Employees(3, "Rahul", "Admin", "rahul@example.com", "pwd321");
	        String expected = "Employees(employeeId=3, name=Rahul, department=Admin, email=rahul@example.com, password=pwd321)";
	        assertEquals(expected, emp.toString());
	    }
	}