package com.java.AssetManagement.model;

import java.sql.Date;

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
public class Maintenance_Records
{
	    private int maintenanceId;
	    private int assetId;
	    private Date maintenanceDate;
	    private String description;
	    private double cost;
}
