package org.sopt.appjam.went;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.Controller.Depth1Adapter;
import org.sopt.appjam.went.Model.ShopItem;
import org.sopt.appjam.went.Model.ShopResult;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    ArrayList<ShopItem> arrayList_shopItem;
    ShopResult shopResult;

    private void initModel() {

        arrayList_shopItem = new ArrayList<>();

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

    }

    public void setResult(ShopResult res){
        this.shopResult=res;
        customAdapter.setSource((ArrayList<ShopItem>) shopResult.item);
    }







}