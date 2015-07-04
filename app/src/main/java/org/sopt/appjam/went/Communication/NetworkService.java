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


/**
 *
 *
 * 레트로핏의 사용 방법 api를 참고하여 아래 문서를 이해하시면 됩니다.
 * @어노테이션을 이용하여 사용할 메소드를 GET POST PUT DELETE를 사용자 입맛대로 만들어 쓰면 됩니다
 * 단 메소드를 정의할 때, 들어가는 인자의 수에 따라 동기식 처리, 비동기식 처리 등이 결정됩니다
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


    /**
     * 메소드 오버로딩이 가능하다는 점을 이용해서 위의 메소드와 똑같은 형태이지만 인자가 다른 우리의 API용 메소드를 정의했습니다
     * 여기에 long id부분에 페이스북 아이디가 들어가게 됩니다.
     * 최종 url 형태는 ENDPOINT/insert/users/{user-id} 형태로 GET 메소드를 호출하게 됩니다
     * 하니코드를 좀 더 파봐서 붙여햐 하는데, 아직 POST  메소드를 정의하지 않았고, 형태가 어떤 지만 일단 확인을 한 상태입니다.
     *
     */
    @GET("/insert/users/{user-id}")
    void getDataAsync(@Path("user-id") long id, Callback<Object> callback);

}
