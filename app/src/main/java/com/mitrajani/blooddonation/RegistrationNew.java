package com.mitrajani.blooddonation;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Mit Rajani on 12/1/2018.
 */

public class RegistrationNew extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText, fnameEditText, lnameEditText, DOB;
    Button register;
    Calendar calendar;
    int year;
    int month;
    int dayOfMonth;
    DatePickerDialog datePickerDialog;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        toolbar = (Toolbar) findViewById(R.id.tbar);
        setSupportActionBar(toolbar);

        //ActionBar bar = getSupportActionBar();
        //bar.setDisplayHomeAsUpEnabled(true);
        //bar.setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");

        usernameEditText = (EditText) findViewById(R.id.usernameText);
        passwordEditText = (EditText) findViewById(R.id.passwordTest);
        emailEditText = (EditText) findViewById(R.id.editText8);
        fnameEditText = (EditText) findViewById(R.id.editText6);
        lnameEditText = (EditText) findViewById(R.id.editText7);
        DOB = (EditText) findViewById(R.id.date_get);
        //DOB.setText("1999-12-09");

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RegistrationNew.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                DOB.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });





        /*DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplication(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                DOB.setText(day + "/" + (month + 1) + "/" + year);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });*/

        register = (Button) findViewById(R.id.get_started);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //Intent intent = new Intent(getApplicationContext(), testdside.class);
                finish(); // need to save state that the user has logged in and so need to get out of the login scree permanently
                startActivity(intent);



                // Need to implement
                //registerUser();
            }
        });


    }

    private void registerUser(){
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String fname = fnameEditText.getText().toString();
        final String lname = lnameEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        final String date = DOB.getText().toString();

        if(TextUtils.isEmpty(fname)){
            fnameEditText.setError("Please enter your first name");
            fnameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lname)){
            lnameEditText.setError("Please enter yout last name");
            lnameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(date)){
            DOB.setError("Please select your date of birth");
            DOB.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(username)){
            usernameEditText.setError("Please enter username");
            usernameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)){
            passwordEditText.setError("Please enter a password");
            passwordEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)){
            emailEditText.setError("Please enter your email");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        class RegisterUser extends AsyncTask<Void, Void, String>{
            private ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error")){
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject userJson = obj.getJSONObject("user");

                        User user = new User(userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("firstname"),
                                userJson.getString("lastname"),
                                userJson.getString("DOB"));
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT).show();

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
                params.put("email", email);
                params.put("password", password);
                params.put("firstname", fname);
                params.put("lastname", lname);
                params.put("DOB", date);

                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
