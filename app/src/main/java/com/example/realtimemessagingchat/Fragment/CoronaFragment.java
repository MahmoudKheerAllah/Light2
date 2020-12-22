package com.example.realtimemessagingchat.Fragment;

import android.Manifest;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.realtimemessagingchat.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class CoronaFragment extends Fragment {
    RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_corona, container, false);


        final TextView textViewaTC=view.findViewById(R.id.textView2_TC);
        final TextView textViewaTD=view.findViewById(R.id.textView2_TD);
        final TextView textViewaTR=view.findViewById(R.id.textView2_TR);

        queue= Volley.newRequestQueue(this.getContext());
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, "https://api.covid19api.com/world/total", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String TC=response.getString("TotalConfirmed");
                    String TD=response.getString("TotalDeaths");
                    String TR=response.getString("TotalRecovered");

                    textViewaTC.setText(TC);
                    textViewaTD.setText(TD);
                    textViewaTR.setText(TR);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(objectRequest);

        return view;

    }

}
