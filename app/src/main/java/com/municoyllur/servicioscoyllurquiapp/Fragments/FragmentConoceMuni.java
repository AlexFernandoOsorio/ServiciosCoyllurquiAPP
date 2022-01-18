package com.municoyllur.servicioscoyllurquiapp.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.municoyllur.servicioscoyllurquiapp.NavigCoyllActivity;
import com.municoyllur.servicioscoyllurquiapp.R;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyFragment;


public class FragmentConoceMuni extends BaseVolleyFragment {
    View v;

    TextView text_web;
    TextView text_fanpage;
    ImageView img_telf;
    ImageView img_lugar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentLugaresMuni FragLugarMuni;
    FragmentTelefonico FragTelefonico;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_conoce_muni, container, false);
        NavigCoyllActivity Navigation= (NavigCoyllActivity) getActivity();
        Toolbar toolbar= Navigation.findViewById(R.id.toolbar);
        toolbar.setTitle("Conoce tu Municipalidad");
        text_web= v.findViewById(R.id.txt_muni_web);
        text_fanpage= v.findViewById(R.id.txt_muni_fanpage);
        img_lugar=v.findViewById(R.id.imageLocation);
        img_telf=v.findViewById(R.id.imagePhone);

        text_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        text_fanpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        img_telf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragTelefonico = new FragmentTelefonico();
                fragmentTransaction.replace(R.id.nav_host_fragment, FragTelefonico);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        img_lugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                FragLugarMuni = new FragmentLugaresMuni();
                fragmentTransaction.replace(R.id.nav_host_fragment, FragLugarMuni);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return  v;
    }
}