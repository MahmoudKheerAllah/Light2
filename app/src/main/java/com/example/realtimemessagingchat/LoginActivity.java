package com.example.realtimemessagingchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editText_email;
    EditText editText_password;
    Button buttonlogin;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        editText_email=findViewById(R.id.editTextlogin_email);
        editText_password=findViewById(R.id.editTextlogin_password);
        buttonlogin=findViewById(R.id.buttonlogin_login);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail=editText_email.getText().toString();
                String password=editText_password.getText().toString();
             progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Uplode .....");
                progressDialog.show();
                Loin(e_mail,password);

            }
        });

    }
    public boolean Loin(String email,String password){
        final boolean[] loginb = new boolean[1];
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    loginb[0] =true;
                    progressDialog.dismiss();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    loginb[0]=false;
                    Toast.makeText(LoginActivity.this, "خطا في الايميل أو كلمة المرور ", Toast.LENGTH_SHORT).show();

                }

            }
        });
        return loginb[0];
    }
}
