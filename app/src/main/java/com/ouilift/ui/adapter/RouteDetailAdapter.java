package com.ouilift.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ouilift.R;
import com.ouilift.presenter.RouteDetailPresenter;
import com.ouilift.ui.LoginActivity;
import com.ouilift.utils.DateUtils;
import com.ouilift.utils.Preference;

import java.util.List;

public class RouteDetailAdapter extends RecyclerView.Adapter<RouteDetailAdapter.RouteDetailViewHolder> {

    private List<RouteDetailPresenter> dataSet;
    private Context context;

    @NonNull
    @Override
    public RouteDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View itemView = inflater.inflate(R.layout.view_route_detail_item, parent, false);
        return new RouteDetailViewHolder(itemView, parent.getContext().getString(R.string.date_format));
    }

    @Override
    public void onBindViewHolder(@NonNull RouteDetailViewHolder holder, int position) {
        holder.displayItem(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public void setData(List<RouteDetailPresenter> presenters) {
        dataSet = presenters;
        notifyDataSetChanged();
    }

    private void onItemSelected(RouteDetailPresenter presenter) {
        Intent intent;
        if(Preference.IsConnected(context)) {
            intent = new Intent(context, LoginActivity.class);
        } else {
            intent = new Intent(context, LoginActivity.class);
        }
        context.startActivity(intent);
    }

    class RouteDetailViewHolder extends RecyclerView.ViewHolder {

        TextView routeDate, hour, price, from, to, place;
        String date_format;
        RouteDetailPresenter presenter;

        RouteDetailViewHolder(@NonNull View itemView, String date_format) {
            super(itemView);
            routeDate = itemView.findViewById(R.id.route_date);
            hour = itemView.findViewById(R.id.route_hour);
            price = itemView.findViewById(R.id.route_price);
            from = itemView.findViewById(R.id.route_from);
            to = itemView.findViewById(R.id.route_to);
            place = itemView.findViewById(R.id.route_place);
            this.date_format = date_format;
            LinearLayout container = itemView.findViewById(R.id.route_item_container);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelected(presenter);
                }
            });
        }

        void displayItem(RouteDetailPresenter presenter) {
            this.presenter = presenter;
            routeDate.setText(DateUtils.dateToString(presenter.routeDate, date_format));
            hour.setText(presenter.hour);
            price.setText(String.valueOf(presenter.routePrice));
            to.setText(presenter.toStation);
            from.setText(presenter.fromStation);
           place.setText(String.valueOf(presenter.remainingPlace));
        }
    }
}
