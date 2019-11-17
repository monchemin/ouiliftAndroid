package com.ouilift.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.CustomerViewModel;

public class RegisterActivity extends BaseActivity {

    TextInputEditText firstName, lastName, eMail, passwordText, confirmationText, phoneNumber;
    MaterialButton createButton;
    private CustomerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindView();
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

    }

    private void bindView() {
        firstName = findViewById(R.id.input_first_name);
        lastName = findViewById(R.id.input_last_name);
        eMail = findViewById(R.id.input_login_email);
        passwordText = findViewById(R.id.input_login_password);
        confirmationText = findViewById(R.id.input_login_confirmation);
        phoneNumber = findViewById(R.id.input_phone);
        createButton = findViewById(R.id.btn_create);
        createButton.setOnClickListener(v -> {
            if (validate()) {
                performRegister();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String email = eMail.getText().toString();
        String password = passwordText.getText().toString();
        String confirmation = confirmationText.getText().toString();
        String phone = phoneNumber.getText().toString();

        if (lName.isEmpty() || lName.length() < 3) {
            lastName.setError(getString(R.string.last_name_error));
            valid = false;
        } else {
            lastName.setError(null);
        }

        if (fName.isEmpty() || fName.length() < 3) {
            firstName.setError(getString(R.string.first_name_error));
            valid = false;
        } else {
            firstName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eMail.setError(getString(R.string.email_error));
            valid = false;
        } else {
            eMail.setError(null);
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            phoneNumber.setError(getString(R.string.phone_error));
            valid = false;
        } else {
            phoneNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError(getString(R.string.password_error));
            valid = false;
        } else {
            passwordText.setError(null);
        }
        if (!password.equals(confirmation)) {
            confirmationText.setError(getString(R.string.confirmation_error));
            valid = false;
        } else {
            confirmationText.setError(null);
        }

        return valid;
    }

    private void performRegister() {
        createButton.setText(R.string.account_in_progress);
        viewModel.register(makeJson())
                .observe(this, result -> {
                    if (result.status == 200 && result.lastIndex != 0) {
                        displayMessage(true, getString(R.string.register_done_message));

                    } else {
                        displayMessage(false, getString(R.string.error_message));
                    }
                });
    }

    private void displayMessage(boolean ok, String message) {

        if(ok) {
            success(message);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("afterRegistration", true);
            startActivity(intent);
            finish();
        } else {
            error(message);
        }
    }

    private JsonObject makeJson() {
        JsonObject data = new JsonObject();
        data.addProperty("firstName", firstName.getText().toString());
        data.addProperty("lastName", lastName.getText().toString());
        data.addProperty("eMail", eMail.getText().toString());
        data.addProperty("phoneNumber", phoneNumber.getText().toString());
        data.addProperty("password", passwordText.getText().toString());
        return data;
    }
}
