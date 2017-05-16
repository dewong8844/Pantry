package com.example.android.pantry.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by dewong4 on 5/14/17.
 */

public class ProductList {

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

    public Product getProduct(SQLiteDatabase db, int productId) {
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

        if (cursor.getCount() == 0) return null;

        cursor.moveToFirst();
        Product product = new Product(
                cursor.getInt(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_BRAND)),
                cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_NAME)),
                cursor.getDouble(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_AMOUNT)),
                cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_UNIT)),
                cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_INGREDIENT)),
                cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_CATEGORY))
        );
        return product;
    }

    private boolean productExists(SQLiteDatabase db, String brand, String name, double amount) {
        String[] projection = {
                PantryContract.ProductsEntry.COLUMN_BRAND,
                PantryContract.ProductsEntry.COLUMN_NAME,
                PantryContract.ProductsEntry.COLUMN_AMOUNT
        };
        String selection =
                PantryContract.ProductsEntry.COLUMN_BRAND + "=?" + " AND " +
                        PantryContract.ProductsEntry.COLUMN_NAME + "=?" + " AND " +
                        PantryContract.ProductsEntry.COLUMN_AMOUNT + "=?";
        String[] selectionArgs = { brand, name, Double.toString(amount) };

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
                                double amount, String unit,
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
                    PantryContract.ProductsEntry.COLUMN_BRAND + "=?" + " AND " +
                            PantryContract.ProductsEntry.COLUMN_NAME + "=?" + " AND " +
                            PantryContract.ProductsEntry.COLUMN_AMOUNT + "=?";
            String[] selectionArgs = { brand, name, Double.toString(amount) };

            Log.i(this.toString() + " saveProductToDb() ", ", updated brand: " + brand +
                    " name: " + name);

            return db.update(
                    PantryContract.ProductsEntry.TABLE_NAME,
                    cv,
                    selection,
                    selectionArgs);


        } else {
            // insert new product into the db
            Log.i(this.toString() + " saveProductToDb() ", ", inserted brand: " + brand +
                    " name: " + name);

            return db.insert(PantryContract.ProductsEntry.TABLE_NAME, null, cv);

        }
    }

}
