package org.sopt.appjam.went.Depth1;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.sopt.appjam.went.Model.Depth1_item;
import org.sopt.appjam.went.Model.Depth2_item;
import org.sopt.appjam.went.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


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
    static View v;
    static final LatLng Seoul = new LatLng(37.58528260494513, 126.98605588363266);
    int MaxSize;
    int i = 0;
    ArrayList<LatLng> pointList = new ArrayList<LatLng>();
    PolylineOptions rectOptions = new PolylineOptions();
    Polyline polyline;

    public GoogleMap mMap; // Might be null if Google Play services APK is not available.

    ArrayList<Depth1_item> list;



    public static MapFragment newInstance() {

        MapFragment fragment = new MapFragment();
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //pointList.add(new LatLng(37.405382, 127.119245));
        //pointList.add(new LatLng(37.421315, 127.141235));
        //pointList.add(new LatLng(37.346094, 127.147644));
        //pointList.add(new LatLng(37.274973, 127.014655));
        //pointList.add(new LatLng(37.375171, 127.021386));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstancedState) {

        list = new ArrayList<Depth1_item>();



        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
        }

        v = inflater.inflate(R.layout.fragment_map, container, false);

        try{
            v = inflater.inflate(R.layout.fragment_map, container, false);
        }
        catch (InflateException e){
            //setUpMapIfNeeded();
        }


        new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
            @Override
            public void run() {
                // 실행할 동작 코딩

                setUpMapIfNeeded();

            }
        }, 2000);










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

        try {
            mMap = ((com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        int i = 0;
        while(i != MaxSize){

            try {
                mMap.addMarker(new MarkerOptions().position(pointList.get(i))
                                                  .title(list.get(i).title)
                                                  .icon(BitmapDescriptorFactory.fromResource(R.mipmap.went_mainmap_picker)));
                //mMap.addMarker(new MarkerOptions().position(pointList.get(i))
                //                                  .title(list.get(i).title));


            } catch  (ArrayIndexOutOfBoundsException e){

                Log.e(TAG,"map die");
                e.printStackTrace();

            } catch (NullPointerException e){

            } catch (Exception e){

            }



            if(polyline == null)
                rectOptions.add(pointList.get(i));

            i++;
        }

        //polyline = mMap.addPolyline(rectOptions);
        //polyline.setColor(Color.RED);

        try {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(setMidPoint(), 15));




            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 500, null);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(setMidPoint())
                    .zoom(11)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            mMap.getUiSettings().setMapToolbarEnabled(true);
            i = 0;

        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e. printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    public LatLng setMidPoint(){

        LatLng northeast = pointList.get(0);
        LatLng southwest = pointList.get(0);

        for(int i = 0 ; i != MaxSize ; i++)
            if(northeast.longitude < pointList.get(i).longitude)
                northeast = pointList.get(i);

        for(int i = 0 ; i != MaxSize ; i++)
            if(southwest.longitude > pointList.get(i).longitude)
                southwest = pointList.get(i);


        LatLngBounds bound = new LatLngBounds(southwest, northeast);


        return bound.getCenter();

    }

   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = (Fragment)getFragmentManager().findFragmentById(R.id.map);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();

        Log.e(TAG, "onDestroy");
    }*/



   public void setResult(ArrayList<Depth1_item> list){

       this.list = list;

       for (int i =0; i < list.size(); i++ ){

           try {
               pointList.add(new LatLng(list.get(i).lat, list.get(i).lon));
               Log.e(TAG, "Userid in? " + this.list.get(i).title.toString() + " " + this.list.get(i).lat.toString() + " " + this.list.get(i).lon.toString());
           } catch (NullPointerException e) {

               e.printStackTrace();
           }


           }
       MaxSize=list.size();
       //MaxSize=pointList.size();

   }


}
