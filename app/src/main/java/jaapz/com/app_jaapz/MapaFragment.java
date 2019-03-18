package jaapz.com.app_jaapz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapaFragment newInstance(String param1, String param2) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    GoogleMap map;
    Boolean actualPosition = true;
    JSONObject jso;
    Double longitudOrigen, latitudOrigen;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            map = googleMap;
            map.setOnMarkerClickListener(this);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
                return;
            }
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if(actualPosition){
                        latitudOrigen = location.getLatitude();
                        longitudOrigen = location.getLongitude();
                        actualPosition= false;

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(latitudOrigen,longitudOrigen))      // Sets the center of the map to Mountain View
                                .zoom(15)
                                .bearing(90)// Sets the zoom
                                .build();                   // Creates a CameraPosition from the builder
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        cargarPuntos(map);
                    }
                }
            });
        }catch(Exception ex){

        }
    }
    private void cargarPuntos(GoogleMap map){
        try{
            String titulo = "",subtitulo = "";

            ConexionSQLite cnn = new ConexionSQLite(getActivity(), Constantes.NOMBRE_BD_SQLITE, null, Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select c.id_cuenta,c.id_cliente,cl.nombres,cl.apellidos,m.codigo,c.latitud,c.longitud,b.nombre,b.id_barrio from " +
                    BaseDatos.TABLA_BARRIO + " b, " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " r, " + BaseDatos.TABLA_APERTURA_LECTURA + " a, " +
                    BaseDatos.TABLA_CUENTA_CLIENTE + " c, " + BaseDatos.TABLA_CLIENTE + " cl," + BaseDatos.TABLA_MEDIDOR +
                    " m, " + BaseDatos.TABLA_PLANILLA  + " p " +
                    " where m.id_medidor = c.id_medidor and cl.id_cliente = c.id_cliente and  r.id_apertura = a.id_apertura and r.id_barrio = b.id_barrio and " +
                    " c.id_barrio = b.id_barrio and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO
                    + "' and r.id_usuario = " + ContextGlobal.getInstance().getIdUsuarioLogeado() + " and b.estado = 'A' and cl.estado = 'A' " +
                    "and r.estado = 'A' and a.estado = 'A' and p.id_cuenta = c.id_cuenta and c.estado = 'A' and m.estado = 'A' " +
                    "and p.estado_toma is null";
            Cursor fila = baseDatos.rawQuery(cadena, null);

            while(fila.moveToNext()){
                if(fila.getString(5)!= null){
                    titulo = fila.getString(2);
                    subtitulo = "Medidor: " + fila.getString(4);;
                    LatLng punto = new LatLng(Double.valueOf(fila.getString(5)),Double.valueOf(fila.getString(6)));
                    Marker dir  = map.addMarker(new MarkerOptions()
                            .position(punto)
                            .title(titulo)
                            .snippet(subtitulo)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    listaPuntos.add(dir);

                    //aqui se llena la entidad del cliente

                    int lecturaActual = getLecturaActual(fila.getInt(0));
                    int lecturaAnterior = getLecturaAnterior(fila.getInt(0));
                    int idPlanilla = getIdPlanilla(fila.getInt(0));
                    int consumo = lecturaActual - lecturaAnterior;
                    EntidadCliente obj = new EntidadCliente("Cliente: " + getNombreCliente(fila.getInt(1)) + " Medidor:" + getNumeroMedidor(fila.getInt(0)),
                            lecturaActual,lecturaAnterior,consumo,"BARRIO: " + fila.getString(7) , fila.getInt(1),fila.getInt(0),idPlanilla);
                    listaEntidad.add(obj);
                    listaIdBarrio.add(fila.getInt(8));
                    listaNombreBarrio.add(fila.getString(7));
                }
            }
            baseDatos.close();
        }catch(Exception ex){
            Toast.makeText(getContext(), String.valueOf(ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    List<Marker> listaPuntos = new ArrayList<Marker>();
    ArrayList<EntidadCliente> listaEntidad = new ArrayList<EntidadCliente>();
    List<Integer> listaIdBarrio = new ArrayList<Integer>();
    List<String> listaNombreBarrio = new ArrayList<String>();
    static final int PICK_CONTACT_REQUEST = 1;
    @Override
    public boolean onMarkerClick(Marker marker) {
        try{
            for(int i = 0 ; i < listaPuntos.size() ; i ++){
                if(listaPuntos.get(i).equals(marker)){
                    Intent inten = new Intent(getContext(),TomaLecutasActivity.class);
                    inten.putExtra("ClienteSeleccionado", listaEntidad.get(i));
                    inten.putExtra("idBarrio", listaIdBarrio.get(i));
                    inten.putExtra("nombreBarrio", listaNombreBarrio.get(i));
                    startActivityForResult(inten, PICK_CONTACT_REQUEST);
                }
            }
            /*
            double latDestino = marker.getPosition().latitude;
            double lonDestino = marker.getPosition().longitude;

            Toast.makeText(getContext(), "entra al metodo: " + latDestino + "," + lonDestino , Toast.LENGTH_SHORT).show();

            boolean bandera = false;
            String url ="https://maps.googleapis.com/maps/api/directions/json?origin="+latitudOrigen+","+longitudOrigen+"&destination=" + latDestino + "," + lonDestino + "";

            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        jso = new JSONObject(response);
                        trazarRuta(jso);
                        Log.i("jsonRuta: ",""+response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            queue.add(stringRequest);
            */
            return true;
        }catch(Exception ex){
            Toast.makeText(getContext(), String.valueOf(ex.getMessage()), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void trazarRuta(JSONObject jso) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){
                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");
                for (int j=0; j<jLegs.length();j++){
                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");
                    for (int k = 0; k<jSteps.length();k++){
                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        map.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(5));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private String getNumeroMedidor(Integer idCuenta){
        try{
            String numeroMedidor = "No. Medidor: No Asignado";
            ConexionSQLite cnn = new ConexionSQLite(getContext(),Constantes.NOMBRE_BD_SQLITE,null,1);
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
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "No. Medidor: No Asignado";
        }
    }
    private int getIdPlanilla(Integer idCuenta){
        try{
            int idPlanilla = 0;
            ConexionSQLite cnn = new ConexionSQLite(getContext(),Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select p.id_planilla from " + BaseDatos.TABLA_PLANILLA + " p, " + BaseDatos.TABLA_CUENTA_CLIENTE + " c " +
                    "where p.id_cuenta = c.id_cuenta and p.estado = 'A' and c.estado = 'A' " +
                    "and c.id_cuenta = " + idCuenta;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                idPlanilla = fila.getInt(0);
            }
            baseDatos.close();
            return idPlanilla;
        }catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    private int getLecturaActual(Integer idCuenta){
        try{
            int lecturaActual = 0;
            ConexionSQLite cnn = new ConexionSQLite(getContext(),Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select p.lectura_actual from " + BaseDatos.TABLA_PLANILLA + " p, " + BaseDatos.TABLA_CUENTA_CLIENTE + " c " +
                    "where p.id_cuenta = c.id_cuenta and p.estado = 'A' and c.estado = 'A' " +
                    "and c.id_cuenta = " + idCuenta;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                lecturaActual = fila.getInt(0);
            }
            baseDatos.close();
            return lecturaActual;
        }catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    private int getLecturaAnterior(Integer idCuenta){
        try{
            int lecturaActual = 0;
            ConexionSQLite cnn = new ConexionSQLite(getContext(),Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select p.lectura_anterior from " + BaseDatos.TABLA_PLANILLA + " p, " + BaseDatos.TABLA_CUENTA_CLIENTE + " c " +
                    "where p.id_cuenta = c.id_cuenta and p.estado = 'A' and c.estado = 'A' " +
                    "and c.id_cuenta = " + idCuenta;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                lecturaActual = fila.getInt(0);
            }
            baseDatos.close();
            return lecturaActual;
        }catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    private String getNombreCliente(Integer idCliente){
        try{
            String nombreCliente = "";
            ConexionSQLite cnn = new ConexionSQLite(getContext(),Constantes.NOMBRE_BD_SQLITE,null,1);
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
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "";
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            map.clear();
            cargarPuntos(map);
        }catch(Exception ex){
            Toast.makeText(getContext(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
