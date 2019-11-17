package com.ouilift.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ouilift.R;
import com.ouilift.ui.account.DriverActivity;
import com.ouilift.ui.account.ReservationListActivity;
import com.ouilift.ui.account.SettingsActivity;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

import static com.ouilift.R.color.colorAccentLight;
import static com.ouilift.R.color.error;
import static com.ouilift.R.color.success;
import static com.ouilift.R.color.yellow;

public class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView navView;
    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                setMenuEnable();
                switch (item.getItemId()) {

                    case R.id.navigation_setting:
                        finish();
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        break;
                    case R.id.navigation_search:
                        finish();
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        break;
                    case R.id.navigation_reservation:
                        finish();
                        startActivity(new Intent(getApplicationContext(), ReservationListActivity.class));
                        break;
                    case R.id.navigation_route:
                        navigationRouteClick();
                        break;
                }
                return true;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    protected void navigationRouteClick() {
        if(Preference.IsDriver(this) && Preference.IsActive(this)) {
            startActivity(new Intent(getApplicationContext(), DriverActivity.class));
        } else {
            error("You must active your account or set your driving licence  before");
        }
    }

    protected void setMenuEnable() {
        if (!Preference.IsConnected(this)) {
            findViewById(R.id.navigation_setting).setEnabled(false);
            findViewById(R.id.navigation_reservation).setEnabled(false);
            findViewById(R.id.navigation_route).setEnabled(false);
        }

    }

    protected void error(String message) {
        showMessage(message, MessageCode.ERROR);

    }

    protected void success(String message) {
        showMessage(message, MessageCode.SUCCESS);
    }

    protected void info(String message) {
        showMessage(message, MessageCode.INFO);
    }

    private void showMessage(String message, MessageCode type) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.view_message,
                findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        ImageView image = layout.findViewById(R.id.message_image);
        text.setText(message);

        switch (type) {
            case INFO:
                layout.setBackground(getDrawable(R.drawable.info_rounded_corner));
                image.setImageResource(R.drawable.ic_info_24px);
                break;
            case ERROR:
                layout.setBackground(getDrawable(R.drawable.error_rounded_corner));
                image.setImageResource(R.drawable.ic_error_outline_24px);
                break;
                default:
                    layout.setBackground(getDrawable(R.drawable.message_rounded_corner));
        }

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    enum MessageCode {
        SUCCESS,
        INFO,
        ERROR
    }

}
