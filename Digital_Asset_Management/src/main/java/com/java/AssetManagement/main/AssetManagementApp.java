package com.java.AssetManagement.main;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.java.AssetManagement.Dao.AssetManagementService;
import com.java.AssetManagement.Dao.AssetManagementServiceImpl;
import com.java.AssetManagement.model.AssetStatus;
import com.java.AssetManagement.model.AssetType;
import com.java.AssetManagement.model.Assets;
import com.java.myexceptions.AssetNotFoundException;
import com.java.myexceptions.AssetNotMaintainException;


public class AssetManagementApp {

    static Scanner sc;
    static AssetManagementService assetdao;

    static {
        assetdao = new AssetManagementServiceImpl();
        sc = new Scanner(System.in);
    }

    public static void main(String[] args) throws ParseException, ClassNotFoundException, SQLException, AssetNotFoundException {
        int choice;
        do {
            System.out.println("\n----------------------------------------");
            System.out.println(" DIGITAL ASSET MANAGEMENT APPLICATION");
            System.out.println("--------------------------------------");
            System.out.println("1. Add Asset");
            System.out.println("2. Update Asset");
            System.out.println("3. Delete Asset");
            System.out.println("4. Allocate Asset");
            System.out.println("5. Deallocate Asset");
            System.out.println("6. Perform Maintenance");
            System.out.println("7. Reserve Asset");
            System.out.println("8. Withdraw Reservation");
            System.out.println("9. Exit");
            System.out.print("Enter your Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    AddAssetMain();
                    break;
                case 2:
                    UpdateAssetMain();
                    break;
                case 3:
                    DeleteAssetMain();
                    break;
                case 4:
                    allocateAsset();
                    break;
                case 5:
                    deallocateAsset();
                    break;
                case 6:
                    performMaintenance();
                    break;
                case 7:
                    reserveAsset();
                    break;
                case 8:
                    withdrawReservation();
                    break;
                case 9:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
                
        } while (choice != 9);
    }

	public static Date convertSql(java.util.Date utildate) {
	        return new Date(utildate.getTime());
		}

   
    public static void AddAssetMain() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Assets asset = new Assets();
        System.out.println("Enter asset name:");
        asset.setAssetName(sc.next()); 
        
        System.out.println("Enter asset type (LAPTOP, VEHICLE, EQUIPMENT, MOBILE, MONITOR, OTHER):");
        String typeInput = sc.next().toUpperCase();
        asset.setAssetType(AssetType.valueOf(typeInput));
        
        System.out.println("Enter serial number:");
        asset.setSerialNumber(sc.next());
        
        System.out.println("Enter purchase date (yyyy-MM-dd):");
        String purchaseDate = sc.next();
        java.util.Date utilDate1=sdf.parse(purchaseDate);
        asset.setPurchaseDate(convertSql(utilDate1));
        
        System.out.println("Enter location:");
        asset.setLocation(sc.next());

        
        System.out.println("Enter owner id:");
        asset.setOwnerId(sc.nextInt());
        
        asset.setAssetStatus(AssetStatus.AVAILABLE);

        boolean result;
		try {
			result = assetdao.addAsset(asset);
			if(result)System.out.println("Asset Added Succesfully");
	        else System.out.println("Please try again.");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
    
    
 
    public static void UpdateAssetMain() throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Assets asset = new Assets();
        System.out.println("Enter asset ID:"); 
        asset.setAssetId(sc.nextInt());
        System.out.println("Enter asset name:");
        asset.setAssetName(sc.next());
        
        System.out.println("Enter asset type (LAPTOP, VEHICLE, EQUIPMENT, MOBILE, MONITOR, OTHER):");
        String typeInput = sc.next().toUpperCase();
        asset.setAssetType(AssetType.valueOf(typeInput));
        
        System.out.println("Enter serial number:");
        asset.setSerialNumber(sc.next());
        
        System.out.println("Enter purchase date (yyyy-MM-dd):");
        String purchaseDate=sc.next();
        java.util.Date utilDate1=sdf.parse(purchaseDate );
        asset.setPurchaseDate(convertSql(utilDate1));
        sc.nextLine(); 

        System.out.println("Enter location:");
        asset.setLocation(sc.next());
        
        System.out.println("Enter status (IN_USE, DECOMMISSIONED, UNDER_MAINTENANCE, AVAILABLE, ALLOCATED):");
        String statusInput = sc.next().toUpperCase();
        asset.setAssetStatus(AssetStatus.valueOf(statusInput));
        
        System.out.println("Enter owner id:");
        asset.setOwnerId(sc.nextInt());
        
        boolean result;
		try {
			result = assetdao.updateAsset(asset);
			if(result)System.out.println("Asset updated successfully.");
	        else System.out.println("Failed to update asset. Please verify the asset ID..");
		}
	        catch (AssetNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
		}
			catch (ClassNotFoundException | SQLException e ) {
			e.printStackTrace();
    }
}
    
        public static void DeleteAssetMain()   {
        System.out.println("Enter asset id to delete:");
        int assetId = sc.nextInt();
    	boolean result=false;
			 try {
				result = assetdao.deleteAsset(assetId);
				 if (result) System.out.println("Asset deleted successfully.");
			     else System.out.println("Failed to delete asset");
			} catch (AssetNotFoundException e) {
	            System.out.println("Error: " + e.getMessage());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
 
        }

    public static void allocateAsset()  
    {
        System.out.println("Enter asset id:");
        int assetId = sc.nextInt();
        System.out.println("Enter employee id:");
        int employeeId = sc.nextInt();
        System.out.println("Enter allocation date (yyyy-MM-dd):");
        String allocationDate = sc.next();
        boolean result;
			try {
				result = assetdao.allocateAsset(assetId, employeeId, allocationDate);
			if (result)
	            System.out.println("Asset allocated successfully.");
	        else
	            System.out.println("Allocation failed.");
		} catch (AssetNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			e.printStackTrace();
		}
    }

        
    public static void deallocateAsset()  {
        System.out.print("Enter Asset ID to deallocate: ");
        int assetId = sc.nextInt();
        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        System.out.print("Enter Return Date (yyyy-mm-dd): ");
        String returnDate = sc.next();
        Boolean result;
            try {
            	    result = assetdao.deallocateAsset(assetId, employeeId, returnDate);  
				    if (result)	System.out.println("Asset deallocated successfully.");
                    else System.out.println("Failed to deallocate asset.");
			} catch (ClassNotFoundException | SQLException | ParseException e) {
	            System.err.println("Error : " + e.getMessage());
				e.printStackTrace();
			}
    }
        public static void performMaintenance() {
        System.out.print("Enter Asset ID for maintenance: ");
        int assetId = sc.nextInt();
        System.out.print("Enter Maintenance Date (yyyy-mm-dd): ");
        String maintenanceDate = sc.next();
        System.out.print("Enter Description: ");
        String description = sc.next();
        System.out.print("Enter Cost: ");
        double cost = sc.nextDouble();

        try {
            boolean result = assetdao.performMaintenance(assetId, maintenanceDate, description, cost);
            if (result)
                System.out.println("Maintenance performed successfully.");
            else
                System.out.println("Failed to perform maintenance.");
        } catch (AssetNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
        public static void reserveAsset()   {
        System.out.print("Enter Asset ID to reserve: ");
        int assetId = sc.nextInt();
        System.out.print("Enter Employee ID: ");
        int employeeId = sc.nextInt();
        String reservationDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        String startDate = sc.next();
        System.out.print("Enter End Date (yyyy-mm-dd): ");
        String endDate = sc.next();
		try 
		{
			boolean result =assetdao.reserveAsset(assetId, employeeId, reservationDate, startDate, endDate);
			if(result)
				System.out.println("Asset reserved successfully.");
			else 
				System.out.println("Failed to reserve asset.");
		} catch (AssetNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (AssetNotMaintainException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
    
     //withdrawing 
    public static void withdrawReservation()  {
        System.out.print("Enter Reservation ID to withdraw: ");
        int reservationId = sc.nextInt();
    	try {
			boolean result = assetdao.withdrawReservation(reservationId);
			if(result)
				System.out.println("Withdrawal of reservation is succesful");
			else
				System.out.println("Cannot Withdraw the reservation.Please try again.");
		} catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
		}
    }
}
        