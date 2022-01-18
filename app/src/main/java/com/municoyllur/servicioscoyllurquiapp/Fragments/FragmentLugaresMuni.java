package com.municoyllur.servicioscoyllurquiapp.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import com.municoyllur.servicioscoyllurquiapp.NavigCoyllActivity;
import com.municoyllur.servicioscoyllurquiapp.R;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentLugaresMuni extends BaseVolleyFragment {
    View v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_lugares_muni, container, false);
        NavigCoyllActivity Navigation= (NavigCoyllActivity) getActivity();
        Toolbar toolbar= Navigation.findViewById(R.id.toolbar);
        toolbar.setTitle("Lugares Importantes");

        //Initialize map fragment
        SupportMapFragment supportMapFragment =(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MarkerOptions markerOptions =new MarkerOptions();
                googleMap.clear();
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.addMarker(new MarkerOptions().position(new LatLng(-13.837046374061249, -72.43208443935929)).title("Municipalidad Coyllurqui"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(-13.837470886128196, -72.43249749954775)).title("Radio Municipal Coyllurqui"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(-13.839681982475415, -72.42945855673383)).title("Garaje Municipal Coyllurqui"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(-13.839411130827452, -72.42778485830898)).title("Ofi. Patrimonio Coyllurqui"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(-13.837215658104558, -72.43168479021624)).title("Biblioteca Municipal Coyllurqui"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-13.837046374061249,-72.43208443935929),new Float(17)));

                //Eventos al tocar un punto
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                    }
                });
            }
        });
        //when map is loaded

        return v;
    }


}