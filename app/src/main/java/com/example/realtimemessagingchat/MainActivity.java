package com.example.realtimemessagingchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.realtimemessagingchat.Fragment.CoronaFragment;
import com.example.realtimemessagingchat.Fragment.CountryFragment;
import com.example.realtimemessagingchat.Fragment.UsersFragment;
import com.example.realtimemessagingchat.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private TextView nameTextView;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        circleImageView = findViewById(R.id.circleimage);
        nameTextView = findViewById(R.id.text_Name);
//
        //firebaseUser
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final @NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                nameTextView.setText(user.getUsername());
                if (user.getImageURL().equals("defult")) {
                    circleImageView.setImageResource(R.drawable.images1);
                } else {
                    Picasso.get().load(user.getImageURL()).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(final @NonNull DatabaseError databaseError) {

            }
        });


        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addfragment(new UsersFragment(), "Users");
        viewPagerAdapter.addfragment(new CoronaFragment(), "News Confid_19");
        viewPagerAdapter.addfragment(new CountryFragment(), "Country status");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final @NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, StartActivity.class));
            return true;
        }
        return false;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments; //arraylistfragmment
        private ArrayList<String> names;
        protected ViewPagerAdapter(final @NonNull FragmentManager fm) {
          super((fm));
            this.fragments = new ArrayList<Fragment>();
            this.names = new ArrayList<String>();
        }

        @NonNull
        @Override
        public Fragment getItem(final int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addfragment(final Fragment fragment, final String title) {
            fragments.add(fragment);
            names.add(title);
        }
        public CharSequence gettitle(final int position) {
            return names.get(position); }

        @Nullable
        @Override
        public CharSequence getPageTitle(final int position) {
            return names.get(position);
        }
    }
}
