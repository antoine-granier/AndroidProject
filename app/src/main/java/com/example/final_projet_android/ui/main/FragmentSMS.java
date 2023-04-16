package com.example.final_projet_android.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.final_projet_android.MainActivity;
import com.example.final_projet_android.R;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentSMS extends Fragment {

    private Button sendSMS;
    private TextView smsTo;

    public FragmentSMS() {}

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_sms, container, false);

        MainActivity main = (MainActivity) getActivity();

        sendSMS = result.findViewById(R.id.smsButton);
        smsTo = result.findViewById(R.id.smsTo);

        main.setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<String> selectedMessages = Objects.requireNonNull(main).getSelectedMessages();
                ArrayList<AndroidContact> selectedContacts = main.getSelectedContacts();

                if(selectedContacts.size() > 0 && selectedMessages.size() > 0) {
                    smsTo.setText("Envoyer les messages suivant : \n" + selectedMessages.toString() + " à " + selectedContacts.toString());
                } else {
                    smsTo.setText("Veuillez choisir au moins un contact et un message.");
                }

                sendSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(selectedContacts.size() > 0 && selectedMessages.size() > 0) {
                            for(AndroidContact contact: selectedContacts) {
                                for(String message: selectedMessages) {
                                    sendTextSMS(contact.getTel(), message);
                                }
                            }
                            Toast.makeText(getContext(), "Message(s) envoyé(s)", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        return result;
    }

    public void sendTextSMS(String phone, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, null, null);
    }
}
