package jaapz.com.app_jaapz;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;
import jaapz.com.app_jaapz.util.MetodosGlobales;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientesFragment newInstance(String param1, String param2) {
        ClientesFragment fragment = new ClientesFragment();
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

    private ListView lvClientes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_clientes, container, false);
        if (v != null) {//se instancian los objetos
            lvClientes = (ListView)v.findViewById(R.id.lvClientes);
            if(cargarDatosLista() == false){
                Toast.makeText(getContext(), "Lista vacia", Toast.LENGTH_SHORT).show();
            }
            lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent inten = new Intent(getContext(),TomaLecutasActivity.class);


                    AdaptadorCliente adapter = (AdaptadorCliente) lvClientes.getAdapter();

                    inten.putExtra("ClienteSeleccionado", adapter.getListaCliente().get(position));
                    inten.putExtra("idBarrio", getIdBarrio(adapter.getListaCliente().get(position).getIdCuenta()));
                    inten.putExtra("nombreBarrio", adapter.getListaCliente().get(position).getBarrio());
                    startActivity(inten);
                }
            });
        }
        return v;
    }
    private Integer getIdBarrio(Integer idCuenta){
        try{
            Integer idBarrio = 0;
            ConexionSQLite cnn = new ConexionSQLite(getContext(), Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select m.id_barrio from " + BaseDatos.TABLA_BARRIO + " m, " + BaseDatos.TABLA_CUENTA_CLIENTE + " c " +
                    "where m.id_barrio = c.id_barrio and c.estado = 'A' and m.estado = 'A' " +
                    "and c.id_cuenta = " + idCuenta;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                idBarrio = fila.getInt(0);
            }
            baseDatos.close();
            return idBarrio;
        }catch (Exception ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    ArrayList<EntidadCliente> listaEntidad;
    public boolean cargarDatosLista(){
        try{
            boolean bandera = false;
            ContextGlobal.getInstance().setLvClientesBarrio(lvClientes);
            MetodosGlobales obj = new MetodosGlobales(getContext());
            listaEntidad = obj.cargarDatosListaTodos();
            if(listaEntidad.size() == 0)
                bandera = false;
            else
                bandera = true;

            return bandera;
        }catch (Exception ex){
            Toast.makeText(getContext(), String.valueOf(ex.getMessage()), Toast.LENGTH_SHORT).show();
            return false;
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
