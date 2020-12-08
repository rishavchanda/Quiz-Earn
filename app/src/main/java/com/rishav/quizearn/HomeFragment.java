package com.rishav.quizearn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rishav.quizearn.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    FirebaseFirestore database;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);



        database = FirebaseFirestore.getInstance();


        final ArrayList<CategoryModel> categories = new ArrayList<>();
        final CategoryAdapter adapter = new CategoryAdapter(getContext(),categories);

        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categories.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()){
                            CategoryModel modal = snapshot.toObject(CategoryModel.class);
                            modal.setCategoryId(snapshot.getId());
                            categories.add(modal);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        binding.category.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.category.setAdapter(adapter);

        binding.spinwheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Spinner.class));
            }
        });

        return binding.getRoot();




    }
}