package jaapz.com.app_jaapz;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import jaapz.com.app_jaapz.pojos.ResponsableLectura;
import jaapz.com.app_jaapz.pojos.ResponsableLecturaDAO;

import jaapz.com.app_jaapz.pojos.SegPerfil;
import jaapz.com.app_jaapz.pojos.SegPerfilDAO;
import jaapz.com.app_jaapz.pojos.SegUsuario;
import jaapz.com.app_jaapz.pojos.SegUsuarioDAO;
import jaapz.com.app_jaapz.pojos.SegUsuarioPerfil;
import jaapz.com.app_jaapz.pojos.SegUsuarioPerfilDAO;
import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ControllerHelper;
import jaapz.com.app_jaapz.util.CuadroDialogo;

public class MainActivity extends AppCompatActivity implements CuadroDialogo.OnDialogListener,DialogoSeguridadLogin.FinalizoCuadroDialogo {
    Button btnLogin;
    Button btnRegistrar;
    ImageButton btnConfiguracion;
    EditText et_usuario;
    TextView tvCiclo;
    EditText et_clave;
    ControllerHelper helper = new ControllerHelper();
    SegPerfilDAO perfilDAO = new SegPerfilDAO();
    SegUsuarioDAO usuarioDAO = new SegUsuarioDAO();
    SegUsuarioPerfilDAO usuarioPerfilDAO = new SegUsuarioPerfilDAO();

    private ProgressDialog progressDialog;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        getSupportActionBar().hide();
        if(getPreference()){
            Intent ListSong = new Intent(getApplicationContext(), PrincipalActivity.class);
            startActivity(ListSong);
            finish();
        }
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnConfiguracion = (ImageButton)findViewById(R.id.btn_configuracion);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_clave = (EditText) findViewById(R.id.et_clave);
        tvCiclo = (TextView) findViewById(R.id.tvCiclo);
        progressDialog= new ProgressDialog(this);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irPantallaConfiguraciones();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        cargarEstadoApertura();
    }
    private void cargarEstadoApertura(){
        try{
            String estadoCiclo = "";
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select an.descripcion,me.descripcion from " + BaseDatos.TABLA_APERTURA_LECTURA + " a, " + BaseDatos.TABLA_ANIO + " an," +
                    BaseDatos.TABLA_MES + " me where a.id_anio = an.id_anio and a.id_mes = me.id_mes and " +
                    "a.estado = 'A' and an.estado = 'A' and me.estado = 'A' and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO + "'";
            Cursor fila = baseDatos.rawQuery(cadena,null);
            if(fila.getCount() > 0){
                while(fila.moveToNext()){
                    estadoCiclo = "Año: " + fila.getString(0) + " Mes: " + fila.getString(1);
                }
            }else
                estadoCiclo = "No hay cargado datos...";
            tvCiclo.setText(estadoCiclo);

        }catch(Exception ex){
            Toast.makeText(context, String.valueOf(ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }
    private void guardarEstadoSesion(Integer idUsuario){
        SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constantes.ID_SHARED_PREFERENCES_ESTADO,true);
        editor.putInt(Constantes.ID_SHARED_PREFERENCES_USUARIO,idUsuario);
        editor.commit();
    }
    private boolean getPreference(){
        SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
        return preferences.getBoolean(Constantes.ID_SHARED_PREFERENCES_ESTADO,false);
    }
    private void iniciarSesion(){
        try{
            String clave = helper.Encriptar(String.valueOf(et_clave.getText()));
            String usuario = helper.Encriptar(String.valueOf(et_usuario.getText()));

            List<SegUsuario> usuarioBuscar = usuarioDAO.buscarUsuario(usuario,clave,this);
            //aqui se trajo el usuario... ahora toca preguntar si tiene perfil de lectura
            if(usuarioBuscar.size() > 0){
                boolean bandera = false;
                Integer idUsuario = usuarioBuscar.get(0).getIdUsuario();
                List<SegUsuarioPerfil> listaPerfiles = usuarioPerfilDAO.getPerfilesUsuarioSQLite(this,idUsuario);
                for(SegUsuarioPerfil usuarioPerfil : listaPerfiles){
                    if(usuarioPerfil.getIdPerfil() == Constantes.ID_USU_LECTURA)
                        bandera = true;
                }
                if(bandera == true){
                    guardarEstadoSesion(usuarioBuscar.get(0).getIdUsuario());
                    Intent ListSong = new Intent(getApplicationContext(), PrincipalActivity.class);
                    startActivity(ListSong);
                    finish();
                }else
                    Toast.makeText(context, "El usuario no tiene perfil de lectura", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "Cédula o Usuario Incorrecto!!", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void actualizar(View view){
        try{
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogFragment fragment = (DialogFragment)fragmentManager.findFragmentByTag(CuadroDialogo.TAG);
            if(fragment == null){
                fragment = new CuadroDialogo();
                Bundle bundle = new Bundle();
                bundle.putString(CuadroDialogo.TAG_MENSAJE,"Al actualizar los datos se eliminaran los registros anteriores y se cargaran los nuevos\n¿Desea Continuar?");
                bundle.putString(CuadroDialogo.TAG_TITULO,"Sincronizar");
                fragment.setArguments(bundle);
            }
            fragment.show(getSupportFragmentManager(),CuadroDialogo.TAG);
        }catch(Exception ex){
        }
    }

    //metodo del resultado de lo q retorna el cuadro de dialogo de inicio de sesion para las configuraciones
    @Override
    public void resultadoCuadroDialogo(Boolean sesionIniciada) {
        try{
            if(sesionIniciada == true){
                Intent intent = new Intent(this, ConfiguracionActivity.class);
                startActivity(intent);
            }else
                Toast.makeText(context, "Usuario o clave incorrecto!!!", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void irPantallaConfiguraciones(){
        try{
            //aqui va para iniciar secion e ir a la pantalla de configuraciones
            new DialogoSeguridadLogin(this,MainActivity.this);
        }catch(Exception ex){
        }
    }
    @Override
    public void OnPositiveButtonClicked() {
        try{
            this.deleteDatabase(Constantes.NOMBRE_BD_SQLITE);

            //recuperar los perfiles
            List<SegPerfil> perfilListSQLite = perfilDAO.getAllPerfilesSQLite(this);
            List<SegPerfil> perfilListPostgres = perfilDAO.getAllPerfilesPostgres(this);
            List<Integer> registrosAnteriores = new ArrayList<>();
            for(SegPerfil perfil : perfilListSQLite)registrosAnteriores.add(perfil.getIdPerfil());
            for(SegPerfil perfilP : perfilListPostgres){
                if(registrosAnteriores.contains(perfilP.getIdPerfil())) //se modifica
                    perfilDAO.updateRecordSQLite(perfilP,this);
                else//se inserta
                    perfilDAO.insertRecordSQLite(perfilP,this);
            }
            //recupera los usuarios
            List<SegUsuario> usuarioListSQLite = usuarioDAO.getAllUsuariosSQLite(this);
            List<SegUsuario> usuarioListPostgres = usuarioDAO.getAllUsuariosPostgres(this);
            List<Integer> registrosAnterioresUser = new ArrayList<>();
            for(SegUsuario usuario : usuarioListSQLite)registrosAnterioresUser.add(usuario.getIdUsuario());
            for(SegUsuario usuarioP : usuarioListPostgres){
                if(registrosAnterioresUser.contains(usuarioP.getIdUsuario())) //se modifica
                    usuarioDAO.updateRecordSQLite(usuarioP,this);
                else//se inserta
                    usuarioDAO.insertRecordSQLite(usuarioP,this);
            }


            //recuperar usuario perfil

            List<SegUsuarioPerfil> usuarioPerfilListSQLite = usuarioPerfilDAO.getAllUsuariosPerfilSQLite(this);
            List<SegUsuarioPerfil> usuarioPerfilListPostgres = usuarioPerfilDAO.getAllUsuariosPerfilPostgres(this);
            List<Integer> registrosAnterioresUserPer = new ArrayList<>();
            for(SegUsuarioPerfil usuario : usuarioPerfilListSQLite)registrosAnterioresUserPer.add(usuario.getIdUsuarioPerfil());
            for(SegUsuarioPerfil usuarioP : usuarioPerfilListPostgres){
                if(registrosAnterioresUser.contains(usuarioP.getIdUsuarioPerfil())) //se modifica
                    usuarioPerfilDAO.updateRecordSQLite(usuarioP,this);
                else//se inserta
                    usuarioPerfilDAO.insertRecordSQLite(usuarioP,this);
            }

            //ahi con eso tenemos la nueva tabla de seguridad

            boolean band = false;
            ClienteDAO clienteDAO = new ClienteDAO();
            //recuperar primero son los cliente antes q nada
            List<Cliente> clientesListSQLite = clienteDAO.getAllClientesSQLite(this);
            List<Cliente> clientesListPostgres = clienteDAO.getAllClientesPostgres(this);
            List<Integer> registrosAnterioresCliente = new ArrayList<>();
            for(Cliente cliente : clientesListSQLite)registrosAnterioresCliente.add(cliente.getIdCliente());
            for(Cliente clienteP : clientesListPostgres){
                if(registrosAnterioresCliente.contains(clienteP.getIdCliente())) //se modifica
                    clienteDAO.updateRecordSQLite(clienteP,this);
                else//se inserta
                    clienteDAO.insertRecordSQLite(clienteP,this);
            }

            AnioDAO anioDAO = new AnioDAO();
            List<Anio> anioListSQLite = anioDAO.getAllAnioSQLite(this);
            List<Anio> anioListPostgres = anioDAO.getAllAnioPostgres(this);
            List<Integer> registrosAnterioresAnio = new ArrayList<>();
            for(Anio anio : anioListSQLite)registrosAnterioresAnio.add(anio.getIdAnio());
            for(Anio anioP : anioListPostgres){
                if(registrosAnterioresAnio.contains(anioP.getIdAnio())) //se modifica
                    anioDAO.updateRecordSQLite(anioP,this);
                else//se inserta
                    anioDAO.insertRecordSQLite(anioP,this);
            }
            //aqui xq solo va a existir una sola apertura en proceso siempre en la base postgresql
            Integer idAperturaProceso = 0;

            AperturaLecturaDAO aperturaLecturaDAO = new AperturaLecturaDAO();
            List<AperturaLectura> aperturaListSQLite = aperturaLecturaDAO.getAllAperturasSQLite(this);
            List<AperturaLectura> aperturaListPostgres = aperturaLecturaDAO.getAllAperturasPostgres(this);
            List<Integer> registrosAnterioresApertura = new ArrayList<>();
            for(AperturaLectura aperturaLectura : aperturaListSQLite)registrosAnterioresApertura.add(aperturaLectura.getIdApertura());
            for(AperturaLectura aperturaLecturaP : aperturaListPostgres){
                idAperturaProceso = aperturaLecturaP.getIdApertura();//aqui capturo el id de la apertura en proceso-------------------------
                if(registrosAnterioresApertura.contains(aperturaLecturaP.getIdApertura())) //se modifica
                    aperturaLecturaDAO.updateRecordSQLite(aperturaLecturaP,this);
                else//se inserta
                    aperturaLecturaDAO.insertRecordSQLite(aperturaLecturaP,this);
            }

            BarrioDAO barrioDAO = new BarrioDAO();
            List<Barrio> barrioListSQLite = barrioDAO.getAllBarriosSQLite(this);
            List<Barrio> barrioListPostgres = barrioDAO.getAllBarriosPostgres(this);
            List<Integer> registrosAnterioresBarrio = new ArrayList<>();
            for(Barrio barrio : barrioListSQLite)registrosAnterioresBarrio.add(barrio.getIdBarrio());
            for(Barrio barrioP : barrioListPostgres){
                if(registrosAnterioresBarrio.contains(barrioP.getIdBarrio())) //se modifica
                    barrioDAO.updateRecordSQLite(barrioP,this);
                else//se inserta
                    barrioDAO.insertRecordSQLite(barrioP,this);
            }

            CuentaClienteDAO cuentaClienteDAO = new CuentaClienteDAO();
            List<CuentaCliente> cuentaClienteListSQLite = cuentaClienteDAO.getAllCuentasSQLite(this);
            List<CuentaCliente>cuentaClienteListPostgres = cuentaClienteDAO.getAllCuentasPostgres(this);
            List<Integer> registrosAnterioresCuentaCliente = new ArrayList<>();
            for(CuentaCliente cuentaCliente : cuentaClienteListSQLite)registrosAnterioresCuentaCliente.add(cuentaCliente.getIdCuenta());
            for(CuentaCliente cuentaClienteP : cuentaClienteListPostgres){
                if(registrosAnterioresCuentaCliente.contains(cuentaClienteP.getIdCuenta())) //se modifica
                    cuentaClienteDAO.updateRecordSQLite(cuentaClienteP,this);
                else//se inserta
                    cuentaClienteDAO.insertRecordSQLite(cuentaClienteP,this);
            }

            MedidorDAO medidorDAO = new MedidorDAO();
            List<Medidor> medidorListSQLite = medidorDAO.getAllMedidoresSQLite(this);
            List<Medidor> medidorListPostgres = medidorDAO.getAllMedidoresPostgres(this);
            List<Integer> registrosAnterioresMedidor = new ArrayList<>();
            for(Medidor medidor : medidorListSQLite)registrosAnterioresMedidor.add(medidor.getIdMedidor());
            for(Medidor medidorP : medidorListPostgres){
                if(registrosAnterioresMedidor.contains(medidorP.getIdMedidor())) //se modifica
                    medidorDAO.updateRecordSQLite(medidorP,this);
                else//se inserta
                    medidorDAO.insertRecordSQLite(medidorP,this);
            }


            MesDAO mesDAO = new MesDAO();
            List<Mes> mesListSQLite = mesDAO.getAllMesesSQLite(this);
            List<Mes> mesListPostgres = mesDAO.getAllMesesPostgres(this);
            List<Integer> registrosAnterioresMes = new ArrayList<>();
            for(Mes mes : mesListSQLite)registrosAnterioresMes.add(mes.getIdMes());
            for(Mes mesP : mesListPostgres){
                if(registrosAnterioresMes.contains(mesP.getIdMes())) //se modifica
                    mesDAO.updateRecordSQLite(mesP,this);
                else//se inserta
                    mesDAO.insertRecordSQLite(mesP,this);
            }

            //estos tres solo se necesitan de la apertura que esta en proceso
            PlanillaDAO planillaDAO = new PlanillaDAO();
            List<Planilla> planillaListSQLite = planillaDAO.getAllPlanillasSQLite(this);
            List<Planilla> planillaListPostgres = planillaDAO.getAllPlanillasPostgres(this,idAperturaProceso);
            List<Integer> registrosAnterioresPlanilla = new ArrayList<>();
            for(Planilla planilla : planillaListSQLite)registrosAnterioresPlanilla.add(planilla.getIdPlanilla());
            for(Planilla planillaP : planillaListPostgres){
                if(registrosAnterioresPlanilla.contains(planillaP.getIdPlanilla())) //se modifica
                    planillaDAO.updateRecordSQLite(planillaP,this);
                else//se inserta
                    planillaDAO.insertRecordSQLite(planillaP,this);
            }

            PlanillaDetalleDAO planillaDetalleDAO = new PlanillaDetalleDAO();
            List<PlanillaDetalle> planillaDetalleListSQLite = planillaDetalleDAO.getAllDetPlanillasSQLite(this);
            List<PlanillaDetalle> planillaDetalleListPostgres = planillaDetalleDAO.getAllDetPlanillasPostgres(this,idAperturaProceso);
            List<Integer> registrosAnterioresplanillaDet = new ArrayList<>();
            for(PlanillaDetalle planillaDetalle : planillaDetalleListSQLite)registrosAnterioresplanillaDet.add(planillaDetalle.getIdPlanillaDet());
            for(PlanillaDetalle planillaDetalleP : planillaDetalleListPostgres){
                if(registrosAnterioresplanillaDet.contains(planillaDetalleP.getIdPlanillaDet())) //se modifica
                    planillaDetalleDAO.updateRecordSQLite(planillaDetalleP,this);
                else//se inserta
                    planillaDetalleDAO.insertRecordSQLite(planillaDetalleP,this);
            }

            ResponsableLecturaDAO responsableDAO = new ResponsableLecturaDAO();
            List<ResponsableLectura> responableListSQLite = responsableDAO.getAllResponsablesSQLite(this);
            List<ResponsableLectura> responsableListPostgres = responsableDAO.getAllResponsablesPostgres(this,idAperturaProceso);
            List<Integer> registrosAnteriorResponsable = new ArrayList<>();
            for(ResponsableLectura responsable : responableListSQLite)registrosAnteriorResponsable.add(responsable.getIdResponsable());
            for(ResponsableLectura responsableP : responsableListPostgres){
                if(registrosAnteriorResponsable.contains(responsableP.getIdResponsable())) //se modifica
                    responsableDAO.updateRecordSQLite(responsableP,this);
                else //se inserta
                    responsableDAO.insertRecordSQLite(responsableP,this);
            }
            Toast.makeText(this, "Se han actualizado los registros", Toast.LENGTH_SHORT).show();
            cargarEstadoApertura();
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnNegativeButtonClicked() {
        try{
            //Toast.makeText(this, "Se ha cancelado la operación", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
