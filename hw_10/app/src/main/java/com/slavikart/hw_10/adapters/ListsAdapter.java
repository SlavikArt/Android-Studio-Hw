package com.slavikart.hw_10.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.slavikart.hw_10.R;
import com.slavikart.hw_10.model.ShoppingList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {
    private List<ShoppingList> lists;
    private OnListClickListener listener;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    public interface OnListClickListener {
        void onListClick(ShoppingList list);
    }

    public ListsAdapter(List<ShoppingList> lists, OnListClickListener listener) {
        this.lists = lists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        ShoppingList list = lists.get(pos);
        holder.tvName.setText(list.getName());
        holder.tvDate.setText(sdf.format(new Date(list.getDate())));
        holder.tvDesc.setText(list.getDescription());
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onListClick(list);
        });
    }

    @Override
    public int getItemCount() { return lists.size(); }

    public void updateLists(List<ShoppingList> newLists) {
        this.lists = newLists;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvDesc;

        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvDate = v.findViewById(R.id.tv_date);
            tvDesc = v.findViewById(R.id.tv_desc);
        }
    }
} 