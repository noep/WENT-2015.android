package org.sopt.appjam.went.Depth2;

/**
 * Created by NOEP on 15. 7. 9..
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.Controller.Depth1Adapter;
import org.sopt.appjam.went.Controller.Depth2Adapter;
import org.sopt.appjam.went.Depth3.Depth3Activity;
import org.sopt.appjam.went.Model.Depth1_item;
import org.sopt.appjam.went.Model.Depth2_item;
import org.sopt.appjam.went.Model.ShopItem;
import org.sopt.appjam.went.Model.ShopResult;
import org.sopt.appjam.went.R;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.Controller.Depth1Adapter;
import org.sopt.appjam.went.Depth2.Depth2Activity;
import org.sopt.appjam.went.Model.Depth1_item;
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

public class Depth2ViewFragment extends Fragment {

    //for logging
    private static final String TAG = "Depth2ViewFragment";


    Activity mActivity;


    /*
    instance statement
     */
    public static Depth2ViewFragment newInstance() {
        Depth2ViewFragment fragment = new Depth2ViewFragment();
        return fragment;
    }
    /*
    Constructer with empty
     */
    public Depth2ViewFragment() {
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

        View v = inflater.inflate(R.layout.fragment_depth2_view ,container,false);

        networkService = AppController.getInstance().getNetworkService();

        initView(v);

        initModel();



        initController();

        return v;
    }

    ImageView depth1ImageView;
    TextView depth1TextView;

    GridView gridView;

    private void initView(View view) {

        gridView = (GridView) view.findViewById(R.id.gridview_depth2_fragment);



        depth1ImageView = (ImageView) view.findViewById(R.id.imageView_depth1Image);
        depth1TextView = (TextView) view.findViewById(R.id.textView_depth1Title);




       // R.id.textView_depth1title //소름 이거뭐지




    }



    /**
     * 모델(자료)에 대한 기술 부분입니다.
     */

    ArrayList<Depth2_item> item;

    private void initModel() {

        item = new ArrayList<Depth2_item>();
    }

    /**
     * 컨트롤러에 대한 부분입니다
     * <p/>
     * CustomAdapter와 ListView에다가 adapter 설정
     */

    Depth2Adapter customAdapter;

    private void initController() {

        customAdapter = new Depth2Adapter (this.getActivity());
        gridView.setAdapter(customAdapter);


       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


               Intent intent = new Intent(getActivity(), Depth3Activity.class);

               intent.putExtra("title", String.valueOf(item.get(i).title));
               intent.putExtra("secondid", String.valueOf(item.get(i).secondid));
               intent.putExtra("time", String.valueOf(item.get(i).time));
               intent.putExtra("content", String.valueOf(item.get(i).content));

               startActivity(intent);







           }
       });






        //listView_result.setAdapter(customAdapter);
        //listView_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    }


    public void setResult(ArrayList<Depth2_item> item) {
        this.item = item;
        customAdapter.setSource(item);
        Log.e(TAG,"shit "+ this.item.get(0).toString());


    }

    public void callAdapter(){


        customAdapter.notifyDataSetChanged();
    }


    public void setDepth1params(int mothercard ,String depth1title) {

        Log.e(TAG,"error"+mothercard+depth1title);




        Glide.with(mActivity.getApplicationContext())
                .load(AppController.getEndpoint().concat("/mothercard/image/").concat(String.valueOf(mothercard)))
                .into(depth1ImageView);


        Typeface tf = Typeface.createFromAsset(mActivity.getAssets(), "ygd340.ttf");



        this.depth1TextView.setTypeface(tf);
        this.depth1TextView.setText(depth1title);


    }


    @Override
    public void onAttach(Activity activity){

            super.onAttach(activity);
            mActivity = activity;




    }




}