package com.example.ciclistasgms;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ciclistasgms.utilities.Utilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private boolean mPermissionDenied = false;
    //Double longitudOrigen, latitudOrigen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                Double latitudOrigen = location.getLatitude();
                Double longitudOrigen = location.getLongitude();
                //LatLng sydney = new LatLng(latitudOrigen, longitudOrigen);
                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                String strlatitud = Double.toString(latitudOrigen);
                String strlongitud = Double.toString(longitudOrigen);
                UsersRegister(strlatitud ,strlongitud);
                consultar();

                }
        });

    }

    private void consultar() {
        ConecionSQLiteHelper conn = new ConecionSQLiteHelper(this, "db_user4",null,6);
        SQLiteDatabase db = conn.getWritableDatabase();
        String selectQuery = "SELECT * FROM "+ Utilities.TableUser +" ORDER BY "+ Utilities.Id +" DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        Toast.makeText(this, "Posicion:\n" + cursor.getString(0), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Latitud:\n" + cursor.getString(2), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Longitud:\n" + cursor.getString(3), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }



    //coneccion  a la base de datos
    private void UsersRegister(String longitude2, String latitudeg2 ) {
        String name = "'felipe'";
        Date actual= Calendar.getInstance().getTime();
        actual.getTime();
        ConecionSQLiteHelper conn = new ConecionSQLiteHelper(this, "db_user4",null,6);
        SQLiteDatabase db = conn.getWritableDatabase();
        String insert = "INSERT INTO "+ Utilities.TableUser+
                " ("+Utilities.Name+","+Utilities.Latitude+","+Utilities.Longitude+","+
                Utilities.Date+")"+" VALUES "+" ("+name+","+longitude2+","+latitudeg2+","+
                "'"+actual.toString()+"')";
        db.execSQL(insert);
        db.close();

    }
}
