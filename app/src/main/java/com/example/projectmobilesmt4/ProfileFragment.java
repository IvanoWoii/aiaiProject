package com.example.projectmobilesmt4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectmobilesmt4.databinding.ActivityMainBinding;


public class ProfileFragment extends Fragment {
    private ImageView btnLog;
    TextView usernameTxt,emailTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLog = (ImageView) view.findViewById(R.id.tombolLogout);
        usernameTxt = (TextView) view.findViewById(R.id.usernameProfile);
        emailTxt = (TextView) view.findViewById(R.id.emailProfile);
        SharedPreferences cekData;


        cekData = getActivity().getSharedPreferences("login_session", Context.MODE_PRIVATE);
        String usernameJson = cekData.getString("username", "");
        String emailJson = cekData.getString("email", "");

        usernameTxt.setText(usernameJson);
        emailTxt.setText(emailJson);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekData.edit().clear().commit();
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        return view;
    }
}