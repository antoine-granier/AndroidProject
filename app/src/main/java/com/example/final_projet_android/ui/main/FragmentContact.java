package com.example.final_projet_android.ui.main;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;

import com.example.final_projet_android.MainActivity;
import com.example.final_projet_android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentContact extends Fragment {

    private ListView listView;
    private ArrayList<AndroidContact> selectedContacts;
    private FloatingActionButton toMessage;

    public FragmentContact(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_contact, container, false);

        listView = result.findViewById(R.id.listView_Android_Contacts);

        toMessage = result.findViewById(R.id.toMessage);
        Navigation.setViewNavController(result, new NavController(getContext()));
        toMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main = (MainActivity) getActivity();
                main.onContactSelected(selectedContacts);
            }
        });
        getContacts(savedInstanceState);

        return result;
    }

    public void getContacts(Bundle savedInstanceState) {
        ArrayList<AndroidContact> contacts = new ArrayList<AndroidContact>();
        Cursor cursor = null;
        ContentResolver contentResolver = getContext().getContentResolver();
        try {
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        } catch (Exception ex) {
            Log.e("Error on contact", ex.getMessage());
        }
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                AndroidContact androidContact = new AndroidContact();
                @SuppressLint("Range") String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String contact_display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                androidContact.setName(contact_display_name);
                @SuppressLint("Range") int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contact_id}, null);

                    while (phoneCursor.moveToNext()) {
                        @SuppressLint("Range") String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        androidContact.setTel(phoneNumber);
                    }
                    phoneCursor.close();
                }
                contacts.add(androidContact);
            }

            if(savedInstanceState != null) {
                ArrayList<AndroidContact> listContacts = new ArrayList<>();
                ArrayList<String> telList = savedInstanceState.getStringArrayList("CONTACTS");
                for(AndroidContact contact: contacts) {
                    if(telList.contains(contact.getTel())) {
                        listContacts.add(new AndroidContact(contact.getName(), contact.getTel(), contact.getID()));
                    }
                }
                selectedContacts = listContacts;
            } else {
                selectedContacts = new ArrayList<AndroidContact>();
            }

            AdapterContacts adapter = new AdapterContacts(getContext(), contacts, selectedContacts);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("info", "save");
        ArrayList<String> telList = new ArrayList<>();
        for(AndroidContact contact: selectedContacts) {
            telList.add(contact.getTel());
        }
        savedInstanceState.putStringArrayList("CONTACTS", telList);
    }

    public interface ContactSelectedListener {
        void onContactSelected(ArrayList<AndroidContact> selectedContacts);
    }
}
