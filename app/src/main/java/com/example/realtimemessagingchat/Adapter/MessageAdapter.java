package com.example.realtimemessagingchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realtimemessagingchat.Model.Massege;
import com.example.realtimemessagingchat.Model.User;
import com.example.realtimemessagingchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    public static final int MSG_LEFT=1;
    public static final int MSG_RIGHT=0;

    private Context context;
    private List<Massege> masseges;
    String imgURL;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    public MessageAdapter(Context context,List<Massege> masseges,String imgURL){
        this.context=context;
        this.masseges=masseges;
        this.imgURL=imgURL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,final int viewType) {
        if (viewType == MSG_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
        MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(view);

        return viewHolder;
    }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(view);

            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user =dataSnapshot.getValue(User.class);

                Massege massege=masseges.get(position);
                holder.msg_text.setText(massege.getMessage());
                if (imgURL.equals("defult")){
                    holder.circleImageView_msg.setImageResource(R.drawable.images1);
                }else {

                    if (holder.getItemViewType()==0) {

                        Picasso.get().load(imgURL).into(holder.circleImageView_msg);
                    }

                    if (holder.getItemViewType()==1){

                            Picasso.get().load(user.getImageURL()).into(holder.circleImageView_msg);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return masseges.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView msg_text;
        CircleImageView circleImageView_msg;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        msg_text=itemView.findViewById(R.id.textView_msg_display);
        circleImageView_msg=itemView.findViewById(R.id.imageView_msg_display);
    }
}

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (masseges.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_LEFT;
        }else{
            return MSG_RIGHT;
        }


    }
}

