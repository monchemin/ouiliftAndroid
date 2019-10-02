package com.ouilift.ui.search;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ouilift.R;
import com.ouilift.presenter.RouteStation;
import com.ouilift.ui.adapter.RouteStationAdapter;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RouteSearchDialog extends DialogFragment {

    private List<RouteStation> stations;
    private RouteStationAdapter adapter = new RouteStationAdapter();

    private View.OnClickListener onItemClickListener = view -> {

        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        loadItem( viewHolder.getAdapterPosition());
    };
    private OnInputListener onInputListener;

    public RouteSearchDialog(List<RouteStation> stations) {
        this.stations = stations;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.route_select_dialog, container, false);
        (rootView.findViewById(R.id.button_close)).setOnClickListener(v -> loadItem(0));
        SearchView searchView = rootView.findViewById(R.id.route_search_view);
        searchView.onActionViewExpanded();
        RecyclerView recyclerView = rootView.findViewById(R.id.route_station_recycler_view);
        LinearLayoutManager manager  = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: " + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setData(stations);
        adapter.setOnItemClickListener(onItemClickListener);
    }

    private void loadItem(int position) {
        RouteStation item = adapter.getItem(position);
        if(item != null) {
            onInputListener.sendInput(item.PK);
        } else {
            onInputListener.sendInput(item.PK);
        }
        dismiss();
    }


    public interface OnInputListener {
        void sendInput(int input);
    }
}
