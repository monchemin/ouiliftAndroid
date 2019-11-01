package com.ouilift.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.CustomerViewModel;
import com.ouilift.presenter.CustomerPresenter;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.LoginActivity;
import com.ouilift.ui.MainActivity;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

public class SettingsActivity extends BaseActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {

            case R.id.navigation_reservation:
                finish();
                startActivity(new Intent(getApplicationContext(), ReservationListActivity.class));
                break;
            case R.id.navigation_search:
                finish();
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case R.id.navigation_route:
                finish();
                startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                break;
        }
        return true;
    };

    TextInputEditText firstName, lastName, passwordText, newPasswordText, confirmationText, phoneNumber,
            oldPasswordText, eMail, rNumber, activationCode, mailPassword, activatePassword;
    private CustomerViewModel viewModel;
    private CustomerPresenter customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        BottomNavigationView navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bindView();
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

    }

    private void bindView() {
        firstName = findViewById(R.id.input_first_name);
        lastName = findViewById(R.id.input_last_name);
        oldPasswordText = findViewById(R.id.input_login_old_pass);
        passwordText = findViewById(R.id.input_login_password);
        newPasswordText = findViewById(R.id.input_new_password);
        rNumber = findViewById(R.id.input_driving_number);
        confirmationText = findViewById(R.id.input_login_confirmation);
        phoneNumber = findViewById(R.id.input_phone);
        eMail = findViewById(R.id.input_login_email);
        activationCode = findViewById(R.id.input_activate);
        mailPassword = findViewById(R.id.input_mail_password);
        activatePassword = findViewById(R.id.input_activate_password);
        MaterialButton changeButton = findViewById(R.id.btn_change_settings);
        changeButton.setOnClickListener(v -> {
            if (validate(false)) {
                performChange();
            }
        });

        MaterialButton changePass = findViewById(R.id.btn_change_pass);
        changePass.setOnClickListener(v -> {
            if (validate(true)) {
                performChangePass();
            }
        });

        MaterialButton driver = findViewById(R.id.btn_diver);
        driver.setOnClickListener(v -> {
                driverChange();
        });

        MaterialButton activate = findViewById(R.id.btn_activate);
        activate.setOnClickListener(v -> {
            activateAccount();
        });

        MaterialButton btnMail = findViewById(R.id.btn_change_mail);
        btnMail.setOnClickListener(v -> {
            mailChange();
        });

        MaterialButton disconnection = findViewById(R.id.btn_disconnection);
        disconnection.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        customer = Preference.getConnection(this);
        firstName.setText(customer.firstName);
        lastName.setText(customer.lastName);
        phoneNumber.setText(customer.phone);
        eMail.setText(customer.eMail);
        rNumber.setText(customer.drivingNumber);

        if(!Preference.IsDriver(this) || !customer.active) {
            findViewById(R.id.navigation_route).setEnabled(false);
        }

    }

    private boolean validate(boolean isPass) {
        boolean valid = true;

        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String old = oldPasswordText.getText().toString();
        String password = newPasswordText.getText().toString();
        String confirmation = confirmationText.getText().toString();
        String phone = phoneNumber.getText().toString();

        if (!isPass) {


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


            if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
                phoneNumber.setError(getString(R.string.phone_error));
                valid = false;
            } else {
                phoneNumber.setError(null);
            }
        } else {

            if (old.isEmpty()) {
                oldPasswordText.setError(getString(R.string.password_error));
                oldPasswordText.requestFocus();
                valid = false;
            } else {
                oldPasswordText.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                newPasswordText.setError(getString(R.string.password_error));
                newPasswordText.requestFocus();
                valid = false;
            } else {
                newPasswordText.setError(null);
            }
            if (!password.equals(confirmation)) {
                confirmationText.setError(getString(R.string.confirmation_error));
                confirmationText.requestFocus();
                valid = false;
            } else {
                confirmationText.setError(null);
            }
        }

        return valid;
    }

    private void performChange() {
        viewModel.change(makeJson(Action.INFO))
                .observe(this, result -> {
                    if (result.status == 200) {
                        displayMessage(getString(R.string.btn_change_settings));

                    } else {
                        displayMessage(result.errorMessage);
                    }
                });
    }

    private void mailChange() {
        String mail = eMail.getText().toString();
        String password = mailPassword.getText().toString();
        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            eMail.setError(getString(R.string.password_error));
            eMail.requestFocus();
            return;
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mailPassword.setError(getString(R.string.password_error));
            mailPassword.requestFocus();
            return;
        }

        viewModel.changeMail(makeJson(Action.MAIL))
                .observe(this, result -> {
                    if (result.status == 200) {
                        customer.eMail = eMail.getText().toString();
                        Preference.setMail(this, eMail.getText().toString());
                        displayMessage(getString(R.string.btn_change_settings));

                    } else {
                        displayMessage(result.errorMessage);
                    }
                });
    }

    private void activateAccount() {
        String code = activationCode.getText().toString();
        String password = activatePassword.getText().toString();
        if (code.length() != 6) {
            passwordText.setError(getString(R.string.password_error));
            passwordText.requestFocus();
            return;
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            activatePassword.setError(getString(R.string.password_error));
            activatePassword.requestFocus();
            return;
        }

        viewModel.activateAccount(makeJson(Action.ACTIVATE))
                .observe(this, result -> {
                    if (result.status == 200) {
                        customer.active = true;
                        Preference.setActive(this);
                        displayMessage(getString(R.string.btn_change_settings));

                    } else {
                        displayMessage(result.errorMessage);
                    }
                });
    }

    private void driverChange() {
        String password = passwordText.getText().toString();
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError(getString(R.string.password_error));
            passwordText.requestFocus();
            return;
        }
        if (rNumber.getText().toString().isEmpty()) {
            rNumber.setError(getString(R.string.password_error));
            rNumber.requestFocus();
            return;
        }
        viewModel.driver(makeJson(Action.DRIVE))
                .observe(this, result -> {
                    if (result.status == 200) {
                        displayMessage(getString(R.string.btn_change_settings));

                    } else {
                        displayMessage(result.errorMessage);
                    }
                });
    }

    private void performChangePass() {
        viewModel.changePassword(makeJson(Action.PASSWORD))
                .observe(this, result -> {
                    if (result.status == 200) {
                        displayMessage("Modification OK");
                        Preference.disConnected(this);
                        finish();
                        startActivity(new Intent(this, LoginActivity.class));
                    } else {
                        displayMessage(result.errorMessage);
                    }
                });
    }

    private JsonObject makeJson(Action action) {
        JsonObject data = new JsonObject();
        data.addProperty("Id", customer.Id);
        switch (action) {
            case MAIL:
                data.addProperty("oldMail", customer.eMail);
                data.addProperty("newMail", eMail.getText().toString());
                data.addProperty("password", mailPassword.getText().toString());
                break;
            case ACTIVATE:
                data.addProperty("email", customer.eMail);
                data.addProperty("password", activatePassword.getText().toString());
                data.addProperty("code", activationCode.getText().toString());
                break;
            case DRIVE:
                data.addProperty("drivingNumber", rNumber.getText().toString());
                data.addProperty("email", customer.eMail);
                data.addProperty("password", passwordText.getText().toString());
                break;
            case INFO:
                data.addProperty("firstName", firstName.getText().toString());
                data.addProperty("lastName", lastName.getText().toString());
                data.addProperty("phoneNumber", phoneNumber.getText().toString());
                break;
            case PASSWORD:
                data.addProperty("newPassword", newPasswordText.getText().toString());
                data.addProperty("oldPassword", oldPasswordText.getText().toString());
                data.addProperty("email", customer.eMail);
        }



        return data;
    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private enum Action {
        PASSWORD,
        MAIL,
        ACTIVATE,
        DRIVE,
        INFO

    }

}
