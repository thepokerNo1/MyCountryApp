package com.l20290968.mycountryapp.paises.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.l20290968.mycountryapp.R;
import com.l20290968.mycountryapp.services.restcontries.models.Country;

import java.util.List;

public class PaisAdapter extends RecyclerView.Adapter<PaisViewHolder> {
    private Context context;

    private List<Country> countries;
    private int lastAnimetedItem = -1;

    public PaisAdapter(Context context, List<Country> countries){
        this.context = context;
        this.countries = countries;

    }

    @NonNull
    @Override
    public PaisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_recycler_item_layout, parent,false);

        return new PaisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisViewHolder holder, int position) {
        Country pais = countries.get(position);

        try {
            Glide.with(context).load( pais.getFlags().getPNG() ).into(holder.sivCountryImg);

            holder.tvCountryName.setText(pais.getName().getOfficial());
            holder.tvCountryPopulation.setText(String.format("%,d",pais.getPopulation()));
            holder.tvCountryCapital.setText(pais.getCapital().get(0));

            setAnimation(holder.itemView,position);
        }catch (Exception E){}
    }

    private void setAnimation(View itemView, int position) {
        if(position > lastAnimetedItem){
            //creando animacion
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.fall_downanimation);
            itemView.startAnimation(animation);
            lastAnimetedItem = position;
        }
    }


    @Override
    public int getItemCount() {
        return countries.size();
    }
}
