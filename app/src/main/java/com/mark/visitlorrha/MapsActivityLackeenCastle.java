package com.mark.visitlorrha;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivityLackeenCastle extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity_lackeen_castle);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows the up/back button in the action bar

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // gets user's location, only if they have accepted the location permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // attractions' lat and lng
        LatLng dominicanAbbey = new LatLng(53.0908735,-8.1257745);
        LatLng augustinianAbbey = new LatLng(53.091964, -8.121302);
        LatLng stRuadhansAbbey = new LatLng(53.091366, -8.120532);
        LatLng lackeenCastle = new LatLng(53.088723, -8.073593);
        LatLng redwoodCasatle = new LatLng(53.138997, -8.105851);

        mMap.addMarker(new MarkerOptions()
                .position(dominicanAbbey)
                .title("Dominican Abbey"));

        mMap.addMarker(new MarkerOptions()
                .position(augustinianAbbey)
                .title("Augustinian Abbey"));

        mMap.addMarker(new MarkerOptions()
                .position(stRuadhansAbbey)
                .title("St. Ruadhan's Abbey"));

        mMap.addMarker(new MarkerOptions()
                .position(lackeenCastle)
                .title("Lackeen Castle")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(redwoodCasatle)
                .title("Redwood Castle"));

        //positioning the camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lackeenCastle, 12)); //centres camera and zooms in

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // to show up/back button in the action bar
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
