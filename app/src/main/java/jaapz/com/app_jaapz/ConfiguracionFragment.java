package jaapz.com.app_jaapz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfiguracionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfiguracionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfiguracionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracionFragment newInstance(String param1, String param2) {
        ConfiguracionFragment fragment = new ConfiguracionFragment();
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
    EditText et_base_sqlite;
    Integer _id = 0;
    EditText et_ip_servidor;
    EditText et_puerto_bd;
    EditText et_postgres_nombre;
    EditText et_usuario_bd;
    EditText et_clave_bd;
    Button btn_grabar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_configuracion, container, false);
        if (v != null){//se instancian los objetos
            et_base_sqlite = (EditText)v.findViewById(R.id.et_base_sqlite);
            et_ip_servidor = (EditText)v.findViewById(R.id.et_ip_servidor);
            et_puerto_bd = (EditText)v.findViewById(R.id.et_puerto_bd);
            et_postgres_nombre = (EditText)v.findViewById(R.id.et_postgres_nombre);
            et_usuario_bd = (EditText)v.findViewById(R.id.et_usuario_bd);
            et_clave_bd = (EditText)v.findViewById(R.id.et_clave_bd);
            btn_grabar = (Button)v.findViewById(R.id.btn_grabar);
            btn_grabar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update();
                }
            });
            cargarDatosSQLite();
        }
        return v;
    }
    public void cargarDatosSQLite(){
        ConexionSQLite cnn = new ConexionSQLite(getActivity(), Constantes.NOMBRE_BD_SQLITE,null,1);
        SQLiteDatabase baseDatos = cnn.getReadableDatabase();
        String datos = "";
        try {
            Cursor fila = baseDatos.rawQuery("SELECT ip,puerto_pg,bd_pg,estado,id,usuario,password FROM configuracion",null);
            while(fila.moveToNext()){

                et_base_sqlite.setText(Constantes.NOMBRE_BD_SQLITE);
                et_puerto_bd.setText(fila.getString(1));
                et_postgres_nombre.setText(fila.getString(2));
                et_ip_servidor.setText(fila.getString(0));
                _id = fila.getInt(4);

                et_usuario_bd.setText(fila.getString(5));
                et_clave_bd.setText(fila.getString(6));
            }
            baseDatos.close();
        }catch(Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void update(){
        try{
            ConexionSQLite cnn = new ConexionSQLite( getActivity(),Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura

            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("ip",String.valueOf(et_ip_servidor.getText()));
            registro.put("puerto_pg",String.valueOf(et_puerto_bd.getText()));
            registro.put("bd_pg",String.valueOf(et_postgres_nombre.getText()));

            registro.put("usuario",String.valueOf(et_usuario_bd.getText()));
            registro.put("password",String.valueOf(et_clave_bd.getText()));

            registro.put("estado","A");
            baseDatos.update(BaseDatos.TABLA_CONFIGURACIONES,registro," id = " + _id,null);
            Toast.makeText(getActivity(), "Registrado", Toast.LENGTH_SHORT).show();
            baseDatos.close();
        }catch(Exception ex){
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
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
}
