package com.example.final_projet_android.ui.main;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_projet_android.R;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListViewHolder> {

    private ArrayList<String> messages;
    private ArrayList<String> selectedMessages;

    public MessageListAdapter(ArrayList<String> messages, ArrayList<String> selectedMessages) {
        this.messages = messages;
        this.selectedMessages = selectedMessages;
    }

    public class MessageListViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private CheckBox checkMessage;

        public MessageListViewHolder(View itemMessage) {
            super(itemMessage);
            message = itemMessage.findViewById(R.id.message);
            checkMessage = itemMessage.findViewById(R.id.checkBoxMessage);
            checkMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkMessage.isChecked()) {
                        selectedMessages.add((String) message.getText());
                    } else {
                        selectedMessages.remove((String) message.getText());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemMessage = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_message, parent,false);
        return new MessageListViewHolder(itemMessage);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder holder, int position) {
        holder.message.setText( messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
