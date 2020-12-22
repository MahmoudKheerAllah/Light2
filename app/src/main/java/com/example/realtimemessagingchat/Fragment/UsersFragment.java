package com.example.realtimemessagingchat.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realtimemessagingchat.Model.Massege;
import com.example.realtimemessagingchat.R;
import com.example.realtimemessagingchat.Model.User;
import com.example.realtimemessagingchat.Adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    List<User> users=new ArrayList<>();
    List<String> usersname=new ArrayList<>();

    FirebaseUser firebaseUser;
    //DatabaseReference reference;
    DatabaseReference referenceuser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_users, container, false);
        //recyclerView
        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

ReadUser();
    return view;
    }

    public void ReadUser(){
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        referenceuser= FirebaseDatabase.getInstance().getReference("User");
        referenceuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersname.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    assert  user!=null;
                    assert  firebaseUser!=null;
                        if (!user.getId().equals(firebaseUser.getUid()) ){

                            users.add(user);


                        }
                    userAdapter=new UserAdapter(getContext(),users);

                    recyclerView.setAdapter(userAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
