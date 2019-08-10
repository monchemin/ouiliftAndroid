package com.ouilift.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.ouilift.R;
import com.ouilift.ui.search.SearchActivity;

public class MainActivity extends BaseActivity {
    private MaterialButton btnMember, btnNonMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnMember = findViewById(R.id.btn_profile_member);
        btnNonMember = findViewById(R.id.btn_profile_non_member);

        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

        btnNonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });


    }
}
