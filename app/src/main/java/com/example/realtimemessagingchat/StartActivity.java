package com.example.realtimemessagingchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class StartActivity extends AppCompatActivity {
    Button login;
    Button Register_btn;
    FirebaseUser firebaseUser;
    ImageView imageView_corona;
    TextView textView_corona;
    TextView textView_confid;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

             if (firebaseUser != null) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
             }
        imageView_corona = findViewById(R.id.imageView_corona);
        textView_corona = findViewById(R.id.textView_corona);
        textView_confid = findViewById(R.id.textView_confid_D);

        reference = FirebaseDatabase.getInstance().getReference("corona");
        //reference= FirebaseDatabase.getInstance().getReference("corona").child("confid_D");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String corona_D = dataSnapshot.child("corona_D").getValue().toString();
                String confid_D = dataSnapshot.child("confid_D").getValue().toString();
                String confid_phpto = dataSnapshot.child("corona_photo").getValue().toString();

                textView_corona.setText(corona_D + "");
                textView_confid.setText(confid_D + "");
                Picasso.get().load(confid_phpto).into(imageView_corona);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        login = findViewById(R.id.button_login);
        Register_btn = findViewById(R.id.button_Register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
