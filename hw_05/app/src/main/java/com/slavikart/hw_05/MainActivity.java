package com.slavikart.hw_05;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<UserModel> users = generateUsers(100);
        UserAdapter adapter = new UserAdapter(this, users);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            UserModel user = users.get(position);
            Toast.makeText(this, user.getFirstName()
                    + " is " + user.getAge() + " years old", Toast.LENGTH_SHORT).show();
        });
    }

    private List<UserModel> generateUsers(int count) {
        List<UserModel> users = new ArrayList<>();

        int[] avatarResIds = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5};
        String[] firstNames = {
                "John", "Emma", "Michael", "Sophia", "William",
                "Olivia", "James", "Ava", "Robert", "Mia"
        };
        String[] lastNames = {
                "Smith", "Johnson", "Brown", "Taylor", "Miller",
                "Davis", "Wilson", "Moore", "Anderson", "Clark"
        };
        String[] countries = {
                "USA", "Canada", "Mexico"
        };
        List<List<String>> citiesByCountry = Arrays.asList(
                Arrays.asList("New York", "Los Angeles", "Chicago", "Houston", "Phoenix"),
                Arrays.asList("Toronto", "Vancouver", "Montreal", "Calgary", "Ottawa"),
                Arrays.asList("Mexico City", "Guadalajara", "Monterrey", "Tijuana", "Puebla")
        );

        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int avatarIndex = random.nextInt(avatarResIds.length);

            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];

            int age = 14 + random.nextInt(86); // 14-99

            int countryIndex = random.nextInt(countries.length);
            String country = countries[countryIndex];
            String city = citiesByCountry.get(countryIndex).get(random.nextInt(5));

            users.add(new UserModel(avatarResIds[avatarIndex],
                    firstName, lastName, age, country, city));
        }
        return users;
    }
}
