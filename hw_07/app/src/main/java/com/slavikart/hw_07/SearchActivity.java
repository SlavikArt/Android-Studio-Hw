package com.slavikart.hw_07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView brandAutoComplete, modelAutoComplete;
    private Spinner yearSpinner;
    private Button searchButton;
    private List<Car> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        brandAutoComplete = findViewById(R.id.brandAutoComplete);
        modelAutoComplete = findViewById(R.id.modelAutoComplete);
        yearSpinner = findViewById(R.id.yearSpinner);
        searchButton = findViewById(R.id.searchButton);

        cars = getIntent().getParcelableArrayListExtra("cars");

        List<String> brands = cars.stream()
                .map(Car::getBrand).distinct().collect(Collectors.toList());
        List<String> models = cars.stream()
                .map(Car::getModel).distinct().collect(Collectors.toList());
        List<Integer> years = cars.stream()
                .map(Car::getYear).distinct().sorted().collect(Collectors.toList());

        years.add(0, 0);

        brandAutoComplete.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, brands));
        modelAutoComplete.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, models));
        yearSpinner.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years));

        searchButton.setOnClickListener(v -> {
            String brand = brandAutoComplete.getText().toString().trim();
            String model = modelAutoComplete.getText().toString().trim();
            int year = (int) yearSpinner.getSelectedItem();

            List<Car> filteredCars = cars.stream()
                    .filter(car -> (brand.isEmpty() || car.getBrand().toLowerCase().contains(brand.toLowerCase()))
                            && (model.isEmpty() || car.getModel().toLowerCase().contains(model.toLowerCase()))
                            && (year == 0 || car.getYear() == year))
                    .collect(Collectors.toList());

            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.putParcelableArrayListExtra("filteredCars", new ArrayList<>(filteredCars));
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
