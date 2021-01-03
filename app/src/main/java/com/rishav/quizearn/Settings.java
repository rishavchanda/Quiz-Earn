package com.rishav.quizearn;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class Settings extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (findViewById(R.id.settingsContent)!=null){
            if (savedInstanceState!=null)
                return;
            getFragmentManager().beginTransaction().add(R.id.settingsContent,new settingsFragment()).commit();
        }
    }
}
