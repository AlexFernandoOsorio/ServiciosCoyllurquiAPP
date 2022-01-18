package com.municoyllur.servicioscoyllurquiapp;

//Importar Utilidades
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyActivity;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.Constantes;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.InternetConnection;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.SharedPrefUsuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseVolleyActivity {
    //Declaracion de variables para Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //Declaracion del Shared Preferences para usuarios
    SharedPrefUsuarios sesionuser;
    //Declaracion de vistas para usuarios
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextView textCuenta;
    private TextView textRecuperaCuenta;
    String pass;
    //Dialogs
    AlertDialog Builder;
    //Variables de data de un usuario
    String  KEY_ID,KEY_DNI,KEY_TIPO,KEY_NOM,KEY_APELLIDO,
            KEY_ESTADO,KEY_EMAIL,KEY_DIRECCION,KEY_PASS,
            KEY_EDAD,KEY_TEL,KEY_GEN,KEY_INTERES,KEY_IMAGEN,KEY_TOKEN,KEY_FECHA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Casteo de Variables para Firebase Data
        mAuth = FirebaseAuth.getInstance();
        //Verificacion de SharedPreferences
        sesionuser =new SharedPrefUsuarios(getApplicationContext());
        Bundle extras=this.getIntent().getExtras();
        if (extras != null) {
            sesionuser.IntentLog(extras);
            LoginActivity.this.finish();
        }
        /*else {
            sesionuser.checkLogin();
            LoginActivity.this.finish();
        }*/
        // Set up the login form.
        mEmailView =  findViewById(R.id.txt_log_email);
        mPasswordView =findViewById(R.id.txt_log_password);
        Button mEmailSignInButton = findViewById(R.id.btn_log_login);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Boolean.valueOf(new InternetConnection(LoginActivity.this).isConnectingInternet()).booleanValue())
                {
                    Snackbar.make(view, "Sin Conexion a Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();;
                }else {
                    LoginRequest();
                }
            }
        });
        //ingreso a los fragmentos CRUD de usuario
        textCuenta=findViewById(R.id.tbutt_creacuenta);
        textRecuperaCuenta=findViewById(R.id.text_recupcontra);
        //Listeners
        textCuenta.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
                startActivity(intent);
            }
        });
        textRecuperaCuenta.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, UpdatePassUserActivity.class);
                startActivity(intent);
            }
        });
    }
    //Clase Implementadas
    //Clase para Identificacion mediante Web Services
    private void LoginRequest(){
        final String emailpri = mEmailView.getText().toString();
        pass = mPasswordView.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_autenticar);
        dialog.show();
        //Iniciamos sesion en firebase
        mAuth.signInWithEmailAndPassword(emailpri, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user.isEmailVerified()) {
                        final String JsonURL = Constantes.GetUsuarioID+"?correoUsuario="+emailpri;
                        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,JsonURL,null,
                                new Response.Listener<JSONObject>() {
                                    // Takes the response from the JSON request
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONObject jsonuser = response.getJSONObject("usuarios");
                                            for (int i = 0; i < jsonuser.length(); i++) {
                                                KEY_ID = jsonuser.getString("us_idUsuario");
                                                KEY_DNI = jsonuser.getString("us_dniUsuario");
                                                KEY_TIPO = jsonuser.getString("us_tipoUsuario");
                                                KEY_NOM = jsonuser.getString("us_nombres");
                                                KEY_APELLIDO = jsonuser.getString("us_apellidos");
                                                KEY_ESTADO = jsonuser.getString("us_estadoUsuario");
                                                KEY_EMAIL = jsonuser.getString("us_correo");
                                                KEY_DIRECCION = jsonuser.getString("us_direccion");
                                                KEY_PASS = jsonuser.getString("us_contraseña");
                                                KEY_EDAD = jsonuser.getString("us_edad");
                                                KEY_TEL = jsonuser.getString("us_telefonico");
                                                KEY_GEN = jsonuser.getString("us_genero");
                                                KEY_INTERES = jsonuser.getString("us_intereses");
                                                KEY_FECHA = jsonuser.getString("us_fechaUsuario");
                                                KEY_IMAGEN = jsonuser.getString("us_imagenUsuario");
                                                KEY_TOKEN = jsonuser.getString("us_tokenUsuario");
                                            }
                                            if(KEY_ESTADO.equals("-1")){
                                                dialog.dismiss();
                                                Toast.makeText(getApplicationContext(),"Su cuenta ha sido eliminada", Toast.LENGTH_LONG).show();
                                            }else {
                                                sesionuser.createLoginSession(KEY_ID,
                                                        KEY_DNI,
                                                        KEY_TIPO,
                                                        KEY_NOM,
                                                        KEY_APELLIDO,
                                                        KEY_ESTADO,
                                                        KEY_EMAIL,
                                                        KEY_DIRECCION,
                                                        KEY_PASS,
                                                        KEY_EDAD,
                                                        KEY_TEL,
                                                        KEY_GEN,
                                                        KEY_INTERES,
                                                        KEY_FECHA,
                                                        KEY_IMAGEN,
                                                        KEY_TOKEN);
                                                Intent intentnavigation =new Intent(LoginActivity.this, NavigCoyllActivity.class);
                                                dialog.dismiss();
                                                startActivity(intentnavigation);
                                                finish();
                                            }
                                        }
                                        catch (JSONException e) {
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"Problema de conexión", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    // Handles errors that occur due to Volley
                                    public void onErrorResponse(VolleyError error) {
                                        System.out.println(error.getMessage());
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Problema de conexión", Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                        addToQueue(obreq);
                    } else {
                        dialog.dismiss();
                        View promptView = getLayoutInflater().inflate(R.layout.dialog_emailverify, null);
                        Builder = new AlertDialog.Builder(LoginActivity.this).create();
                        final Button now=promptView.findViewById(R.id.btn_dialogfirebaseok);
                        final Button later=promptView.findViewById(R.id.btn_dialogfirebaseno);
                        now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(LoginActivity.this, "Email de verificación enviada", Toast.LENGTH_SHORT).show();
                                        Builder.dismiss();
                                    }
                                });
                            }
                        });
                        later.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Builder.dismiss();
                            }
                        });
                        Builder.setView(promptView);
                        Builder.show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Email y/o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            //Toast.makeText(getApplicationContext(),"Sin firebase account", Toast.LENGTH_LONG).show();

        }
    }
}