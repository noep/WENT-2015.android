package org.sopt.appjam.went.Communication;
/**
 * Created by NOEP on 15. 7. 1..
 */

import org.sopt.appjam.went.Model.Depth1_item;
import org.sopt.appjam.went.Model.Depth2_item;
import org.sopt.appjam.went.Model.Photo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;


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

    /**
     * Depth1 GET
     * @param id
     * @param callback
     */
    @GET("/mothercard/{user_id}")
    void getDataAsync(@Path("user_id") String id, Callback<Object> callback);





    @GET("/mothercard/{user_id}")
    void getDataAsyncForTest(@Path("user_id") String id, Callback<ArrayList<Depth1_item>> callback);






    /**
     * Depth2 post
     * @param userid
     * @param motherid
     * @param callback
     */


    @GET("/secondcard/{user_id}/{mother_id}")
    void getDataAsync(@Path("user_id") String userid,
                      @Path("mother_id") int motherid, Callback<ArrayList<Depth2_item>> callback);



    /**
     * serverpart
     */
    @GET("/photos")
    void getPhotos(Callback<ArrayList<Photo>> callback);

    @GET("/photos/{photo_id}")
    void getPhotoByID(@Path("photo_id") String id, Callback<Photo> callback);



    @Multipart
    @POST("/mothercard")
    void newPhoto(@Part("photo") TypedFile photo,
                  @Part("title") TypedString title,
                  @Part("content") TypedString content, Callback<Photo> callback);


    /**
     * Depth1 ADD
     * @param photo
     * @param title
     * @param address
     * @param lon
     * @param lat
     * @param callback
     */
    @Multipart
    @POST("/mothercard")
    void newPhoto(@Part("photo") TypedFile photo,
                  @Part("title") TypedString title,
                  @Part("address") TypedString address,
                  @Part("lon") Double lon,
                  @Part("lat") Double lat,
                  @Part("user_id") TypedString user_id,

                  Callback<Photo> callback);


    /**
     * Depth2 ADD
     * @param photo
     * @param title
     * @param address
     * @param lon
     * @param lat
     * @param callback
     */
    @Multipart
    @POST("/secondcard")
    void newPhoto(@Part("photo") TypedFile photo,
                  @Part("title") TypedString title,
                  @Part("address") TypedString address,
                  @Part("lon") Double lon,
                  @Part("lat") Double lat,
                  @Part("content") TypedString content,
                  @Part("time") TypedString time,
                  @Part("mother_id") TypedString mother_id,
                  @Part("user_id") TypedString user_id,

                  Callback<Object> callback);





    @DELETE("/mothercard/{mother_id}")
    void DeleteDepth1Card(@Path("mother_id") int mother_id, Callback<Object> callback);

    @DELETE("/secondcard/{second_id}")
    void DeleteDepth2Card(@Path("mother_id") int second_id, Callback<Object> callback);






}
