package com.slavikart.hw_08;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NameDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_details, container, false);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvMeaning = view.findViewById(R.id.tvMeaning);
        Button btnBack = view.findViewById(R.id.btnBack);

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                NameModel model = activity.getNameDetails(name);
                if (model != null) {
                    tvName.setText(model.getName());
                    tvDate.setText("Дати іменин: " + model.getDate());
                    tvMeaning.setText("Значення: " + model.getMeaning());
                }
            }
        }
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }
}
