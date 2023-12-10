package com.l20290968.mycountryapp.paises.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.l20290968.mycountryapp.R;

public class PaisViewHolder extends RecyclerView.ViewHolder {
    TextView tvCountryName, tvCountryCapital,tvCountryPopulation;

    ShapeableImageView sivCountryImg;
    public PaisViewHolder(@NonNull View itemView) {
        super(itemView);

        tvCountryName = itemView.findViewById(R.id.contryItemTvContryName);
        tvCountryPopulation = itemView.findViewById(R.id.contryItemConstryPopulation);
        tvCountryCapital = itemView.findViewById(R.id.contryItemConstryCapital);

        sivCountryImg = itemView.findViewById(R.id.contryItemSivContryimg);

    }
}
