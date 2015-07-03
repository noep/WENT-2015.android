package org.sopt.appjam.went.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;

import org.sopt.appjam.went.Model.ShopItem;
import org.sopt.appjam.went.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by NOEP on 15. 7. 1..
 */
public class Depth1Adapter extends BaseAdapter {

private ArrayList<ShopItem> arrayList_shopItem;
private LayoutInflater layoutInflater;
private Context context;

        /**
         * Constructor
         */

        public Depth1Adapter(Context context) {

            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            /**
             * Glide 초기화
             *
             * synchronized를 이용한 이유는 Glide.class에 lock을 걸어두고 멀티스레딩 환경에서
             *
             * Glide.class에 여러 스레드가 접근해서 여러개의 Glide.class가(내부의 Diskcache나 Builder등이) 만들어지지 않도록 한 부분입니다.
             *
             * if 내부의 함수는 만약 isSetup()을 이용해서 Glide가 사용할 준비가 아직 되지 않았다면 builder를 통해서 설정해주는 부분입니다.
             */

            synchronized (Glide.class) {

                if (!Glide.isSetup()) {


                    File file = Glide.getPhotoCacheDir(context);
                    int size = 1024 * 1024 * 64;

                    DiskCache cache = DiskLruCacheWrapper.get(file, size);

                    GlideBuilder builder = new GlideBuilder((context));
                    builder.setDiskCache(cache);

                    Glide.setup(builder);

                }

            }


        }


        /**
         * Setter
         */

        public void setSource(ArrayList<ShopItem> arrayList_shopItem) {

            this.arrayList_shopItem = arrayList_shopItem;
            notifyDataSetChanged();

        }


        /**
         * Getter
         */

        @Override
        public int getCount() {
            return (arrayList_shopItem != null) ? arrayList_shopItem.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return ((arrayList_shopItem != null) && (position >= 0 && arrayList_shopItem.size() > position) ? arrayList_shopItem.get(position) : null);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder viewHolder;

            if (convertView == null) {

                //convertView = layoutInflater.inflate(R.layout.layout_item, parent, false);
                convertView = layoutInflater.inflate(R.layout.layout_item_card, parent , false) ;



                viewHolder = new ViewHolder();

                viewHolder.imageView_downImage = (ImageView) convertView.findViewById(R.id.imageView_downImage);
                viewHolder.textView_title = (TextView) convertView.findViewById(R.id.textView_title);
                viewHolder.textView_maxPrice = (TextView) convertView.findViewById(R.id.textView_maxPrice);


                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();

            }

            ShopItem item = arrayList_shopItem.get(position);


            /**
             * 아래는 Glide를 이용하여 이미지를 불러오고 그에 따라 캐싱까지해주는 한 줄짜리 코드입니다.
             */
            Glide.with(context)
                    .load(item.image_url)
                    .into(viewHolder.imageView_downImage);

            viewHolder.textView_title.setText(item.title);
            viewHolder.textView_maxPrice.setText("" + item.price_max);


            return convertView;
        }


}
