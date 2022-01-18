package com.municoyllur.servicioscoyllurquiapp.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentComunicados;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentConoceMuni;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentMiCuenta;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentReportes;
import com.municoyllur.servicioscoyllurquiapp.NavigCoyllActivity;
import com.municoyllur.servicioscoyllurquiapp.R;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyFragment;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.Constantes;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.InternetConnection;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.SharedPrefUsuarios;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HomeFragment extends BaseVolleyFragment {

    View root;
    //Clase que guarda el usuario logueado
    SharedPrefUsuarios sesionuser;
    HashMap<String, String> user;

    private HomeViewModel homeViewModel;
    Dialog initialDialog;
    Dialog dialogsubida;
    AlertDialog alertDialogBuilder;
    AlertDialog getAlertDialogBuilderlibro;
    AlertDialog Builderimage;

    //Variables para verificar status
    String KEY_ID;
    String KEY_ESTADO;
    String KEY_TIPO;
    String KEY_PLAN;

    //variables de imagen
    String extension;

    ImageView ImgPick1;
    ImageView ImgPick2;
    ImageView ImgPick3;
    ImageView ImgPick4;
    ImageView ImgPick5;

    NavigationView navigationView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        NavigCoyllActivity Navigation= (NavigCoyllActivity) getActivity();
        Toolbar toolbar= Navigation.findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        sesionuser =new SharedPrefUsuarios(getContext());
        user = sesionuser.getUserDetails();
        //Procedimiento  para verificar status
        StatusRequest();

        ImgPick1=root.findViewById(R.id.ImgPick1);
        ImgPick2=root.findViewById(R.id.ImgPick2);
        ImgPick3=root.findViewById(R.id.ImgPick3);
        ImgPick4=root.findViewById(R.id.ImgPick4);
        ImgPick5=root.findViewById(R.id.ImgPick5);

        ImgPick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(1);
            }
        });
        ImgPick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(2);
            }
        });
        ImgPick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(6);
            }
        });
        ImgPick4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(4);
            }
        });
        ImgPick5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(5);
            }
        });

        return root;
    }


    //Metodo para verificar status de un usuario
    private void StatusRequest(){
        final String JsonURL = Constantes.GetUsuarioID+"?correoUsuario="+user.get(SharedPrefUsuarios.KEY_EMAIL).toString();
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,JsonURL,null,
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonuser = response.getJSONObject("usuarios");
                            for (int i = 0; i < jsonuser.length(); i++) {

                                KEY_ID = jsonuser.getString("us_idUsuario");
                                KEY_TIPO = jsonuser.getString("us_tipoUsuario");
                                KEY_ESTADO = jsonuser.getString("us_estadoUsuario");
                                KEY_PLAN = jsonuser.getString("us_planUsuario");
                            }
                            if (user.get(SharedPrefUsuarios.KEY_TIPO).toString().equals(KEY_TIPO)){
                            }{sesionuser.sharedStatusTipo(KEY_TIPO);}
                            if (user.get(SharedPrefUsuarios.KEY_ESTADO).toString().equals(KEY_ESTADO)){
                            }{sesionuser.sharedStatusEstado(KEY_ESTADO);}

                            if(user.get(SharedPrefUsuarios.KEY_ESTADO).toString().equals("-1")){
                                sesionuser.logoutUser();
                                getActivity().finish();
                            }
                            /*Menu menu = navigationView.getMenu();
                            MenuItem menuadministrador = menu.findItem(R.id.MenuAdministrador);
                            switch (KEY_TIPO) {
                                case "1":
                                    menuadministrador.setVisible(true);
                                    break;
                                case "2":
                                    menuadministrador.setVisible(false);
                                    break;
                                case "3":
                                    menuadministrador.setVisible(false);
                                    break;
                            }*/
                        }
                        catch (JSONException e) {
                            //Toast.makeText(getContext(),e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        addToQueue(obreq);
    }

    public void setFragment(int position) {
        if (!Boolean.valueOf(new InternetConnection(getActivity().getApplicationContext()).isConnectingInternet()).booleanValue())
        {
            Snackbar.make(root, "Sin Conexion a Internet", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();;
        }else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentConoceMuni Muni = new FragmentConoceMuni();
            FragmentComunicados Comunicados = new FragmentComunicados();
            FragmentReportes Rep = new FragmentReportes();
            FragmentMiCuenta Account = new FragmentMiCuenta();
            switch (position) {
                case 1:
                    fragmentTransaction.replace(R.id.nav_host_fragment, Muni);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 2:
                    fragmentTransaction.replace(R.id.nav_host_fragment, Comunicados);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 4:
                    fragmentTransaction.replace(R.id.nav_host_fragment, Rep);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 5:
                    fragmentTransaction.replace(R.id.nav_host_fragment, Account);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 6:
                    View pagoslinea = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pagoslinea, null);
                    final android.app.AlertDialog alertDialogBuilder = new android.app.AlertDialog.Builder(getContext()).create();
                    alertDialogBuilder.setView(pagoslinea);
                    alertDialogBuilder.show();
                    break;
            }
        }
    }
}