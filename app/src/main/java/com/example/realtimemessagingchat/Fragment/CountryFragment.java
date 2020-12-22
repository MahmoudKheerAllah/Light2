package com.example.realtimemessagingchat.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.realtimemessagingchat.Adapter.AdapterCountry;
import com.example.realtimemessagingchat.Model.Country;
import com.example.realtimemessagingchat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;


public class CountryFragment extends Fragment {
    RequestQueue queue;
    List<Country> countries=new ArrayList<>();
    AdapterCountry adapterCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_country, container, false);
        queue= Volley.newRequestQueue(getContext());
        EditText editTextSearch=view.findViewById(R.id.editTextsearch);
        final RecyclerView recyclerView=view.findViewById(R.id.recycleviewcountry);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String url="https://api.covid19api.com/summary";
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("Countries");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        Country country=new Country();
                        country.setCName(object.getString("Country"));
                        country.setNewConfirmed(object.getInt("NewConfirmed"));
                        country.setNewDeaths(object.getInt("NewDeaths"));
                        country.setNewRecovered(object.getInt("NewRecovered"));
                        country.setTotalRecovered(object.getInt("TotalRecovered"));
                        country.setTotalDeaths(object.getInt("TotalDeaths"));
                        country.setTotalConfirmed(object.getInt("TotalConfirmed"));
                         countries.add(country);
                    }
                    AdapterCountry adapterCountry=new AdapterCountry(countries,getContext());
                    recyclerView.setAdapter(adapterCountry);

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
         adapterCountry=new AdapterCountry(countries,getContext());
        recyclerView.setAdapter(adapterCountry);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });


        return view;
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Country> filterdcountry = new ArrayList<>();

        //looping through existing elements
        for (Country country : countries) {
            //if the existing elements contains the search input
            if (text!= null && !text.isEmpty()&&text.contains("Error")) {
                if (country.getCName().toUpperCase().contains(text)) {
                    //adding the element to filtered list
                    filterdcountry.add(country);
                }
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapterCountry.filterList(filterdcountry);
    }
}
