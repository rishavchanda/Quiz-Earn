package com.rishav.quizearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText emailBox,passwordBox;
    Button loginButton,Crtacnt;

    FirebaseAuth auth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait you will be logged in...");

        auth=FirebaseAuth.getInstance();

        emailBox=findViewById(R.id.Emailtxt);
        passwordBox=findViewById(R.id.Passwordtxt);
        loginButton=findViewById(R.id.Loginbtn);
        Crtacnt=findViewById(R.id.CrtAcnt);

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(Login.this,Dashboard.class));
            finish();
        }



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,password;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()){

                            Toast.makeText(Login.this,"Logged In",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,Dashboard.class));
                            finish();

                        }else {
                            Toast.makeText(Login.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });




        Crtacnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Singup.class));
            }
        });
    }
}