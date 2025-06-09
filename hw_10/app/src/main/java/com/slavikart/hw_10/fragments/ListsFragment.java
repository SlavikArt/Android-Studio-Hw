package com.slavikart.hw_10.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.slavikart.hw_10.R;
import com.slavikart.hw_10.adapters.ListsAdapter;
import com.slavikart.hw_10.db.DatabaseOperations;
import com.slavikart.hw_10.model.ShoppingList;
import java.util.ArrayList;

public class ListsFragment extends Fragment {
    private RecyclerView rv;
    private ListsAdapter adapter;
    private DatabaseOperations dbOps;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lists, container, false);
        
        dbOps = new DatabaseOperations(getContext());
        rv = v.findViewById(R.id.rv_lists);
        fab = v.findViewById(R.id.fab_add_list);
        
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListsAdapter(new ArrayList<>(), this::onListClick);
        rv.setAdapter(adapter);
        
        fab.setOnClickListener(v1 -> showCreateListDialog());
        
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbOps.open();
        loadLists();
    }

    @Override
    public void onPause() {
        super.onPause();
        dbOps.close();
    }

    private void loadLists() {
        adapter.updateLists(dbOps.getAllLists());
    }

    private void onListClick(ShoppingList list) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putLong("list_id", list.getId());
        fragment.setArguments(args);
        
        getParentFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit();
    }

    private void showCreateListDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_list, null);
        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etDesc = dialogView.findViewById(R.id.et_desc);
        
        new AlertDialog.Builder(getContext())
            .setTitle(R.string.new_list)
            .setView(dialogView)
            .setPositiveButton(R.string.create, (dialog, which) -> {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                
                if (!name.isEmpty()) {
                    ShoppingList list = new ShoppingList(0, name, System.currentTimeMillis(), desc);
                    dbOps.insertList(list);
                    loadLists();
                }
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }
} 