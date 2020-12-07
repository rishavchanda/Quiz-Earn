package com.rishav.quizearn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.rishav.quizearn.databinding.FragmentWalletBinding;


public class WalletFragment extends Fragment {


    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentWalletBinding binding;
    FirebaseFirestore database;
    Users user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(inflater,container,false);
        database=FirebaseFirestore.getInstance();

        database.collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(Users.class);
                binding.currentCoins.setText(String.valueOf(user.getCoins()));
            }
        });

        binding.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getCoins() > 50000){
                    String uid = FirebaseAuth.getInstance().getUid();
                    String paypal = binding.paypalEmailid.getText().toString();
                    if(binding.paypalEmailid == null) {
                        Toast.makeText(getContext(),"Please enter Paypal Emailid",Toast.LENGTH_SHORT).show();
                    }else {
                        withdrawRequest request = new withdrawRequest(uid, paypal,user.getName());
                        database.collection("withdraws")
                                .document(uid)
                                .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(),"Request sent Sucessfully !..Your Money will be transfered within 4 to 5 days.",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }else {
                    Toast.makeText(getContext(),"You need more coins to withdraw money.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }
}