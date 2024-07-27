package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, List<Person> persons) {
        super(context, 0, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);
        }

        Person person = getItem(position);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView cityTextView = convertView.findViewById(R.id.cityTextView);

        nameTextView.setText(person.getName());
        cityTextView.setText(person.getCity());

        return convertView;
    }
}
