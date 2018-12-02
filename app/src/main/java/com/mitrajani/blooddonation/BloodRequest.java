package com.mitrajani.blooddonation;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class BloodRequest extends AppCompatActivity {

    EditText bloodUnit;
    Button SearchLocation, submit;
    TextView placeDetails;

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

        bloodUnit = (EditText) findViewById(R.id.editText4);
        SearchLocation = (Button) findViewById(R.id.location);

        placeDetails = (TextView) findViewById(R.id.textView2);

        SearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutocompleteActivity();
            }
        });

        bloodUnit.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    hidekeyboard(view);
                }
            }
        });

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        bar.setTitle("Request Blood");


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Spinner mySpinner = (Spinner) findViewById(R.id.blood_groups);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BloodRequest.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bloodGroups));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(myAdapter);

        submit = (Button) findViewById(R.id.submit_request);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
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

    public void hidekeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("error", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("error", "Place Selected: " + place.getName());

                // Format the place's details and display them in the TextView.

                String placeDetailsStr = place.getName() + "\n"
                        //+ place.getId() + "\n"
                        //+ place.getLatLng().toString() + "\n"
                        + place.getAddress() + "\n";
                        //+ place.getAttributions();
                placeDetails.setText(placeDetailsStr);
                placeDetails.setVisibility(View.VISIBLE);
                /*
                SearchLocation.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));
                */

                /*
                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    mPlaceAttribution.setText("");
                }
                */
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("Error", "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }


    /*
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.id.location, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.location, name, id, address, phoneNumber,
                websiteUri));

    }
    */
}
