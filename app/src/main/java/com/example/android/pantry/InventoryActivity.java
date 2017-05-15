package com.example.android.pantry;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.pantry.data.Inventory;
import com.example.android.pantry.data.PantryContract;
import com.example.android.pantry.data.PantryDbHelper;
import com.example.android.pantry.data.Product;

public class InventoryActivity extends AppCompatActivity {
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        PantryDbHelper dbHelper = new PantryDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        // saveProductToDb(mDb, "Kong Yen", "rice vinegar", 20.2f, "FL OZ", "vinegar", "grocery");

        // get all products, populate the recycler view
        Inventory inventory = new Inventory();
        Cursor cursor = inventory.getInventory(mDb);
        int count = cursor.getCount();
        Log.i(this.toString(), "Found " + count + " products.");
        for (int i=0; i < count; i++) {
            if (!cursor.moveToPosition(i)) continue;
            String productId = cursor.getString(cursor.getColumnIndex(PantryContract.InventoryEntry.COLUMN_PRODUCT_ID));
            String quantity = cursor.getString(cursor.getColumnIndex(PantryContract.InventoryEntry.COLUMN_QUANTITY));
            Log.i(this.toString(), "Prod id: " + productId + ", quantity: " + quantity);
        }

    }
}
