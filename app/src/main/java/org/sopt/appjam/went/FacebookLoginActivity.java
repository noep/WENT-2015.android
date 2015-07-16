package org.sopt.appjam.went;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.sopt.appjam.went.Depth1.MainActivity;




/**
처음에 로그인 위드 페이스북 버튼을 띄워주는 부분입니다. 통신 부분은 여기서 보실만한 게 별로 없습니다




 */
public class FacebookLoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_facebook_login);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.button_login);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loginButton.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException e) {

            }
        });




    }

    protected void onResume(){

        super.onResume();

        if(AccessToken.getCurrentAccessToken() != null){

            loginButton.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

        else {

            loginButton.setVisibility(View.VISIBLE);

        }

    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode,data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
