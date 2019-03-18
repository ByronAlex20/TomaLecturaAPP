package jaapz.com.app_jaapz.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import jaapz.com.app_jaapz.AdaptadorCliente;
import jaapz.com.app_jaapz.ClientesBarrioActivity;
import jaapz.com.app_jaapz.EntidadCliente;
import jaapz.com.app_jaapz.pojos.CuentaCliente;
import jaapz.com.app_jaapz.pojos.CuentaClienteDAO;

public class MetodosGlobales {
    Context context;
    public MetodosGlobales(Context context){
        this.context = context;
    }
    public ArrayList<EntidadCliente> cargarDatosLista(Integer idBarrio,String nombreBarrio){
        try{
            ArrayList<EntidadCliente> listaEntidad = new ArrayList<EntidadCliente>();
            CuentaClienteDAO cuentaDAO = new CuentaClienteDAO();

            final ArrayList<CuentaCliente> listaCuenta = cuentaDAO.getListaClienteBarrio(idBarrio,context);
            for(CuentaCliente cuenta : listaCuenta){
                int lecturaActual = getLecturaActual(cuenta.getIdCuenta());
                int lecturaAnterior = getLecturaAnterior(cuenta.getIdCuenta());
                int idPlanilla = getIdPlanilla(cuenta.getIdCuenta());
                int consumo = lecturaActual - lecturaAnterior;
                EntidadCliente obj = new EntidadCliente("Cliente: " + getNombreCliente(cuenta.getIdCliente()) + " Medidor:" + getNumeroMedidor(cuenta.getIdCuenta()),
                        lecturaActual,lecturaAnterior,consumo,"BARRIO: " + nombreBarrio , cuenta.getIdCliente(),cuenta.getIdCuenta(),idPlanilla);
                listaEntidad.add(obj);
            }
            ContextGlobal.getInstance().getLvClientesBarrio().setAdapter(null);
            Collections.sort(listaEntidad);
            AdaptadorCliente adaptador = new AdaptadorCliente(listaEntidad,context);

            ContextGlobal.getInstance().getLvClientesBarrio().setAdapter(adaptador);


            return listaEntidad;
        }catch (Exception ex){
            return null;
        }
    }

    public ArrayList<EntidadCliente> cargarDatosListaTodos(){
        try{
            ArrayList<EntidadCliente> listaEntidad = new ArrayList<EntidadCliente>();
            CuentaClienteDAO cuentaDAO = new CuentaClienteDAO();

            final ArrayList<CuentaCliente> listaCuenta = cuentaDAO.getListaClienteTodos(context);
            for(CuentaCliente cuenta : listaCuenta){
                int lecturaActual = getLecturaActual(cuenta.getIdCuenta());
                int lecturaAnterior = getLecturaAnterior(cuenta.getIdCuenta());
                int idPlanilla = getIdPlanilla(cuenta.getIdCuenta());
                int consumo = lecturaActual - lecturaAnterior;
                String nombreBarrio = getNombreBarrio(cuenta.getIdBarrio());
                EntidadCliente obj = new EntidadCliente("Cliente: " + getNombreCliente(cuenta.getIdCliente()) + " Medidor:" + getNumeroMedidor(cuenta.getIdCuenta()),
                        lecturaActual,lecturaAnterior,consumo,"BARRIO: " + nombreBarrio , cuenta.getIdCliente(),cuenta.getIdCuenta(),idPlanilla);
                listaEntidad.add(obj);
            }
            ContextGlobal.getInstance().getLvClientesBarrio().setAdapter(null);
            Collections.sort(listaEntidad);
            AdaptadorCliente adaptador = new AdaptadorCliente(listaEntidad,context);

            ContextGlobal.getInstance().getLvClientesBarrio().setAdapter(adaptador);


            return listaEntidad;
        }catch (Exception ex){
            return null;
        }
    }

    private String getNombreBarrio(Integer idBarrio){
        try{
            String numeroMedidor = "Barrio no asignado";
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,1);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select b.nombre from " + BaseDatos.TABLA_BARRIO + " b where b.id_barrio = " + idBarrio;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                numeroMedidor = fila.getString(0);
            }
            baseDatos.close();
            return numeroMedidor;
        }catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "Barrio no asignado";
        }
    }
    private String getNombreCliente(Integer idCliente){
        try{
            String nombreCliente = "";
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,1);
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
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "";
        }
    }
    private String getNumeroMedidor(Integer idCuenta){
        try{
            String numeroMedidor = "No. Medidor: No Asignado";
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,1);
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
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "No. Medidor: No Asignado";
        }
    }
    private int getIdPlanilla(Integer idCuenta){
        try{
            int idPlanilla = 0;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,1);
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
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    private int getLecturaActual(Integer idCuenta){
        try{
            int lecturaActual = 0;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,1);
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
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    private int getLecturaAnterior(Integer idCuenta){
        try{
            int lecturaActual = 0;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,1);
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
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}
