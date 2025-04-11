package com.java.junit;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import com.java.AssetManagement.model.AssetStatus;
import com.java.AssetManagement.model.AssetType;
import com.java.AssetManagement.model.Assets;

public class AssetsTest {

    @Test
    public void testGettersAndSetters() {
        Assets asset = new Assets();
        Date purchaseDate = Date.valueOf("2023-05-10");

        asset.setAssetId(101);
        asset.setAssetName("Dell Inspiron");
        asset.setAssetType(AssetType.LAPTOP);
        asset.setSerialNumber("SN123456");
        asset.setPurchaseDate(purchaseDate);
        asset.setLocation("Chennai");
        asset.setAssetStatus(AssetStatus.AVAILABLE);
        asset.setOwnerId(501);

        assertEquals(101, asset.getAssetId());
        assertEquals("Dell Inspiron", asset.getAssetName());
        assertEquals(AssetType.LAPTOP, asset.getAssetType());
        assertEquals("SN123456", asset.getSerialNumber());
        assertEquals(purchaseDate, asset.getPurchaseDate());
        assertEquals("Chennai", asset.getLocation());
        assertEquals(AssetStatus.AVAILABLE, asset.getAssetStatus());
        assertEquals(501, asset.getOwnerId());
    }

    @Test
    public void testConstructor() {
        Date purchaseDate = Date.valueOf("2022-08-15");

        Assets asset = new Assets(102, "HP EliteBook", AssetType.LAPTOP, "SN987654",
                purchaseDate, "Bangalore", AssetStatus.ALLOCATED, 502);

        assertEquals(102, asset.getAssetId());
        assertEquals("HP EliteBook", asset.getAssetName());
        assertEquals(AssetType.LAPTOP, asset.getAssetType());
        assertEquals("SN987654", asset.getSerialNumber());
        assertEquals(purchaseDate, asset.getPurchaseDate());
        assertEquals("Bangalore", asset.getLocation());
        assertEquals(AssetStatus.ALLOCATED, asset.getAssetStatus());
        assertEquals(502, asset.getOwnerId());
    }

    @Test
    public void testToString() {
        Date purchaseDate = Date.valueOf("2024-01-20");

        Assets asset = new Assets(103, "MacBook Pro", AssetType.LAPTOP, "SN777888",
                purchaseDate, "Mumbai", AssetStatus.UNDER_MAINTENANCE, 503);

        String expected = "Assets(assetId=103, assetName=MacBook Pro, assetType=LAPTOP, serialNumber=SN777888, purchaseDate=2024-01-20, location=Mumbai, assetStatus=UNDER_MAINTENANCE, ownerId=503)";
        assertEquals(expected, asset.toString());
    }
}