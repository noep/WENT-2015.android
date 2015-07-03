package org.sopt.appjam.went;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.Model.ShopItem;
import org.sopt.appjam.went.Model.ShopResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity  implements UserInformationFragment.OnFragmentInteractionListener{


    //for facebook info
    CallbackManager callbackManager;
    private static String TAG = "MainActivity";

    //fab divide
    boolean flag = true;

    private static MapFragment mapFragment;
    private static FirstDepthViewFragment viewFragment;
    private static UserInformationFragment infoFragment;

    private static FloatingActionButton fab;


    ShopResult shopResult;


    String facebookinfo;


    //Network connection
    NetworkService networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Network Connection
        networkService = AppController.getInstance().getNetworkService();

        setTitle("WENT");
        setToolbar();
        setFAB();
        getFacbookInfo();










        //Add Fragment

        mapFragment = MapFragment.newInstance();
        viewFragment = FirstDepthViewFragment.newInstance();






        //infoFragment = UserInformationFragment.newInstance("str1","str2");

        // if insert viweFragment instead of mapFragment change fragment
        getFragmentManager().beginTransaction().replace(R.id.fragment_main, viewFragment).commit();



        //Need to Communication get List of Depth1 informations
        getFromServer();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }



    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();


        if(AccessToken.getCurrentAccessToken() == null){

            Intent intent = new Intent(getApplicationContext(), FacebookLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }










    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings :
                Toast.makeText(getApplicationContext(),"facebookfragment deprecated",Toast.LENGTH_SHORT).show();
                fab.setEnabled(false);
                fab.setVisibility(View.GONE);
                //getFragmentManager().beginTransaction().replace(R.id.fragment_main, infoFragment).commit();
                //getFragmentManager().beginTransaction().replace(R.id.fragment_main, viewFragment).commit();
                break;
        }
        return true;
    }
    /*

    need for facebook fragment
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    private void setToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_list);
        toolbar.setTitleTextColor(Color.WHITE);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag) {
                        toolbar.setTitle("List");
                        toolbar.setNavigationIcon(R.mipmap.ic_map);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_main, viewFragment).commit();
                        fab.setEnabled(true);
                        fab.setVisibility(View.VISIBLE);
                        flag = false;
                    }

                    else {
                        toolbar.setTitle("Map");
                        toolbar.setNavigationIcon(R.mipmap.ic_list);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_main, mapFragment).commit();
                        fab.setEnabled(true);
                        fab.setVisibility(View.VISIBLE);
                        flag = true;
                    }
                }
            });
        }
    } //method end
    //floating action button
    private void setFAB() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                use flag to divide
                 */
                //Toast.makeText(view.getContext(),"test"+flag ,Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(),"test "+facebookinfo ,Toast.LENGTH_SHORT).show();
            }
        });




    } //method end




    private void getFacbookInfo(){

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        Bundle parameter = new Bundle();
        parameter.putString("field", "id,name,link");

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                /*
                only need facebook id
                 */

                //ProfilePictureView profilePictureView;
                String facebook_id;//facebook_name, facebook_link;
                facebook_id= (String)(jsonObject.optString("id"));
                //facebook_name = jsonObject.optString("name");
                //facebook_link = jsonObject.optString("link");
                facebookinfo = facebook_id;// + " " + facebook_name + " " + facebook_link;
            }
        });
        request.setParameters(parameter);
        request.executeAsync();
    } // method end


    /**
     * 서버로부터 자료를 가져오는 메소드입니다!!
     */

    private void getFromServer() {


        /**
         * EditText에 입력한 값을 받아와서 q에 저장!
         */

        String q = "starbucks";

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
                    viewFragment.setResult(shopResult);

                    //로그 들어가는거 확인
                    //Log.e(TAG,shopResult.item.get(0).title.toString());
                    //Log.e(TAG,viewFragment.shopResult.item.get(0).title.toString());
                    // 그 후 json을 문자열 형태로 받아와서 GSON을 이용해 shopResult에 매핑해줍니다.

                    //customAdapter.setSource((ArrayList<ShopItem>) shopResult.item);
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
