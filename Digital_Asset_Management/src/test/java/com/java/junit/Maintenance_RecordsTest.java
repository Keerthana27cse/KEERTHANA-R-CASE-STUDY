package com.java.junit;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import com.java.AssetManagement.model.Maintenance_Records;

public class Maintenance_RecordsTest {
	  @Test
	    public void testGettersAndSetters() {
	        Maintenance_Records record = new Maintenance_Records();
	        Date maintenanceDate = Date.valueOf("2025-04-01");

	        record.setMaintenanceId(10);
	        record.setAssetId(1001);
	        record.setMaintenanceDate(maintenanceDate);
	        record.setDescription("Fan replacement");
	        record.setCost(250.75);

	        assertEquals(10, record.getMaintenanceId());
	        assertEquals(1001, record.getAssetId());
	        assertEquals(maintenanceDate, record.getMaintenanceDate());
	        assertEquals("Fan replacement", record.getDescription());
	        assertEquals(250.75, record.getCost(), 0.01);
	    }

	    @Test
	    public void testConstructor() {
	        Date date = Date.valueOf("2025-03-28");
	        Maintenance_Records record = new Maintenance_Records(11, 2002, date, "Battery replacement", 499.99);

	        assertEquals(11, record.getMaintenanceId());
	        assertEquals(2002, record.getAssetId());
	        assertEquals(date, record.getMaintenanceDate());
	        assertEquals("Battery replacement", record.getDescription());
	        assertEquals(499.99, record.getCost(), 0.01);
	    }

	    @Test
	    public void testToString() {
	        Date date = Date.valueOf("2025-04-05");
	        Maintenance_Records record = new Maintenance_Records(12, 3003, date, "Software update", 120.00);
	        String expected = "Maintenance_Records(maintenanceId=12, assetId=3003, maintenanceDate=2025-04-05, description=Software update, cost=120.0)";
	        assertEquals(expected, record.toString());
	    }
}