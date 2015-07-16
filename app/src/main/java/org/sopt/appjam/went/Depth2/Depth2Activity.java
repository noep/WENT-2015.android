package org.sopt.appjam.went.Depth2;

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

import org.json.JSONObject;
import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.Controller.BackPressCloseHandler;
import org.sopt.appjam.went.Depth1.MapFragment;
import org.sopt.appjam.went.FacebookLoginActivity;
import org.sopt.appjam.went.Model.Depth1_item;
import org.sopt.appjam.went.Model.Depth2_item;
import org.sopt.appjam.went.R;
import org.sopt.appjam.went.Splash;
import org.sopt.appjam.went.UserInformationFragment;

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








public class Depth2Activity extends AppCompatActivity
{

    //for facebook info
    private static String TAG = "Depth2Activity";

    //fab divide
    boolean flag = true;

    private static Depth2ViewFragment depth2ViewFragment;
    private static Depth2MapFragment depth2MapFragment;

    private static FloatingActionButton fab;





    String facebookinfo;
    int mothercard;
    String depth1title;


    //Network connection
    NetworkService networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startActivity(new Intent(this, Splash.class));

        setContentView(R.layout.activity_depth2);

        Intent intent = getIntent();
        facebookinfo = (String) intent.getSerializableExtra("facebookid");
        mothercard = (int) intent.getSerializableExtra("mothercard");
        depth1title = (String) intent.getSerializableExtra("depth1title");

        Log.e(TAG,facebookinfo+" "+String.valueOf(mothercard));
        Log.e(TAG,"onCreate");
        //Network Connection
        networkService = AppController.getInstance().getNetworkService();

        setTitle("");
        setToolbar();
        setFAB();

        //Add Fragment
        //viewFragment = FirstDepthViewFragment.newInstance();
        depth2MapFragment = Depth2MapFragment.newInstance();
        depth2ViewFragment = Depth2ViewFragment.newInstance();

        // if insert viweFragment instead of mapFragment change fragment
        getFragmentManager().beginTransaction().replace(R.id.fragment_main, depth2ViewFragment).commit();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getFromDepth2();
        depth2ViewFragment.setDepth1params(mothercard,depth1title);

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
                //fab.setEnabled(false);
                //fab.setVisibility(View.GONE);

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
                      //  toolbar.setTitle("Map");
                        toolbar.setNavigationIcon(R.mipmap.went_listmap_icon_cardview);

                        getFragmentManager().beginTransaction().replace(R.id.fragment_main, depth2MapFragment).commit();
                        fab.setEnabled(true);
                        fab.setVisibility(View.VISIBLE);
                        flag = false;
                    }

                    else {
                    //    toolbar.setTitle("List");
                        toolbar.setNavigationIcon(R.mipmap.went_main_button_map);

                        // 맵에서 앱 종료시 이미 파괴된 fragment라고 나오는 것 해결하기 위해 viewFragment로 갈 때에만 파괴해줌.
                        Fragment f = getFragmentManager().findFragmentById(R.id.map);
                        if (f != null)
                            getFragmentManager().beginTransaction().remove(f).commit();

                        getFragmentManager().beginTransaction().replace(R.id.fragment_main, depth2ViewFragment).commit();

                        fab.setEnabled(true);
                        fab.setVisibility(View.VISIBLE);
                        flag = true;

                        //TODO: 통신처리를 여러번 하게 되면 별로 무의미할수도 있다 잘 판단해봐
                    }
                getFromDepth2();
                //depth2ViewFragment.setDepth1params(mothercard,depth1title);

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


                /**
                 * 현재는 AddActivity 하나만 존재, 뎁스2 카드 추가부분 존재하지 않음
                 * use flag to divide
                 */
                //intent.putExtra("facebookid", facebookinfo);
                Intent intent = new Intent(getApplicationContext(), Depth2AddActivity.class);
                intent.putExtra("facebookid",facebookinfo);
                intent.putExtra("mothercard",mothercard);
                startActivity(intent);



            }
        });
    } //method end


    private void getFromDepth2() {

        /**

         아래에 정의되어 있는 메소드는 인터페이스 NetworkService에 기술되어 있습니다.
         retrofit 문서를 확인해보시면 알겠지만, 비동기처리 형태는 메소드에 자신이 필요한 파라미터+콜백함수를 넣어주는 구조입니다.
         */

        networkService.getDataAsync(facebookinfo,mothercard, new Callback<ArrayList<Depth2_item>>() {
            /**
             *
             * @param list : 성공시에 응답 결과로 받은 객체
             * @param response : 성공시에 오는 response라고 합니다
             */
            @Override
            public void success(ArrayList<Depth2_item> list, Response response) {
                depth2ViewFragment.setDepth1params(mothercard,depth1title);


                depth2ViewFragment.setResult(list);
                depth2MapFragment.setResult(list);

                Log.e(TAG, "Success" + list);
                depth2ViewFragment.callAdapter();






            }
            @Override
            public void failure(RetrofitError error) {

                Log.e("ERROR", "Error : " + error.getUrl() + ">>>>" + error.getMessage());

            }

        });


    }


}
