package com.ouilift.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.LoginViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.ui.search.ReservationActivity;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

public class LoginActivity extends BaseActivity {

    TextInputEditText _emailText, _passwordText;
    MaterialButton loginButton;
    TextView signUp;
    private LoginViewModel viewModel;
    private boolean forRoute;
    private int routeId, place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_login);
        _emailText = findViewById(R.id.input_login_email);
        _passwordText = findViewById(R.id.input_login_password);
        signUp = findViewById(R.id.link_sign_up);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    performLogin();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        routeId = getIntent().getIntExtra("routeId", 0);
        place = getIntent().getIntExtra("routePlace", 0);
        forRoute = getIntent().getBooleanExtra("forRoute", false);

    }


    private void performLogin() {
        JsonObject data = new JsonObject();
        data.addProperty("login", _emailText.getText().toString());
        data.addProperty("password", _passwordText.getText().toString());
        viewModel.login(data).observe(this, new Observer<PresenterFactory<Void>>() {
            @Override
            public void onChanged(PresenterFactory<Void> result) {
                if (result.status == 200 && result.isLog) {
                    postLogin();
                }
            }
        });
    }

    private void postLogin() {
        Preference.makeConnect(this);
        if(forRoute) {
            Intent intent = new Intent(this, ReservationActivity.class);
            intent.putExtra("routeId", routeId);
            intent.putExtra("place", place);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, SearchActivity.class));
        }
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.email_error));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3 || password.length() > 10) {
            _passwordText.setError(getString(R.string.last_name_error));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
