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
        viewListener();
        initModel();
        initController();

        return v;
    }

    EditText editText_query;
    Button button_search;
    ListView listView_result;

    private void initView(View view) {

        editText_query = (EditText) view.findViewById(R.id.editText_query);
        button_search = (Button) view.findViewById(R.id.button_search);
        listView_result = (ListView) view.findViewById(R.id.listView_fragment_view);


    }



    /**
     * 버튼에 대한 리스너를 따로 빼서 구현했습니다.
     */
    private void viewListener() {

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFromServer();

            }
        });

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


    /**
     * 서버로부터 자료를 가져오는 메소드입니다!!
     */

    private void getFromServer() {


        /**
         * EditText에 입력한 값을 받아와서 q에 저장!
         */

        String q = editText_query.getText().toString();

        /**
         * String q에 값이 있는지 확인
         */
        if (TextUtils.isEmpty(q))
            return;


        /**
         * HashMap 클래스의 객체에다가 query로 날릴 변수들을
         *
         * key - value 구조로 넣어줍니다.
         */

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("q", q);
        parameters.put("result", "15");
        parameters.put("pageno", "1");
        parameters.put("sort", "pop");
        parameters.put("output", "json");


        /**
         * 비동기 처리로 네트워킹을 합니다.
         *
         * parameter로 query에 대한 내용을 전달합니다.(위에 해시맵)
         */
        networkService.getDataAsync(parameters, new Callback<Object>() {


            /**
             *
             * @param o : 성공시에 응답 결과로 받은 객체
             * @param response : 성공시에 오는 response라고 합니다
             */
            @Override
            public void success(Object o, Response response) {

                /**
                 * 파싱을 위해 Gson 객체를 이용합니다.
                 *
                 * 그 후 받은 객체 o 를 Json형태로 만들어서 jsonString으로 저장(String 형태로).
                 */

                Gson gson = new Gson();

                String jsonString = gson.toJson(o);

                try {

                    JSONObject json = new JSONObject(jsonString);
                    // jsonString을 JSON객체로 만들고

                    json = json.getJSONObject("channel");
                    // channel 안에 있는 JSON 객체를 가져옵니다! => 다음 api 예시 참고

                    shopResult = gson.fromJson(json.toString(), ShopResult.class);
                    // 그 후 json을 문자열 형태로 받아와서 GSON을 이용해 shopResult에 매핑해줍니다.

                    customAdapter.setSource((ArrayList<ShopItem>) shopResult.item);
                    // 그 결과에서 item를 어댑터를 통해서 리스트뷰의 source로 지정해줍니다.

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("ERROR", "Error : " + error.getUrl() + ">>>>" + error.getMessage());

            }

        });


    }
}