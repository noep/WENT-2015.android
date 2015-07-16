package org.sopt.appjam.went;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by NOEP on 15. 7. 9..
 */
public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        initialize();
    }

    private void initialize()
    {
        Log.e("Splash", "why?");
        Handler handler =    new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
