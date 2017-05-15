package com.example.android.pantry.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by dewong4 on 5/14/17.
 */

public class Product {

    public Cursor getAllProducts(SQLiteDatabase db) {
        return db.query(
                PantryContract.ProductsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PantryContract.ProductsEntry.COLUMN_PRODUCT_ID
        );
    }

    public Cursor getProduct(SQLiteDatabase db, int productId) {
        String selection =
                PantryContract.ProductsEntry.COLUMN_PRODUCT_ID + " = ?";
         String[] selectionArgs = { Integer.toString(productId) };

        Cursor cursor = db.query(
                PantryContract.ProductsEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                PantryContract.ProductsEntry.COLUMN_PRODUCT_ID
        );

        return cursor;
    }

    private boolean productExists(SQLiteDatabase db, String brand, String name, float amount) {
        String[] projection = {
                PantryContract.ProductsEntry.COLUMN_BRAND,
                PantryContract.ProductsEntry.COLUMN_NAME,
                PantryContract.ProductsEntry.COLUMN_AMOUNT
        };
        String selection =
                PantryContract.ProductsEntry.COLUMN_BRAND + " = ? AND " +
                        PantryContract.ProductsEntry.COLUMN_NAME + " = ? AND " +
                        PantryContract.ProductsEntry.COLUMN_AMOUNT + " = ?";
        String[] selectionArgs = { brand, name, Float.toString(amount) };

        Cursor cursor = db.query(
                PantryContract.ProductsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                PantryContract.ProductsEntry.COLUMN_PRODUCT_ID
        );

        return (cursor.getCount() >= 1);   // TODO: what if there is more than 1 ?
    }

    public long saveToDb(SQLiteDatabase db, String brand, String name,
                                float amount, String unit,
                                String ingredient, String category) {

        ContentValues cv = new ContentValues();
        cv.put(PantryContract.ProductsEntry.COLUMN_BRAND, brand);
        cv.put(PantryContract.ProductsEntry.COLUMN_NAME, name);
        cv.put(PantryContract.ProductsEntry.COLUMN_AMOUNT, amount);
        cv.put(PantryContract.ProductsEntry.COLUMN_UNIT, unit);
        cv.put(PantryContract.ProductsEntry.COLUMN_INGREDIENT, ingredient);
        cv.put(PantryContract.ProductsEntry.COLUMN_CATEGORY, category);

        if (productExists(db, brand, name, amount)) {
            // update the product in db
            String selection =
                    PantryContract.ProductsEntry.COLUMN_BRAND + " = ? AND " +
                            PantryContract.ProductsEntry.COLUMN_NAME + " = ? AND " +
                            PantryContract.ProductsEntry.COLUMN_AMOUNT + " = ?";
            String[] selectionArgs = { brand, name, Float.toString(amount) };

            Log.i(this.toString() + " saveProductToDb() ", ", updated brand: " + brand +
                    " name: " + name);

            return db.update(
                    PantryContract.ProductsEntry.TABLE_NAME,
                    cv,
                    selection,
                    selectionArgs);


        } else {
            // insert new product into the db
            return db.insert(PantryContract.ProductsEntry.TABLE_NAME, null, cv);
        }
    }

}
