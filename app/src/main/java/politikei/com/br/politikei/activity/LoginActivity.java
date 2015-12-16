package politikei.com.br.politikei.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import politikei.com.br.politikei.R;
import politikei.com.br.politikei.database.DataBase;
import politikei.com.br.politikei.datatype.User;
import politikei.com.br.politikei.datatype.request.FacebookLoginRequest;
import politikei.com.br.politikei.datatype.request.LoginRequest;
import politikei.com.br.politikei.datatype.response.EmptyResponse;
import politikei.com.br.politikei.datatype.response.LoginResponse;
import politikei.com.br.politikei.network.GsonRequest;
import politikei.com.br.politikei.network.NetworkRequestQueue;

public class LoginActivity extends AppCompatActivity implements Response.Listener<LoginResponse>, Response.ErrorListener{
    private final static String LOGIN_API = "https://politikei-api.herokuapp.com/api/v1/auth";
    private final static String FB_LOGIN_API = "https://politikei-api.herokuapp.com/api/v1/facebookauth";
    private final static String RETRIEVE_PASSWD_API = "https://politikei-api.herokuapp.com/api/v1/retrievepasswd";

    private RequestQueue mQueue;
    private ProgressDialog _progressBar;
    private CallbackManager callbackManager;
    private String mUser;
    private boolean mIsFacebookAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        callbackManager = CallbackManager.Factory.create();
        _progressBar = new ProgressDialog(LoginActivity.this);

        //Normal Login
        Button entrarBt = (Button) findViewById(R.id.login_enter);
        entrarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();

                EditText loginEt = (EditText) findViewById(R.id.login_user);
                EditText passwdEt = (EditText) findViewById(R.id.login_passwd);

                mUser = loginEt.getText().toString();
                String strPasswd = passwdEt.getText().toString();

                LoginRequest login = new LoginRequest();
                login.setEmail(mUser);
                login.setPassword(strPasswd);

                GsonRequest gsonRequest = new GsonRequest(LOGIN_API, LoginResponse.class, null, LoginActivity.this, LoginActivity.this, login);

                //Making the request
                mQueue.add(gsonRequest);
            }
        });

        //Facebook Login
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_fb_button);
        loginButton.setReadPermissions("public_profile", "user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showLoading();
                FacebookLoginRequest login = new FacebookLoginRequest();
                mUser = loginResult.getAccessToken().getUserId();
                login.setUUID(mUser);
                login.setToken(loginResult.getAccessToken().getToken());
                mIsFacebookAccount = true;

                GsonRequest gsonRequest = new GsonRequest(FB_LOGIN_API, LoginResponse.class, null, LoginActivity.this, LoginActivity.this, login);

                //Making the request
                mQueue.add(gsonRequest);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "Falha ao autenticar", Toast.LENGTH_SHORT).show();
            }
        });

        //Forget my passwd option
        TextView retrieveTv = (TextView) findViewById(R.id.login_retrieve);
        retrieveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginEt = (EditText) findViewById(R.id.login_user);
                String strLogin = loginEt.getText().toString();
                if(!strLogin.isEmpty()) {
                    showLoading();

                    LoginRequest login = new LoginRequest();
                    login.setEmail(strLogin);

                    GsonRequest gsonRequest = new GsonRequest(RETRIEVE_PASSWD_API, EmptyResponse.class, null, new Response.Listener() {
                        @Override
                        public void onResponse(Object response) {
                            cancelLoading();
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle(getString(R.string.login_retrieve_title));
                            alertDialog.setMessage(getString(R.string.login_retrieve_msg));
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            cancelLoading();
                            Toast.makeText(LoginActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                        }
                    }, login);

                    //Making the request
                    mQueue.add(gsonRequest);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQueue = NetworkRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        cancelLoading();
        Toast.makeText(LoginActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(LoginResponse data) {
        cancelLoading();

        User user = new User();
        user.setName(mUser);
        user.setToken(data.getToken());
        if(mIsFacebookAccount)
            user.setForFacebook();
        DataBase.getInstance(LoginActivity.this).saveUserData(user);

        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void showLoading(){
        _progressBar.setTitle(getString(R.string.loading_title));
        _progressBar.setMessage(getString(R.string.loading_msg));
        _progressBar.setCancelable(false);
        _progressBar.show();
    }

    private void cancelLoading(){
        _progressBar.dismiss();
    }
}
