package jaapz.com.app_jaapz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.pojos.CuentaClienteDAO;
import jaapz.com.app_jaapz.pojos.Planilla;
import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;

public class AdaptadorCliente extends BaseAdapter {
    private ArrayList<EntidadCliente> listaCliente;
    private Context context;

    public AdaptadorCliente(ArrayList<EntidadCliente> listaCliente, Context context) {
        this.listaCliente = listaCliente;
        this.context = context;
    }

    public AdaptadorCliente(){

    }
    @Override
    public int getCount() {
        return listaCliente.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCliente.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EntidadCliente item = (EntidadCliente) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.adaptador_cliente,null);

        TextView tvNumMedidorCliente = (TextView) convertView.findViewById(R.id.tvNumMedidorCliente);
        TextView tvLecturaAnterior = (TextView) convertView.findViewById(R.id.tvLecturaAnterior);
        TextView tvLecturaActual = (TextView) convertView.findViewById(R.id.tvLecturaActual);
        TextView tvConsumo = (TextView) convertView.findViewById(R.id.tvConsumo);
        TextView tvBarrio = (TextView) convertView.findViewById(R.id.tvBarrio);

        tvNumMedidorCliente.setText(String.valueOf(item.getNumMedidor()));
        tvLecturaAnterior.setText("Lectura anterior: " + item.getLecturaAnterior());
        tvLecturaActual.setText("Lectura actual: " + item.getLecturaActual());
        tvConsumo.setText("Consumo: " + item.getConsumo());
        tvBarrio.setText(item.getBarrio());

        if(validarlecturaTomada(item.getIdPlanilla()) == true)
            convertView.setBackgroundColor(Color.rgb(221,219,218));
        return convertView;
    }
    private boolean validarlecturaTomada(Integer idPlanilla){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select estado_toma from " + BaseDatos.TABLA_PLANILLA + " where id_planilla = " + idPlanilla;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                if(fila.getString(0) != null)
                    if(fila.getString(0).equals(Constantes.ESTADO_TOMA_REALIZADO))
                        bandera = true;
            }
            baseDatos.close();
            return bandera;
        }catch(Exception ex){
            Toast.makeText(context, "SQLite: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public ArrayList<EntidadCliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(ArrayList<EntidadCliente> listaCliente) {
        this.listaCliente = listaCliente;
    }
}
