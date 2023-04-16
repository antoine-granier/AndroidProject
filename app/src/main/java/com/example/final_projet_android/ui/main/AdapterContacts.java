package com.example.final_projet_android.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.final_projet_android.R;

public class AdapterContacts extends BaseAdapter {

    Context context;
    List<AndroidContact> listAndroidContacts;
    ArrayList<AndroidContact> selectedContacts;

    public AdapterContacts(Context context, List<AndroidContact> contact, ArrayList<AndroidContact> selectedContacts) {
        this.context = context;
        this.listAndroidContacts = contact;
        this.selectedContacts = selectedContacts;
    }

    @Override
    public int getCount() {
        return listAndroidContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return listAndroidContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view=View.inflate(context,R.layout.contactlist_android_items,null);
        TextView textviewName= (TextView) view.findViewById(R.id.textview_android_contact_name);
        TextView textviewTel= (TextView) view.findViewById(R.id.textview_android_contact_phoneNumber);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    selectedContacts.add(new AndroidContact(listAndroidContacts.get(position).getName(), listAndroidContacts.get(position).getTel(), listAndroidContacts.get(position).getID()));
                } else {
                    selectedContacts.remove(new AndroidContact(listAndroidContacts.get(position).getName(), listAndroidContacts.get(position).getTel(), listAndroidContacts.get(position).getID()));
                }
            }
        });
        textviewName.setText(listAndroidContacts.get(position).getName());
        textviewTel.setText(listAndroidContacts.get(position).getTel());
        view.setTag(listAndroidContacts.get(position).getName());
        return view;
    }
}
