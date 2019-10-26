package com.ouilift.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.CustomerViewModel;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.ui.search.ReservationActivity;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

public class LoginActivity extends BaseActivity {

    TextInputEditText _emailText, _passwordText;
    MaterialButton loginButton;
    TextView signUp, afterRegistration;
    private CustomerViewModel viewModel;
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
        afterRegistration = findViewById(R.id.registration_message);


        loginButton.setOnClickListener(v -> {
            if(validate()) {
                performLogin();
            }
        });
        signUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        routeId = getIntent().getIntExtra("routeId", 0);
        place = getIntent().getIntExtra("routePlace", 0);
        forRoute = getIntent().getBooleanExtra("forRoute", false);
        if(getIntent().getBooleanExtra("afterRegistration", false)) {
            afterRegistration.setVisibility(View.VISIBLE);
        }

    }


    private void performLogin() {
        JsonObject data = new JsonObject();
        data.addProperty("login", _emailText.getText().toString());
        data.addProperty("password", _passwordText.getText().toString());
        loginButton.setEnabled(false);
        loginButton.setText(R.string.in_connexion);
        viewModel.login(data).observe(this, result -> {
            loginButton.setEnabled(true);
            loginButton.setText(R.string.btn_login_text);
            if (result.status == 200 && !result.response.isEmpty()) {
                postLogin(result.response.get(0));
            }
            else {
                _emailText.setError(getString(R.string.login_error));
                _emailText.requestFocus();
            }
        });
    }

    private void postLogin(CustomerPresenter customer) {
        Preference.makeConnect(this, customer);
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
            _emailText.requestFocus();
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3 || password.length() > 10) {
            _passwordText.setError(getString(R.string.last_name_error));
            _passwordText.requestFocus();
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
