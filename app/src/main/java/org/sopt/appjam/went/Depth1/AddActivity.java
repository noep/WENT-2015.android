package org.sopt.appjam.went.Depth1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.sopt.appjam.went.R;



/**
 * Created by NOEP on 15. 7. 3..
 */





public class AddActivity extends AppCompatActivity {








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        setTitle("Write main card");




        setToolbar();

        setView();
        setViewListeners();

    }



    private void setToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_list);
        toolbar.setTitleTextColor(Color.WHITE);


    } //method end



    private ImageButton addbutton;
    private ImageView photo;
    private EditText title;
    private TextView place;

    private void setView(){

        addbutton = (ImageButton) findViewById(R.id.add_button);
        photo = (ImageView) findViewById(R.id.ImageView_pic);
        title = (EditText) findViewById(R.id.editText_title);
        place = (TextView) findViewById(R.id.textView_place);

    } //method end
    private void setViewListeners(){

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_SHORT).show();

            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"pic",Toast.LENGTH_SHORT).show();
            }
        });

        //edittext title don't need listners

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"placepick",Toast.LENGTH_SHORT).show();
            }
        });









    } //method end






}
