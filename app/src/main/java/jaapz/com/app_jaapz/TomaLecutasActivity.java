package jaapz.com.app_jaapz;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;
import jaapz.com.app_jaapz.util.CuadroDialogo;
import jaapz.com.app_jaapz.util.MetodosGlobales;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class TomaLecutasActivity extends AppCompatActivity implements CuadroDialogo.OnDialogListener {
    EntidadCliente entidadRecivida;
    TextView tvCliente;
    TextView tvLatitud;
    TextView tvLongitud;
    TextView tvBarrio;
    TextView tvLecturaAnterior;
    EditText etLecturaActual;
    Button btnGrabar;
    EditText etObservaciones;
    Integer idBarrio;
    String nombreBarrio;
    CheckBox chkValidar;

    String mensaje1, latitud = "", longitud = "";
    String mensaje2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toma_lecutas);
        tvCliente = (TextView)findViewById(R.id.tvCliente);
        tvBarrio = (TextView)findViewById(R.id.tvBarrio);

        tvLatitud = (TextView)findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);

        tvLecturaAnterior = (TextView)findViewById(R.id.tvLecturaAnterior);
        etLecturaActual = (EditText)findViewById(R.id.etLecturaActual);
        btnGrabar = (Button)findViewById(R.id.btnGrabar);
        etObservaciones = (EditText)findViewById(R.id.etObservaciones);
        chkValidar = (CheckBox)findViewById(R.id.chkValidar);

        String cliente;
        entidadRecivida = (EntidadCliente) getIntent().getSerializableExtra("ClienteSeleccionado");
        idBarrio = (Integer) getIntent().getSerializableExtra("idBarrio");
        nombreBarrio = (String) getIntent().getSerializableExtra("nombreBarrio");

        cliente = "CLIENTE: " + getNombreCliente(entidadRecivida.getIdCliente()) + " \nCOD. MEDIDOR:" + getNumeroMedidor(entidadRecivida.getIdCuenta());
        tvCliente.setText(cliente);
        tvBarrio.setText("BARRIO: " + entidadRecivida.getBarrio());
        tvLecturaAnterior.setText("LECTURA ANTERIOR: " + entidadRecivida.getLecturaAnterior());


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DialogFragment fragment = (DialogFragment)fragmentManager.findFragmentByTag(CuadroDialogo.TAG);
                    if(fragment == null){
                        fragment = new CuadroDialogo();
                        Bundle bundle = new Bundle();
                        bundle.putString(CuadroDialogo.TAG_MENSAJE,"Desea grabar los datos?");
                        bundle.putString(CuadroDialogo.TAG_TITULO,"Grabar datos");
                        fragment.setArguments(bundle);
                    }
                    fragment.show(getSupportFragmentManager(),CuadroDialogo.TAG);
                }catch(Exception ex){
                }
            }
        });
        etLecturaActual.setFocusable(true);
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion local = new Localizacion();
        local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) local);

        mensaje1 = "Localizacion agregada";
        mensaje2 = "";
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    mensaje2 = "Mi direccion es: \n"
                            + DirCalle.getAddressLine(0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getNombreCliente(Integer idCliente){
        try{
            String nombreCliente = "";
            ConexionSQLite cnn = new ConexionSQLite(this,Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select nombres,apellidos from " + BaseDatos.TABLA_CLIENTE + " where estado = 'A' " +
                    "and id_cliente = " + idCliente;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                nombreCliente = fila.getString(0) + " " + fila.getString(1);
            }
            baseDatos.close();
            return nombreCliente;
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "No. Medidor: No Asignado";
        }
    }
    private String getNumeroMedidor(Integer idCuenta){
        try{
            String numeroMedidor = "No. Medidor: No Asignado";
            ConexionSQLite cnn = new ConexionSQLite(this,Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select m.codigo from " + BaseDatos.TABLA_MEDIDOR + " m, " + BaseDatos.TABLA_CUENTA_CLIENTE + " c " +
                    "where m.id_medidor = c.id_medidor and c.estado = 'A' and m.estado = 'A' " +
                    "and c.id_cuenta = " + idCuenta;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                numeroMedidor = fila.getString(0);
            }
            baseDatos.close();
            return numeroMedidor;
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "No. Medidor: No Asignado";
        }
    }

    @Override
    public void OnPositiveButtonClicked() {
        try{
            if(Integer.parseInt(String.valueOf(etLecturaActual.getText())) < 0){
                Toast.makeText(this, "No se permite números negativos", Toast.LENGTH_SHORT).show();
                return;
            }
            if(chkValidar.isChecked()){
                if(Integer.parseInt(String.valueOf(etLecturaActual.getText())) < entidadRecivida.getLecturaAnterior()){
                    Toast.makeText(this, "Lectura actual debe de ser mayor a la anterior", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            ConexionSQLite cnn = new ConexionSQLite(this,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("lectura_actual",String.valueOf(etLecturaActual.getText()));
            registro.put("consumo",(Integer.parseInt(String.valueOf(etLecturaActual.getText())) - entidadRecivida.getLecturaAnterior()));
            registro.put("usuario_crea",ContextGlobal.getInstance().getIdUsuarioLogeado());
            registro.put("cancelado",Constantes.EST_FAC_PENDIENTE);
            registro.put("origen",Constantes.ORIGEN_MOVIL);
            registro.put("estado",Constantes.EST_ACTIVO);
            registro.put("observaciones",String.valueOf(etObservaciones.getText()));
            registro.put("latitud",latitud);
            registro.put("longitud",longitud);
            registro.put("estado_toma",Constantes.ESTADO_TOMA_REALIZADO);
            baseDatos.update(BaseDatos.TABLA_PLANILLA,registro," id_planilla = " + entidadRecivida.getIdPlanilla(),null);

            //graba detalle
            //primero se regupera el detalle que es.. xq puede haber varios detalles
            Integer idDetalle = 0;
            String cadena = "select id_planilla_det,identificador_operacion from " + BaseDatos.TABLA_PLANILLA_DETALLE
                    + " where id_planilla = " + entidadRecivida.getIdPlanilla();
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                if(fila.getString(1) != null)
                    if(fila.getString(1).equals(Constantes.IDENT_LECTURA))
                        idDetalle = fila.getInt(0);
            }
            //entonces se guarda el detalle
            if(idDetalle != 0){
                ContentValues registroDetalle = new ContentValues();
                registroDetalle.put("usuario_crea",ContextGlobal.getInstance().getIdUsuarioLogeado());
                registroDetalle.put("cantidad",(Integer.parseInt(String.valueOf(etLecturaActual.getText())) - entidadRecivida.getLecturaAnterior()));
                registroDetalle.put("subtotal",(Integer.parseInt(String.valueOf(etLecturaActual.getText())) - entidadRecivida.getLecturaAnterior()));
                baseDatos.update(BaseDatos.TABLA_PLANILLA_DETALLE,registroDetalle," id_planilla_det = " + idDetalle,null);
            }
            baseDatos.close();
            Toast.makeText(this, "Registrado", Toast.LENGTH_SHORT).show();
            MetodosGlobales obj = new MetodosGlobales(this);
            obj.cargarDatosLista(idBarrio,nombreBarrio);

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnNegativeButtonClicked() {

    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        TomaLecutasActivity mainActivity;

        public TomaLecutasActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(TomaLecutasActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            mensaje1 = Text;
            tvLatitud.setText("Lat: " + String.valueOf(loc.getLatitude()));
            latitud = String.valueOf(loc.getLatitude());
            longitud = String.valueOf(loc.getLongitude());
            tvLongitud.setText("Long: " + String.valueOf(loc.getLongitude()));

            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1 = "GPS Desactivado";
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1 = "GPS Activado";
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
