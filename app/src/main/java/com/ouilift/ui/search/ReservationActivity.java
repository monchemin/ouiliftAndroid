package com.ouilift.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ouilift.R;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.LoginActivity;
import com.ouilift.utils.Preference;

public class ReservationActivity extends BaseActivity {
    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            return false;
        }
    };
    private MaterialButton reservationBtn;
    private TextInputEditText reservationPlace;
    private int routeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        viewBind();
    }

    private void viewBind() {
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        reservationPlace = findViewById(R.id.input_reservation);
        reservationBtn = findViewById(R.id.reservation_button);
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick();
            }
        });
        reservationBtn.setEnabled(false);

        reservationPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onPlaceChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onPlaceChange() {
        try {
            int place = Integer.parseInt(reservationPlace.getText().toString());
            if(place > 0) {
                reservationBtn.setEnabled(true);
            }

        } catch (NumberFormatException ex) {
            reservationBtn.setEnabled(false);
        }
    }

    private void handleClick() {
        if(Preference.IsConnected(this)) {
            performReservation();
        } else {
           startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void performReservation() {
    }
}
