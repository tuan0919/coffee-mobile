package com.nlu.packages;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class StoreLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    String locationNeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SupportMapFragment mapFragment = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.mapStore);
        mapFragment.getMapAsync(this);
        locationNeed = getIntent().getStringExtra("locationOfTheStore");
        ImageButton goBack = findViewById(R.id.goBackButton);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        double lntlng1 ;
        double lntlng2 ;
        try {
            List<Address> list = geocoder.getFromLocationName(locationNeed,1);
            lntlng1 = list.get(0).getLatitude();
            lntlng2 = list.get(0).getLongitude();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LatLng location = new LatLng(lntlng1, lntlng2);
        googleMap.addMarker(new MarkerOptions().position(location).title("Dejabrew"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12));
    }
}