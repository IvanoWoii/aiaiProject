package com.example.projectmobilesmt4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    TextView btn_reg;
    ProgressDialog progressDialog;
    SharedPreferences cekData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        login = (Button) findViewById(R.id.loginButton);
        btn_reg = (TextView) findViewById(R.id.btn_reg);
        progressDialog = new ProgressDialog(LoginActivity.this);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = username.getText().toString();
                String sPassword = password.getText().toString();

                CheckLogin(sEmail, sPassword);
            }
        });

        cekData = getSharedPreferences("login_session", MODE_PRIVATE);
                if(cekData.getString("username", null) != null){
                    startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                    finish();
                } else {

                }
    }

    public void CheckLogin(final String username, final String password) {
        if(checkNetworkConnection()){
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                String respData = jsonObject.getString("data");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), respData, Toast.LENGTH_SHORT).show();
                                    Intent dashboardIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(dashboardIntent);

                                    try {
                                        //mengambil data json
                                        String jsonString = "{\"id\":\"1\",\"username\":\"ivano\",\"email\":\"rezaivano123@gmail.com\",\"password\":\"123\"}";

                                        //parsing data
                                        JSONObject jsonObject1 = new JSONObject(jsonString);

                                        //ambil data/nilai
                                        int idJson = jsonObject1.getInt("id");
                                        String usernameJson = jsonObject1.getString("username");
                                        String emailJson = jsonObject1.getString("email");
                                        String pasJson = jsonObject1.getString("password");

                                        getSharedPreferences("login_session", MODE_PRIVATE)
                                                .edit()
                                                .putInt("id",idJson)
                                                .putString("username", usernameJson)
                                                .putString("email", emailJson)
                                                .putString("password",pasJson)
                                                .apply();

                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                Log.e("JSON Error", "Error parsing JSON: " + e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            VolleyConnection.getInstance(LoginActivity.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);

        } else {
            Toast.makeText(getApplicationContext(), "Sambungan gagal", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return (capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)));
    }


}