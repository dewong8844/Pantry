package com.example.android.pantry.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by dewong4 on 5/14/17.
 */

public class Barcode {
    public Cursor getBarcode(SQLiteDatabase db, String value) {
        String selection =
                PantryContract.BarcodesEntry.COLUMN_VALUE + " = ?";
        String[] selectionArgs = { value };

        Cursor cursor = db.query(
                PantryContract.BarcodesEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                PantryContract.BarcodesEntry.COLUMN_BARCODE_ID
        );

        return cursor;
    }

    private boolean barcodeExists(SQLiteDatabase db, String value) {
        String[] projection = { PantryContract.BarcodesEntry.COLUMN_VALUE };
        String selection =
                PantryContract.BarcodesEntry.COLUMN_VALUE + " = ?";
        String[] selectionArgs = { value };

        Cursor cursor = db.query(
                PantryContract.BarcodesEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                PantryContract.BarcodesEntry.COLUMN_BARCODE_ID
        );

        return (cursor.getCount() >= 1);  // TODO: what if there is more than 1 ?
    }

    public long saveBarcodeToDb(SQLiteDatabase db, String value, String type, int productId) {

        ContentValues cv = new ContentValues();
        cv.put(PantryContract.BarcodesEntry.COLUMN_VALUE, value);
        cv.put(PantryContract.BarcodesEntry.COLUMN_TYPE, type);
        cv.put(PantryContract.BarcodesEntry.COLUMN_PRODUCT_ID, productId);

        if (barcodeExists(db, value)) {
            // update the barcode in db
            String selection =
                    PantryContract.BarcodesEntry.COLUMN_VALUE + " = ? ";
            String[] selectionArgs = { value };

            Log.i(this.toString() + " saveProductToDb() ", ", updated barcode: " + value);

            return db.update(
                    PantryContract.BarcodesEntry.TABLE_NAME,
                    cv,
                    selection,
                    selectionArgs);


        } else {
            // insert new product into the db
            return db.insert(PantryContract.BarcodesEntry.TABLE_NAME, null, cv);
        }
    }


}
