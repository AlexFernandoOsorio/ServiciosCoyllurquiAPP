package com.municoyllur.servicioscoyllurquiapp.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.municoyllur.servicioscoyllurquiapp.Adapters.AdapterComunicados;
import com.municoyllur.servicioscoyllurquiapp.Entidades.ClassComunicados;
import com.municoyllur.servicioscoyllurquiapp.NavigCoyllActivity;
import com.municoyllur.servicioscoyllurquiapp.R;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyFragment;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.Constantes;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class FragmentComunicados extends BaseVolleyFragment {
    View v;
    Dialog dialog;
    NavigationView navigationView;
    public List<ClassComunicados> listComunicados = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterComunicados comunicadoAdapter;
    private String TAG = "SqliteDatabaseActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_comunicados, container, false);
        NavigCoyllActivity Navigation= (NavigCoyllActivity) getActivity();
        navigationView = Navigation.findViewById(R.id.nav_view);
        Toolbar toolbar= Navigation.findViewById(R.id.toolbar);
        toolbar.setTitle("Comunicados");
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cargando_linear);
        dialog.show();
        ReceiveComunicados();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerlist_libros);
        recyclerView.setHasFixedSize(true);
        comunicadoAdapter = new AdapterComunicados(listComunicados);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(comunicadoAdapter);
        comunicadoAdapter.notifyDataSetChanged();
        recyclerView.setNestedScrollingEnabled(false);


        return  v;
    }


    public void ReceiveComunicados()
    {
        final String JsonURL = Constantes.GetComunicados+"?idTipoComunicado=1";
        final JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,JsonURL,null,
                new Response.Listener<JSONObject>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonarray = response.getJSONArray("Comunicados");
                            ClassComunicados comunicado;
                            listComunicados.clear();
                            for (int i = 0; i <= jsonarray.length()-1; i++) {
                                JSONObject jsonObject = jsonarray.getJSONObject(i);
                                String idComunicado=jsonObject.getString("co_idComunicado");
                                String TipoComunicado=jsonObject.getString("co_TipoComunicado");
                                String objetivoComunicado=jsonObject.getString("co_objetivoComunicado");
                                String titulo=jsonObject.getString("co_titulo");
                                String descripcion=jsonObject.getString("co_descripcion");
                                String link=jsonObject.getString("co_link");
                                String fechaComunicado=jsonObject.getString("co_fechaComunicado");
                                comunicado=new ClassComunicados(idComunicado,TipoComunicado,objetivoComunicado,titulo,descripcion,link,fechaComunicado);
                                listComunicados.add(comunicado);
                                comunicadoAdapter.notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        }
                        catch (JSONException e) {
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        onConnectionFailed(error.toString());
                    }
                }
        );
        addToQueue(obreq);
    }
}