package com.slavikart.hw_08;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class NamesListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_names_list, container, false);
        GridView gridView = view.findViewById(R.id.gridView);

        List<String> namesList = new ArrayList<>();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null)
            for (NameModel model : activity.names)
                namesList.add(model.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                namesList
        );
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            NameDetailsFragment fragment = new NameDetailsFragment();
            Bundle args = new Bundle();
            args.putString("name", namesList.get(position));
            fragment.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}
