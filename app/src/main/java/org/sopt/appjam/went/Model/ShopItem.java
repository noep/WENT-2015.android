package org.sopt.appjam.went.Model;

/**
 * Created by NOEP on 15. 7. 1..
 */

/**
 * http://developers.daum.net/services/apis/shopping/search
 *
 * 위 링크에 있는 문서에 item에 해당하는 자료형과 변수명!
 *
 * 문서와 꼭 맞추어줘야(변수와 형을) 파싱에 문제가 없습니다.
 */
public class ShopItem {

    public String docid;
    public String title;
    public String description;
    public String price_group;
    public int price_min;
    public int price_max;
    public String publish_dat;
    public String maker;
    public String brand;
    public String image_url;
    public String category_name;
    public String shoppingmall;
    public String shoppingmall_count;
    public int  review_count;
    public String link;
    public String product_type;


}
