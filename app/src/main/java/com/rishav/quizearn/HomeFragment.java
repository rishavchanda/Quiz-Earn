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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
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
        binding.progresshome.setVisibility(View.VISIBLE);



        database = FirebaseFirestore.getInstance();


        final ArrayList<CategoryModel> categories = new ArrayList<>();
        final CategoryAdapter adapter = new CategoryAdapter(getContext(),categories);

        database.collection("categories")
                .orderBy("index", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        categories.clear();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                            CategoryModel modal = snapshot.toObject(CategoryModel.class);
                            modal.setCategoryId(snapshot.getId());
                            categories.add(modal);
                        }
                        adapter.notifyDataSetChanged();
                        binding.progresshome.setVisibility(View.GONE);
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

        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 try{
                     Intent intent = new Intent(Intent.ACTION_SEND);
                     intent.setType("text/plain");
                     intent.putExtra(Intent.EXTRA_SUBJECT,"Quiz Earn");
                     intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+getContext().getPackageName());
                     startActivity(Intent.createChooser(intent,"Share With"));
                 }catch (Exception e){
                     Toast.makeText(getActivity(), "Unable to share at this moment.."+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                 }
            }
        });

        return binding.getRoot();




    }
}