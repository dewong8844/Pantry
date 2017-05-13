package com.example.android.pantry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.pantry.scanner.BarcodeScannerActivity;

public class MainActivity extends AppCompatActivity {
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    // Called when user taps the Scan button
    public void startScanActivity(View view) {
        launchActivity(BarcodeScannerActivity.class);
    }

    // Called when user taps the Show Products button
    public void startProductsActivity(View view) {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
    }

    // Called when user taps the Show Inventory button
    public void startInventoryActivity(View view) {
        Log.e("TAG", "Show Inventory not implemented yet!");
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the Barcode Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}