package com.example.android.pantry.model;

import com.example.android.pantry.model.Product;

/**
 * Created by dewong4 on 5/16/17.
 */

public class InventoryItem {
    private long mInventoryId;  // set to 0 if id unknown
    private String mLocation;
    private int mQuantity;
    private String mExpirationDate;
    private Product mProductInfo;

    public InventoryItem(long inventoryId, String location, int quantity,
                         String expirationDate, Product productInfo) {
        mInventoryId = inventoryId;
        mLocation = location;
        mQuantity = quantity;
        mExpirationDate = expirationDate;
        mProductInfo = productInfo;
    }

    public long getInventoryId() {
        return mInventoryId;
    }

    public void setInventoryId(long inventoryId) {
        mInventoryId = inventoryId;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocationId(String location) {
        mLocation = location;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public String getExpirationDate() {
        return mExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        mExpirationDate = expirationDate;
    }

    public Product getProductInfo() {
        return mProductInfo;
    }

    public void setProductInfo(Product productInfo) {
        mProductInfo = productInfo;
    }
}