package com.example.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.startup.Backend.BroadcastReciever;
import com.example.startup.Constants.NavigationDrawerConstants;

import org.json.JSONObject;

import java.nio.channels.NetworkChannel;
import java.util.HashMap;
import java.util.Objects;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
public class Login extends AppCompatActivity {

    Button logininto;
    TextView forgot;
    private static final String URL = "https://edfinix.herokuapp.com/api/login/checkCredentials";

    ProgressBar prog;
    SharedPreferences sp;

    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_check_connection= findViewById(R.id.tv_check_connection);
        mNetworkReceiver = new BroadcastReciever();
        registerNetworkBroadcastForNougat();


        prog=  findViewById(R.id.progress_login);
        final EditText id = findViewById(R.id.User);
        final EditText pass = findViewById(R.id.Password);
        Login.this.setTitle(NavigationDrawerConstants.TAG_Login);

        forgot = findViewById(R.id.forgot_pass);
        logininto = findViewById(R.id.buttonLogin);


        //final String username = "14441";
        //final String password = "12345";

        sp=getSharedPreferences("login",MODE_PRIVATE);
        if(sp.contains("username") && sp.contains("password")){
            if(sp.contains("student"))
            {
                startActivity(new Intent(Login.this,StudentMainActivity.class));
                finish();   //finish current activity
            }
            else if(sp.contains("teacher"))
            {
                startActivity(new Intent(Login.this,TeacherMainActivity.class));
                finish();   //finish current activity
            }
           else
            {
                Toast.makeText(this, "invalid", Toast.LENGTH_SHORT).show();
            }
        }

        logininto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = String.valueOf(id.getText());
                final String password = String.valueOf(pass.getText());

                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    loginuser(username, password);
                }
                else {
                    Toast.makeText(Login.this, "Enter The Details To login", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
    public void loginuser(final String username, final String password) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response","inside response");
                        try {
                            //Process os success response
                            //  Log.d("response",response.login);
                            JSONObject login = (JSONObject) response.get("login");
                            int isUser = login.getInt("message");
                            //Toast.makeText(Login.this, "here"+isUser, Toast.LENGTH_SHORT).show();
                            if(isUser == 1 ){
                                Log.d("response", "valid");
                                if(login.getString("user").equals("student"))
                                {
                                    SharedPreferences.Editor e=sp.edit();
                                    e.putString("username",username);
                                    e.putString("password",password);
                                    e.putString("student","student");
                                    e.apply();

                                   // Log.d("response", "student");
                                    //Toast.makeText(Login.this, "student", Toast.LENGTH_SHORT).show();
                                    Intent a = new Intent(Login.this,StudentMainActivity.class);
                                    startActivity(a);
                                    finish();
                                    // student logic
                                }
                                else if(login.getString("user").equals("teacher"))
                                {
                                    SharedPreferences.Editor e=sp.edit();
                                    e.putString("username",username);
                                    e.putString("password",password);
                                    e.putString("teacher","teacher");
                                    e.apply();
                                    //Log.d("response", "teacher");
                                    //Toast.makeText(Login.this, "teacher", Toast.LENGTH_SHORT).show();
                                    Intent a = new Intent(Login.this,TeacherMainActivity.class);
                                    startActivity(a);
                                    finish();

                                }
                                else
                                {
                                    //Log.d("response", "admin");
                                    Toast.makeText(Login.this, "Wrong ID or Password", Toast.LENGTH_SHORT).show();

                                }


                            }
                            else if(isUser == 0)
                            {
                                Log.d("response","invalid");
                            }
                            else
                            {
                                Log.d("response", "server error");
                            }
                        } catch (Exception e) {
                            Log.d("response",e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", Objects.requireNonNull(error.getMessage()));
            }

        });

// add the request object to the queue to be executed
        requestQueue.add(request_json);
    }



    public static void dialog(boolean value){

        if(value){
            tv_check_connection.setText("We are back !!!");
            tv_check_connection.setBackgroundColor(Color.YELLOW);
            tv_check_connection.setTextColor(Color.WHITE);
            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Could not Connect to internet");
            tv_check_connection.setBackgroundColor(Color.RED);
            tv_check_connection.setTextColor(Color.WHITE);
        }
    }


    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}

