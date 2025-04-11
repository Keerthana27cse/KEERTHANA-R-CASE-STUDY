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

public class Asset_Allocations 
{
       private int allocationId;
       private int assetId;
       private int employeeId;
       private Date allocationDate;
       private Date returnDate;
}