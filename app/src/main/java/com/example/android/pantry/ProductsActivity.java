package com.example.android.pantry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.pantry.data.PantryContract;
import com.example.android.pantry.data.PantryDbHelper;
import com.example.android.pantry.data.Product;

public class ProductsActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        PantryDbHelper dbHelper = new PantryDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        // saveProductToDb(mDb, "Kong Yen", "rice vinegar", 20.2f, "FL OZ", "vinegar", "grocery");

        // get all products, populate the recycler view
        Product product = new Product();
        Cursor cursor = product.getAllProducts(mDb);
        int count = cursor.getCount();
        Log.i(this.toString(), "Found " + count + " products.");
        for (int i=0; i < count; i++) {
            if (!cursor.moveToPosition(i)) continue;
            String brand = cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_BRAND));
            String name = cursor.getString(cursor.getColumnIndex(PantryContract.ProductsEntry.COLUMN_NAME));
            Log.i(this.toString(), "Prod brand: " + brand + ", name: " + name);
        }
    }

    @Override
    protected void onDestroy() {
        mDb.close();
        super.onDestroy();
    }

}
