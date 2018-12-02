package com.mitrajani.blooddonation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Mit Rajani on 11/14/2018.
 */

public class MyCheckBoxSecondPagerFragment extends Fragment {
    TextView placeDetails;
    Button SearchLocation, submit;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.camp_request, null);

        placeDetails = (TextView) v.findViewById(R.id.textView3);

        SearchLocation = (Button) v.findViewById(R.id.location2);

        SearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutocompleteActivity();
            }
        });

        submit = (Button) v.findViewById(R.id.submit_request2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        return v;
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("error", message);
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
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
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.e("Error", "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }


}
