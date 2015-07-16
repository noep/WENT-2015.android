package org.sopt.appjam.went.Depth2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.sopt.appjam.went.Communication.AppController;
import org.sopt.appjam.went.Communication.NetworkService;
import org.sopt.appjam.went.FindLocationActivity;
import org.sopt.appjam.went.Model.Photo;
import org.sopt.appjam.went.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import dialog.Dialog_photo;
import dialog.Dialog_place;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;


/**
 * Created by NOEP on 15. 7. 3..
 */





public class Depth2AddActivity extends AppCompatActivity {

    public static final String TAG = "AddActivity";
    static final int REQUEST_CODE_PLACE = 5;



    NetworkService networkService;
    Dialog_photo photoDialog ;
    //Dialog_place placeDialog;
    String facebookinfo;
    int mothercard;


    Uri IMAGE_HOLDER;

    LatLng latLng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depth2_add);

        getIntent().getExtras().getString("facebookid", facebookinfo);

        Intent intent = getIntent();
        facebookinfo = (String) intent.getSerializableExtra("facebookid");
        mothercard = (int) intent.getSerializableExtra("mothercard");

        Log.e(TAG,"facebookinfo+ "+facebookinfo + mothercard);
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
        toolbar.setNavigationIcon(R.mipmap.went_three_button_back);
        toolbar.setTitleTextColor(Color.WHITE);


    } //method end



    private ImageButton addbutton;
    private ImageView photo;
    private EditText title;
    private Button place;
    private Button time;
    private EditText contents;

    private int myYear, myMonth, myDay, myHour, myMinute;
    String tempdate, temptime;


    private void setView(){

        addbutton = (ImageButton) findViewById(R.id.add_button);
        photo = (ImageView) findViewById(R.id.ImageView_pic);
        title = (EditText) findViewById(R.id.editText_title);
        place = (Button) findViewById(R.id.button_place);
        time = (Button) findViewById(R.id.button_time);
        contents = (EditText) findViewById(R.id.editText_contents);



        String color_place = "#E3F6F4";
        String color_moment = "#B0E4E0";
        place.setBackgroundColor(Color.parseColor(color_place));
        time.setBackgroundColor(Color.parseColor(color_moment));
        //for test



    } //method end
    private void setViewListeners(){

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"add",Toast.LENGTH_SHORT).show();

                newPhoto();



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
               // Intent intent = new Intent(getApplicationContext(), FindLocationActivity.class);
               // startActivity(intent);
                //placeDialog.show(getFragmentManager(), Dialog_place.class.getName());


                Intent intent = new Intent(getApplicationContext(), FindLocationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PLACE);
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //datepicker
                Calendar c = Calendar.getInstance();

                myYear = c.get(Calendar.YEAR);
                myMonth = c.get(Calendar.MONTH);
                myDay = c.get(Calendar.DAY_OF_MONTH);

                myHour = c.get(Calendar.HOUR_OF_DAY);
                myMinute = c.get(Calendar.MINUTE);

                //Toast.makeText(MainActivity.this,"- onCreateDialog(ID_TIMEPICKER) -", Toast.LENGTH_LONG).show();
                Dialog dlgTime = new TimePickerDialog(Depth2AddActivity.this, myTimeSetListener,myHour, myMinute, false);
                dlgTime.show();

                //Toast.makeText(MainActivity.this,"- onCreateDialog(ID_DATEPICKER) -", Toast.LENGTH_LONG).show();
                Dialog dlgDate = new DatePickerDialog(Depth2AddActivity.this, myDateSetListener,myYear, myMonth, myDay);
                dlgDate.show();

            }
        });

        //edittext contents don't need listeners

    } //method end


    private void setDialogs(){



        //placeDialog = new Dialog_place();
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
        /*
                placeDialog.setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {

                            case DialogInterface.BUTTON_POSITIVE : {
                                //search

                                Intent intent = new Intent(getApplicationContext(), FindLocationActivity.class);
                                startActivityForResult(intent, REQUEST_CODE_PLACE);

                                break;
                            }

                            case DialogInterface.BUTTON_NEUTRAL : {
                                //by gps

                                break;
                            }

                            case DialogInterface.BUTTON_NEGATIVE : {
                                //cancel


                                break;


                            }
                        }
                    }

                });

        */







    }


    private File createTemporaryFile(String name) throws IOException {

        File temp = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp/");

        if (!temp.exists())
            temp.mkdir();

        return new File(temp, name);
    }


    /**
     * Upload photo method
     */
    public void newPhoto() {



        Editable title = this.title.getText();

        //Editable content = (Editable) (place.getText().toString());


        //for test
        LatLng test = new LatLng(37.58528260494513, 126.98605588363266);
        String address = "진짜 주소";

        Double lon = test.longitude;
        Double lat = test.latitude;


        String timetime = "시간시간시간시간";
        String contents = this.contents.getText().toString();




        Log.e(TAG,"err? "+ title.toString()+address.toString());


        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(address)) {

            Toast.makeText(getApplicationContext(), "Please write more contents :O", Toast.LENGTH_LONG).show();
            return;
        }

        try {

            BitmapDrawable drawable = (BitmapDrawable) photo.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytes = stream.toByteArray();

            final File file = createTemporaryFile("image.jpg");
            FileOutputStream writer = new FileOutputStream(file);

            writer.write(bytes);

            stream.close();
            writer.close();


            //networkService.newPhoto(new TypedFile("image/jpeg", file), new TypedString(title.toString()), new TypedString(address.toString()),
            networkService.newPhoto(new TypedFile("image/jpeg",file), new TypedString(title.toString()), new TypedString(address.toString()),
                    latLng.longitude, latLng.latitude,new TypedString(contents.toString()),new TypedString(time.getText().toString()),new TypedString(String.valueOf(mothercard)),new TypedString(facebookinfo.toString()),
                    new Callback<Object>() {

                        @Override
                        public void success(Object photo, Response response) {

                            Toast.makeText(getApplicationContext(), "Success to post a new photo :)", Toast.LENGTH_LONG).show();
                            // adapter.push(photo);
                            file.delete();
                            finish();

                        }

                        @Override
                        public void failure(RetrofitError error) {

                            Toast.makeText(getApplicationContext(), "Failed to post a new photo :(", Toast.LENGTH_LONG).show();
                            file.delete();
                        }
                    });
        }
        catch (IOException e) { e.printStackTrace(); }
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


                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;

                    AssetFileDescriptor fileDescriptor =null;
                    fileDescriptor = getContentResolver().openAssetFileDescriptor(IMAGE_HOLDER,"r");

                    Bitmap bitmap
                            = BitmapFactory.decodeFileDescriptor(
                            fileDescriptor.getFileDescriptor(), null, options);



                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), IMAGE_HOLDER);



                    Log.e(TAG,"bitmap width"+String.valueOf(bitmap.getWidth()) );

                    /**
                     * bitmap resize
                     * if (bitmap.getWidth() > maxTextureSize[0] || bitmap.getHeight() > maxTextureSize[0]){
                     *
                     */

                    if (bitmap.getWidth() > 4096){
                        int resizedWidth = bitmap.getWidth()/4;
                        int resizedHeight = bitmap.getHeight()/4;
                        Bitmap temp = bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);

                        photo.setImageBitmap(temp);

                    }
                    else {
                        photo.setImageBitmap(bitmap);

                    }



                }
                catch (Exception e)  { e.printStackTrace(); }

                break;
            }

            case Dialog_photo.REQUEST_CODE_GALLERY : {

                Uri uri = data.getData();

                try {

                    InputStream stream = getContentResolver().openInputStream(uri);


                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;

                    Bitmap bitmap = BitmapFactory.decodeStream(stream,null,options);


                    if(bitmap.getWidth()>4096){

                        int resizedWidth = bitmap.getWidth()/4;
                        int resizedHeight = bitmap.getHeight()/4;
                        Bitmap temp = bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);



                        photo.setImageBitmap(temp);

                    }else {


                        photo.setImageBitmap(bitmap);

                    }
                    //stream.reset();
                    stream.close();
                }
                catch (FileNotFoundException e) { e.printStackTrace(); }
                catch (IOException e) { e.printStackTrace(); }


                break;
            }

            case REQUEST_CODE_PLACE : {

                double lat, lon;

                lat = (double) data.getSerializableExtra("latitude");
                lon = (double) data.getSerializableExtra("longitude");

                latLng = new LatLng(lat, lon);

                Log.e(TAG,"latlng"+lat + " " + lon);
                place.setText("lat/lng : " + latLng.latitude +"/"+ latLng.longitude);

            }
        }
    }



    private DatePickerDialog.OnDateSetListener myDateSetListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String date = String.valueOf(year) + "-" +String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
            tempdate=date;
            //.makeText(MainActivity.this, date, Toast.LENGTH_LONG).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = " " + String.valueOf(hourOfDay) + ":"+ String.valueOf(minute);
            temptime= time;
            //Toast.makeText(MainActivity.this, time, Toast.LENGTH_LONG).show();
            setTime();
        }
    };

    private void setTime() {

        Log.e(TAG,temptime.toString());
        time.setText(tempdate.concat(temptime));

    }



}
