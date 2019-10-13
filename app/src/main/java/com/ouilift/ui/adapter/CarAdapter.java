package com.ouilift.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ouilift.R;
import com.ouilift.presenter.CarPresenter;
import com.ouilift.presenter.RouteStation;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<CarPresenter> dataSet;
    private View.OnClickListener mOnItemClickListener;

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_car_item, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.displayItem(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public void setData(List<CarPresenter> presenters) {
        dataSet = presenters;
      notifyDataSetChanged();
    }

    private void onItemSelected(CarPresenter presenter) {
       // Intent intent = new Intent(context, ReservationActivity.class);
        //intent.putExtra("routeId", presenter.PK);
        //context.startActivity(intent);
    }



    public CarPresenter getItem(int position) {
        return dataSet != null ? dataSet.get(position) : null;

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    class CarViewHolder extends RecyclerView.ViewHolder {

        TextView carDetail;
        SwitchCompat carToggle;
        CarPresenter presenter;


      CarViewHolder(@NonNull View itemView) {
            super(itemView);

            carDetail = itemView.findViewById(R.id.car_detail);
            carToggle = itemView.findViewById(R.id.car_toggle);

            LinearLayout container = itemView.findViewById(R.id.car_item_container);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelected(presenter);
                }
            });
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        void displayItem(CarPresenter presenter) {
            this.presenter = presenter;
            String text = presenter.brand + " / " + presenter.model + " / " +
                            presenter.year + " / " + presenter.color + " / " + presenter.number;
                    carDetail.setText(text);
            carToggle.setChecked(presenter.checked);
        }
    }
}
