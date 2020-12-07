package com.rishav.quizearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    ImageView logout;
    RecyclerView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        category=findViewById(R.id.category);
        ArrayList<CategoryModel> categories = new ArrayList<>();
        categories.add(new CategoryModel("","Mathematics","https://cdn.dribbble.com/users/2552641/screenshots/6549959/icon_challenge_originals_edu2_1x.jpg"));
        categories.add(new CategoryModel("","Mathematics","https://cdn.dribbble.com/users/2552641/screenshots/6549959/icon_challenge_originals_edu2_1x.jpg"));
        categories.add(new CategoryModel("","Mathematics","https://cdn.dribbble.com/users/2552641/screenshots/6549959/icon_challenge_originals_edu2_1x.jpg"));
        categories.add(new CategoryModel("","Mathematics","https://cdn.dribbble.com/users/2552641/screenshots/6549959/icon_challenge_originals_edu2_1x.jpg"));
        categories.add(new CategoryModel("","Mathematics","https://cdn.dribbble.com/users/2552641/screenshots/6549959/icon_challenge_originals_edu2_1x.jpg"));
        categories.add(new CategoryModel("","Mathematics","https://cdn.dribbble.com/users/2552641/screenshots/6549959/icon_challenge_originals_edu2_1x.jpg"));

        CategoryAdapter adapter = new CategoryAdapter(this,categories);
        category.setLayoutManager(new GridLayoutManager(this,2));
        category.setAdapter(adapter);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logoutDialog = new AlertDialog.Builder(v.getContext());
                logoutDialog.setTitle("Logout");
                logoutDialog.setMessage("If you want to Logout then click ok.");


                logoutDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Dashboard.this,Login.class));
                    }
                });
                logoutDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                logoutDialog.create().show();

            }
        });



    }
}