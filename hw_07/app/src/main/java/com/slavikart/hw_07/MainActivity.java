package com.slavikart.hw_07;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cars = new ArrayList<>(Arrays.asList(
                new Car("Ford", "Mustang", 2021, "Like new", 35000, R.drawable.car1),
                new Car("Chevrolet", "Camaro", 2022, "Brand new", 40000, R.drawable.car2),
                new Car("Toyota", "Corolla", 2020, "Good condition", 20000, R.drawable.car3),
                new Car("Honda", "Civic", 2019, "Excellent condition", 18000, R.drawable.car4),
                new Car("BMW", "X5", 2021, "Luxury SUV", 55000, R.drawable.car5),
                new Car("Audi", "A4", 2020, "Premium sedan", 45000, R.drawable.car6),
                new Car("Mercedes-Benz", "C-Class", 2022, "Luxury sedan", 60000, R.drawable.car7),
                new Car("Tesla", "Model 3", 2021, "Electric car", 50000, R.drawable.car8),
                new Car("Nissan", "Altima", 2018, "Reliable sedan", 22000, R.drawable.car9),
                new Car("Hyundai", "Tucson", 2020, "Compact SUV", 25000, R.drawable.car10),
                new Car("Kia", "Sorento", 2021, "Family SUV", 30000, R.drawable.car11),
                new Car("Volkswagen", "Golf", 2019, "Hatchback", 20000, R.drawable.car12),
                new Car("Subaru", "Outback", 2020, "All-wheel drive", 28000, R.drawable.car13),
                new Car("Mazda", "CX-5", 2021, "Stylish SUV", 32000, R.drawable.car14),
                new Car("Lexus", "RX", 2022, "Luxury SUV", 65000, R.drawable.car15)
        ));

        carAdapter = new CarAdapter(cars);
        recyclerView.setAdapter(carAdapter);

        findViewById(R.id.searchButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putParcelableArrayListExtra("cars", new ArrayList<>(cars));
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<Car> filteredCars = data.getParcelableArrayListExtra("filteredCars");
            if (filteredCars != null) {
                carAdapter = new CarAdapter(filteredCars);
                recyclerView.setAdapter(carAdapter);
            }
        }
    }
}
