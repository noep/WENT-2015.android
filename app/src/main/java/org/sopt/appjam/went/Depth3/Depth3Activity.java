package org.sopt.appjam.went.Depth3;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.R;

/**
 * Created by 홍주영 on 2015-07-09.
 */
public class Depth3Activity extends AppCompatActivity {

    //for facebook info
    private static String TAG = "Depth3Activity";

    ImageView imageview_depth3;

    TextView textview_title;
    TextView textview_Date;
    TextView textview_text;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate3");

        setContentView(R.layout.activity_depth3);//intent를 받았다!
        Intent intent = getIntent();


        setView();

        putTextFromIntent(intent);

        setTitle("");
        setToolbar();





    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void setView(){


        textview_title   = (TextView)findViewById(R.id.textview_depth3_title);
        textview_Date    = (TextView)findViewById(R.id.textview_depth3_Date);
        textview_text    = (TextView)findViewById(R.id.textview_depth3_text);
        imageview_depth3 = (ImageView)findViewById(R.id.imageview_depth3image);



        Typeface tf = Typeface.createFromAsset(getAssets(), "ygd340.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "ygd330.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(), "ygd320.ttf");


        textview_title.setTypeface(tf);
        textview_Date.setTypeface(tf2);
        textview_text.setTypeface(tf2);


        //textView_maxPrice.setTypeface(tf);






    }

    private void putTextFromIntent(Intent intent) {


        textview_title.setText((String) intent.getSerializableExtra("title"));
        textview_Date.setText((String)intent.getSerializableExtra("time"));
        textview_text.setText((String) intent.getSerializableExtra("content"));
        Glide.with(this)
                .load(AppController.getEndpoint().concat("/secondcard/image/my/").concat((String)intent.getSerializableExtra("secondid")))
                .into(imageview_depth3);




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

                break;
        }
        return true;
    }





    private void setToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.went_three_button_back);
        //toolbar.setTitleTextColor(Color.WHITE);
        //toolbar.getBackground().setAlpha(84);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });
        }
    } //method end




}






