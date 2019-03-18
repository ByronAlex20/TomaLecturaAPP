package jaapz.com.app_jaapz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.pojos.Anio;
import jaapz.com.app_jaapz.pojos.AnioDAO;
import jaapz.com.app_jaapz.pojos.AperturaLectura;
import jaapz.com.app_jaapz.pojos.AperturaLecturaDAO;
import jaapz.com.app_jaapz.pojos.Barrio;
import jaapz.com.app_jaapz.pojos.BarrioDAO;
import jaapz.com.app_jaapz.pojos.Cliente;
import jaapz.com.app_jaapz.pojos.ClienteDAO;
import jaapz.com.app_jaapz.pojos.CuentaCliente;
import jaapz.com.app_jaapz.pojos.CuentaClienteDAO;
import jaapz.com.app_jaapz.pojos.Medidor;
import jaapz.com.app_jaapz.pojos.MedidorDAO;
import jaapz.com.app_jaapz.pojos.Mes;
import jaapz.com.app_jaapz.pojos.MesDAO;
import jaapz.com.app_jaapz.pojos.Planilla;
import jaapz.com.app_jaapz.pojos.PlanillaDAO;
import jaapz.com.app_jaapz.pojos.PlanillaDetalle;
import jaapz.com.app_jaapz.pojos.PlanillaDetalleDAO;
import jaapz.com.app_jaapz.pojos.RegistroFotos;
import jaapz.com.app_jaapz.pojos.RegistroFotosDAO;
import jaapz.com.app_jaapz.pojos.ResponsableLectura;
import jaapz.com.app_jaapz.pojos.ResponsableLecturaDAO;
import jaapz.com.app_jaapz.pojos.SegPerfil;
import jaapz.com.app_jaapz.pojos.SegPerfilDAO;
import jaapz.com.app_jaapz.pojos.SegUsuario;
import jaapz.com.app_jaapz.pojos.SegUsuarioDAO;
import jaapz.com.app_jaapz.pojos.SegUsuarioPerfil;
import jaapz.com.app_jaapz.pojos.SegUsuarioPerfilDAO;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.CuadroDialogo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CargarDatosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CargarDatosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CargarDatosFragment extends Fragment implements CuadroDialogo.OnDialogListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CargarDatosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CargarDatosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CargarDatosFragment newInstance(String param1, String param2) {
        CargarDatosFragment fragment = new CargarDatosFragment();
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
    Button btn_cargar_datos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cargar_datos, container, false);
        if (v != null) {//se instancian los objetos
            btn_cargar_datos = (Button)v.findViewById(R.id.btn_cargar_datos);
            btn_cargar_datos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cargarDatos();
                }
            });
        }
        return v;
    }

    private void cargarDatos(){
        try{

            SegPerfilDAO perfilDAO = new SegPerfilDAO();
            SegUsuarioDAO usuarioDAO = new SegUsuarioDAO();
            SegUsuarioPerfilDAO usuarioPerfilDAO = new SegUsuarioPerfilDAO();
            //recuperar los perfiles
            List<SegPerfil> perfilListSQLite = perfilDAO.getAllPerfilesSQLite(getContext());
            List<SegPerfil> perfilListPostgres = perfilDAO.getAllPerfilesPostgres(getContext());
            List<Integer> registrosAnteriores = new ArrayList<>();
            for(SegPerfil perfil : perfilListSQLite)registrosAnteriores.add(perfil.getIdPerfil());
            for(SegPerfil perfilP : perfilListPostgres){
                if(registrosAnteriores.contains(perfilP.getIdPerfil())) //se modifica
                    perfilDAO.updateRecordSQLite(perfilP,getContext());
                else//se inserta
                    perfilDAO.insertRecordSQLite(perfilP,getContext());
            }
            //recupera los usuarios
            List<SegUsuario> usuarioListSQLite = usuarioDAO.getAllUsuariosSQLite(getContext());
            List<SegUsuario> usuarioListPostgres = usuarioDAO.getAllUsuariosPostgres(getContext());
            List<Integer> registrosAnterioresUser = new ArrayList<>();
            for(SegUsuario usuario : usuarioListSQLite)registrosAnterioresUser.add(usuario.getIdUsuario());
            for(SegUsuario usuarioP : usuarioListPostgres){
                if(registrosAnterioresUser.contains(usuarioP.getIdUsuario())) //se modifica
                    usuarioDAO.updateRecordSQLite(usuarioP,getContext());
                else//se inserta
                    usuarioDAO.insertRecordSQLite(usuarioP,getContext());
            }


            //recuperar usuario perfil

            List<SegUsuarioPerfil> usuarioPerfilListSQLite = usuarioPerfilDAO.getAllUsuariosPerfilSQLite(getContext());
            List<SegUsuarioPerfil> usuarioPerfilListPostgres = usuarioPerfilDAO.getAllUsuariosPerfilPostgres(getContext());
            List<Integer> registrosAnterioresUserPer = new ArrayList<>();
            for(SegUsuarioPerfil usuario : usuarioPerfilListSQLite)registrosAnterioresUserPer.add(usuario.getIdUsuarioPerfil());
            for(SegUsuarioPerfil usuarioP : usuarioPerfilListPostgres){
                if(registrosAnterioresUser.contains(usuarioP.getIdUsuarioPerfil())) //se modifica
                    usuarioPerfilDAO.updateRecordSQLite(usuarioP,getContext());
                else//se inserta
                    usuarioPerfilDAO.insertRecordSQLite(usuarioP,getContext());
            }

            //ahi con eso tenemos la nueva tabla de seguridad

            boolean band = false;
            ClienteDAO clienteDAO = new ClienteDAO();
            //recuperar primero son los cliente antes q nada
            List<Cliente> clientesListSQLite = clienteDAO.getAllClientesSQLite(getContext());
            List<Cliente> clientesListPostgres = clienteDAO.getAllClientesPostgres(getContext());
            List<Integer> registrosAnterioresCliente = new ArrayList<>();
            for(Cliente cliente : clientesListSQLite)registrosAnterioresCliente.add(cliente.getIdCliente());
            for(Cliente clienteP : clientesListPostgres){
                if(registrosAnterioresCliente.contains(clienteP.getIdCliente())) //se modifica
                    clienteDAO.updateRecordSQLite(clienteP,getContext());
                else//se inserta
                    clienteDAO.insertRecordSQLite(clienteP,getContext());
            }

            AnioDAO anioDAO = new AnioDAO();
            List<Anio> anioListSQLite = anioDAO.getAllAnioSQLite(getContext());
            List<Anio> anioListPostgres = anioDAO.getAllAnioPostgres(getContext());
            List<Integer> registrosAnterioresAnio = new ArrayList<>();
            for(Anio anio : anioListSQLite)registrosAnterioresAnio.add(anio.getIdAnio());
            for(Anio anioP : anioListPostgres){
                if(registrosAnterioresAnio.contains(anioP.getIdAnio())) //se modifica
                    anioDAO.updateRecordSQLite(anioP,getContext());
                else//se inserta
                    anioDAO.insertRecordSQLite(anioP,getContext());
            }
            //aqui xq solo va a existir una sola apertura en proceso siempre en la base postgresql
            Integer idAperturaProceso = 0;

            AperturaLecturaDAO aperturaLecturaDAO = new AperturaLecturaDAO();
            List<AperturaLectura> aperturaListSQLite = aperturaLecturaDAO.getAllAperturasSQLite(getContext());
            List<AperturaLectura> aperturaListPostgres = aperturaLecturaDAO.getAllAperturasPostgres(getContext());
            List<Integer> registrosAnterioresApertura = new ArrayList<>();
            for(AperturaLectura aperturaLectura : aperturaListSQLite)registrosAnterioresApertura.add(aperturaLectura.getIdApertura());
            for(AperturaLectura aperturaLecturaP : aperturaListPostgres){
                idAperturaProceso = aperturaLecturaP.getIdApertura();//aqui capturo el id de la apertura en proceso-------------------------
                if(registrosAnterioresApertura.contains(aperturaLecturaP.getIdApertura())) //se modifica
                    aperturaLecturaDAO.updateRecordSQLite(aperturaLecturaP,getContext());
                else//se inserta
                    aperturaLecturaDAO.insertRecordSQLite(aperturaLecturaP,getContext());
            }

            BarrioDAO barrioDAO = new BarrioDAO();
            List<Barrio> barrioListSQLite = barrioDAO.getAllBarriosSQLite(getContext());
            List<Barrio> barrioListPostgres = barrioDAO.getAllBarriosPostgres(getContext());
            List<Integer> registrosAnterioresBarrio = new ArrayList<>();
            for(Barrio barrio : barrioListSQLite)registrosAnterioresBarrio.add(barrio.getIdBarrio());
            for(Barrio barrioP : barrioListPostgres){
                if(registrosAnterioresBarrio.contains(barrioP.getIdBarrio())) //se modifica
                    barrioDAO.updateRecordSQLite(barrioP,getContext());
                else//se inserta
                    barrioDAO.insertRecordSQLite(barrioP,getContext());
            }

            CuentaClienteDAO cuentaClienteDAO = new CuentaClienteDAO();
            List<CuentaCliente> cuentaClienteListSQLite = cuentaClienteDAO.getAllCuentasSQLite(getContext());
            List<CuentaCliente>cuentaClienteListPostgres = cuentaClienteDAO.getAllCuentasPostgres(getContext());
            List<Integer> registrosAnterioresCuentaCliente = new ArrayList<>();
            for(CuentaCliente cuentaCliente : cuentaClienteListSQLite)registrosAnterioresCuentaCliente.add(cuentaCliente.getIdCuenta());
            for(CuentaCliente cuentaClienteP : cuentaClienteListPostgres){
                if(registrosAnterioresCuentaCliente.contains(cuentaClienteP.getIdCuenta())) //se modifica
                    cuentaClienteDAO.updateRecordSQLite(cuentaClienteP,getContext());
                else//se inserta
                    cuentaClienteDAO.insertRecordSQLite(cuentaClienteP,getContext());
            }

            MedidorDAO medidorDAO = new MedidorDAO();
            List<Medidor> medidorListSQLite = medidorDAO.getAllMedidoresSQLite(getContext());
            List<Medidor> medidorListPostgres = medidorDAO.getAllMedidoresPostgres(getContext());
            List<Integer> registrosAnterioresMedidor = new ArrayList<>();
            for(Medidor medidor : medidorListSQLite)registrosAnterioresMedidor.add(medidor.getIdMedidor());
            for(Medidor medidorP : medidorListPostgres){
                if(registrosAnterioresMedidor.contains(medidorP.getIdMedidor())) //se modifica
                    medidorDAO.updateRecordSQLite(medidorP,getContext());
                else//se inserta
                    medidorDAO.insertRecordSQLite(medidorP,getContext());
            }


            MesDAO mesDAO = new MesDAO();
            List<Mes> mesListSQLite = mesDAO.getAllMesesSQLite(getContext());
            List<Mes> mesListPostgres = mesDAO.getAllMesesPostgres(getContext());
            List<Integer> registrosAnterioresMes = new ArrayList<>();
            for(Mes mes : mesListSQLite)registrosAnterioresMes.add(mes.getIdMes());
            for(Mes mesP : mesListPostgres){
                if(registrosAnterioresMes.contains(mesP.getIdMes())) //se modifica
                    mesDAO.updateRecordSQLite(mesP,getContext());
                else//se inserta
                    mesDAO.insertRecordSQLite(mesP,getContext());
            }

            //estos tres solo se necesitan de la apertura que esta en proceso
            PlanillaDAO planillaDAO = new PlanillaDAO();
            List<Planilla> planillaListSQLite = planillaDAO.getAllPlanillasSQLite(getContext());
            List<Planilla> planillaListPostgres = planillaDAO.getAllPlanillasPostgres(getContext(),idAperturaProceso);
            List<Integer> registrosAnterioresPlanilla = new ArrayList<>();
            for(Planilla planilla : planillaListSQLite)registrosAnterioresPlanilla.add(planilla.getIdPlanilla());
            for(Planilla planillaP : planillaListPostgres){
                if(registrosAnterioresPlanilla.contains(planillaP.getIdPlanilla())) //se modifica
                    planillaDAO.updateRecordSQLite(planillaP,getContext());
                else//se inserta
                    planillaDAO.insertRecordSQLite(planillaP,getContext());
            }

            PlanillaDetalleDAO planillaDetalleDAO = new PlanillaDetalleDAO();
            List<PlanillaDetalle> planillaDetalleListSQLite = planillaDetalleDAO.getAllDetPlanillasSQLite(getContext());
            List<PlanillaDetalle> planillaDetalleListPostgres = planillaDetalleDAO.getAllDetPlanillasPostgres(getContext(),idAperturaProceso);
            List<Integer> registrosAnterioresplanillaDet = new ArrayList<>();
            for(PlanillaDetalle planillaDetalle : planillaDetalleListSQLite)registrosAnterioresplanillaDet.add(planillaDetalle.getIdPlanillaDet());
            for(PlanillaDetalle planillaDetalleP : planillaDetalleListPostgres){
                if(registrosAnterioresplanillaDet.contains(planillaDetalleP.getIdPlanillaDet())) //se modifica
                    planillaDetalleDAO.updateRecordSQLite(planillaDetalleP,getContext());
                else//se inserta
                    planillaDetalleDAO.insertRecordSQLite(planillaDetalleP,getContext());
            }

            ResponsableLecturaDAO responsableDAO = new ResponsableLecturaDAO();
            List<ResponsableLectura> responableListSQLite = responsableDAO.getAllResponsablesSQLite(getContext());
            List<ResponsableLectura> responsableListPostgres = responsableDAO.getAllResponsablesPostgres(getContext(),idAperturaProceso);
            List<Integer> registrosAnteriorResponsable = new ArrayList<>();
            for(ResponsableLectura responsable : responableListSQLite)registrosAnteriorResponsable.add(responsable.getIdResponsable());
            for(ResponsableLectura responsableP : responsableListPostgres){
                if(registrosAnteriorResponsable.contains(responsableP.getIdResponsable())) //se modifica
                    responsableDAO.updateRecordSQLite(responsableP,getContext());
                else //se inserta
                    responsableDAO.insertRecordSQLite(responsableP,getContext());
            }
            Toast.makeText(getContext(), "Se han actualizado los registros", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
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

    }

    @Override
    public void OnNegativeButtonClicked() {
        Toast.makeText(getContext(), "hola", Toast.LENGTH_SHORT).show();
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
