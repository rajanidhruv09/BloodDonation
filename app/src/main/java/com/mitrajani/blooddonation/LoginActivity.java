package com.mitrajani.blooddonation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button button, button1;
    EditText usernameEdittext, passwordEdittext;
    private static final String loginURL="http://127.0.0.1/BD/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        button = (Button) findViewById(R.id.register_button);

        button1 = (Button) findViewById(R.id.login_button);

        usernameEdittext = (EditText) findViewById(R.id.usernameText);

        passwordEdittext = (EditText) findViewById(R.id.passText);


        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                /* Main thing working right now
                //setContentView(R.layout.activity_main);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //Intent intent = new Intent(getApplicationContext(), testdside.class);
                finish(); // need to save state that the user has logged in and so need to get out of the login scree permanently
                startActivity(intent);
                */
                userlogin();
            }
        });

        /*button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.activity_registration);
                Intent intent = new Intent(getApplicationContext(), RegistrationNew.class);
                startActivity(intent);
            }
        });

        usernameEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    hidekeyboard(view);
                }
            }
        });

        passwordEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    hidekeyboard(view);
                }
            }
        });
    }

    public void hidekeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void userlogin()
    {
        StringRequest request=new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("success")){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //Intent intent = new Intent(getApplicationContext(), testdside.class);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid username or password", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("loginuser", "true");
                params.put("username", usernameEdittext.getText().toString().trim());
                params.put("userpassword", passwordEdittext.getText().toString().trim());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }

    private void login(){
        final String username = usernameEdittext.getText().toString();
        final String password = passwordEdittext.getText().toString();

        if (TextUtils.isEmpty(username)){
            usernameEdittext.setError("Please enter your username");
            usernameEdittext.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            passwordEdittext.setError("Please enter your password");
            passwordEdittext.requestFocus();
            return;
        }

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar2);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject object = new JSONObject(s);

                    if (!object.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject userJson = object.getJSONObject("user");

                        User user = new User(userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("firstname"),
                                userJson.getString("lastname"),
                                userJson.getString("DOB"));

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();

                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return requestHandler.sendPostRequest(URLs.URL_LOGIN,params);
            }
        }
    }
}
