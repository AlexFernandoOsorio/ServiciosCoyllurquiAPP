 package com.municoyllur.servicioscoyllurquiapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentMiCuenta;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentTelefonico;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyActivity;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.Constantes;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.SharedPrefUsuarios;
import com.municoyllur.servicioscoyllurquiapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentReportes;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentComunicados;
import com.municoyllur.servicioscoyllurquiapp.Fragments.FragmentConoceMuni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

 public class NavigCoyllActivity extends BaseVolleyActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Clase que guarda el usuario logueado
    SharedPrefUsuarios sesionuser;
    HashMap<String, String> user;
     String KEY_ID,KEY_DNI,KEY_TIPO,KEY_NOM,KEY_APELLIDO,
             KEY_ESTADO,KEY_EMAIL,KEY_DIRECCION,KEY_PASS,
             KEY_EDAD,KEY_TEL,KEY_GEN,KEY_INTERES,KEY_IMAGEN,KEY_TOKEN,KEY_FECHA;
    String refreshedToken;
    //variablepara identificar  tipo de usuario
    String tipousuario;
    //Variablepara enviar datos
    Bundle bundleidcat;
    Dialog dialog;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        sesionuser =new SharedPrefUsuarios(getApplicationContext());
        user= sesionuser.getUserDetails();

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_closesesion);


        //Captamos el token de nuestra aplicacion
        //Se obtiene el token actualizado
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText(NavigCoyllActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Get new FCM registration token
                        refreshedToken = task.getResult();

                    }
                });
        switch (user.get(SharedPrefUsuarios.KEY_TIPO).toString()) {
            case "4":
                tipousuario="Administrador";
                break;
            case "3":
                tipousuario="Servidor Municipal";//
                break;
            case "2":
                tipousuario="Estudiante";
                break;
            case "1":
                tipousuario="Usuario Normal";
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Sentencias para acceder al headerview del navigationview
        View headerView = navigationView.getHeaderView(0);
        TextView nav_headertipo= headerView.findViewById(R.id.nav_textView1);
        TextView nav_headernombre=headerView.findViewById(R.id.nav_textView2);

        ImageView drawerProfile = (ImageView) headerView.findViewById(R.id.nav_imageView);
        Glide.with(this).load(user.get(SharedPrefUsuarios.KEY_IMAGEN).toString()).into(drawerProfile);
        //drawerProfile.setImageDrawable(rounded);
        nav_headertipo.setText("Bienvenido  "+ tipousuario);
        nav_headernombre.setText(user.get(SharedPrefUsuarios.KEY_NOM));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        setFragmentn(121);


    }
     @Override
     public void onBackPressed() {
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         if (drawer.isDrawerOpen(GravityCompat.START)) {
             drawer.closeDrawer(GravityCompat.START);
         } else {
             super.onBackPressed();
         }
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



     @Override
     //Metodo que realiza una transicion de fragments segun el indice del Menu Principal lateral Izquierdo seleccionado
     public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setFragmentn(0);
        } else if (id == R.id.nav_nosotros) {
            setFragmentn(2);
        } else if (id == R.id.nav_comunicados) {
            setFragmentn(3);
        } else if (id == R.id.nav_reportes) {
            setFragmentn(1);
        } else if (id == R.id.nav_account) {
            setFragmentn(4);
        } else if (id == R.id.nav_Directorio) {
            setFragmentn(5);
        }else if (id == R.id.nav_fanpage) {
            //Inicio del metodo GetAplicationContext para indexar facebook
            Intent facebookIntent = openFacebook(getApplicationContext());
            startActivity(facebookIntent);
        }else if (id == R.id.nav_politicas) {
            //Inicio simple para  indexar una URI
            String url = "https://servicios.municoyllurqui.gob.pe/Scripts/Politicas%20y%20Privacidad.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        else if (id == R.id.nav_closesesion) {
            setFragmentn(7);
        }

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setFragmentn(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fragmentos a  Transaction
        HomeFragment Home = new HomeFragment();
        FragmentReportes Reporte=new FragmentReportes();
        FragmentConoceMuni ConoceMuni= new FragmentConoceMuni();
        FragmentComunicados Comunicados= new FragmentComunicados();
        FragmentMiCuenta Micuenta= new FragmentMiCuenta();
        FragmentTelefonico directel= new FragmentTelefonico();

        Bundle bundled=new Bundle();

        switch (position) {
            case 121:
                fragmentTransaction.replace(R.id.nav_host_fragment, Home);
                fragmentTransaction.commit();
                break;
            case 0:
                fragmentTransaction.replace(R.id.nav_host_fragment, Home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.nav_host_fragment, Reporte);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.nav_host_fragment, ConoceMuni);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.nav_host_fragment, Comunicados);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentTransaction.replace(R.id.nav_host_fragment, Micuenta);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 5:
                fragmentTransaction.replace(R.id.nav_host_fragment, directel);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;


            case 6:
                View pagoslinea = LayoutInflater.from(this).inflate(R.layout.dialog_pagoslinea, null);
                final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this).create();
                alertDialogBuilder.setView(pagoslinea);
                alertDialogBuilder.show();
                break;

            case 7:
                dialog.show();
                uploadRefreshToken("default","sesion");
                break;

        }
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
                                KEY_TOKEN = jsonuser.getString("us_tokenUsuario");
                            }
                            if (user.get(SharedPrefUsuarios.KEY_TIPO).toString().equals(KEY_TIPO)){
                            }{sesionuser.sharedStatusTipo(KEY_TIPO);}
                            if (user.get(SharedPrefUsuarios.KEY_ESTADO).toString().equals(KEY_ESTADO)){
                            }{sesionuser.sharedStatusEstado(KEY_ESTADO);}
                            //Verificacion de token
                            if (refreshedToken.equals(KEY_TOKEN)){
                            }{uploadRefreshToken(refreshedToken,"update");}
                            if(user.get(SharedPrefUsuarios.KEY_ESTADO).toString().equals("-1")){
                                sesionuser.logoutUser();
                                finish();
                            }

                        }
                        catch (JSONException e) {
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

    //Metodo para actualizar token de un usuario a nuestraBD
    private void uploadRefreshToken(final String token,final String tipoupdate){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.updatetUsuario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (tipoupdate.equals("sesion")){
                            dialog.dismiss();
                            sesionuser.logoutUser();
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new Hashtable<String, String>();
                params.put("tokenUsuario",token);
                params.put("idUsuario",user.get(SharedPrefUsuarios.KEY_ID).toString());
                return params;
            }
        };
        addToQueue(stringRequest);
    }
     //Metodo que realiza una transicion de fragments segun el indice del Submenu lateral derecho seleccionado
     public boolean onOptionsItemSelected(MenuItem item) {
         // Handle action bar item clicks here. The action bar will
         // automatically handle clicks on the Home/Up button, so long
         // as you specify a parent activity in AndroidManifest.xml.
         int id = item.getItemId();
         //noinspection SimplifiableIfStatement
         //Despliegue del mensaje de version de la app
         if (id == R.id.action_settings) {
             View promptView = LayoutInflater.from(this).inflate(R.layout.dialog_comentario_acerca, null);
             final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this).create();
             alertDialogBuilder.setView(promptView);
             alertDialogBuilder.show();
         }
         //Cierre de sesion
         if (id == R.id.action_settings2) {
             View promptView = LayoutInflater.from(this).inflate(R.layout.dialog_user_closesesion, null);
             final  AlertDialog sesionBuilder = new AlertDialog.Builder(this).create();
             Button sesionyes=promptView.findViewById(R.id.btn_sesionyes);
             sesionyes.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.show();
                     uploadRefreshToken("default","sesion");
                     sesionBuilder.dismiss();
                 }
             });
             Button sesionno=promptView.findViewById(R.id.btn_sesionno);
             sesionno.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     sesionBuilder.dismiss();
                 }
             });
             sesionBuilder.setView(promptView);
             sesionBuilder.show();
         }
         return super.onOptionsItemSelected(item);
     }

     //Metodo para indexar facebook
     public static Intent openFacebook(Context context) {

         try {
             context.getPackageManager()
                     .getPackageInfo("com.facebook.katana", 0);
             return new Intent(Intent.ACTION_VIEW,
                     Uri.parse("https://www.facebook.com/municoyllurqui"));
         } catch (Exception e){

             return new Intent(Intent.ACTION_VIEW,
                     Uri.parse("https://www.facebook.com/municoyllurqui"));
         }


     }
}