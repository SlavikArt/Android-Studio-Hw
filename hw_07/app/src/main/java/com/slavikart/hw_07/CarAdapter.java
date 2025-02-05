package com.slavikart.hw_07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> cars;

    public CarAdapter(List<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent
                .getContext())
                .inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.brand.setText(car.getBrand());
        holder.model.setText(car.getModel());
        holder.year.setText(String.valueOf(car.getYear()));
        holder.cost.setText("$" + car.getCost());
        holder.description.setText(car.getDescription());
        holder.image.setImageResource(car.getImgResID());
    }

    @Override
    public int getItemCount() { return cars.size(); }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView brand, model, year, cost, description;
        ImageView image;

        public CarViewHolder(View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.brandTextView);
            model = itemView.findViewById(R.id.modelTextView);
            year = itemView.findViewById(R.id.yearTextView);
            cost = itemView.findViewById(R.id.costTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            image = itemView.findViewById(R.id.carImageView);
        }
    }
}
