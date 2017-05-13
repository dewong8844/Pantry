package com.example.android.pantry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.pantry.data.PantryContract;
import com.example.android.pantry.data.PantryDbHelper;

public class ProductsActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        PantryDbHelper dbHelper = new PantryDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        // saveProductToDb("Kong Yen", "rice vinegar", 20.2f, "FL OZ", "vinegar", "grocery");

        Cursor cursor = getAllProducts();
        int count = cursor.getCount();
        Log.e("TAG", "Found " + count + " products.");
        for (int i=0; i < count; i++) {
            if (!cursor.moveToPosition(i)) continue;
            String brand = cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_BRAND));
            String name = cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_NAME));
            Log.e("TAG","Prod brand: " + brand + ", name: " + name);
        }

    }

    private Cursor getAllProducts() {
        return mDb.query(
                PantryContract.ProductsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PantryContract.ProductsEntry.COLUMN_PRODUCT_ID
        );
    }

    private long saveProductToDb(String brand, String name, float amount, String unit,
                               String ingredient, String category) {
        ContentValues cv = new ContentValues();
        cv.put(PantryContract.ProductsEntry.COLUMN_BRAND, brand);
        cv.put(PantryContract.ProductsEntry.COLUMN_NAME, name);
        cv.put(PantryContract.ProductsEntry.COLUMN_AMOUNT, amount);
        cv.put(PantryContract.ProductsEntry.COLUMN_UNIT, unit);
        cv.put(PantryContract.ProductsEntry.COLUMN_INGREDIENT, ingredient);
        cv.put(PantryContract.ProductsEntry.COLUMN_CATEGORY, category);

        return mDb.insert(PantryContract.ProductsEntry.TABLE_NAME, null, cv);
    }
}
