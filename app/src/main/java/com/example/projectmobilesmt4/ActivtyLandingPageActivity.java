package com.example.projectmobilesmt4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivtyLandingPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_landing_page);

        Button btn_loginPage = (Button) findViewById(R.id.btn1);
        Button btn_RegisterPage = (Button) findViewById(R.id.btn2);

        btn_loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivtyLandingPageActivity.this, LoginActivity.class));
                finish();
            }
        });

        btn_RegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivtyLandingPageActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}