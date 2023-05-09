package com.example.projectmobilesmt4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class HomeFragment extends Fragment {
   Button btn_scan,btn_test;
   EditText txt1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_home, container, false);

        btn_scan = (Button) view1.findViewById(R.id.btn_scan);
        btn_test = (Button) view1.findViewById(R.id.btn_test1);
        txt1 = (EditText) view1.findViewById(R.id.txt_username);
        btn_scan.setOnClickListener(view -> {
            scanCode();
        });

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("login_session", Context.MODE_PRIVATE);
                int idJson = sharedPreferences.getInt("id",0);
                String usernameJson = sharedPreferences.getString("username","");
                String emailJson = sharedPreferences.getString("email","");

                String toastMSG = idJson + " " +usernameJson+ " "+emailJson;

                Toast.makeText(getActivity(), toastMSG, Toast.LENGTH_SHORT).show();
            }
        });


        return view1;
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            txt1.setText(result.getContents());
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle("Result");
//            builder.setMessage(result.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            }).show();
        }
    });
}