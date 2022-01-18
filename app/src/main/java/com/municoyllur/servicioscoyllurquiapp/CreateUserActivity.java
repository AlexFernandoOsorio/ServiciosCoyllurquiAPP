package com.municoyllur.servicioscoyllurquiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyActivity;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.Constantes;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateUserActivity extends BaseVolleyActivity {

    //Declaracion de variables para Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String refreshedToken;
    //asignaciones de edittext
    private EditText etx_apellidos;
    private EditText etx_nombres;
    private EditText etx_correo;
    private EditText etx_tipousuario;
    private EditText etx_contraseña;
    private Spinner spinnerestado;
    //String para manipular datos de los EditText

    String s_apellidos;
    String s_nombres;
    String s_correo;
    String posicionspinner;
    String s_contraseña;

    //Cuadro de Dialogo de Carga
    Dialog dialog;
    AlertDialog Builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //Casteo de Variables para Firebase Data
        mAuth = FirebaseAuth.getInstance();

        //casteo de vistas
        etx_apellidos=findViewById(R.id.reg_apellidos);
        etx_nombres=findViewById(R.id.reg_nombres);
        etx_correo=findViewById(R.id.reg_correo);
        etx_contraseña=findViewById(R.id.reg_contraseña1);


        spinnerestado = findViewById(R.id.reg_spinnertipo);
        String[] arraytipo = {"Usuario Normal","Estudiante","Servidor Publico"};
        ArrayAdapter<String> adaptadores = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraytipo);
        adaptadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerestado.setAdapter(adaptadores);

        Button bur=findViewById(R.id.reg_button);
        bur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {

                                    Toast.makeText(CreateUserActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                // Get new FCM registration token
                                refreshedToken = task.getResult();

                            }
                        });

                s_apellidos=etx_apellidos.getText().toString();
                s_nombres=etx_nombres.getText().toString();
                s_correo=etx_correo.getText().toString();
                s_contraseña=etx_contraseña.getText().toString();

                etx_apellidos.setError(null);
                etx_nombres.setError(null);
                etx_correo.setError(null);
                etx_contraseña.setError(null);

                View focusView = null;
                if (!Boolean.valueOf(new InternetConnection(CreateUserActivity.this).isConnectingInternet()).booleanValue())
                {
                    Snackbar.make(v, "Sin Conexion a Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    if (TextUtils.isEmpty(s_nombres)
                            &&TextUtils.isEmpty(s_apellidos)
                            &&TextUtils.isEmpty(s_correo)
                            &&TextUtils.isEmpty(s_contraseña))
                    {
                        etx_nombres.setError("Dato Necesario");
                        etx_apellidos.setError("Dato Necesario");
                        etx_correo.setError("Dato Necesario");
                        etx_contraseña.setError("Dato Necesario");
                        Snackbar.make(v, "Necesita llenar los campos", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        if(TextUtils.isEmpty(s_nombres)){
                            etx_nombres.setError("Dato Necesario");
                            focusView=etx_nombres;
                            focusView.requestFocus();
                            return;
                        }
                        if(TextUtils.isEmpty(s_apellidos)){
                            etx_apellidos.setError("Dato Necesario");
                            focusView=etx_apellidos;
                            focusView.requestFocus();
                            return;
                        }
                        if(TextUtils.isEmpty(s_correo)){
                            etx_correo.setError("Dato Necesario");
                            focusView=etx_correo;
                            focusView.requestFocus();
                            return;
                        }
                        if(TextUtils.isEmpty(s_contraseña)){
                            etx_contraseña.setError("Dato Necesario");
                            focusView=etx_contraseña;
                            focusView.requestFocus();
                            return;
                        }
                        if (s_correo.contains("@"))
                        {
                            if (s_contraseña.length()<6)
                            {
                                etx_contraseña.setError("La contraseña necesita 6 o mas digitos");
                                focusView=etx_contraseña;
                                focusView.requestFocus();
                            }
                            else {
                                //Toast.makeText(CreateUserActivity.this, Integer.toString(spinnerestado.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();
                                posicionspinner=Integer.toString(spinnerestado.getSelectedItemPosition());
                                ReceiverWSEmail(s_correo);
                            }
                        }
                        else {
                            etx_correo.setError("Escriba un email valido");
                            focusView=etx_correo;
                            focusView.requestFocus();
                        }

                    }
                }

            }
        });
    }

    public void ReceiverWSEmail(String pas_email)
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cargando_linear);
        dialog.show();
        final String ws_correo = pas_email;
        final String JsonURL = Constantes.GetUsuarioID+"?correoUsuario="+ws_correo;
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,JsonURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            View focusView = null;
                            String jsonobjetestado = response.getString("estado");
                            if (jsonobjetestado.equals("1")){
                                dialog.dismiss();
                                etx_correo.setError("Email Invalido");
                                focusView=etx_correo;
                                focusView.requestFocus();
                                Toast.makeText(CreateUserActivity.this, "Este email ya existe,Intente con otro", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String[] datosinsert=new String[]{
                                        "1",//Posicion 1 activo
                                        s_nombres,
                                        s_apellidos,
                                        posicionspinner+1,
                                        s_correo,
                                        s_contraseña
                                };
                                InsertRegister(datosinsert);
                            }
                        }
                        catch (JSONException e) {
                            dialog.dismiss();
                            //onConnectionFailed("Email Invalido");
                            Toast.makeText(CreateUserActivity.this, e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        onConnectionFailed("Error con la conexión");
                    }
                }
        );
        addToQueue(obreq);
    }

    public void InsertRegister(String[] pas_array)
    {
        if (refreshedToken == null) {
            dialog.dismiss();
            Toast.makeText(CreateUserActivity.this, "Intentelo Nuevamente", Toast.LENGTH_LONG).show();
            return;
        }
        final String[] array_datos = pas_array;
        final String JsonURL2 = Constantes.insertUsuario;
        String ImagePath="https://servicios.municoyllurqui.gob.pe/AndroidImages/UploadUserImages/user_unknow.jpg";
        HashMap<String, String> map = new HashMap<>();// Mapeo previo
        map.put("tipoUsuario",array_datos[3]);
        map.put("nombres",array_datos[1]);
        map.put("apellidos",array_datos[2]);
        map.put("estadoUsuario",array_datos[0]);
        map.put("correo",array_datos[4]);
        map.put("contrasena",array_datos[5]);
        map.put("tokenUsuario",refreshedToken);
        map.put("imagenUsuario",ImagePath);
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.POST,JsonURL2,jobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        mAuth.createUserWithEmailAndPassword(array_datos[4], array_datos[5]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(getActivity().getApplicationContext(), "well", Toast.LENGTH_SHORT).show();
                                    // Start the next activity
                                    View promptView = LayoutInflater.from(CreateUserActivity.this).inflate(R.layout.dialog_user_create_firebase, null);
                                    Builder = new AlertDialog.Builder(CreateUserActivity.this).create();
                                    final Button now=promptView.findViewById(R.id.btn_dialogfirebaseok);
                                    final Button later=promptView.findViewById(R.id.btn_dialogfirebaseno);
                                    now.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getApplicationContext(), "Dentro de uos momentos le enviaremos un correo de Confirmacion", Toast.LENGTH_SHORT).show();
                                                    Builder.dismiss();
                                                    Intent mainIntent = new Intent(CreateUserActivity.this, LoginActivity.class);
                                                    startActivity(mainIntent);
                                                    CreateUserActivity.this.finish();
                                                }
                                            });
                                        }
                                    });
                                    later.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Builder.dismiss();
                                            Intent mainIntent = new Intent().setClass(
                                                    CreateUserActivity.this, LoginActivity.class);
                                            startActivity(mainIntent);
                                            CreateUserActivity.this.finish();

                                        }
                                    });
                                    Builder.setView(promptView);
                                    Builder.show();
                                } else {

                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                                    } else {
                                        //Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        });

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        //onConnectionFailed("Intentelo nuevamente");
                        //Toast.makeText(getApplicationContext(), "Verifique sus datos",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();
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
}