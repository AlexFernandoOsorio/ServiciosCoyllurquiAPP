package com.municoyllur.servicioscoyllurquiapp.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
        
import java.io.ByteArrayOutputStream;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.municoyllur.servicioscoyllurquiapp.NavigCoyllActivity;
import com.municoyllur.servicioscoyllurquiapp.R;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.BaseVolleyFragment;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.Constantes;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.InternetConnection;
import com.municoyllur.servicioscoyllurquiapp.Utilidades.SharedPrefUsuarios;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class FragmentReportes extends BaseVolleyFragment {

    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 0 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 0 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL = 0 ;
    //Variables para uso de camara
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 2 ;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1 ;

    private EditText descrip;
    private Button buttonChooseCamera;
    private Button buttonChooseSD;
    private Button buttonUpload;
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private ImageView imageView;
    EditText Lat;
    Double Latitud;
    Double Longitud;

    Dialog dialog;
    Dialog dialogsubida;
    //----------------------------------------------//
    public LocationManager locManager;
    private LocationManager locationManager;
    public LocationListener locListener;
    private Spinner cmbOpciones;

    SharedPrefUsuarios sesionuser;
    HashMap<String, String> user;

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_reportes, container, false);
        NavigCoyllActivity Navigation= (NavigCoyllActivity) getActivity();
        Toolbar toolbar= Navigation.findViewById(R.id.toolbar);
        toolbar.setTitle("Reportes y Denuncias");
        checkpermisosCALL();
        permisosCAMERA();
        permisosREADSD();
        permisosWRITESD();

        sesionuser =new SharedPrefUsuarios(getContext());
        user = sesionuser.getUserDetails();

        imageView  = (ImageView) v.findViewById(R.id.imageView);
        //Codigo para spinner
        cmbOpciones = v.findViewById(R.id.CmbOpciones);
        String[] arraytipo = {"Mal uso y/o fuga de Agua Potable","Robo y/o Hurto en Proceso","Maltrato al niño y/o adolescente","Peleas, pleitos entre otros similares","Otras Actividades no permitidas"};
        ArrayAdapter<String> adaptadores = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, arraytipo);
        adaptadores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbOpciones.setAdapter(adaptadores);
        //Codigo para google maps
        //Initialize map fragment
        SupportMapFragment supportMapFragment =(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map_reporte);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MarkerOptions markerOptions =new MarkerOptions();
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-13.837046374061249,-72.43208443935929),new Float(17)));

                //Eventos al tocar un punto
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Lat.setText(null);
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(latLng));
                        Latitud = latLng.latitude;
                        Longitud = latLng.longitude;
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(Latitud, Longitud, 1);
                            String address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            //String knownName = addresses.get(0).getFeatureName();
                            Lat.setText(address+" "+city+" "+state+" "+country);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        //Codigo para botones
        buttonChooseCamera = v.findViewById(R.id.buttonCamera);
        buttonChooseSD =  v.findViewById(R.id.buttonSDCard);
        buttonUpload =  v.findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion=descrip.getText().toString();
                String ubicacion=Lat.getText().toString();
                String Latitudup=Latitud.toString();
                String Longitudup=Longitud.toString();

                int i= cmbOpciones.getSelectedItemPosition();
                if (!Boolean.valueOf(new InternetConnection(getActivity().getApplicationContext()).isConnectingInternet()).booleanValue())
                {
                    Snackbar.make(v, "Sin Conexion a Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();;
                }
                else{
                    if (TextUtils.isEmpty(descripcion)||TextUtils.isEmpty(ubicacion))
                    {
                        Snackbar.make(v, "Necesita completar los campos", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();;
                    }
                    else
                    {
                        uploadReport(descripcion,ubicacion,Latitudup,Longitudup,i);
                    }
                }
            }
        });
        //------------Image--------------------------//
        buttonChooseCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        buttonChooseSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        descrip=(EditText)v.findViewById(R.id.rep_textdescrip);
        Lat=(EditText)v.findViewById(R.id.rep_textLat);

        return v;
    }

    //Clase que revisa los permisos otorgados
    private void checkpermisosCALL(){
        if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    private void uploadReport(final String descripcion, String ubicacion,String Latitud, String Longitud,int categoria){
        dialogsubida = new Dialog(getContext());
        dialogsubida.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogsubida.setContentView(R.layout.dialog_cargando_linear);
        dialogsubida.show();

        final String JsonURL2 = Constantes.insertReporte;
        HashMap<String, String> map = new HashMap<>();// Mapeo previo
        map.put("idCategoria",Integer.toString(categoria+1));
        map.put("Descripcion",descripcion);
        map.put("idUsuario",user.get(SharedPrefUsuarios.KEY_ID));
        map.put("lugarLatitud",Latitud);
        map.put("lugarLongitud",Longitud);
        if(bitmap != null){
            String image = getStringImage(bitmap);
            map.put("imagenReporte", image);
        }
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.POST,JsonURL2,jobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialogsubida.dismiss();
                        Toast.makeText(getContext(), "Reporte Enviado Correctamente" , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialogsubida.dismiss();
                        Toast.makeText(getContext(), "Reporte no se pudo Enviar" , Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), error.toString() , Toast.LENGTH_LONG).show();
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            String mimeType = getActivity().getContentResolver().getType(filePath);
            if (mimeType.equals("image/jpeg")||mimeType.equals("image/webp")||mimeType.equals("image/jpg"))
            {
                try {
                    String extension=filePath.toString();
                    String extension2 = extension.substring(extension.lastIndexOf(".") + 1);
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }}
            else {
                Toast.makeText(getContext(),"Por favor elija una imagen jpg/jpeg", Toast.LENGTH_LONG).show();
            }

        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

        }
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
}