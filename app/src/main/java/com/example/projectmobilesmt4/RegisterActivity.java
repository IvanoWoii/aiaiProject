package com.example.projectmobilesmt4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText edit1,edit2,edit3,edit4,edit5;

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
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

}