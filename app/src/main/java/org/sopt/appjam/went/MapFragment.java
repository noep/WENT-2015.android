package org.sopt.appjam.went;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("ALL")
public class MapFragment extends Fragment {
    //for logging
    private static final String TAG = "MapFragment";

    static final LatLng Seoul = new LatLng(37.58528260494513, 126.98605588363266);
    public GoogleMap mMap; // Might be null if Google Play services APK is not available.



    public static MapFragment newInstance() {

        MapFragment fragment = new MapFragment();
        return fragment;

    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //setUpMapIfNeeded();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstancedState) {

        View v = inflater.inflate(R.layout.fragment_map, container, false);
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();

            if (parent != null)
                parent.removeView(v);
        }

        try {
            v = inflater.inflate(R.layout.fragment_map, container, false);
        }
        catch (Exception e) {
          /* map is already there, just return view as it is */
        }

        setUpMapIfNeeded();

        return v;
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            //well?
            mMap = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }

            mMap.getUiSettings().setMapToolbarEnabled(true);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Seoul, 15));


        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.addMarker(new MarkerOptions().position(Seoul).title("Marker"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Fragment f = (Fragment)getFragmentManager().findFragmentById(R.id.map);

        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();

    }


}
