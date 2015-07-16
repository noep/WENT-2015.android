package org.sopt.appjam.went.Controller;

import android.content.Context;
import android.graphics.Typeface;
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

import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Model.Depth1_item;
import org.sopt.appjam.went.Model.Depth2_item;
import org.sopt.appjam.went.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by NOEP on 15. 7. 9..
 */
public class Depth2Adapter extends BaseAdapter {

    private ArrayList<Depth2_item> depth2_item;
    private LayoutInflater layoutInflater;
    private Context context;


    private static final String TAG = "Depth1Adapter";



    /**
     * Constructor
     */

    public Depth2Adapter(Context context) {

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

    public void setSource(ArrayList<Depth2_item> item) {

        this.depth2_item = item;
        notifyDataSetChanged();

    }


    /**
     * Getter
     */

    @Override
    public int getCount() {
        return (depth2_item != null) ? depth2_item.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return ((depth2_item != null) && (position >= 0 && depth2_item.size() > position) ? depth2_item.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            //convertView = layoutInflater.inflate(R.layout.layout_item, parent, false);
            convertView = layoutInflater.inflate(R.layout.depth2_card, parent , false) ;


            viewHolder = new ViewHolder();

            viewHolder.imageView_downImage = (ImageView) convertView.findViewById(R.id.depth2_card_ImageView);
            viewHolder.textView_title = (TextView) convertView.findViewById(R.id.depth2_card_TextView);
            //viewHolder.textView_maxPrice = (TextView) convertView.findViewById(R.id.textView_maxPrice);
            Typeface tf = Typeface.createFromAsset(convertView.getContext().getAssets(), "ygd340.ttf");
            viewHolder.textView_title.setTypeface(tf);
            //viewHolder.textView_maxPrice.setTypeface(tf);



            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        Depth2_item item = depth2_item.get(position);

        /**
         * 아래는 Glide를 이용하여 이미지를 불러오고 그에 따라 캐싱까지해주는 한 줄짜리 코드입니다.
         * http:111.111.111.111:3000/mothercard/{mother_id}
         * http:111.111.111.111:3000/secondcard/image/{second_id}
         */
        Glide.with(context)
                .load(AppController.getEndpoint().concat("/secondcard/image/my/").concat(String.valueOf(item.secondid)))
                .into(viewHolder.imageView_downImage);



        viewHolder.textView_title.setText(item.title);
        //viewHolder.textView_maxPrice.setText("" + item.price_max);


        return convertView;
    }


}
