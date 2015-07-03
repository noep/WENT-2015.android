package org.sopt.appjam.went.Communication;
/**
 * Created by NOEP on 15. 7. 1..
 */

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;


/**
 * Retrofit 예시입니다.
 *
 * 자세한 내용은 Reference인 아래 링크를 참고하세요
 *
 * http://square.github.io/retrofit/
 *
 */


public interface NetworkService {


    /**
     * 아래보이는건 API_KEY이며 현재는 app에 넣어두었지만 보안상 대체로 서버를 통해서 인증키를 받아갑니다
     *
     * Local Device -> Server(Get API_KEY) -> Request 이런식입니다.
     */

    //TODO : API_KEY 넣기

    public static final String API_KEY = "4abc597591421f87535a41b5eef18c5e";
    //public static final String API_KEY = "91e048991a4e9d0779f8191a2aed49ae";

    /**
     * GET 방식으로 요청을 하며 동기화 방식으로 자료를 요청합니다.
     *
     * @param parameters : Reference를 참고하면 나오지만 Query가 여러개이기 때문에 QueryMap 형식으로 받아서 요청!
     * @return : 가져온 객체(응답)를 반환합니다.
     */

    @GET("/shopping/search")
    Object getDataSync(@QueryMap HashMap<String, String> parameters);


    /**
     * GET 방식으로 요청을 하며 비동기 방식으로 자료를 요청합니다.
     *
     * 안드로이드에서는 내부적으로 비동기화 방식으로 하도록 권장하며 설계되었다고 합니다.
     *
     * @param parameters : Reference를 참고하면 나오지만 Query가 여러개이기 때문에 QueryMap 형식으로 받아서 요청!
     * @param callback : Object에 응답결과가 오며 그 결과를 가지고 Callback으로 처리를 합니다.
     */


    @GET("/shopping/search")
   void getDataAsync(@QueryMap HashMap<String, String> parameters, Callback<Object> callback);

    @GET("/insert/users/{user-id}")
    void getDataAsync(@Path("user-id") long id, Callback<Object> callback);

}
