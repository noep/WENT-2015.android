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
public class ShopResult {

    public int result;
    public String title;
    public String sort;
    public String desc;
    public String totalCount;
    public String q;
    public List<ShopItem> item;
    public int pageno;

}
