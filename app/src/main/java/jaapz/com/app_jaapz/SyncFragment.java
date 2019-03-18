package jaapz.com.app_jaapz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.pojos.Planilla;
import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;
import jaapz.com.app_jaapz.util.CuadroDialogo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SyncFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SyncFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SyncFragment extends Fragment implements CuadroDialogo.OnDialogListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SyncFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SyncFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SyncFragment newInstance(String param1, String param2) {
        SyncFragment fragment = new SyncFragment();
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
    TextView et_clientes_tomar;
    TextView et_clientes_lectura;
    TextView et_clientes_faltantes;
    Button btn_sincronizar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sync, container, false);
        if (v != null) {//se instancian los objetos
            et_clientes_tomar = (TextView)v.findViewById(R.id.et_clientes_tomar);
            et_clientes_lectura = (TextView)v.findViewById(R.id.et_clientes_lectura);
            et_clientes_faltantes = (TextView)v.findViewById(R.id.et_clientes_faltantes);
            btn_sincronizar = (Button)v.findViewById(R.id.btn_sincronizar);
            btn_sincronizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        FragmentManager fragmentManager = getFragmentManager();
                        DialogFragment fragment = (DialogFragment)fragmentManager.findFragmentByTag(CuadroDialogoFragment.TAG);
                        if(fragment == null){
                            fragment = new CuadroDialogoFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(CuadroDialogoFragment.TAG_MENSAJE,"Se sinccronizarán los resultados\n¿Desea Continuar?");
                            bundle.putString(CuadroDialogoFragment.TAG_TITULO,"Sincronizar");
                            fragment.setArguments(bundle);
                        }
                        fragment.show(getFragmentManager(),CuadroDialogoFragment.TAG);
                    }catch(Exception ex){
                    }
                }
            });
            cargarDatos();
        }
        return v;
    }
    private void cargarDatos() {
            ConexionSQLite cnn = new ConexionSQLite(getActivity(), Constantes.NOMBRE_BD_SQLITE, null, Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            try {
                int cont = 0;
                //hay q preguntar por el usuario logueado
                String cadena = "select b.id_barrio,b.nombre,b.descripcion,b.estado from " +
                        BaseDatos.TABLA_BARRIO + " b, " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " r, " + BaseDatos.TABLA_APERTURA_LECTURA + " a, " +
                        BaseDatos.TABLA_CUENTA_CLIENTE + " c where r.id_apertura = a.id_apertura and r.id_barrio = b.id_barrio and " +
                        " c.id_barrio = b.id_barrio and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO
                        + "' and r.id_usuario = " + ContextGlobal.getInstance().getIdUsuarioLogeado() + " and b.estado = 'A' " +
                        "and r.estado = 'A' and a.estado = 'A' and c.estado = 'A'";
                Cursor fila = baseDatos.rawQuery(cadena, null);
                while(fila.moveToNext()){
                    cont = cont + 1;
                }
                et_clientes_tomar.setText(String.valueOf(cont));

                cont = 0;
                cadena = "select b.id_barrio,b.nombre,b.descripcion,b.estado from " +
                        BaseDatos.TABLA_BARRIO + " b, " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " r, " + BaseDatos.TABLA_APERTURA_LECTURA + " a, " +
                        BaseDatos.TABLA_CUENTA_CLIENTE + " c," + BaseDatos.TABLA_PLANILLA + " p where r.id_apertura = a.id_apertura and " +
                        "r.id_barrio = b.id_barrio and p.id_cuenta = c.id_cuenta and " +
                        " c.id_barrio = b.id_barrio and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO
                        + "' and r.id_usuario = " + ContextGlobal.getInstance().getIdUsuarioLogeado() + " and b.estado = 'A' " +
                        "and r.estado = 'A' and a.estado = 'A' and c.estado = 'A' and p.estado_toma = '" + Constantes.ESTADO_TOMA_REALIZADO + "'";

                Cursor fila2 = baseDatos.rawQuery(cadena, null);
                while(fila2.moveToNext()){
                    cont = cont + 1;
                }
                et_clientes_lectura.setText(String.valueOf(cont));
                cont = 0;
                cadena = "select b.id_barrio,b.nombre,b.descripcion,b.estado from " +
                        BaseDatos.TABLA_BARRIO + " b, " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " r, " + BaseDatos.TABLA_APERTURA_LECTURA + " a, " +
                        BaseDatos.TABLA_CUENTA_CLIENTE + " c," + BaseDatos.TABLA_PLANILLA + " p where r.id_apertura = a.id_apertura and " +
                        "r.id_barrio = b.id_barrio and p.id_cuenta = c.id_cuenta and " +
                        " c.id_barrio = b.id_barrio and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO
                        + "' and r.id_usuario = " + ContextGlobal.getInstance().getIdUsuarioLogeado() + " and b.estado = 'A' " +
                        "and r.estado = 'A' and a.estado = 'A' and c.estado = 'A' and p.estado_toma is null and p.ident_instalacion <> 'INS'";

                Cursor fila3 = baseDatos.rawQuery(cadena, null);
                while(fila3.moveToNext()){
                    cont = cont + 1;
                }
                et_clientes_faltantes.setText(String.valueOf(cont));
                baseDatos.close();
            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

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

    @Override
    public void OnPositiveButtonClicked() {
        try {
            Toast.makeText(getContext(), "hola", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){

        }
    }

    @Override
    public void OnNegativeButtonClicked() {

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

    public static class CuadroDialogoFragment extends DialogFragment {
        public static final String TAG = "CuadroDialogo";
        public static final String TAG_MENSAJE = "MensajeDialogo";
        public static final String TAG_TITULO = "TituloDialogo";


        private String mensaje;
        private String titulo;
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            mensaje = getArguments().getString(TAG_MENSAJE,"Algo ocurrio mal");
            titulo = getArguments().getString(TAG_TITULO,"Algo ocurrio mal");
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(titulo)
                    .setMessage(mensaje)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sincronizarResultados();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.setCancelable(true);
            return builder.create();
        }
        private void sincronizarResultados(){
            try{
                ConexionSQLite cnn = new ConexionSQLite(getActivity(), Constantes.NOMBRE_BD_SQLITE, null, Constantes.VERSION_BD_SQLITE);
                SQLiteDatabase baseDatos = cnn.getReadableDatabase();
                List<Planilla> listaPlanillaSync = new ArrayList<Planilla>();
                String cadena = "select p.id_planilla,p.id_cuenta,p.id_apertura,p.usuario_crea,p.fecha,p.lectura_actual,p.ident_instalacion," +
                        "p.lectura_anterior,p.consumo_minimo,p.consumo,p.total_pagar,p.total_letras," +
                        "p.convenio,p.cancelado,p.latitud,p.longitud,p.fecha_ingreso,p.estado,p.origen,p.observaciones" +
                        " from " + BaseDatos.TABLA_BARRIO + " b, " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " r, " + BaseDatos.TABLA_APERTURA_LECTURA + " a, " +
                        BaseDatos.TABLA_CUENTA_CLIENTE + " c," + BaseDatos.TABLA_PLANILLA + " p where r.id_apertura = a.id_apertura and " +
                        "r.id_barrio = b.id_barrio and p.id_cuenta = c.id_cuenta and " +
                        " c.id_barrio = b.id_barrio and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO
                        + "' and r.id_usuario = " + ContextGlobal.getInstance().getIdUsuarioLogeado() + " and b.estado = 'A' " +
                        "and r.estado = 'A' and a.estado = 'A' and c.estado = 'A' and p.consumo > 0";
                Cursor fila = baseDatos.rawQuery(cadena, null);
                while(fila.moveToNext()){
                    Planilla planilla = new Planilla();
                    planilla.setIdPlanilla(fila.getInt(0));
                    //planilla.setIdCuenta(fila.getInt(1));
                    //planilla.setIdApertura(fila.getInt(2));
                    planilla.setUsuarioCrea(fila.getInt(3));
                    //planilla.setFecha(Date.valueOf(fila.getString(4)));
                    planilla.setLecturaActual(fila.getInt(5));
                    //planilla.setIdentInstalacion(fila.getString(6));
                    planilla.setLecturaAnterior(fila.getInt(7));
                    planilla.setConsumoMinimo(fila.getInt(8));
                    planilla.setConsumo(fila.getInt(9));
                    //planilla.setTotalPagar(fila.getDouble(10));
                    //planilla.setTotalLetras(fila.getString(11));
                    //planilla.setConvenio(fila.getString(12));
                    //planilla.setCancelado(fila.getString(13));
                    planilla.setLatitud(fila.getString(14));
                    planilla.setLongitud(fila.getString(15));
                    //planilla.setFechaIngreso(Date.valueOf(fila.getString(16)));
                    //planilla.setEstado(fila.getString(17));
                    planilla.setOrigen(fila.getString(18));
                    planilla.setObservaciones(fila.getString(19));
                    listaPlanillaSync.add(planilla);
                }
                baseDatos.close();

                ConexionSQLite cn = new ConexionSQLite(getActivity(), Constantes.NOMBRE_BD_SQLITE, null, Constantes.VERSION_BD_SQLITE);
                SQLiteDatabase bDatos = cn.getReadableDatabase();

                ConexionPostgreSQL db = new ConexionPostgreSQL(getContext());
                for(Planilla planilla : listaPlanillaSync){
                    cadena = "update " + BaseDatos.TABLA_PLANILLA + " set lectura_actual = " + planilla.getLecturaActual() + ", " +
                            "consumo = " + planilla.getConsumo() + ", latitud = '" + planilla.getLatitud() + "'," +
                            "longitud = '" + planilla.getLongitud() + "', origen = '" + planilla.getOrigen() + "'," +
                            "observaciones = '" + planilla.getObservaciones() + "',usuario_crea = " + planilla.getUsuarioCrea() +
                            " where id_planilla = " + planilla.getIdPlanilla();
                    db.execute(cadena);

                    cadena = "select usuario_crea,cantidad,subtotal,id_planilla_det " +
                            " from " + BaseDatos.TABLA_PLANILLA_DETALLE + " where estado = 'A' " +
                            "and identificador_operacion = '" + Constantes.IDENT_LECTURA + "' and id_planilla = " + planilla.getIdPlanilla();

                    Cursor fila1 = bDatos.rawQuery(cadena, null);
                    while(fila1.moveToNext()){
                        cadena = "update " + BaseDatos.TABLA_PLANILLA_DETALLE + " set usuario_crea = " + fila1.getInt(0) + ", " +
                                "cantidad = " + fila1.getInt(1) + ", subtotal = " + fila1.getDouble(2) + " " +
                                "where id_planilla_det = " + fila1.getInt(3);
                        db.execute(cadena);
                    }
                }

                bDatos.close();



                Toast.makeText(getContext(), "Datos actualizados!!", Toast.LENGTH_SHORT).show();
            }catch (Exception ex){
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
