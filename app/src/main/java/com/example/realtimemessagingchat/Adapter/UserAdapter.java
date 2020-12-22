package com.example.realtimemessagingchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realtimemessagingchat.MassegeActivity;
import com.example.realtimemessagingchat.Model.User;
import com.example.realtimemessagingchat.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
        private Context context;
        private List<User> users;
        public UserAdapter(Context context,List<User> users){
            this.context=context;
            this.users=users;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
    UserAdapter.ViewHolder viewHolder=new UserAdapter.ViewHolder(view);


    return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user=users.get(position);
        holder.textView_username.setText(user.getUsername());
        if (user.getImageURL().equals("defult")){
            holder.imageView.setImageResource(R.drawable.images1);
        }else {
            Picasso.get().load(user.getImageURL()).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MassegeActivity.class);
                intent.putExtra("userid",user.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    public CircleImageView imageView;
    public TextView textView_username;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.item_image);
        textView_username=itemView.findViewById(R.id.text_Name_item);
    }
}
}
