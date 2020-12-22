package com.example.realtimemessagingchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realtimemessagingchat.Adapter.MessageAdapter;
import com.example.realtimemessagingchat.Model.Massege;
import com.example.realtimemessagingchat.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MassegeActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView textView_username;

    EditText editText_message;
    ImageView imageView_message;

    String userid;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    MessageAdapter messageAdapter;
    RecyclerView recyclerViewmsg;
    List<Massege> masseges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massege);
        Toolbar toolbar=findViewById(R.id.toolbarmsg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(MassegeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
            }
        });

        Intent intent=getIntent();
        if (intent.hasExtra("userid")){
             userid=intent.getStringExtra("userid");
        }

        recyclerViewmsg=findViewById(R.id.recyclermsg);
        recyclerViewmsg.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerViewmsg.setLayoutManager(layoutManager);

        circleImageView=findViewById(R.id.circleimage_msg);
        textView_username=findViewById(R.id.textView_name_msg);
        editText_message=findViewById(R.id.editText_message);
        imageView_message=findViewById(R.id.imageView_sendmessage);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("User").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                textView_username.setText(user.getUsername());
                if (user.getImageURL().equals("defult")){
                    circleImageView.setImageResource(R.drawable.images1);

                }else {
                    Picasso.get().load(user.getImageURL()).into(circleImageView);
                }
                ReadMessage(firebaseUser.getUid(),userid,user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imageView_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=editText_message.getText().toString();
                if (!msg.trim().equals(""))
                SendMessage(firebaseUser.getUid(),userid,msg);

                editText_message.setText("");

            }
        });

    }

    public void SendMessage(String sender,String reciver,String message){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        Massege massege=new Massege(sender,reciver,message);
        databaseReference.child("Chats").push().setValue(massege);

    }
    public void ReadMessage(final String myid, final String heid, final String imgurl){
        masseges=new ArrayList<>();
        Query query =FirebaseDatabase.getInstance().getReference("Chats").limitToLast(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                masseges.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Massege massege=snapshot.getValue(Massege.class);
                    if (massege.getReciver().equals(myid)&&massege.getSender().equals(heid)||
                            massege.getReciver().equals(heid)&&massege.getSender().equals(myid)){
                        masseges.add(massege);
                    }
                    messageAdapter=new MessageAdapter(MassegeActivity.this,masseges,imgurl);
                    recyclerViewmsg.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MassegeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
