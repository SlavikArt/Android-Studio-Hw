package com.slavikart.hw_05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserModel> {

    public UserAdapter(Context context, List<UserModel> users) {
        super(context, R.layout.list_item_user, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModel user = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_user, parent, false);
            viewHolder.avatar = convertView.findViewById(R.id.avatarImageView);
            viewHolder.name = convertView.findViewById(R.id.nameTextView);
            viewHolder.age = convertView.findViewById(R.id.ageTextView);
            viewHolder.location = convertView.findViewById(R.id.locationTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.avatar.setImageResource(user.getAvatarResId());
        viewHolder.name.setText(user.getFirstName() + " " + user.getLastName());
        viewHolder.age.setText("Age: " + user.getAge());
        viewHolder.location.setText(user.getCity() + ", " + user.getCountry());

        return convertView;
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView age;
        TextView location;
    }
}
