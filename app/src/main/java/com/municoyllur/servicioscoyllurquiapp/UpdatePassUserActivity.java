package com.municoyllur.servicioscoyllurquiapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.municoyllur.servicioscoyllurquiapp.Utilidades.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyActivity;

public class UpdatePassUserActivity extends BaseVolleyActivity {

    //asignaciones de edittext
    private EditText etx_correo;
    //Firebase
    private FirebaseAuth mAuth;
    //String para manipular datos de los EditText
    String s_correo;
    //Cuadro de Dialogo de Carga
    Dialog dialog;
    Dialog dialoghappy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_user);

        //casteo de vistas
        etx_correo=findViewById(R.id.regc_correo);
        //Variables de firebase
        mAuth = FirebaseAuth.getInstance();
        //Casteo de button
        Button bur=findViewById(R.id.regc_button);
        bur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(UpdatePassUserActivity.this);
                dialog.setContentView(R.layout.dialog_cargando_circular);
                dialog.show();
                s_correo=etx_correo.getText().toString();
                etx_correo.setError(null);

                View focusView = null;

                if (!Boolean.valueOf(new InternetConnection(UpdatePassUserActivity.this).isConnectingInternet()).booleanValue())
                {
                    Snackbar.make(v, "Sin Conexion a Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();;
                    dialog.dismiss();
                }
                else{
                    if (TextUtils.isEmpty(s_correo))
                    {
                        etx_correo.setError("Dato necesario");
                        focusView=etx_correo;
                        focusView.requestFocus();
                        dialog.dismiss();
                    }
                    else {
                        mAuth.sendPasswordResetEmail(s_correo)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Enviamos un email para resetear su contraseña", Toast.LENGTH_LONG).show();
                                            Intent mainIntent = new Intent(UpdatePassUserActivity.this, LoginActivity.class);
                                            startActivity(mainIntent);
                                            UpdatePassUserActivity.this.finish();
                                            dialog.dismiss();
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Hubo un error, verifique su correo", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
}