package com.example.projectmobilesmt4;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;


public class ProfileFragment extends Fragment {

    CardView btnInfo;
    TextView usernameTxt,emailTxt,noInduk;
    Button btn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btn_logout = (Button) view.findViewById(R.id.btnLogProfile);
        usernameTxt = (TextView) view.findViewById(R.id.usernameProfile);
        emailTxt = (TextView) view.findViewById(R.id.emailProfile);
        noInduk = (TextView) view.findViewById(R.id.unitIndukProfile);
        btnInfo = (CardView) view.findViewById(R.id.cardView1);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Info.class));
                getActivity().finish();
            }
        });

        //get data user
        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        //memasukan data user ke textView
        usernameTxt.setText("Username : " + String.valueOf(user.getUsername()));
        emailTxt.setText("Nama : " + String.valueOf(user.getNama()));
        noInduk.setText("Unit Induk : " + String.valueOf(user.getUnit_induk()));

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat objek AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // Mengatur judul dan pesan pada dialog
                builder.setTitle("Konfirmasi Keluar");
                builder.setMessage("Anda yakin ingin keluar?");

                // Menambahkan tombol "Ya"
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Menutup fragment saat pengguna memilih "Ya"
                        Intent intentLogout = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intentLogout);
                        getActivity().finish();
                        SharedPrefManager.getInstance(getActivity()).logout();
                        Toast.makeText(getActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                    }
                });

                // Menambahkan tombol "Tidak"
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tidak melakukan apa-apa saat pengguna memilih "Tidak"
                    }
                });

                // Membuat dan menampilkan dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        return view;
    }
}