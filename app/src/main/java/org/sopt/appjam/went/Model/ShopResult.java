package org.sopt.appjam.went.Model;

/**
 * Created by NOEP on 15. 7. 1..
 */
import java.util.List;

/**
 * http://developers.daum.net/services/apis/shopping/search
 *
 * 위 링크에 있는 문서에 출력 결과에 해당하는 자료형과 변수명!
 *
 * 문서와 꼭 맞추어줘야(변수와 형을) 파싱에 문제가 없습니다.
 *
 * List는 임의로 지정한것입니다. item이라는 변수명만 맞으면 됩니다.
 */





/**
다음 API에 있는 모델명들을 가져와서 변수명 등등을 그대로 맞춰줍니다.
그래서 JSON으로 파싱할 때 한번에 가능합니다(변수명이 하나라도 달라지면 매핑이 안될껍니다(다안되는지 일부만안되는지는 안해봤습니다)
 애니웨이, 우리들의 모델을 만들어야 합니다.


 */
public class ShopResult {

    public int result;
    public String title;
    public String sort;
    public String desc;
    public String totalCount;
    public String q;
    public List<ShopItem> item; //ShopItem이라는 객체를 리스트 형태를 가지고 있습니다. 실제로 가지고 올 때에도 이런 형태입니다.
    public int pageno;

}
