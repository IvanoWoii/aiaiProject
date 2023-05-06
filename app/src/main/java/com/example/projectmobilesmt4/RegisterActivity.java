package com.example.projectmobilesmt4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText edit1,edit2,edit3,edit4,edit5;
    ProgressDialog progressDialog;
    String username,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView btnKembali = (ImageView) findViewById(R.id.r_tombolKembali);
        TextView btnLogin = (TextView) findViewById(R.id.r_tombolLogin);
        Button btnReg = (Button) findViewById(R.id.r_btnReg);
        edit1 = (EditText) findViewById(R.id.R_edt1);
        edit2 = (EditText) findViewById(R.id.R_edt2);
        edit3 = (EditText) findViewById(R.id.R_edt3);
        edit4 = (EditText) findViewById(R.id.R_edt4);
        edit5 = (EditText) findViewById(R.id.R_edt5);
        progressDialog = new ProgressDialog(RegisterActivity.this);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,ActivtyLandingPageActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit1.getText().toString())){
                    edit1.setError("Username Harus Di Isi !");
                    return;
                }
                if (TextUtils.isEmpty(edit2.getText().toString())) {
                    edit2.setError("Password Harus Di Isi !");
                    return;
                }
                if (TextUtils.isEmpty(edit3.getText().toString())) {
                    edit3.setError("Email Harus Di Isi !");
                    return;
                }
                if (TextUtils.isEmpty(edit4.getText().toString())) {
                    edit4.setError("No HP Harus Di Isi !");
                    return;
                }
                if (TextUtils.equals(edit5.getText().toString(),edit2.getText().toString())){
                    // kosong
                } else {
                    edit5.setError("Password Tidak Sama");
                    return;
                }

                username = edit1.getText().toString();
                email = edit3.getText().toString();
                password = edit2.getText().toString();
                register(username , email, password);

//                String username,email,password;
//                username = String.valueOf(edit1.getText());
//                email = String.valueOf(edit3.getText());
//                password = String.valueOf(edit2.getText());
//                if(!username.equals("") && !email.equals("") && !password.equals("") ) {
//                    //Start ProgressBar first (Set visibility VISIBLE)
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Starting Write and Read data with URL
//                            //Creating array for parameters
//                            String[] field = new String[3];
//                            field[0] = "username";
//                            field[1] = "email";
//                            field[2] = "password";
//                            //Creating array for data
//                            String[] data = new String[3];
//                            data[0] = username;
//                            data[1] = email;
//                            data[2] = password;
//                            PutData putData = new PutData("https://192.168.0.149/apismt4/signup.php", "POST", field, data);
//                            if (putData.startPut()) {
//                                if (putData.onComplete()) {
//                                    String result = putData.getResult();
//                                    if(result.equals("Sign Up Success")){
//                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
//                                        Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
//                                        startActivity(loginIntent);
//                                        finish();
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
//                                    }
//                                    //End ProgressBar (Set visibility to GONE)
//                                }
//                            }
//                            //End Write and Read data with URL
//                        }
//                    });
//                } else {
//                    Toast.makeText(getApplicationContext(),"Tolong isi semua form", Toast.LENGTH_SHORT).show();
//                }
//                String username = edit1.getText().toString();
//                String password = edit2.getText().toString();

//                CreateDataToServer(username, password);
//                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(loginIntent);
            }
        });
    }

    private void register(String username, String email, String password) {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
//    public void CreateDataToServer(final String username, final String password){
//        if(checkNetworkConnection()){
//            progressDialog.show();
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_REGISTER_URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                String resp = jsonObject.getString("server_response");
//                                if (resp.equals("[{\"status\":\"OK\"}]")){
//                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("username", username);
//                    params.put("password", password);
//                    return params;
//                }
//            };
//
//            VolleyConnection.getInstance(RegisterActivity.this).addToRequestQue(stringRequest);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progressDialog.cancel();
//                }
//            }, 2000);
//        } else{
//            Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public boolean checkNetworkConnection(){
//        ConnectivityManager connectivityManager= (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
}