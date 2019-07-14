package com.ouilift.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ouilift.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}
