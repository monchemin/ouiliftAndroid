package com.ouilift.ui.search;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ouilift.R;
import com.ouilift.model.SearchViewModel;
import com.ouilift.presenter.PresenterFactory;
import com.ouilift.presenter.RouteStation;
import com.ouilift.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchFragment extends Fragment implements SearchFragmentListerner {


    private OnFragmentInteractionListener mListener;
    private Spinner fromSpinner, toSpinner;
    private TextInputEditText searchDateView;
    int from, to;
    private String searchDate;
    private List<RouteStation> fromList = new ArrayList<>();
    private List<RouteStation> toList = new ArrayList<>();
    private Boolean fromInitial = true, toInitial = true;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        fromSpinner = view.findViewById(R.id.from_spinner);
        toSpinner = view.findViewById(R.id.to_spinner);
        toSpinner.setEnabled(false);
        searchDateView = view.findViewById(R.id.search_date);
        searchDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        MaterialButton searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(searchDate, from, to);
            }
        });
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onFromSpinnerItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onToSpinnerItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadSpinnerData();

        return view;
    }

    private void loadSpinnerData() {
    }

    private void onFromSpinnerItemSelected(int position) {
        if(fromInitial) {
            fromInitial = false;
        } else {
            RouteStation station = fromList.get(position);
            from = station.PK;
            toList.clear();
            RouteStation prompt = new RouteStation();
            prompt.stationName = getString(R.string.route_to);
            toList.add(prompt);
            for (RouteStation presenter : fromList) {
                if (presenter != station && presenter.PK != 0) {
                    toList.add(presenter);
                }
            }
            ArrayAdapter<RouteStation> adapter = new ArrayAdapter<RouteStation>(getContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    toList);
            toSpinner.setEnabled(true);
            toSpinner.setPromptId(R.string.route_to);
            toSpinner.setAdapter(adapter);
            toInitial = true;
        }
    }

    private void onToSpinnerItemSelected(int position) {
        if(toInitial) {
            toInitial = false;
        } else {
            if(toList.get(position).PK != 0) {
                to = toList.get(position).PK;
            }
        }
    }

    private void showDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        final int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        DatePickerDialog picker = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int selectM = monthOfYear + 1;
                        Date date = DateUtils.stringToDate(year + "-" + selectM + "-" + dayOfMonth);
                        searchDate = DateUtils.dateToString(date, "yyyy-MM-dd");
                        searchDateView.setText(DateUtils.dateToString(date, getString(R.string.date_format)));
                    }
                }, year, month, day);
        picker.show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setViewModel(SearchViewModel viewModel) {
        viewModel.getRouteStation().observe(this, new Observer<PresenterFactory<RouteStation>>() {
            @Override
            public void onChanged(PresenterFactory<RouteStation> routeStationPresenterFactory) {
                fillSpinners(routeStationPresenterFactory.response);
            }
        });
    }

    private void fillSpinners(List<RouteStation> response) {
        RouteStation prompt = new RouteStation();
        fromList.clear();
        prompt.stationName = getString(R.string.route_from);
        fromList.add(prompt);
        fromList.addAll(response);
        ArrayAdapter<RouteStation> adapter = new ArrayAdapter<RouteStation>(getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                fromList);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setPrompt(getString(R.string.route_from));
        //fromSpinner.
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String date, int from, int to);
    }
}
