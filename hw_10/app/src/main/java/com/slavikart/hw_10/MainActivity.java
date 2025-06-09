package com.slavikart.hw_10;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.slavikart.hw_10.fragments.ListsFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        fm = getSupportFragmentManager();
        navView = findViewById(R.id.nav_view);
        
        navView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            
            if (id == R.id.nav_lists) {
                fm.beginTransaction()
                    .replace(R.id.fragment_container, new ListsFragment())
                    .commit();
                return true;
            }
            return false;
        });
        
        if (savedInstanceState == null)
            fm.beginTransaction()
                .replace(R.id.fragment_container, new ListsFragment())
                .commit();
    }
}