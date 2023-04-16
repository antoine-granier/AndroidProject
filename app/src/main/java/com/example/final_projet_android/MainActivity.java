package com.example.final_projet_android;

import android.os.Bundle;

import com.example.final_projet_android.ui.main.AndroidContact;
import com.example.final_projet_android.ui.main.FragmentContact;
import com.example.final_projet_android.ui.main.FragmentMessage;
import com.example.final_projet_android.ui.main.FragmentSMS;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.example.final_projet_android.ui.main.SectionsPagerAdapter;
import com.example.final_projet_android.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentContact.ContactSelectedListener, FragmentMessage.MessageSelectedListener {

    private ActivityMainBinding binding;
    private ArrayList<AndroidContact> selectedContacts;
    private ArrayList<String> selectedMessages;
    private ViewPager viewPager;
    private FragmentRefreshListener fragmentRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.selectedContacts = new ArrayList<AndroidContact>();
        this.selectedMessages = new ArrayList<String>();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        selectedContacts = new ArrayList<AndroidContact>();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onContactSelected(ArrayList<AndroidContact> selectedContacts) {
        this.selectedContacts = selectedContacts;
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
        Log.i("selected contacts", selectedContacts.toString());
    }

    @Override
    public void onMessageSelected(ArrayList<String> selectedMessages) {
        this.selectedMessages = selectedMessages;
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
        Log.i("selected messages", selectedMessages.toString());
        if(getFragmentRefreshListener()!= null){
            getFragmentRefreshListener().onRefresh();
        }
    }

    public ArrayList<AndroidContact> getSelectedContacts() {
        return selectedContacts;
    }

    public ArrayList<String> getSelectedMessages() {
        return selectedMessages;
    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }
}