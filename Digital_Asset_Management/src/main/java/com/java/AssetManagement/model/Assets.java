package com.java.AssetManagement.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Assets {
     private int assetId;
     private String assetName;
     private AssetType assetType;
     private String serialNumber;
     private Date purchaseDate;
     private String location;
     private AssetStatus assetStatus;
     private int ownerId;
}