package com.ouilift.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.presenter.CarColorModelPresenter;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.adapter.CarAdapter;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.Preference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DriverActivity extends BaseActivity implements CarColorModelDialog.OnInputListener {
    private ReservationViewModel viewModel;
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
            case R.id.navigation_setting:
                finish();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
        }
        return true;
    };
    MaterialButton btnAddCar, btnValidCar;
    LinearLayout addCarContainer;
    CarAdapter carAdapter;
    TextInputEditText colorInput, modelInput, yearInput, registrationInput;
    int color, model;
    boolean isColor, colorFocusDisable, modelFocusDisable;
    List<CarColorModelPresenter> modelPresenters, colorPresenters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
        carAdapter = new CarAdapter();
        bindView();
    }

    private void bindView() {
        BottomNavigationView navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        btnAddCar = findViewById(R.id.btn_add_car);
        btnValidCar = findViewById(R.id.btn_valid_car);
        addCarContainer = findViewById(R.id.add_car_container);
        RecyclerView carRecycler = findViewById(R.id.car_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        carRecycler.setLayoutManager(manager);
        carRecycler.setAdapter(carAdapter);
        registrationInput = findViewById(R.id.input_car_number);
        yearInput = findViewById(R.id.input_car_year);
        colorInput = findViewById(R.id.input_car_color);
        modelInput = findViewById(R.id.input_car_model);

        clickListeners();


    }
    private void clickListeners() {
        colorInput.setOnClickListener(v -> {
            isColor = true;
            showDialog();
        });
        colorInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !colorFocusDisable) {
                isColor = true;
                showDialog();
            }
        });
        modelInput.setOnClickListener(v -> {
            isColor = false;
            showDialog();
        });
        modelInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !modelFocusDisable) {
                isColor = false;
                showDialog();
            }
        });

        btnAddCar.setOnClickListener(v -> addCarContainer.setVisibility(View.VISIBLE));

        btnValidCar.setOnClickListener(v -> performAddCar());
    }



    @Override
    protected void onResume() {
        super.onResume();

        viewModel.registeredCar(makeJson(true)).observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                carAdapter.setData(result.response);

            }
        });
        viewModel.getCarColor().observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                colorPresenters =  result.response;

            }
        });
        viewModel.getCarModel().observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                modelPresenters = result.response;
            }
        });
    }


    private void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CarColorModelDialog newFragment = isColor ? new CarColorModelDialog(colorPresenters) : new CarColorModelDialog(modelPresenters);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private boolean validate() {
       boolean valid = true;

       int year;
       try {
           year = Integer.parseInt(yearInput.getText().toString());
       } catch (NumberFormatException ex){
           year = 0;
       }

       if(registrationInput.getText().toString().isEmpty()) {
           registrationInput.setError("");
           registrationInput.requestFocus();
           valid = false;
       }

       if(year == 0 || year > Calendar.getInstance().get(Calendar.YEAR)) {
           yearInput.setError("");
           yearInput.requestFocus();
           valid = false;
       }

       if(color == 0) {
           colorInput.setError("");
           colorInput.requestFocus();
           valid = false;
       }

        if(model == 0) {
            modelInput.setError("");
            modelInput.requestFocus();
            valid = false;
        }

       return valid;
    }


    private void performAddCar() {

        if(validate()) {
            viewModel.carCreate(makeJson(false)).observe(this, result -> {
                if (result.status == 200 && result.response != null) {
                    carAdapter.setData(result.response);
                    addCarContainer.setVisibility(View.GONE);
                }
            });
        }
    }
    private JsonObject makeJson(boolean get) {
        JsonObject data = new JsonObject();
        data.addProperty("PK", Preference.getConnection(this).PK);
        if(!get){
            data.addProperty("year", yearInput.getText().toString());
            data.addProperty("model", model);
            data.addProperty("color", color);
            data.addProperty("number", registrationInput.getText().toString());
        }

        return data;
    }

    @Override
    public void sendInput(int input) {
        if(isColor) {
            for(CarColorModelPresenter presenter : colorPresenters) {
                if(presenter.PK == input) {
                    color = input;
                    colorInput.setText(presenter.colorName);
                    colorFocusDisable = true;
                    break;
                }

            }
        } else {
            for(CarColorModelPresenter presenter : modelPresenters) {
                if(presenter.PK == input) {
                    model = input;
                    modelInput.setText(presenter.model);
                    modelFocusDisable = true;
                    break;
                }

            }
        }

    }
}
