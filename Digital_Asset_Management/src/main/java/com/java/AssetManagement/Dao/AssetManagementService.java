package com.java.AssetManagement.Dao;

import java.sql.SQLException;
import java.text.ParseException;

import com.java.AssetManagement.model.Assets;
import com.java.myexceptions.AssetNotFoundException;
import com.java.myexceptions.AssetNotMaintainException;

public interface AssetManagementService {

	    boolean addAsset(Assets asset) throws ClassNotFoundException, SQLException;

	    boolean updateAsset(Assets asset) throws ClassNotFoundException, SQLException, AssetNotFoundException;

	    boolean deleteAsset(int assetId) throws ClassNotFoundException, SQLException, AssetNotFoundException;

	    boolean allocateAsset(int assetId, int employeeId,  String allocationDate) throws ClassNotFoundException, SQLException, AssetNotFoundException, ParseException;

	    boolean deallocateAsset(int assetId, int employeeId, String returnDate) throws ClassNotFoundException, SQLException, ParseException;
	    
	    boolean performMaintenance(int assetId, String maintenanceDate,String description,  double cost) throws ClassNotFoundException, SQLException,  AssetNotFoundException,ParseException;

	    boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) throws ClassNotFoundException, SQLException,ParseException, AssetNotFoundException, AssetNotMaintainException;

	    boolean withdrawReservation(int reservationId) throws ClassNotFoundException, SQLException;
}
