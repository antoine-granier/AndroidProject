package com.example.final_projet_android.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_projet_android.MainActivity;
import com.example.final_projet_android.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentMessage extends Fragment {

    private EditText newMessage;
    private Button addMessage;
    private RecyclerView listMessage;
    private MessageListAdapter messageAdapter;
    private ArrayList<String> messages;
    private ArrayList<String> selectedMessages;
    private FloatingActionButton toSMS;

    public FragmentMessage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_message, container, false);

        newMessage = result.findViewById(R.id.newMessage);
        addMessage = result.findViewById(R.id.addMessage);
        listMessage = (RecyclerView) result.findViewById(R.id.listMessage);
        selectedMessages = new ArrayList<String>();
        toSMS = result.findViewById(R.id.toSMS);

        if(savedInstanceState != null) {
            messages = savedInstanceState.getStringArrayList("MESSAGES");
        } else {
            messages = new ArrayList<>();
        }

        messageAdapter = new MessageListAdapter(messages, selectedMessages);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        listMessage.setLayoutManager(layoutManager);
        listMessage.setItemAnimator(new DefaultItemAnimator());
        listMessage.setAdapter(messageAdapter);

        addMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                String message = newMessage.getText().toString();
                if(!message.equals("") && !message.equals("Enter your message")) {
                    messages.add(message);
                    messageAdapter.notifyDataSetChanged();
                    newMessage.setText("");
                }
            }
        });

        toSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main = (MainActivity) getActivity();
                main.onMessageSelected(selectedMessages);
            }
        });

        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("info", "save");
        savedInstanceState.putStringArrayList("MESSAGES", messages);
    }

    public interface MessageSelectedListener {
        void onMessageSelected(ArrayList<String> selectedMessages);
    }
}
