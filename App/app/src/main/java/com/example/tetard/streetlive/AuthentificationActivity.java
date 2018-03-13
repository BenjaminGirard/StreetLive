package com.example.tetard.streetlive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tetard.streetlive.Database.DatabaseHandler;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class AuthentificationActivity extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler();

    /*
    *  FACEBOOK variables
    * */
    CallbackManager callbackManager;
    LoginButton     _fbLoginButton;

   /*
    *  TWITTER variables
    * */
    TwitterAuthClient mTwitterAuthClient;
    TwitterLoginButton  _twLoginButton;

    /*
    *  SOCIAL NETWORK variable
    * */
    String              _token;
    String              _secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        *  Facebook initialization (app ID and secret in manifests/AndroidManifest.xml)
        * */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        /*
        *  Twitter initialization
        */
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("N2GPGLvujMlXi3bWsGRoMVYwp",
                        "iOjm4aSTLINPTuTGcsrPxQ0FDtAfYjob0koksBsCAMU4qCmZBH"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        setContentView(R.layout.activity_authentification);
        Log.wtf("ONCREATE", "entered create");
        Log.wtf("CREATE", "quit create");

        db.connect("http://127.0.0.1:3306/phpmyadmin/index.php?db=test&table=category&target=sql.php&token=7ba13a3a742ca58c4a4e2c4418f99134#PMAURL:db=test&table=category&target=sql.php&token=7ba13a3a742ca58c4a4e2c4418f99134");
        twitterLoginCallback();
        mTwitterAuthClient = new TwitterAuthClient();
    }
    public void connectButton(View view) {
        Intent intent = new Intent(this, ArtistLocate.class);
        startActivity(intent);
    }

    public void signupButton(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    public void facebookLoginCallBack() {
        _fbLoginButton = (LoginButton) findViewById(R.id.facebook_connect);
        _fbLoginButton.setReadPermissions("email");
        // Callback registration
        _fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.wtf("SUCESS", "entered success");
                // App code
            }

            @Override
            public void onCancel() {
                Log.wtf("CANCEL", "entered cancel");

                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
                // App code
            }
        });
    }

    public void twitterLoginCallback() {
        _twLoginButton =  (TwitterLoginButton) findViewById(R.id.twitter_connect);
        _twLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.wtf("SUCESS", "entered success");
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                _token = authToken.token;
                _secret = authToken.secret;
                // redirection vers artist locate
          }

            @Override
            public void failure(TwitterException exception) {
                exception.printStackTrace();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.wtf("ACTIVITY", "entered activity");
        // Pass the activity result to the login button.
        callbackManager.onActivityResult(requestCode, resultCode, data);
        _twLoginButton.onActivityResult(requestCode, resultCode, data);
    }


}
