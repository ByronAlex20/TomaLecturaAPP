package jaapz.com.app_jaapz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.pojos.Barrio;
import jaapz.com.app_jaapz.pojos.BarrioDAO;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarrioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarrioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarrioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BarrioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarrioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarrioFragment newInstance(String param1, String param2) {
        BarrioFragment fragment = new BarrioFragment();
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
    private ListView lvBarrios;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_barrio, container, false);
        if (v != null) {//se instancian los objetos
            BarrioDAO barrioDAO = new BarrioDAO();
            lvBarrios = (ListView)v.findViewById(R.id.lvBarrios);

            final ArrayList<Barrio> listaBarrio = barrioDAO.getAllBarriosAsignadosSQLite(getContext());
            if(listaBarrio.size() > 0){
                ArrayList<EntidadBarrio> listaEntidad = new ArrayList<EntidadBarrio>();
                for(Barrio barrio : listaBarrio){
                    EntidadBarrio obj = new EntidadBarrio(barrio.getNombre(),barrio.getIdBarrio());
                    listaEntidad.add(obj);
                }
                AdaptadorBarrio adaptador = new AdaptadorBarrio(listaEntidad,getContext());
                lvBarrios.setAdapter(adaptador);
                lvBarrios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int i, long id) {
                        Intent intent = new Intent(getContext(),ClientesBarrioActivity.class);
                        intent.putExtra("Barrio Seleccionado",listaBarrio.get(i));
                        startActivity(intent);
                    }
                });
            }else
                Toast.makeText(getContext(), "El usuario no tiene asignado barrios", Toast.LENGTH_SHORT).show();
        }
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
