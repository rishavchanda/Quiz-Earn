package com.rishav.quizearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rishav.quizearn.databinding.ActivitySpinnerBinding;

public class Spinner extends AppCompatActivity {
    ActivitySpinnerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}