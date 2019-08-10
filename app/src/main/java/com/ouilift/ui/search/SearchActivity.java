package com.ouilift.ui.search;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ouilift.R;
import com.ouilift.model.SearchViewModel;
import com.ouilift.ui.BaseActivity;

public class SearchActivity extends BaseActivity implements SearchFragment.OnFragmentInteractionListener  {

BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            navView.setVisibility(View.INVISIBLE);
            SearchFragment fragment = new SearchFragment();
            fragment.setViewModel(viewModel);
            return loadFragment(fragment);
        }
    };
    private SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

    }


    @Override
    protected void onResume() {
        super.onResume();
        navView.setVisibility(View.VISIBLE);
        ResultFragment fragment = new ResultFragment();
        fragment.setViewModel(viewModel);
        fragment.setData(null, 0, 0);
        loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onFragmentInteraction(String rDate, int from, int to) {
        ResultFragment fragment = new ResultFragment();
        fragment.setViewModel(viewModel);
        fragment.setData(rDate, from, to);
        loadFragment(fragment);
        navView.setVisibility(View.VISIBLE);
    }


}
