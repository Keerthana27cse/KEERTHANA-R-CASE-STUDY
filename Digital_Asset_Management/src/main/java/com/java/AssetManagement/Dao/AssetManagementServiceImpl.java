package com.java.AssetManagement.Dao;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.java.AssetManagement.model.Assets;
import com.java.AssetManagement.util.DBConnection;
import com.java.myexceptions.AssetNotFoundException;
import com.java.myexceptions.AssetNotMaintainException;



public class AssetManagementServiceImpl implements AssetManagementService {

    Connection connection;
    PreparedStatement pst;
    
    @Override
    public boolean addAsset(Assets asset) throws ClassNotFoundException, SQLException {
        connection = DBConnection.getConnection();
        String cmd = "INSERT INTO assets (asset_name, asset_type, serial_number, purchase_date, location, asset_status, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        pst = connection.prepareStatement(cmd);
        pst.setString(1, asset.getAssetName());
        pst.setString(2, asset.getAssetType().toString());
        pst.setString(3, asset.getSerialNumber());
        Date today=new Date(); 
        Date utilDate1 = new Date(asset.getPurchaseDate().getTime());
		if (dateDiff(today, utilDate1) > 0) {
            System.out.println("Purchase date cannot be in the future.");
			return false;
		}
	    pst.setDate(4, asset.getPurchaseDate());
        pst.setString(5, asset.getLocation());
        pst.setString(6, asset.getAssetStatus().toString());
        pst.setInt(7, asset.getOwnerId());
        return pst.executeUpdate() > 0;
    }

    
    @Override
    public boolean updateAsset(Assets asset) throws ClassNotFoundException, SQLException, AssetNotFoundException {
        connection = DBConnection.getConnection();
        String chckcmd="SELECT *FROM assets WHERE asset_id = ?";
        pst = connection.prepareStatement(chckcmd);
        pst.setInt(1, asset.getAssetId());
        ResultSet rs = pst.executeQuery();
        if(!rs.next()) {
            throw new AssetNotFoundException("Asset with ID " + asset.getAssetId() + " not found.");
        }
        String updatecmd= "UPDATE assets SET asset_name = ?, asset_type = ?, serial_number = ?, purchase_date = ?, location = ?, asset_status = ?, owner_id = ? WHERE asset_id = ?";
        pst = connection.prepareStatement(updatecmd);        
        pst.setString(1, asset.getAssetName());
        pst.setString(2, asset.getAssetType().toString());
        pst.setString(3, asset.getSerialNumber());
        Date today=new Date();
        Date utilDate1 = new Date(asset.getPurchaseDate().getTime());
		if (dateDiff(today, utilDate1) > 0) {
            System.out.println("Purchase date cannot be in the future.");
			return false;
		}
        pst.setDate(4, asset.getPurchaseDate());
        pst.setString(5, asset.getLocation());
        pst.setString(6, asset.getAssetStatus().toString());
        pst.setInt(7, asset.getOwnerId());
        pst.setInt(8, asset.getAssetId());

        return pst.executeUpdate() > 0;
    } 


    @Override
    public boolean deleteAsset(int assetId) throws ClassNotFoundException, SQLException, AssetNotFoundException {
        connection = DBConnection.getConnection();
            String checkSql = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
		    pst = connection.prepareStatement(checkSql);
		    pst.setInt(1, assetId);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next() && rs.getInt(1) == 0) {
            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
		    }
			String deleteCmd = "DELETE FROM assets WHERE asset_id = ?";
	        pst = connection.prepareStatement(deleteCmd);
	        pst.setInt(1, assetId);
	        return pst.executeUpdate() > 0; 
    }
    
       public static int dateDiff(Date startDate, Date endDate) {
  		long ms = endDate.getTime() - startDate.getTime();
  		long days = (ms / (1000 * 60 * 60 * 24));
  		return (int)days;
      }
  		public static Date conversql(java.util.Date utildate) {
  	        return new Date(utildate.getTime());
  		}

  		@Override
  		public boolean allocateAsset(int assetId, int employeeId, String allocationDate) throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException {
  		    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
  		    java.util.Date today = new java.util.Date();
  		    java.util.Date utilDate = sf.parse(allocationDate); 
  		    
  		    if (dateDiff(today, utilDate) < 0) {
  		        System.out.println("Allocation date cannot be in the past.");
  		        return false;
  		    }
  		    connection = DBConnection.getConnection();
  		    String checkSql = "SELECT asset_status FROM assets WHERE asset_id = ?";
		    pst = connection.prepareStatement(checkSql);
		    pst.setInt(1, assetId);
		    ResultSet rs = pst.executeQuery();
		    if(!rs.next()) {
            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
        }
  		    String status = rs.getString("asset_status");
  		    if ("decommissioned".equalsIgnoreCase(status)) {
  		        System.out.println("Asset is decommissioned and cannot be allocated.");
  		        return false;
  		    }
  		   
  		    String maintenanceSql = "SELECT MAX(maintenance_date) FROM maintenance_records WHERE asset_id = ?";
  		    pst = connection.prepareStatement(maintenanceSql);
  		    pst.setInt(1, assetId);
  		    rs = pst.executeQuery();
  		    if (rs.next()) {
  		        java.sql.Date lastMaintained = rs.getDate(1);
  		        if (lastMaintained == null || dateDiff(today, lastMaintained) > 730) {
  		            System.out.println("Asset hasn't been maintained for over 2 years.");
  		            return false;
  		        }
  		    }
  		    String allocCheckSql = "SELECT return_date FROM asset_allocations WHERE asset_id = ? ORDER BY allocation_id DESC LIMIT 1";
  		    pst = connection.prepareStatement(allocCheckSql);
  		    pst.setInt(1, assetId);
  		    rs = pst.executeQuery();
  		    if (rs.next()) {
  		        java.sql.Date returnDate = rs.getDate("return_date");
  		        if (returnDate == null || dateDiff(utilDate, returnDate) > 0) {
  		            System.out.println("Asset is currently allocated or return date is after requested allocation date.");
  		            return false;
  		        }
  		    }
  		    String insertSql = "INSERT INTO asset_allocations (asset_id, employee_id, allocation_date) VALUES (?, ?, ?)";
  		    pst = connection.prepareStatement(insertSql);
  		    pst.setInt(1, assetId);
  		    pst.setInt(2, employeeId);
  		    pst.setDate(3, new java.sql.Date(utilDate.getTime())); 
  		    int result = pst.executeUpdate();
  		    return result > 0;
  		}


  		@Override
  		public boolean deallocateAsset(int assetId, int employeeId, String returnDate) throws ParseException, ClassNotFoundException, SQLException {
  		    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
  		    java.util.Date utilDate = sf.parse(returnDate);
  		    connection = DBConnection.getConnection();
  		    String checkSql = "SELECT allocation_date FROM asset_allocations WHERE asset_id = ? AND employee_id = ? AND return_date IS NULL";
  		    pst = connection.prepareStatement(checkSql);
  		    pst.setInt(1, assetId);
  		    pst.setInt(2, employeeId);
  		    ResultSet rs = pst.executeQuery();
  		    if (!rs.next()) {
  		        System.out.println("No active allocation found for this asset and employee.");
  		        return false;
  		    }
  		    java.util.Date allocationDate = rs.getDate("allocation_date");
  		    if (dateDiff(allocationDate, utilDate) < 0) {
  		        System.out.println("Return date cannot be before allocation date.");
  		        return false;
  		    }
  		    String updateSql = "UPDATE asset_allocations SET return_date = ? WHERE asset_id = ? AND employee_id = ? AND return_date IS NULL";
  		    pst = connection.prepareStatement(updateSql);
  		    pst.setDate(1, new java.sql.Date(utilDate.getTime())); 
  		    pst.setInt(2, assetId);
  		    pst.setInt(3, employeeId);
  		    return pst.executeUpdate() > 0;
  		}

  		@Override
  		public boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost)
  		        throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException {
  		    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
  		    java.util.Date utilDate = sf.parse(maintenanceDate);
  		    connection = DBConnection.getConnection();
  		    String checkSql = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
  		    pst = connection.prepareStatement(checkSql);
  		    pst.setInt(1, assetId);
  		    ResultSet rs = pst.executeQuery();
  		    if(!rs.next()) {
              throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
          }
  		    Date today = new Date();
  		    Date utilDate1 = new Date(utilDate.getTime()); 
  	        if (dateDiff(today, utilDate1) > 0) {
  	        System.out.println("Maintenance Date cannot be in the future.");
  	        return false;
  	    }
  		    String cmd = "INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost) VALUES(?,?,?,?)";
  		    pst = connection.prepareStatement(cmd);
  		    pst.setInt(1, assetId);
  		    pst.setDate(2, new java.sql.Date(utilDate.getTime())); 
  		    pst.setString(3, description);
  		    pst.setDouble(4, cost);
  		    return pst.executeUpdate() > 0;
  		}

  		@Override
  		public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate)
  		        throws ClassNotFoundException, SQLException, ParseException, AssetNotFoundException, AssetNotMaintainException {
  		    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
  		    java.util.Date resDate = sf.parse(reservationDate);
  		    java.util.Date start = sf.parse(startDate);
  		    java.util.Date end = sf.parse(endDate);

  		    if (dateDiff(resDate, start) < 0) {
  		        System.out.println("Start date cannot be before reservation date.");
  		        return false;
  		    }

  		    if (dateDiff(start, end) < 0) {
  		        System.out.println("End date cannot be before start date.");
  		        return false;
  		    }
  		    connection = DBConnection.getConnection();
  		    String checkSql = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
		    pst = connection.prepareStatement(checkSql);
		    pst.setInt(1, assetId);
		    ResultSet rs = pst.executeQuery();
		    if(!rs.next()) {
            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
        }
  		    String maintenanceSql = "SELECT MAX(maintenance_date) FROM maintenance_records WHERE asset_id = ?";
  		    pst = connection.prepareStatement(maintenanceSql);
  		    pst.setInt(1, assetId);
  		    rs = pst.executeQuery();
  		    if (rs.next()) {
  		        java.sql.Date lastMaintained = rs.getDate(1);
  		        java.util.Date today = new java.util.Date();
  		        if (lastMaintained == null || dateDiff(today, lastMaintained) > 730) {
  		            throw new AssetNotMaintainException("Asset with ID " + assetId + " has not been maintained for more than 2 years.");
  		        }
  		    }
  		String reservedSql = "SELECT COUNT(*) FROM reservations WHERE asset_id = ? AND " +
  	            "(start_date <= ? AND end_date >= ?)";
  	    pst = connection.prepareStatement(reservedSql);
  	    pst.setInt(1, assetId);
		pst.setDate(2, new java.sql.Date(end.getTime())); 
		pst.setDate(3, new java.sql.Date(start.getTime())); 
  	    rs = pst.executeQuery();
  	    if (rs.next() && rs.getInt(1) > 0) {
  	        System.out.println("Asset already reserved for the given dates.");
  	        return false;
  	    }
  		    String reserveSql = "INSERT INTO reservations (asset_id, employee_id, reservation_date, start_date, end_date) VALUES(?,?,?,?,?)";
  		    pst = connection.prepareStatement(reserveSql);
  		    pst.setInt(1, assetId);
  		    pst.setInt(2, employeeId);
  		    pst.setDate(3, new java.sql.Date(resDate.getTime())); 
  		    pst.setDate(4, new java.sql.Date(start.getTime())); 
  		    pst.setDate(5, new java.sql.Date(end.getTime())); 
  		    return pst.executeUpdate() > 0;
  		} 
  		
  		
  		
  		@Override
  		public boolean withdrawReservation(int reservationId) throws ClassNotFoundException, SQLException {
  		    connection = DBConnection.getConnection();
  		    String checkSql = "SELECT asset_id FROM reservations WHERE reservation_id = ?";
  		    pst = connection.prepareStatement(checkSql);
  		    pst.setInt(1, reservationId);
  		    ResultSet rs = pst.executeQuery();
  		    if (!rs.next()) {
  		        System.out.println("Reservation ID not found");
  		        return false;
  		    }
  		    int assetId = rs.getInt("asset_id");
  		    String deleteSql = "DELETE FROM reservations WHERE reservation_id = ?";
  		    pst = connection.prepareStatement(deleteSql);
  		    pst.setInt(1, reservationId);
  		    int rowsdeleted = pst.executeUpdate();
  		    if (rowsdeleted > 0) {
  		        String updateAssetSql = "UPDATE assets SET asset_status = 'available' WHERE asset_id = ?";
  		        pst = connection.prepareStatement(updateAssetSql);
  		        pst.setInt(1, assetId);
  		        pst.executeUpdate();
  		        System.out.println("Reservation successfully withdrawn, and asset is now available.");
  		        return true;
  		    }
  		    System.out.println("Failed to withdraw the reservation.");
  		    return false;
  		}
}

   