package com.example.projectmobilesmt4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText username,password,email,noHP,konfirmasiPassword;
    Button btnReg;
    TextView btnKembaliLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        email =(EditText) findViewById(R.id.emailEditText);
        noHP = (EditText) findViewById(R.id.noHPEditText);
        konfirmasiPassword = (EditText) findViewById(R.id.konfirmasiPasswordEditText);
        btnReg = (Button) findViewById(R.id.btnReg);
        btnKembaliLogin = (TextView) findViewById(R.id.r_tombolLogin);

        btnKembaliLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });
    }
}