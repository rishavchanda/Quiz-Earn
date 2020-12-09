package com.rishav.quizearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rishav.quizearn.databinding.FragmentProfileBinding;

import java.net.URI;

import static android.app.Activity.RESULT_OK;

public class Profile_Fragment extends Fragment {


    public Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentProfileBinding binding;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

       binding.addpic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               choosePicture();
           }
       });


        return binding.getRoot();
    }



    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            binding.profilepic.setImageURI(imageUri);
            uploadpic();
        }
    }

    private void uploadpic(){

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Uploading Image..");
        pd.show();

        StorageReference riversRef = storageReference.child("images/"+ FirebaseAuth.getInstance().getUid());

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                       //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        pd.dismiss();
                        Toast.makeText(getContext(), "Image Uploaded.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        pd.dismiss();
                        Toast.makeText(getContext(), "Fail to upload.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percent "+(int)progressPercent+"%");
                pd.setCanceledOnTouchOutside(false);
            }
        });
    }

}