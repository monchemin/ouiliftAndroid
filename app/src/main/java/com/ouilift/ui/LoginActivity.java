package com.ouilift.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.constant.DataConstant;
import com.ouilift.constant.IntentConstant;
import com.ouilift.model.CustomerViewModel;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.ui.search.ReservationActivity;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

import java.util.Locale;

public class LoginActivity extends BaseActivity {

    TextInputEditText _emailText, _passwordText;
    MaterialButton loginButton;
    TextView signUp, afterRegistration, recovery;
    private CustomerViewModel viewModel;
    private boolean forRoute, registration;
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
        routeId = getIntent().getIntExtra(IntentConstant.ROUTE_ID, 0);
        place = getIntent().getIntExtra(IntentConstant.ROUTE_PLACE, 0);
        forRoute = getIntent().getBooleanExtra(IntentConstant.FOR_ROUTE, false);
        registration =  getIntent().getBooleanExtra(IntentConstant.AFTER_REGISTRATION, false);
        if(registration) {
            afterRegistration.setVisibility(View.VISIBLE);
        }

        recovery = findViewById(R.id.password_recovery_link);
        recovery.setOnClickListener(v -> recoveryPassword());
    }


    private void performLogin() {
        JsonObject data = new JsonObject();
        data.addProperty(DataConstant.LOGIN, _emailText.getText().toString());
        data.addProperty(DataConstant.PASSWORD, _passwordText.getText().toString());
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
            intent.putExtra(IntentConstant.ROUTE_ID, routeId);
            intent.putExtra(IntentConstant.ROUTE_PLACE, place);
            intent.putExtra(IntentConstant.IS_FIRST_REGISTRATION, registration);
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

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getString(R.string.password_error));
            _passwordText.requestFocus();
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void recoveryPassword() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.view_password_recovery);

        TextView text = dialog.findViewById(R.id.input_recovery_email);

        Button dialogButton = dialog.findViewById(R.id.btn_recovery);

        dialogButton.setOnClickListener(v -> {
            String mailText = text.getText().toString();
            if (mailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mailText).matches()) {
                text.setError(getString(R.string.email_error));
                text.requestFocus();
            } else {
                JsonObject data = new JsonObject();
                data.addProperty(DataConstant.E_MAIL, mailText);
                data.addProperty(DataConstant.LANGUAGE, Locale.getDefault().getLanguage());
                viewModel.passwordRecovery(data);
                dialog.dismiss();
            }

        });

        dialog.show();
    }


}
