package org.sopt.appjam.went.Communication;

/**
 * Created by NOEP on 15. 7. 1..
 */

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;


/**
 * Application 전체를 관리하게 해주는 Class입니다.
 *
 * 4개의 컴포넌트(Activity, CP, BR, Service)보다 먼저 App이 실행될 때 생성이됩니다.
 */


/**

여기는 클라쪽에서 처리하는 부분인데, 앞에 endpoint, 맨 마지막에 apikey를 추가하는 부분만 보시면 됩니다

 */

public class AppController extends Application {


    /**
     * 현재 이 AppController 객체를 가리키는 instance라는 변수와
     *
     * 인스턴스를 가져오기 위한 getInstance() 메소드 생성성
     *
     */

    private static AppController instance;


    /**
     *
     * 이 위치에 나중에 최종 서버 주소+포트를 적어주면 됩니다.
     */
    private static final String ENDPOINT = "http://192.168.0.17:3000";
    //public  static final String ENDPOINT = "https://apis.daum.net";

    public static AppController getInstance(){return instance;}


    /**
     * App이 생성될 때 실행되는 onCreate() 메소드
     */
    public void onCreate(){

        super.onCreate();

        AppController.instance = this;
        AppController.instance.init();

    }


    /**
     * NetworkService를 가리키는 networkService라는 변수와
     *
     * 인스턴스를 가져오기 위한 getNetworkSercie() 메소드 생성
     */
    private NetworkService networkService;
    public NetworkService getNetworkService() {return networkService;}


    /**
     * 네트워킹을 위한 NetworkService 객체를 만드는 과정입니다.
     *
     * RestAdapter와 Builder의 상세한 내용은 Reference 참조
     *
     * Builder는 RestAdapter를 위한 기반 정보들을 만드는 객체이며
     *
     * 마지막에 builder.build()를 통해서 adapter 객체를 만들고
     *
     * NetworkService를 어댑터를 통해서 만듭니다.
     */
    private void init(){

        /**
         * 쿠키 값들을 저장하기 위한 CookieManger와 Http 처리 라이브러리인 OkHttp를 이용해서 쿠키를 남기게 합니다.
         */

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(cookieManager);

        RestAdapter.Builder builder = new RestAdapter.Builder();
       // builder.setEndpoint("https://apis.daum.net");
        builder.setEndpoint(ENDPOINT);

        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

                //네트워크를 요청하기 전에 마지막 처리르 여기서 해줄 수 있다고 합니다 헤더나 등등


                //네트웍 처리전 마지막 부분인데 나중에 밑의 메소드를 주석처리하면 됩니다.
                //request.addQueryParam("apikey", NetworkService.API_KEY);

            }
        });

        builder.setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS);
        builder.setClient(new OkClient(client));

        RestAdapter adapter = builder.build();
        networkService = adapter.create(NetworkService.class);




    }


}
