package com.java.junit;
import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.java.AssetManagement.model.Asset_Allocations;

public class Asset_AllocationsTest {

    @Test
    public void testGetterAndSetters() throws ParseException {
        Asset_Allocations asset = new Asset_Allocations();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date allocDate = new Date(sdf.parse("2025-09-09").getTime());
        Date returnDate = new Date(sdf.parse("2025-09-12").getTime());

        asset.setAllocationId(1);
        asset.setAssetId(2);
        asset.setEmployeeId(1);
        asset.setAllocationDate(allocDate);
        asset.setReturnDate(returnDate);

        assertEquals(1, asset.getAllocationId());
        assertEquals(2, asset.getAssetId());
        assertEquals(1, asset.getEmployeeId());
        assertEquals(allocDate, asset.getAllocationDate());
        assertEquals(returnDate, asset.getReturnDate());
    }

    @Test
    public void testConstructor() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date allocDate = new Date(sdf.parse("2025-03-01").getTime());
        Date returnDate = new Date(sdf.parse("2025-03-10").getTime());
        Asset_Allocations asset = new Asset_Allocations(10, 200, 300, allocDate, returnDate);

        assertEquals(10, asset.getAllocationId());
        assertEquals(200, asset.getAssetId());
        assertEquals(300, asset.getEmployeeId());
        assertEquals(allocDate, asset.getAllocationDate());
        assertEquals(returnDate, asset.getReturnDate());
    }

    @Test
    public void testToString() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date allocationDate = new Date(sdf.parse("2025-04-10").getTime());
        Date returnDate = new Date(sdf.parse("2025-04-20").getTime());
        Asset_Allocations asset = new Asset_Allocations(1, 1001, 501, allocationDate, returnDate);
        String expected = "Asset_Allocations(allocationId=1, assetId=1001, employeeId=501, allocationDate=2025-04-10, returnDate=2025-04-20)";
        assertEquals(expected, asset.toString());
    }
}