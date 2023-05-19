package com.example.projectmobilesmt4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, nama, unit_induk, up3, ulp, confirmPassword;
    private Button btn_daftar;
    private TextView btn_kembali;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama = findViewById(R.id.namaEditText);
        unit_induk = findViewById(R.id.unitIndukEditTect);
        up3 = findViewById(R.id.up3EditText);
        ulp = findViewById(R.id.ulpEditText);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.konfirmasiPasswordEditText);
        btn_daftar = findViewById(R.id.btnReg);
        btn_kembali = findViewById(R.id.r_tombolLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        unit_induk.setText("Kalimantan Barat");
        up3.setText("Pontianak");
        ulp.setText("Menpewah");

        unit_induk.setEnabled(false);
        up3.setEnabled(false);
        ulp.setEnabled(false);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });

    }

    private void registerUser() {
        final String sNama = nama.getText().toString().trim();
        final String sUnitInduk = unit_induk.getText().toString().trim();
        final String sUp3 = up3.getText().toString().trim();
        final String sUlp = ulp.getText().toString().trim();
        final String sUsername = username.getText().toString().trim();
        final String sPassword = password.getText().toString().trim();
        final String sConfrimPassword = confirmPassword.getText().toString().trim();

        //valdiasi form
        if(TextUtils.isEmpty(sNama)){
            nama.setError("Nama Harus Di isi");
            nama.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(sUnitInduk)){
            unit_induk.setError("unit induk harus di isi");
            unit_induk.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(sUp3)){
            up3.setError("field ini harus di isi");
            up3.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(sUlp)){
            ulp.setError("field ini harus di isi");
            ulp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sUsername)) {
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(sPassword)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sConfrimPassword)){
            confirmPassword.setError("Password harus di isi");
            confirmPassword.requestFocus();
            return;
        }

        if(!TextUtils.equals(sPassword, sConfrimPassword)){
            confirmPassword.setError("Pastikan Password sama");
            confirmPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", sNama);
                params.put("unit_induk", sUnitInduk);
                params.put("up3", sUp3);
                params.put("ulp", sUlp);
                params.put("username", sUsername);
                params.put("password", sPassword );
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}