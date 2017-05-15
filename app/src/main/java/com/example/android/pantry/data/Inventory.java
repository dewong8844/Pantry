package com.example.android.pantry.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by dewong4 on 5/15/17.
 */

public class Inventory {
    public Cursor getInventory(SQLiteDatabase db) {
        return db.query(
                PantryContract.InventoryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PantryContract.InventoryEntry.COLUMN_INVENTORY_ID
        );
    }

    public Cursor getInventoryByProductId(SQLiteDatabase db, int productId) {
        String selection =
                PantryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = { Integer.toString(productId) };

        Cursor cursor = db.query(
                PantryContract.InventoryEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                PantryContract.ProductsEntry.COLUMN_PRODUCT_ID
        );

        return cursor;
    }

    private boolean productExistsInInventory(SQLiteDatabase db, int productId) {
        String[] projection = {
                PantryContract.InventoryEntry.COLUMN_PRODUCT_ID
        };
        String selection = PantryContract.InventoryEntry.COLUMN_PRODUCT_ID;
        String[] selectionArgs = { Integer.toString(productId) };

        Cursor cursor = db.query(
                PantryContract.InventoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                PantryContract.InventoryEntry.COLUMN_PRODUCT_ID
        );

        return (cursor.getCount() >= 1);   // TODO: what if there is more than 1 ?
    }

    public long saveToDb(SQLiteDatabase db, int productId, int locationId,
                                int quantity, String expiration) {

        ContentValues cv = new ContentValues();
        cv.put(PantryContract.InventoryEntry.COLUMN_PRODUCT_ID, productId);

        if (productExistsInInventory(db, productId)) {
            // update the product in db
            String selection =
                    PantryContract.InventoryEntry.COLUMN_PRODUCT_ID + " = ? ";
            String[] selectionArgs = { Integer.toString(productId) };

            Log.i(this.toString() + " saveProductToDb() ", ", updated product: " + productId);

            return db.update(
                    PantryContract.InventoryEntry.TABLE_NAME,
                    cv,
                    selection,
                    selectionArgs);


        } else {
            // insert new product into the db
            return db.insert(PantryContract.InventoryEntry.TABLE_NAME, null, cv);
        }
    }

}
