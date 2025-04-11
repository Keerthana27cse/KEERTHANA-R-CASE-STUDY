package com.java.junit;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import com.java.AssetManagement.model.ReservationStatus;
import com.java.AssetManagement.model.Reservations;
public class ReservationTest {

    @Test
    public void testGettersAndSetters() {
        Reservations res = new Reservations();
        Date resDate = Date.valueOf("2025-04-10");
        Date start = Date.valueOf("2025-04-15");
        Date end = Date.valueOf("2025-04-20");

        res.setReservationId(1);
        res.setAssetId(101);
        res.setEmployeeId(201);
        res.setReservationDate(resDate);
        res.setStartDate(start);
        res.setEndDate(end);
        res.setReservationStatus(ReservationStatus.PENDING);

        assertEquals(1, res.getReservationId());
        assertEquals(101, res.getAssetId());
        assertEquals(201, res.getEmployeeId());
        assertEquals(resDate, res.getReservationDate());
        assertEquals(start, res.getStartDate());
        assertEquals(end, res.getEndDate());
        assertEquals(ReservationStatus.PENDING, res.getReservationStatus());
    }
 
    @Test
    public void testConstructor() {
        Date resDate = Date.valueOf("2025-03-30");
        Date start = Date.valueOf("2025-04-01");
        Date end = Date.valueOf("2025-04-05");

        Reservations res = new Reservations(2, 102, 202, resDate, start, end, ReservationStatus.APPROVED);

        assertEquals(2, res.getReservationId());
        assertEquals(102, res.getAssetId());
        assertEquals(202, res.getEmployeeId());
        assertEquals(resDate, res.getReservationDate());
        assertEquals(start, res.getStartDate());
        assertEquals(end, res.getEndDate());
        assertEquals(ReservationStatus.APPROVED, res.getReservationStatus());
    }

    @Test
    public void testToString() {
        Date resDate = Date.valueOf("2025-04-01");
        Date start = Date.valueOf("2025-04-10");
        Date end = Date.valueOf("2025-04-15");

        Reservations res = new Reservations(3, 103, 203, resDate, start, end, ReservationStatus.CANCELED);
        String expected = "Reservations(reservationId=3, assetId=103, employeeId=203, reservationDate=2025-04-01, startDate=2025-04-10, endDate=2025-04-15, reservationStatus=CANCELED)";
        assertEquals(expected, res.toString());
    }
}