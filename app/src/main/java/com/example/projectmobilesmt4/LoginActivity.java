package com.example.projectmobilesmt4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView btnReg = (TextView) findViewById(R.id.btn_reg);
        ImageView btnKembali = (ImageView) findViewById(R.id.tombol_kembali);
        EditText usernameTxt = (EditText) findViewById(R.id.l_edit1);
        EditText passwordTxt= (EditText) findViewById(R.id.l_edit2);
        Button btnLog = (Button) findViewById(R.id.l_btnLog);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ActivtyLandingPageActivity.class));
                finish();
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(usernameTxt.getText().toString())){
                    usernameTxt.setError("Username Harus Di Isi !");
                    return;
                }

                if (TextUtils.isEmpty(passwordTxt.getText().toString())){
                    passwordTxt.setError("Password Harus Di Isi !");
                    return;
                }

                startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                finish();
            }
        });
    }
}