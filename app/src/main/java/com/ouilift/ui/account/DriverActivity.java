package com.ouilift.ui.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.ouilift.R;
import com.ouilift.model.ReservationViewModel;
import com.ouilift.model.SearchViewModel;
import com.ouilift.presenter.CarColorModelPresenter;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.RouteStation;
import com.ouilift.ui.ActionChoosListener;
import com.ouilift.ui.ActionEnum;
import com.ouilift.ui.BaseActivity;
import com.ouilift.ui.adapter.RouteDetailAdapter;
import com.ouilift.ui.search.RouteSearchDialog;
import com.ouilift.ui.search.SearchActivity;
import com.ouilift.utils.DateUtils;
import com.ouilift.utils.Preference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DriverActivity extends BaseActivity implements ActionChoosListener {
    private ReservationViewModel viewModel;

    MaterialButton btnAddCar, btnValidCar, btnRouteAdd;
    LinearLayout addCarContainer, addRouteContainer;
    RouteDetailAdapter adapter = new RouteDetailAdapter();
    int color, model, fromPK, toPK, hourPK, carPK;
    boolean colorFocusDisable, modelFocusDisable, fromFocusDisable, toFocusDisable,
            addCarOn, hourFocusDisable, carFocusDisable;
    List<CarColorModelPresenter> modelPresenters, colorPresenters, hourPresenters, carPresenters = new ArrayList<>();
    ActionEnum action;
    private TextInputEditText searchDateView, searchFrom, searchTo, hour, colorInput,
            modelInput, yearInput, registrationInput, placeInput, priceInput, carInput;
    private String searchDate;
    private List<RouteStation> stations = new ArrayList<>();
    private SearchViewModel searchViewModel;
    private ContentLoadingProgressBar loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        viewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        bindView();
    }

    private void bindView() {
        navView = findViewById(R.id.dashboard_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        btnAddCar = findViewById(R.id.btn_add_car);
        btnValidCar = findViewById(R.id.btn_valid_car);
        addCarContainer = findViewById(R.id.add_car_container);
        addRouteContainer = findViewById(R.id.add_route_container);
        RecyclerView recyclerView = findViewById(R.id.route_detail_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        registrationInput = findViewById(R.id.input_car_number);
        yearInput = findViewById(R.id.input_car_year);
        colorInput = findViewById(R.id.input_car_color);
        modelInput = findViewById(R.id.input_car_model);
        searchDateView = findViewById(R.id.search_date);
        searchFrom = findViewById(R.id.search_from);
        searchTo = findViewById(R.id.search_to);
        hour = findViewById(R.id.search_hour);
        hour.setVisibility(View.VISIBLE);
        btnRouteAdd = findViewById(R.id.route_add_button);
        placeInput = findViewById(R.id.route_place);
        priceInput = findViewById(R.id.route_price);
        carInput = findViewById(R.id.input_car);
        loadingIndicator = findViewById(R.id.loading_indicator);

        clickListeners();


    }

    private void clickListeners() {
        carInput.setOnClickListener(v -> {
            action = ActionEnum.CAR;
            showDialog(new CarColorModelDialog(carPresenters, action));
        });
        carInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !carFocusDisable && !carPresenters.isEmpty()) {

            }
        });
        carInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!carFocusDisable && !carPresenters.isEmpty()) {
                    action = ActionEnum.CAR;
                    showDialog(new CarColorModelDialog(carPresenters, action));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        colorInput.setOnClickListener(v -> {
            action = ActionEnum.COLOR;
            showDialog(new CarColorModelDialog(colorPresenters, action));
        });
        colorInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !colorFocusDisable) {
                action = ActionEnum.COLOR;
                showDialog(new CarColorModelDialog(colorPresenters, action));
            }
        });

        modelInput.setOnClickListener(v -> {
            action = ActionEnum.MODEL;
            showDialog(new CarColorModelDialog(modelPresenters, action));
        });
        modelInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !modelFocusDisable) {
                action = ActionEnum.MODEL;
                showDialog(new CarColorModelDialog(modelPresenters, action));
            }
        });

        hour.setOnClickListener(v -> {
            action = ActionEnum.HOUR;
            showDialog(new CarColorModelDialog(hourPresenters, action));
        });
        hour.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !hourFocusDisable) {
                action = ActionEnum.HOUR;
                showDialog(new CarColorModelDialog(hourPresenters, action));
            }
        });

        btnAddCar.setOnClickListener(v -> {
            if (addCarOn) {
                addCarContainer.setVisibility(View.GONE);
                addRouteContainer.setVisibility(View.VISIBLE);
                hour.setVisibility(View.VISIBLE);
            } else {
                addCarContainer.setVisibility(View.VISIBLE);
                addRouteContainer.setVisibility(View.GONE);
                hour.setVisibility(View.GONE);
            }
            addCarOn = !addCarOn;
        });

        btnValidCar.setOnClickListener(v -> performAddCar());
        searchDateView.setOnClickListener(v -> showDatePicker());
        searchDateView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker();
        });
        searchFrom.setOnClickListener(v -> {
            action = ActionEnum.FROM;
            showRouteDialog();
        });
        searchFrom.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !fromFocusDisable) {
                action = ActionEnum.FROM;
                showRouteDialog();
            }
        });
        searchTo.setOnClickListener(v -> {
            action = ActionEnum.TO;
            showRouteDialog();
        });
        searchTo.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !toFocusDisable) {
                action = ActionEnum.TO;
                showRouteDialog();
            }
        });
        btnRouteAdd.setOnClickListener(v -> performRouteAdd());
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadingIndicator.show();
        viewModel.registeredCar(makeJson(true)).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200 && result.response != null) {
                makeCarPresenter(result.response);
            }
        });
        viewModel.getCarColor().observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                colorPresenters = result.response;

            }
        });
        viewModel.getCarModel().observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                modelPresenters = result.response;
            }
        });

        viewModel.gethour().observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                hourPresenters = result.response;
            }
        });

        viewModel.getOwnerRoutes(makeJson(true)).observe(this, result -> {
            if (result.status == 200 && result.response != null) {
                adapter.setData(result.response);
            }
        });

        if (stations.isEmpty()) {
            searchViewModel.getRouteStation().observe(this, result -> {
                if (result != null && result.status == 200 && !result.response.isEmpty()) {
                    stations = result.response;
                }
            });
        }

    }

    private void makeCarPresenter(List<CarPresenter> response) {
        if (response.isEmpty()) {
            return;
        }
        carPresenters.clear();
        for (CarPresenter car : response) {
            CarColorModelPresenter presenter = new CarColorModelPresenter();
            presenter.Id = car.Id;
            presenter.customOne = car.brand + " " + car.model + " " + car.year;
            presenter.customTwo = car.number + " " + car.color;
            carPresenters.add(presenter);
        }
    }


    private void showDialog(CarColorModelDialog newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void showRouteDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RouteSearchDialog newFragment = new RouteSearchDialog(stations);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private boolean validate() {
        boolean valid = true;

        int year;
        try {
            year = Integer.parseInt(yearInput.getText().toString());
        } catch (NumberFormatException ex) {
            year = 0;
        }

        if (registrationInput.getText().toString().isEmpty()) {
            registrationInput.setError("");
            registrationInput.requestFocus();
            valid = false;
        }

        if (year == 0 || year > Calendar.getInstance().get(Calendar.YEAR)) {
            yearInput.setError("");
            yearInput.requestFocus();
            valid = false;
        }

        if (color == 0) {
            colorInput.setError("");
            colorInput.requestFocus();
            valid = false;
        }

        if (model == 0) {
            modelInput.setError("");
            modelInput.requestFocus();
            valid = false;
        }

        return valid;
    }


    private void performAddCar() {

        if (validate()) {
            loadingIndicator.show();
            viewModel.carCreate(makeJson(false)).observe(this, result -> {
                loadingIndicator.hide();
                if (result.status == 200 && result.response != null) {
                    makeCarPresenter(result.response);
                    addCarContainer.setVisibility(View.GONE);
                    addRouteContainer.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private JsonObject makeJson(boolean get) {
        JsonObject data = new JsonObject();
        data.addProperty("customerId", Preference.getConnection(this).Id);
        if (!get) {
            data.addProperty("year", yearInput.getText().toString());
            data.addProperty("model", model);
            data.addProperty("color", color);
            data.addProperty("number", registrationInput.getText().toString());
        }

        return data;
    }

    @Override
    public void sendInput(int input) {
        switch (action) {
            case TO:
                onToSelect(input);
                break;
            case FROM:
                onFromSelect(input);
                break;
            case COLOR:
                onColorSelect(input);
                break;
            case MODEL:
                onModelSelect(input);
            case HOUR:
                onHourSelect(input);
            case CAR:
                onCarSelect(input);
        }

    }

    private void onColorSelect(int input) {
        System.out.println("nyemo color " + input);
        colorFocusDisable = true;
        for (CarColorModelPresenter presenter : colorPresenters) {
            if (presenter.Id == input) {
                color = input;
                colorInput.setText(presenter.colorName);
                break;
            }

        }
    }

    private void onModelSelect(int input) {
        modelFocusDisable = true;
        for (CarColorModelPresenter presenter : modelPresenters) {
            if (presenter.Id == input) {
                model = input;
                modelInput.setText(presenter.model);
                break;
            }
        }
    }

    private void onHourSelect(int input) {
        hourFocusDisable = true;
        for (CarColorModelPresenter presenter : hourPresenters) {
            if (presenter.Id == input) {
                hourPK = input;
                hour.setText(presenter.hour);
                break;
            }
        }
    }

    private void onCarSelect(int input) {
        carFocusDisable = true;
        for (CarColorModelPresenter presenter : carPresenters) {
            if (presenter.Id == input) {
                carPK = input;
                carInput.setText(presenter.customOne);
                break;
            }
        }
    }

    private void onToSelect(int input) {
        toFocusDisable = true;
        for (RouteStation station : stations) {
            if (station.stationId == input) {
                searchTo.setText(station.stationName);
                toPK = input;
                break;
            }
        }

    }

    private void onFromSelect(int input) {
        fromFocusDisable = true;
        for (RouteStation station : stations) {
            if (station.stationId == input) {
                searchFrom.setText(station.stationName);
                fromPK = input;
                break;
            }
        }

    }

    private void showDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        final int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        DatePickerDialog picker = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    int selectM = monthOfYear + 1;
                    Date date = DateUtils.stringToDate(year1 + "-" + selectM + "-" + dayOfMonth);
                    searchDate = DateUtils.dateToString(date, "yyyy-MM-dd");
                    searchDateView.setText(DateUtils.dateToString(date, getString(R.string.date_format)));
                }, year, month, day);
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        picker.show();
    }

    private void performRouteAdd() {
        String error = getString(R.string.error_label);
        if (carPK == 0) {
            carInput.setError(error);
        }
        if (fromPK == 0) {
            searchFrom.setError(error);
            return;
        }
        if (toPK == 0 || fromPK == toPK) {
            searchTo.setError(error);
            return;
        }
        if (searchDate == null) {
            searchDateView.setError(error);
            return;
        }
        if (hourPK == 0) {
            hour.setError(error);
        }

        if (!DateUtils.isNumeric(placeInput.getText().toString()) && Integer.parseInt(placeInput.getText().toString()) > 3) {
            placeInput.setError(error);
            placeInput.requestFocus();
            return;
        }

        if (!DateUtils.isNumeric(priceInput.getText().toString())) {
            priceInput.setError(error);
            priceInput.requestFocus();
            return;
        }

        JsonObject data = new JsonObject();
        data.addProperty("customerId", Preference.getConnection(this).Id);
        data.addProperty("departure", fromPK);
        data.addProperty("arrival", toPK);
        data.addProperty("date", searchDate);
        data.addProperty("price", priceInput.getText().toString());
        data.addProperty("place", placeInput.getText().toString());
        data.addProperty("hour", hourPK);
        data.addProperty("car", carPK);
        loadingIndicator.show();
        viewModel.createRoute(data).observe(this, result -> {
            loadingIndicator.hide();
            if (result.status == 200 && result.response != null) {
                adapter.setData(result.response);
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
