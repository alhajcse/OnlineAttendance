package com.eatl.onlineattendance.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.eatl.onlineattendance.R;
import com.eatl.onlineattendance.utility.GPSTracker;
import com.eatl.onlineattendance.utility.PermissionsHelper;

public class MainActivity extends AppCompatActivity {

    GPSTracker gpsTracker;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // checkPermissions();


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        gpsTracker=new GPSTracker(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

           if(requestCode==LOCATION_PERMISSION_REQUEST_CODE){

               if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   if (ContextCompat.checkSelfPermission(MainActivity.this,
                           Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                       Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
               }
           }


    }






    @Override
    protected void onRestart() {
        super.onRestart();
        if (!gpsTracker.CheckGpsStatus()) gpsTracker.showSettingsAlert();
    }
}
