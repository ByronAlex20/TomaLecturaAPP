package jaapz.com.app_jaapz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import jaapz.com.app_jaapz.pojos.SegUsuario;
import jaapz.com.app_jaapz.pojos.SegUsuarioDAO;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;
import jaapz.com.app_jaapz.util.CuadroDialogo;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BarrioFragment.OnFragmentInteractionListener,
ClientesFragment.OnFragmentInteractionListener,ConfiguracionFragment.OnFragmentInteractionListener,
MapaFragment.OnFragmentInteractionListener,SyncFragment.OnFragmentInteractionListener,CuadroDialogo.OnDialogListener,
CargarDatosFragment.OnFragmentInteractionListener{

    TextView txtNombreUsuario;
    SegUsuarioDAO usuarioDAO = new SegUsuarioDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //capturar las id de los campos
        View headerView = navigationView.getHeaderView(0);
        txtNombreUsuario = (TextView) headerView.findViewById(R.id.txtNombreUsuario);
        getPreference();
    }

    private void getPreference(){
        SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
        txtNombreUsuario.setText(getUsuario(preferences.getInt(Constantes.ID_SHARED_PREFERENCES_USUARIO,0)));
        ContextGlobal.getInstance().setIdUsuarioLogeado(preferences.getInt(Constantes.ID_SHARED_PREFERENCES_USUARIO,0));
    }
    private String getUsuario(Integer codigo){
        String usuario = "";
        List<SegUsuario> usuarios = usuarioDAO.getUsuarioById(codigo,this);
        if(usuarios.size() > 0)
            usuario = usuarios.get(0).getNombre() + " " + usuarios.get(0).getApellido();
        else
            usuario = "";
        return usuario;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Fragment miFragment = new ConfiguracionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.conten_principal,miFragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }else if(id == R.id.action_sesion) {
            try{
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogFragment fragment = (DialogFragment)fragmentManager.findFragmentByTag(CuadroDialogo.TAG);
                if(fragment == null){
                    fragment = new CuadroDialogo();
                    Bundle bundle = new Bundle();
                    bundle.putString(CuadroDialogo.TAG_MENSAJE,"Seguro desea cerrar sesión?");
                    bundle.putString(CuadroDialogo.TAG_TITULO,"Cerrar sesión");
                    fragment.setArguments(bundle);
                }
                fragment.show(getSupportFragmentManager(),CuadroDialogo.TAG);
            }catch(Exception ex){
            }
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;
        boolean fragmentSeleccionado = false;

        if (id == R.id.nav_barrios) {
            miFragment = new BarrioFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_clientes) {
            miFragment = new ClientesFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_mapa) {
            miFragment = new MapaFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_configuraciones) {
            miFragment = new ConfiguracionFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_sync) {
            miFragment = new SyncFragment();
            fragmentSeleccionado = true;
        } else if(id == R.id.nav_sync_datos){
            miFragment = new CargarDatosFragment();
            fragmentSeleccionado = true;
        }else if(id == R.id.nav_sesion){
            try{
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogFragment fragment = (DialogFragment)fragmentManager.findFragmentByTag(CuadroDialogo.TAG);
                if(fragment == null){
                    fragment = new CuadroDialogo();
                    Bundle bundle = new Bundle();
                    bundle.putString(CuadroDialogo.TAG_MENSAJE,"Seguro desea cerrar sesión?");
                    bundle.putString(CuadroDialogo.TAG_TITULO,"Cerrar sesión");
                    fragment.setArguments(bundle);
                }
                fragment.show(getSupportFragmentManager(),CuadroDialogo.TAG);
            }catch(Exception ex){
            }
        }
        if(fragmentSeleccionado)
            getSupportFragmentManager().beginTransaction().replace(R.id.conten_principal,miFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    //acciones para el cierre de sesion
    @Override
    public void OnPositiveButtonClicked() {
        try{
            SharedPreferences preferences = getSharedPreferences(Constantes.ID_SHARED_PREFERENCES,MODE_PRIVATE);
            preferences.edit().putBoolean(Constantes.ID_SHARED_PREFERENCES_ESTADO,false).apply();
            Intent ListSong = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(ListSong);
            finish();
        }catch(Exception ex){

        }
    }

    @Override
    public void OnNegativeButtonClicked() {

    }

}
