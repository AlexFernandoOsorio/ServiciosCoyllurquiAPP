package com.municoyllur.servicioscoyllurquiapp.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.municoyllur.servicioscoyllurquiapp.NavigCoyllActivity;
import com.municoyllur.servicioscoyllurquiapp.R;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyFragment;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.Constantes;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.InternetConnection;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.SharedPrefUsuarios;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class FragmentMiCuenta extends BaseVolleyFragment {
    SharedPrefUsuarios sesionuser;
    HashMap<String, String> user;
    //Variables para verificar status
    String KEY_ID;
    String KEY_ESTADO;
    String KEY_TIPO;
    String KEY_PLAN;
    //Casteo de Edittext
    TextView enombreapell;
    TextView ecorreo;
    TextView eestado;
    TextView etelefono;
    TextView egenero;
    TextView einteres;

    TextView edniuser;
    TextView edireccion;
    TextView eedad;
    ImageView imageupdate;
    //String que contienen los datos guardados en el Shared preferences
    String sdniuser;
    String snombreapell;
    String snombreapellaux;
    String scorreo;
    String sdireccion;
    String sedad;
    String sestado;
    String stelefono;
    String sgenero;
    String sinteres;

    Button btactualiza;
    //Variables para uso de camara
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 2 ;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 0 ;
    private static final int CAMERA_REQUEST = 1888;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    String uripath;
    private String encodedString;
    private String filename;

    android.app.AlertDialog Builder;
    AlertDialog Builderupdate;
    Dialog dialogsubida;

    View v;
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
        NavigCoyllActivity Navigation= (NavigCoyllActivity) getActivity();
        navigationView = Navigation.findViewById(R.id.nav_view);
        Toolbar toolbar= Navigation.findViewById(R.id.toolbar);
        toolbar.setTitle("Mi Cuenta");

        sesionuser =new SharedPrefUsuarios(getContext());
        user = sesionuser.getUserDetails();

        enombreapell=v.findViewById(R.id.txt_account_nombre);
        ecorreo=v.findViewById(R.id.txt_account_correo);
        eestado=v.findViewById(R.id.txt_account_estado);
        etelefono=v.findViewById(R.id.txt_account_telefono);
        egenero=v.findViewById(R.id.txt_account_genero);
        einteres=v.findViewById(R.id.txt_account_intereses);
        edniuser=v.findViewById(R.id.txt_account_dni);
        edireccion=v.findViewById(R.id.txt_account_direccion);
        eedad=v.findViewById(R.id.txt_account_edad);

        btactualiza=v.findViewById(R.id.but_account_actualizar);
        btactualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View promptView = getLayoutInflater().inflate(R.layout.dialog_user_update, null);
                Builderupdate = new AlertDialog.Builder(getContext()).create();
                final TextInputEditText nombre=promptView.findViewById(R.id.dial_accountnombres);
                final TextInputEditText apellidos=promptView.findViewById(R.id.dial_accountapellidos);
                final TextInputEditText dniuser=promptView.findViewById(R.id.dial_accountdni);
                final TextInputEditText telefono=promptView.findViewById(R.id.dial_accounttelefono);
                final Spinner genero=promptView.findViewById(R.id.spinnergenero);
                String[] arraytipo = {"Masculino","Femenino"};
                ArrayAdapter<String> adaptadores = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arraytipo);
                adaptadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                genero.setAdapter(adaptadores);
                final TextInputEditText edad=promptView.findViewById(R.id.dial_accountedad);
                final TextInputEditText interes=promptView.findViewById(R.id.dial_accountintereses);
                final TextInputEditText direccion=promptView.findViewById(R.id.dial_accountubicacion);
                //Asignamos datos a los textviews
                nombre.setText(user.get(SharedPrefUsuarios.KEY_NOM).toString());
                dniuser.setText(sdniuser);
                if (snombreapellaux.equals("- - -")){
                    apellidos.setText("");
                }else{apellidos.setText(snombreapellaux);}

                if (stelefono.equals("- - -")){
                    telefono.setText("");
                }else{telefono.setText(stelefono);}
                if (sedad.equals("- - -")){
                    edad.setText("");
                }else{edad.setText(sedad);}
                if (sinteres.equals("- - -")){
                    interes.setText("");
                }else{interes.setText(sinteres);}
                if (sdireccion.equals("- - -")){
                    direccion.setText("");
                }else{direccion.setText(sdireccion);}
                final Button acept=promptView.findViewById(R.id.btn_accountyes);
                acept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View focusView = null;
                        if (!Boolean.valueOf(new InternetConnection(getActivity().getApplicationContext()).isConnectingInternet()).booleanValue())
                        {
                            Toast.makeText(getContext(), "Sin Conexión a internet" , Toast.LENGTH_LONG).show();
                        }
                        else{
                            if (TextUtils.isEmpty(dniuser.getText().toString())
                                    ||TextUtils.isEmpty(nombre.getText().toString()))
                            {
                                dniuser.setError("Dato necesario");
                                nombre.setError("Dato necesario");
                                focusView=dniuser;
                                focusView.requestFocus();
                                Toast.makeText(getContext(), "Verifique que los datos esten completos" , Toast.LENGTH_LONG).show();
                            }
                            else{
                                if (dniuser.getText().toString().length()<8||dniuser.getText().toString().length()>8)
                                {
                                    dniuser.setError("El DNI dene tener 8 digitos");
                                    focusView=dniuser;
                                    focusView.requestFocus();
                                }
                                else {

                                    if (nombre.getText().toString().length()<3)
                                    {
                                        nombre.setError("Su nombre debe tener al menos 3 digitos");
                                        focusView=nombre;
                                        focusView.requestFocus();
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(telefono.getText().toString())&&telefono.getText().toString().length()<6)
                                    {
                                        telefono.setError("El Telefono debe tener al menos 6 digitos");
                                        focusView=telefono;
                                        focusView.requestFocus();
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(edad.getText().toString())&&Integer.parseInt(edad.getText().toString())<11)
                                    {
                                        edad.setError("Su edad debe ser al menos de 11 años");
                                        focusView=edad;
                                        focusView.requestFocus();
                                        return;
                                    }
                                    String pivot;
                                    if(genero.getSelectedItemPosition()==0)
                                    {
                                        pivot="M";
                                    }else{pivot="F";}
                                    uploadDataUser(
                                            nombre.getText().toString(),
                                            apellidos.getText().toString(),
                                            dniuser.getText().toString(),
                                            telefono.getText().toString(),
                                            edad.getText().toString(),
                                            interes.getText().toString(),
                                            direccion.getText().toString(),
                                            pivot);

                                }

                            }
                        }
                    }
                });
                final Button cancel=promptView.findViewById(R.id.btn_accountdel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Builderupdate.dismiss();
                    }
                });
                Builderupdate.setView(promptView);
                Builderupdate.show();
            }
        });

        imageupdate=v.findViewById(R.id.img_propietario_photo);
        Glide.with(this).load(user.get(SharedPrefUsuarios.KEY_IMAGEN).toString()).into(imageupdate);

        imageupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requeststorage), Toast.LENGTH_LONG).show();
                    permisosREADSD();
                    return;
                }
                if (ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requeststorage), Toast.LENGTH_LONG).show();
                    permisosWRITESD();
                    return;
                }
                View promptView = getLayoutInflater().inflate(R.layout.dialog_user_updateimage, null);
                Builder = new android.app.AlertDialog.Builder(getContext()).create();
                final RadioButton camara=promptView.findViewById(R.id.photo_radiocamara);
                camara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                });
                final RadioButton memoria=promptView.findViewById(R.id.photo_radiomemoria);
                memoria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFileChooser();
                    }
                });
                final RadioButton cancel=promptView.findViewById(R.id.photo_radiocancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Builder.dismiss();
                    }
                });
                Builder.setView(promptView);
                Builder.show();
            }
        });

        sdniuser=user.get(SharedPrefUsuarios.KEY_DNI).toString();
        if (sdniuser.equals("null")||sdniuser.equals("")||sdniuser.equals("0"))
        {   sdniuser="- - -";
        }
        edniuser.setText(sdniuser);
        sedad=user.get(SharedPrefUsuarios.KEY_EDAD).toString();
        if (sedad.equals("null")||sedad.equals("")||sedad.equals("0"))
        {   sedad="- - -";
        }
        eedad.setText(sedad);
        if (user.get(SharedPrefUsuarios.KEY_APELLIDO).toString().equals("null"))
        {   snombreapellaux="";
        }else {snombreapellaux=user.get(SharedPrefUsuarios.KEY_APELLIDO).toString();}
        snombreapell=user.get(SharedPrefUsuarios.KEY_NOM).toString()+" "+snombreapellaux;
        scorreo = user.get(SharedPrefUsuarios.KEY_EMAIL).toString();

        sestado = user.get(SharedPrefUsuarios.KEY_ESTADO).toString();
        switch (sestado){
            case "1":
                eestado.setText("Activo");
                eestado.setTextColor(Color.GREEN);
                break;
            case "0":
                eestado.setText("Suspendido");
                eestado.setTextColor(Color.MAGENTA);
                break;
            case "-1":
                eestado.setText("Eliminado");
                eestado.setTextColor(Color.RED);
                break;
        }
        stelefono = user.get(SharedPrefUsuarios.KEY_TEL).toString();
        if (stelefono.equals("null")||stelefono.equals("")||stelefono.equals("0")){
            stelefono="- - -";
        }
        etelefono.setText(stelefono);
        sgenero = user.get(SharedPrefUsuarios.KEY_GEN).toString();
        switch (sgenero){
            case "M":
                egenero.setText("Masculino");
                break;
            case "F":
                egenero.setText("Femenino");
                break;
            default:
                egenero.setText("- - -");
                break;
        }
        sinteres = user.get(SharedPrefUsuarios.KEY_INTERES).toString();
        if (sinteres.equals("null")||sinteres.equals(""))
        {   sinteres="- - -";
        }
        einteres.setText(sinteres);
        sdireccion=user.get(SharedPrefUsuarios.KEY_DIRECCION).toString();
        if (sdireccion.equals("null")||sdireccion.equals(""))
        {   sdireccion="- - -";
        }
        edireccion.setText(sdireccion);
        enombreapell.setText(snombreapell);
        ecorreo.setText(scorreo);
        //Metodo para verificar status
        StatusRequest();
        return v;
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    //Clase para garantizar permisos
    private void permisosCAMERA()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this.getActivity(),  android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this.getActivity(),
                            new String[]{android.Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_READ_CAMERA);
                }
            }
        }
    }
    private void permisosWRITESD()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this.getActivity(),  android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this.getActivity(),
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
            }
        }
    }
    private void permisosREADSD()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this.getActivity(),  android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this.getActivity(),
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_STORAGE);
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getContext(), "1", Toast.LENGTH_LONG).show();
                    //Aquí lo que se hace si aceptan el permiso

                } else {
                    //Toast.makeText(getContext(), "0", Toast.LENGTH_LONG).show();
                    //Aquí lo que se hace si no lo aceptan
                }
                return;
            }
        }
    }

    //Clase para actualizar datos de un usuario
    public void uploadDataUser(
            final String nombre,
            final String apellidos,
            final String dniuser,
            final String telefono,
            final String edad,
            final String interes,
            final String direccion,
            final String genero
    )
    {
        dialogsubida = new Dialog(getContext());
        dialogsubida.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogsubida.setContentView(R.layout.dialog_cargando_linear);
        dialogsubida.show();
        Map<String,String> params = new Hashtable<String, String>();
        params.put("idUsuario",user.get(SharedPrefUsuarios.KEY_ID).toString());
        params.put("dniUsuario",dniuser);
        params.put("tipoUsuario",user.get(SharedPrefUsuarios.KEY_TIPO).toString());
        params.put("nombres",nombre);
        params.put("apellidos",apellidos);
        params.put("estadoUsuario",user.get(SharedPrefUsuarios.KEY_ESTADO).toString());
        params.put("correo",user.get(SharedPrefUsuarios.KEY_EMAIL).toString());
        params.put("direccion",direccion);
        params.put("contrasena",user.get(SharedPrefUsuarios.KEY_PASS).toString());
        params.put("edad",edad);
        params.put("telefono",telefono);
        params.put("genero",genero);
        params.put("intereses",interes);
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(params);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.POST,Constantes.updatetUsuario,jobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sesionuser.shareddatauser(
                                nombre,
                                apellidos,
                                dniuser,
                                telefono,
                                edad,
                                interes,
                                direccion,
                                genero
                        );
                        dialogsubida.dismiss();
                        Builderupdate.dismiss();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FragmentMiCuenta cuenta=new FragmentMiCuenta();
                        fragmentTransaction.replace(R.id.nav_host_fragment, cuenta);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        Toast.makeText(getContext(), "Se Actualizarón los datos Correctamente", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialogsubida.dismiss();
                        Builderupdate.dismiss();
                        Toast.makeText(getContext(), "Hubo un problema", Toast.LENGTH_LONG).show();
                    }
                }
        ){

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8" + getParamsEncoding();
            }

        };
        addToQueue(obreq);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), PICK_IMAGE_REQUEST);
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
}