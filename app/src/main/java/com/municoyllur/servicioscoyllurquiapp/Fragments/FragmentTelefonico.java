package com.municoyllur.servicioscoyllurquiapp.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.municoyllur.servicioscoyllurquiapp.NavigCoyllActivity;
import com.municoyllur.servicioscoyllurquiapp.R;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyFragment;


public class FragmentTelefonico extends BaseVolleyFragment {

    //Datos para manejar permisos
    int checkcall;

    View v;

    private static final int MY_PERMISSIONS_REQUEST_READ_CALL = 0 ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_telefonico, container, false);
        NavigCoyllActivity Navigation= (NavigCoyllActivity) getActivity();
        Toolbar toolbar= Navigation.findViewById(R.id.toolbar);
        toolbar.setTitle("Directorio Telefonico");
        checkpermisosCALL();

        LinearLayout app_call1 =  v.findViewById (R.id.call_general);
        LinearLayout app_call2 =  v.findViewById (R.id.call_rpp);
        LinearLayout app_call3 =  v.findViewById (R.id.call_defensacivil);
        LinearLayout app_call4 = (LinearLayout) v.findViewById (R.id.call_ciudadana);
        LinearLayout app_call5 = (LinearLayout) v.findViewById (R.id.call_programasocial);
        LinearLayout app_call6 = (LinearLayout) v.findViewById (R.id.call_secretarigeneral);
        LinearLayout app_call7 = (LinearLayout) v.findViewById (R.id.call_supervision);
        LinearLayout app_call8 = (LinearLayout) v.findViewById (R.id.call_sgdinfra);
        LinearLayout app_call9 = (LinearLayout) v.findViewById (R.id.call_sgeconomico);
        LinearLayout app_call10 = (LinearLayout) v.findViewById (R.id.call_sgsocial);
        LinearLayout app_call11 = (LinearLayout) v.findViewById (R.id.call_rrhh);
        LinearLayout app_call12 = (LinearLayout) v.findViewById (R.id.call_abastecimiento);
        LinearLayout app_call13 = (LinearLayout) v.findViewById (R.id.call_contabilidad);
        LinearLayout app_call14 = (LinearLayout) v.findViewById (R.id.call_patrimonio);
        LinearLayout app_call15 = (LinearLayout) v.findViewById (R.id.call_tesoreria);
        LinearLayout app_call16 = (LinearLayout) v.findViewById (R.id.call_asesorial);
        app_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"920221525"));
                    startActivity(intent);
                }
            }
        });
        app_call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"971823245"));
                    startActivity(intent);
                }
            }
        });
        app_call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"939607060"));
                    startActivity(intent);
                }
            }
        });
        app_call4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"984986246"));
                    startActivity(intent);
                }
            }
        });
        app_call5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"974707070"));
                    startActivity(intent);
                }
            }
        });
        app_call6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"961203559"));
                    startActivity(intent);
                }
            }
        });
        app_call7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"984225578"));
                    startActivity(intent);
                }
            }
        });
        app_call8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"943519459"));
                    startActivity(intent);
                }
            }
        });
        app_call9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"991773245"));
                    startActivity(intent);
                }
            }
        });
        app_call10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"986823373"));
                    startActivity(intent);
                }
            }
        });
        app_call11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"980240789"));
                    startActivity(intent);
                }
            }
        });
        app_call12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"943517326"));
                    startActivity(intent);
                }
            }
        });
        app_call13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"980449479"));
                    startActivity(intent);
                }
            }
        });
        app_call14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"997886879"));
                    startActivity(intent);
                }
            }
        });
        app_call15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"961786205"));
                    startActivity(intent);
                }
            }
        });
        app_call16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), getResources().getString(R.string.txt_requestcall), Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+"984506659"));
                    startActivity(intent);
                }
            }
        });
        return  v;
    }

    //Clase de permisos

    //Clase que revisa los permisos otorgados
    private void checkpermisosCALL(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.getActivity(),  android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_READ_CALL);
            }else{
                checkcall=1;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkcall=1;
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
}