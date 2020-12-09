package com.rishav.quizearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Singup extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailBox,passwordBox,nameBox,codeBox;
    Button AlrdyAcnt,singupButton;
    ProgressDialog dialog,verifyemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Signing");
        dialog.setMessage("We are creating new account");
        dialog.setCanceledOnTouchOutside(false);

        verifyemail = new ProgressDialog(this);
        verifyemail.setTitle("Verify Email");
        verifyemail.setTitle("Verifing email is send to your Email id....go and click the link to verify your email");
        verifyemail.setCanceledOnTouchOutside(false);
        verifyemail.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        nameBox=findViewById(R.id.FullName);
        emailBox=findViewById(R.id.Emailtxt);
        passwordBox=findViewById(R.id.Passwordtxt);
        codeBox=findViewById(R.id.code);
        singupButton=findViewById(R.id.Singupbtn);
        AlrdyAcnt=findViewById(R.id.CrtAcnt);


        singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,pass,name,code;
                email=emailBox.getText().toString();
                pass=passwordBox.getText().toString();
                name=nameBox.getText().toString();
                code=codeBox.getText().toString();

                final Users user =new Users();
                user.setName(name);
                user.setEmail(email);
                user.setPass(pass);
                user.setCode(code);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid =task.getResult().getUser().getUid();
                            database.collection("Users")
                                    .document(uid)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                    startActivity(new Intent(Singup.this,Dashboard.class));
                                    finish();
                                }
                            });
                        }else {
                            dialog.dismiss();
                            Toast.makeText(Singup.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        AlrdyAcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Singup.this,Login.class));
            }
        });
    }
}