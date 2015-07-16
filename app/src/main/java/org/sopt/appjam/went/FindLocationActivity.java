package org.sopt.appjam.went;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class FindLocationActivity extends FragmentActivity {

    MapFragment mapFragment;
    Button button;
    EditText edt_location;
    boolean tag;

    GpsInfo gps;
    String location;
    Address address;
    GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        Init();

        setUpMapIfNeeded();

        setEdtTextListener();

        getGpsInfo();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_location, menu);
        return true;
    }

    // An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            googleMap.clear();

            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){

                address = (Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);

                googleMap.addMarker(markerOptions);

                // Locate the first location
                if(i == 0)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = mapFragment.getMap();
            // Check if we were successful in obtaining the map.
            if (googleMap != null) {
                setUpMap();
            }
        }
    }

    private void setEdtTextListener() {


        edt_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(latLng != null && !tag) {
                    button.setText("FIND");

                    tag = true;
                }
            }

        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location = edt_location.getText().toString();

                if(tag && location != null && !location.equals("")){
                    new GeocoderTask().execute(location);

                    button.setText("SELECT");

                    tag = false;
                }

                // Select ��ư ������ ���
                else if(!tag) {
                    Intent intent = new Intent();

                    intent.putExtra("latitude", latLng.latitude);
                    intent.putExtra("longitude", latLng.longitude);

                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    private void Init(){

        gps = new GpsInfo(this);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        button = (Button) findViewById(R.id.button);
        edt_location = (EditText) findViewById(R.id.editText_location);
        tag = true;
    }

    private void setUpMap() {
        //googleMap.addMarker(new MarkerOptions().position(Seoul).title("Marker"));
    }

    private void getGpsInfo() {

        if(gps.isGetLocation()) {

            button.setText("SELECT");
            tag = false;

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // Creating a LatLng object for the current location
            latLng = new LatLng(latitude, longitude);

            // Showing the current location in Google Map
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Map �� zoom �մϴ�.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));


            // ��Ŀ ����.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(latLng);// ���� ? �浵
            optFirst.title("Current Position");// ���� �̸�����
            googleMap.addMarker(optFirst).showInfoWindow();

        }

    }

}
