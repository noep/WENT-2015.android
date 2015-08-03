package org.sopt.appjam.went.Depth1;

import android.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.Controller.BackPressCloseHandler;
import org.sopt.appjam.went.Depth2.Depth2Activity;
import org.sopt.appjam.went.FacebookLoginActivity;
import org.sopt.appjam.went.Model.Depth1_item;
import org.sopt.appjam.went.R;
import org.sopt.appjam.went.Splash;
import org.sopt.appjam.went.UserInformationFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 *
 * 제일 먼저 실행되는 액티비티입니다. OnCreate 안에 맨 마지막에
 *
 * getFromServer 메소드를 실행하면, 현재 세팅되어 있는 대로, 다음api+쿼리+키값 을 세팅해서 Get메소드로 쏩니다.
 *
 *
 *
 */


public class MainActivity extends AppCompatActivity  implements
        UserInformationFragment.OnFragmentInteractionListener
//        OnFacebookIDLoadListener //DEPRECATED
{


    private static String TAG = "MainActivity";

    //fab divide
    boolean flag = true;

    private static MapFragment mapFragment;
    private static FirstDepthViewFragment viewFragment;
    private static FloatingActionButton fab;
    private BackPressCloseHandler backPressCloseHandler;





//    //for facebook info
    CallbackManager callbackManager;
    String facebookinfo;



    ArrayList<Depth1_item> list;


    //Network connection
    NetworkService networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, Splash.class));

        setContentView(R.layout.activity_main);


        Log.e(TAG, "onCreate");
        //Network Connection
        networkService = AppController.getInstance().getNetworkService();

        setTitle("");
        setToolbar();
        setFAB();




        getFacebookInfo();

        list = new ArrayList<Depth1_item>();

        //Backbutton Handler
        backPressCloseHandler = new BackPressCloseHandler(this);

        //Add Fragment
        mapFragment = MapFragment.newInstance();
        viewFragment = FirstDepthViewFragment.newInstance();



        // if insert viweFragment instead of mapFragment change fragment
        getFragmentManager().beginTransaction().replace(R.id.fragment_main, viewFragment).commit();


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

//        synchronized (MainActivity.class) {
//            getFromServer();
//        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings :
               // Toast.makeText(getApplicationContext(),"facebookfragment deprecated"+facebookinfo,Toast.LENGTH_SHORT).show();
               // fab.setEnabled(false);
               // fab.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"이양희 유태훈 안희석 이동열 김지욱 홍주영 김민제 박지영 문지현 박도윤 8조 ADND",Toast.LENGTH_SHORT).show();





                //getFragmentManager().beginTransaction().replace(R.id.fragment_main, infoFragment).commit();
                //getFragmentManager().beginTransaction().replace(R.id.fragment_main, viewFragment).commit();
                break;
        }
        return true;
    }

    /**

    need for facebook fragment
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
    }


    private void setToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.went_main_button_map);

        //toolbar.setTitleTextColor(Color.WHITE);
        //toolbar.getBackground().setAlpha(50);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag) {
                        //toolbar.setTitle("Map");
                        toolbar.setNavigationIcon(R.mipmap.went_listmap_icon_cardview);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_main, mapFragment).commit();
                        fab.setEnabled(true);
                        fab.setVisibility(View.VISIBLE);
                        flag = false;
                    }

                    else {
                        //toolbar.setTitle("List");
                        toolbar.setNavigationIcon(R.mipmap.went_main_button_map);

                        // 맵에서 앱 종료시 이미 파괴된 fragment라고 나오는 것 해결하기 위해 viewFragment로 갈 때에만 파괴해줌.
                        Fragment f = getFragmentManager().findFragmentById(R.id.map);
                        if (f != null)
                            getFragmentManager().beginTransaction().remove(f).commit();

                        getFragmentManager().beginTransaction().replace(R.id.fragment_main, viewFragment).commit();

                        fab.setEnabled(true);
                        fab.setVisibility(View.VISIBLE);
                        flag = true;

                        //TODO: 통신처리를 여러번 하게 되면 별로 무의미할수도 있다 잘 판단해봐
                    }
                    getFromServer();

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
                //Toast.makeText(view.getContext(),"test"+flag ,Toast.LENGTH_SHORT).show();
                //Toast.makeText(view.getContext(),"test "+facebookinfo ,Toast.LENGTH_SHORT).show();

                /**
                 * 현재는 AddActivity 하나만 존재, 뎁스2 카드 추가부분 존재하지 않음
                 * use flag to divide
                 */
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra("facebookid", facebookinfo);
                startActivity(intent);
            }
        });
    } //method end


    /**
     *
     * 페이스북 콜백을 이용해서 페이스북 아이디를 받아냅니다 형태는 스트링으로 받고, 나중에 서버에 쏠 때에는 안정해지기는 했지만
     * long이나 필요한 형변환이 필요할수도 있습니다. 그건 여기서 해서 보내주는 부분이라 서버파트와 협의만 되면 크게 문제는 없을껍니다.
     *
     */

    private void getFacebookInfo(){


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {

                Bundle parameter = new Bundle();
                parameter.putString("field", "id,name,link");

                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                /*
                only need facebook id
                 */         try {
                            //ProfilePictureView profilePictureView;
                            String facebook_id;//facebook_name, facebook_link;
                            facebook_id = (String) (jsonObject.optString("id"));
                            //facebook_name = jsonObject.optString("name");
                            //facebook_link = jsonObject.optString("link");
                            // + " " + facebook_name + " " + facebook_link;
//                            MainActivity.this.onFacebookIDLoaded(facebook_id);
                            }catch (Exception e){
                        }
                    }
                });
                request.setParameters(parameter);
                request.executeAndWait();
//            }
//        });
//        thread.start();

    } // method end




    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    /**
     * 서버로부터 자료를 가져오는 메소드입니다!!
     */

    private void getFromServer() {

        /**
         * EditText에 입력한 값을 받아와서 q에 저장!
         */
        //String q = "starbucks"; //현재는 임의의 쿼리값/다음api기준으로 세팅되어 있습니다
        //나중에 facebookinfo를 집어넣어 주면 됩니다(+형변환)
        String q = facebookinfo;

        /**
         * String q에 값이 있는지 확인
         */
        if (TextUtils.isEmpty(q)){

            Log.e(TAG,"getFromServer(): Check FacebookServer Connection");
            return;
        }

            /**
             * HashMap 클래스의 객체에다가 query로 날릴 변수들을
             *
             * key - value 구조로 넣어줍니다.
             */
            /**
            해쉬맵 형태로 쿼리를 만드는데, 우리가 사용할 api에 따라 필요 없어지는 부분이 될 겁니다.

             */
            HashMap<String, String> parameters = new HashMap<>();

            //parameters.put("q", q);
            //parameters.put("result", "15");
            //parameters.put("pageno", "1");
            //parameters.put("sort", "pop");
            //parameters.put("output", "json");


        /**
         * 비동기 처리로 네트워킹을 합니다.
         *
         * parameter로 query에 대한 내용을 전달합니다.(위에 해시맵)
         */


        /**

        아래에 정의되어 있는 메소드는 인터페이스 NetworkService에 기술되어 있습니다.
         retrofit 문서를 확인해보시면 알겠지만, 비동기처리 형태는 메소드에 자신이 필요한 파라미터+콜백함수를 넣어주는 구조입니다.
         */
        networkService.getDataAsyncForTest(facebookinfo, new Callback<ArrayList<Depth1_item>>() {

            @Override
            public void success(ArrayList<Depth1_item> list, Response response) {

                viewFragment.setResult(list);
                mapFragment.setResult(list);
                Log.e(TAG,"successs " + list.get(0).toString());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG,"Connection Fail ");
                //Toast.makeText(getApplicationContext(), "ERROR ", Toast.LENGTH_SHORT).show();
            }

        } );






    }


//DEPRECATED
//    @Override
//    public void onFacebookIDLoaded(String facebook_id) {
//
//        this.facebookinfo = facebook_id;
//
//        synchronized (MainActivity.class) {
//
//            getFromServer();
//        }
//    }
}
