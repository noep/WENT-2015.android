package org.sopt.appjam.went.Depth1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dialog.Dialog_photo;


/**
 * Created by NOEP on 15. 7. 3..
 */





public class AddActivity extends AppCompatActivity {

    public static final String TAG = "AddActivity";




    NetworkService networkService;
    Dialog_photo photoDialog ;



    Uri IMAGE_HOLDER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        setTitle("Write main card");


        //network setting
        //Network Connection
        networkService = AppController.getInstance().getNetworkService();




        setToolbar();

        setView();
        setViewListeners();


        setDialogs();







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
                //Toast.makeText(getApplicationContext(),"pic",Toast.LENGTH_SHORT).show();
                photoDialog.show(getFragmentManager(), Dialog_photo.class.getName());





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


    private void setDialogs(){




        photoDialog = new Dialog_photo();
        photoDialog.setOnClickListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE : {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        try  {

                            File photo = createTemporaryFile("temp.jpg");
                            photo.delete();

                            IMAGE_HOLDER = Uri.fromFile(photo);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, IMAGE_HOLDER);
                            startActivityForResult(intent, Dialog_photo.REQUEST_CODE_CAPTURE);
                        }
                        catch(Exception e)  { e.printStackTrace(); }

                        break;
                    }

                    case DialogInterface.BUTTON_NEUTRAL : {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, Dialog_photo.REQUEST_CODE_GALLERY);

                        break;
                    }

                    case DialogInterface.BUTTON_NEGATIVE : { break; }
                }
            }
        });









    }


    private File createTemporaryFile(String name) throws IOException {

        File temp = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp/");

        if (!temp.exists())
            temp.mkdir();

        return new File(temp, name);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        //int[] maxTextureSize = new int[1];
        //GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);

        //Log.e(TAG,"max textrue size : " +maxTextureSize[0]);



        Log.e(TAG, "resultCode " + Integer.toString(resultCode) + " requestCode " + Integer.toString(requestCode));

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(getApplicationContext(), "Failed to get a new photo :(", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {

            case Dialog_photo.REQUEST_CODE_CAPTURE : {

                getContentResolver().notifyChange(IMAGE_HOLDER, null);

                try  {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), IMAGE_HOLDER);


                   //bitmap resize
                   // if (bitmap.getWidth() > maxTextureSize[0] || bitmap.getHeight() > maxTextureSize[0]){
                    if (bitmap.getHeight() > 4096){
                        int resizedWidth = bitmap.getWidth()/2;
                        int resizedHeight = bitmap.getHeight()/2;
                        bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
                    }





                    photo.setImageBitmap(bitmap);
                }
                catch (Exception e)  { e.printStackTrace(); }

                break;
            }

            case Dialog_photo.REQUEST_CODE_GALLERY : {

                Uri uri = data.getData();

                try {

                    InputStream stream = getContentResolver().openInputStream(uri);



                    photo.setImageBitmap(BitmapFactory.decodeStream(stream));

                    stream.close();
                }
                catch (FileNotFoundException e) { e.printStackTrace(); }
                catch (IOException e) { e.printStackTrace(); }


                break;
            }
        }
    }




}
