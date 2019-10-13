package com.ouilift.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ouilift.R;
import com.ouilift.presenter.RouteStation;

import java.util.List;
import java.util.stream.Collectors;

public class RouteStationAdapter extends RecyclerView.Adapter<RouteStationAdapter.RouteStationViewHolder> {

    private List<RouteStation> dataSet, unMutableDataSet;
    private View.OnClickListener mOnItemClickListener;

    @NonNull
    @Override
    public RouteStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_route_station_item, parent, false);
        return new RouteStationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteStationViewHolder holder, int position) {
        holder.displayItem(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public void setData(List<RouteStation> presenters) {
        dataSet = presenters;
        unMutableDataSet = presenters;
      notifyDataSetChanged();
    }

    private void onItemSelected(RouteStation presenter) {
       // Intent intent = new Intent(context, ReservationActivity.class);
        //intent.putExtra("routeId", presenter.PK);
        //context.startActivity(intent);
    }

    public void filter(String text) {
        dataSet  = unMutableDataSet
                .stream()
                .filter(c -> c.stationName.toLowerCase().contains(text.toLowerCase())
                        || c.StationAddress.toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
        notifyDataSetChanged();

    }

    public RouteStation getItem(int position) {
        return dataSet != null ? dataSet.get(position) : null;

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    class RouteStationViewHolder extends RecyclerView.ViewHolder {

        TextView routeStation, stationAddress;
        RouteStation presenter;


        RouteStationViewHolder(@NonNull View itemView) {
            super(itemView);

            routeStation = itemView.findViewById(R.id.route_station);
            stationAddress = itemView.findViewById(R.id.route_station_address);

            LinearLayout container = itemView.findViewById(R.id.route_item_container);
            container.setOnClickListener(v -> onItemSelected(presenter));
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        void displayItem(RouteStation presenter) {
            this.presenter = presenter;
            routeStation.setText(presenter.stationName);
            stationAddress.setText(presenter.StationAddress);
        }
    }
}
