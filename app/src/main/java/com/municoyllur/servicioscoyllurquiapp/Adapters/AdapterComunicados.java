package com.municoyllur.servicioscoyllurquiapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.municoyllur.servicioscoyllurquiapp.Entidades.ClassComunicados;
import com.municoyllur.servicioscoyllurquiapp.R;

import java.util.List;

public class AdapterComunicados extends RecyclerView.Adapter<AdapterComunicados.MyViewHolder>{

    private List<ClassComunicados> listaComunicados;

    private Context context;
    AlertDialog Builder;
    AlertDialog BuilderDelete;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titulo, descripcion, link, fechacomunicado;

        public MyViewHolder(View view) {
            super(view);
            titulo =  view.findViewById(R.id.txt_cardtitulo);
            descripcion =  view.findViewById(R.id.txt_card_descripcion);
            link =  view.findViewById(R.id.txt_card_link);
            fechacomunicado =  view.findViewById(R.id.txt_card_fecha);
        }
    }

    public AdapterComunicados(List<ClassComunicados> listaparameter) {

        this.listaComunicados = listaparameter;
    }

    @Override
    public AdapterComunicados.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemscard_comunicados_adapter, parent, false);
        context=parent.getContext();
        return new AdapterComunicados.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterComunicados.MyViewHolder holder, int position) {
        final ClassComunicados comunicadoTemporal = listaComunicados.get(position);

        holder.titulo.setText(comunicadoTemporal.getTitulo());
        holder.descripcion.setText(comunicadoTemporal.getDescripcion());
        holder.link.setText(comunicadoTemporal.getLink());
        holder.fechacomunicado.setText(comunicadoTemporal.getFechaComunicado());

    }

    @Override
    public int getItemCount() {

        return listaComunicados.size();
    }
    //metodo para regresar idioma segun identificador

}

