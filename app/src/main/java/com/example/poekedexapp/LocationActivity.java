package com.example.poekedexapp;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.poekedexapp.models.Latitude;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Location activity.
 */
public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Latitude> latitudes = new ArrayList<>();

        latitudes.add(new Latitude("Zaragoza", 41.661545,-0.894701));
        latitudes.add(new Latitude("Torre Eiffel Paris", 48.8615,-2.2893));
        latitudes.add(new Latitude("Disneyland Tokio", 35.6312,139.8809));
        latitudes.add(new Latitude("Plaza de Armas de Chancay", -11.563121,-77.270121));
        latitudes.add(new Latitude("Pier39 ", 37.8086,-122.4098));


        for (Latitude latitude : latitudes) {
            // Add a marker in Sydney and move the camera
            LatLng latLng = new LatLng(latitude.getPosX(), latitude.getPosY());
            mMap.addMarker(new MarkerOptions().position(latLng).title(latitude.getLocation()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }


    }

}