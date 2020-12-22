package com.example.realtimemessagingchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.realtimemessagingchat.Model.Country;
import com.example.realtimemessagingchat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCountry extends RecyclerView.Adapter<AdapterCountry.ViewHolder>  {
    List<Country> countries;
    Context context;


    public AdapterCountry(List<Country> countries,Context context){
        this.context=context;

        this.countries=countries;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.country_item,parent,false);
        AdapterCountry.ViewHolder viewHolder=new AdapterCountry.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country=countries.get(position);
        holder.Country.setText(country.getCName()+"");
        holder.NewConfirmed.setText(country.getNewConfirmed()+"");
        holder.NewDeaths.setText(country.getNewDeaths()+"");
        holder.NewRecovered.setText(country.getNewRecovered()+"");
        holder.TotalConfirmed.setText(country.getTotalConfirmed()+"");
        holder.TotalDeaths.setText(country.getTotalDeaths()+"");
        holder.TotalRecovered.setText(country.getTotalRecovered()+"");


    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void filterList(ArrayList<Country> countryArrayList) {
        this.countries = countryArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Country;
        public TextView NewConfirmed;
        public TextView TotalConfirmed;
        public TextView NewDeaths;
        public TextView TotalDeaths;
        public TextView NewRecovered;
        public TextView TotalRecovered;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Country=itemView.findViewById(R.id.Country_item);
            NewConfirmed=itemView.findViewById(R.id.NewConfirmed);
            TotalConfirmed=itemView.findViewById(R.id.TotalConfirmed);
            NewDeaths=itemView.findViewById(R.id.NewDeaths);
            TotalDeaths=itemView.findViewById(R.id.TotalDeaths);
            NewRecovered=itemView.findViewById(R.id.NewRecovered);
            TotalRecovered=itemView.findViewById(R.id.TotalRecovered);
        }
    }


}
