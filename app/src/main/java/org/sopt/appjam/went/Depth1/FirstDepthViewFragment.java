package org.sopt.appjam.went.Depth1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.Controller.Depth1Adapter;
import org.sopt.appjam.went.Depth2.Depth2Activity;
import org.sopt.appjam.went.Model.Depth1_item;
import org.sopt.appjam.went.Model.Photo;
import org.sopt.appjam.went.Model.ShopItem;
import org.sopt.appjam.went.Model.ShopResult;
import org.sopt.appjam.went.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Part;

/**
 * Created by NOEP on 15. 7. 1..
 */


//여기에 있는 통신 메소드를 메인으로 옮긴다

public class FirstDepthViewFragment extends Fragment{

    //for logging
    private static final String TAG = "FirstDepthViewFragment";


    /*
    instance statement
     */
    public static FirstDepthViewFragment newInstance() {
        FirstDepthViewFragment fragment = new FirstDepthViewFragment();
        return fragment;
    }
    /*
    Constructer with empty
     */
    public FirstDepthViewFragment() {
        //empty
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }



    /**
     * NetworkService
     * deprecated for main activity
     */
    NetworkService networkService;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstancedState) {

        View v = inflater.inflate(R.layout.fragment_first_depth_view ,container,false);

        networkService = AppController.getInstance().getNetworkService();

        initView(v);

        initModel();



        initController();

        return v;
    }

    ListView listView_result;

    private void initView(View view) {

        listView_result = (ListView) view.findViewById(R.id.listView_fragment_view);


    }



    /**
     * 모델(자료)에 대한 기술 부분입니다.
     */

    ArrayList<Depth1_item> item;
    ArrayList<ShopItem> arrayList_shopItem;
    ShopResult shopResult;

    private void initModel() {

        arrayList_shopItem = new ArrayList<>();
        item = new ArrayList<Depth1_item>();
    }

    /**
     * 컨트롤러에 대한 부분입니다
     * <p/>
     * CustomAdapter와 ListView에다가 adapter 설정
     */

    Depth1Adapter customAdapter;

    private void initController() {

        customAdapter = new Depth1Adapter (this.getActivity());
        listView_result.setAdapter(customAdapter);




        listView_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getActivity().getApplicationContext(),"dasdadsas"+i,Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getActivity(), Depth2Activity.class);
                intent.putExtra("facebookid", item.get(i).userid);
                intent.putExtra("mothercard", item.get(i).motherid);
                intent.putExtra("depth1title", item.get(i).title);

                startActivity(intent);


                //getFromDepth2(i);


            }
        });

        listView_result.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                networkService.DeleteDepth1Card(item.get(i).motherid, new Callback<Object>() {
                    @Override
                    public void success(Object o, Response response) {

                        Log.e(TAG,"DELETE SUCCESS");

                        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(100);
                        customAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void failure(RetrofitError error) {


                        Log.e(TAG,"DELETE FAIL");


                    }
                });//method end








                return true;
            }
        });












    }

    public void setResult(ArrayList<Depth1_item> item){

        this.item=item;



        customAdapter.setSource(item);
        customAdapter.notifyDataSetChanged();

        //customAdapter.setSource((ArrayList<ShopItem>) shopResult.item);
    }






}