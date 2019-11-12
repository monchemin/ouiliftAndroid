package com.ouilift.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ouilift.R;
import com.ouilift.presenter.RouteReservationPresenter;

import java.util.List;

public class RouteReservationAdapter extends RecyclerView.Adapter<RouteReservationAdapter.ViewHolder> {

    private List<RouteReservationPresenter> dataSet;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_route_reservation_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.displayItem(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public void setData(List<RouteReservationPresenter> presenters) {
        dataSet = presenters;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView routeDate, from, to;
        RatingBar ratingBar;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            routeDate = itemView.findViewById(R.id.route_date);
            from = itemView.findViewById(R.id.route_from);
            to = itemView.findViewById(R.id.route_to);
            ratingBar = itemView.findViewById(R.id.route_rating_bar);

        }

        void displayItem(RouteReservationPresenter presenter) {
            routeDate.setText(presenter.lastName + " " + presenter.firstName);
            to.setText(presenter.phoneNumber);
            from.setText(presenter.mail);
            ratingBar.setNumStars(presenter.place);
            ratingBar.setRating(presenter.place);

        }
    }


}
