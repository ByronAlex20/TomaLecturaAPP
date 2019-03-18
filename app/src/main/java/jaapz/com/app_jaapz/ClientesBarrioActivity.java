package jaapz.com.app_jaapz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import jaapz.com.app_jaapz.pojos.Barrio;
import jaapz.com.app_jaapz.pojos.CuentaCliente;
import jaapz.com.app_jaapz.pojos.CuentaClienteDAO;
import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;
import jaapz.com.app_jaapz.util.MetodosGlobales;

public class ClientesBarrioActivity extends AppCompatActivity {
    private ListView lvClientesBarrio;
    ArrayList<EntidadCliente> listaEntidad;
    Barrio barrio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_barrio);
        barrio = (Barrio) getIntent().getExtras().getSerializable("Barrio Seleccionado");
        lvClientesBarrio = (ListView) findViewById(R.id.lvClientesBarrio);
        if(cargarDatosLista() == false){
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show();
        }
        lvClientesBarrio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten = new Intent(getApplicationContext(),TomaLecutasActivity.class);
                AdaptadorCliente adapter = (AdaptadorCliente) lvClientesBarrio.getAdapter();

                inten.putExtra("ClienteSeleccionado", adapter.getListaCliente().get(position));
                inten.putExtra("idBarrio", barrio.getIdBarrio());
                inten.putExtra("nombreBarrio", barrio.getNombre());
                startActivity(inten);
            }
        });
    }

    public boolean cargarDatosLista(){
        try{
            boolean bandera = false;
            ContextGlobal.getInstance().setLvClientesBarrio(lvClientesBarrio);
            MetodosGlobales obj = new MetodosGlobales(this);
            listaEntidad = obj.cargarDatosLista(barrio.getIdBarrio(),barrio.getNombre());
            if(listaEntidad.size() == 0)
                bandera = false;
            else
                bandera = true;

            return bandera;
        }catch (Exception ex){
            Toast.makeText(this, "aqui " + String.valueOf(ex.getMessage()), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
