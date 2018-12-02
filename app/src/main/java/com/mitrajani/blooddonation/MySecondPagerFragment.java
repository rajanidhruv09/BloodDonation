package com.mitrajani.blooddonation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Mit Rajani on 8/19/2018.
 */

public class MySecondPagerFragment extends Fragment {
    Button register;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, null);

        register = (Button) v.findViewById(R.id.get_started);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });


        return v;
    }
}
