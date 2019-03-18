package jaapz.com.app_jaapz.pojos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;

public class CuentaClienteDAO {
    public ArrayList<CuentaCliente> getListaClienteBarrio(Integer idBarrio,Context context){
        try{
            ArrayList<CuentaCliente> listaCuentas = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_cuenta,id_cliente,id_categoria,id_barrio,id_medidor,usuario_crea," +
                    "fecha_ingreso,observacion,direccion,email,latitud," +
                    "longitud,estado from " + BaseDatos.TABLA_CUENTA_CLIENTE + " where id_barrio = " + idBarrio + "" +
                    " and id_medidor is not null and estado = 'A'";
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                CuentaCliente obj = new CuentaCliente();
                obj.setIdCuenta(fila.getInt(0));
                obj.setIdCliente(fila.getInt(1));
                obj.setIdCategoria(fila.getInt(2));
                obj.setIdBarrio(fila.getInt(3));
                obj.setIdMedidor(fila.getInt(4));
                obj.setUsuarioCrea(fila.getInt(5));

                obj.setObservacion(fila.getString(7));
                obj.setDireccion(fila.getString(8));
                obj.setEmail(fila.getString(9));
                obj.setLatitud(fila.getString(10));
                obj.setLongitud(fila.getString(11));
                obj.setEstado(fila.getString(12));
                listaCuentas.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaCuentas;
        }catch(Exception ex){
            Toast.makeText(context, String.valueOf(ex.getMessage()), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public ArrayList<CuentaCliente> getListaClienteTodos(Context context){
        try{
            ArrayList<CuentaCliente> listaCuentas = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();

            String cadena = "select c.id_cuenta,c.id_cliente,c.id_categoria,c.id_barrio,c.id_medidor," +
                    "c.usuario_crea,c.fecha_ingreso,c.observacion,c.direccion,c.email,c.latitud,c.longitud,c.estado from " +
                    BaseDatos.TABLA_BARRIO + " b, " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " r, " + BaseDatos.TABLA_APERTURA_LECTURA + " a, " +
                    BaseDatos.TABLA_CUENTA_CLIENTE + " c where r.id_apertura = a.id_apertura and r.id_barrio = b.id_barrio and " +
                    " c.id_barrio = b.id_barrio and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO
                    + "' and r.id_usuario = " + ContextGlobal.getInstance().getIdUsuarioLogeado() + " and b.estado = 'A' " +
                    "and r.estado = 'A' and a.estado = 'A' and c.estado = 'A'";

            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                CuentaCliente obj = new CuentaCliente();
                obj.setIdCuenta(fila.getInt(0));
                obj.setIdCliente(fila.getInt(1));
                obj.setIdCategoria(fila.getInt(2));
                obj.setIdBarrio(fila.getInt(3));
                obj.setIdMedidor(fila.getInt(4));
                obj.setUsuarioCrea(fila.getInt(5));

                obj.setObservacion(fila.getString(7));
                obj.setDireccion(fila.getString(8));
                obj.setEmail(fila.getString(9));
                obj.setLatitud(fila.getString(10));
                obj.setLongitud(fila.getString(11));
                obj.setEstado(fila.getString(12));
                listaCuentas.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaCuentas;
        }catch(Exception ex){
            return null;
        }
    }

    public int getCantidadClientesBarrio(Integer idBarrio,Context context){
        try{
            int cantidad = 0;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select count(*) as cantidad " +
                    " from " + BaseDatos.TABLA_CUENTA_CLIENTE + " where id_barrio = " + idBarrio;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                cantidad = fila.getInt(0);
            }
            baseDatos.close();
            return cantidad;
        }catch(Exception ex){
            return 0;
        }
    }
    public List<CuentaCliente> getAllCuentasSQLite(Context context){
        try{
            List<CuentaCliente> listaCuentas = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_cuenta,id_cliente,id_categoria,id_barrio,id_medidor,usuario_crea," +
                    "fecha_ingreso,observacion,direccion,email,latitud," +
                    "longitud,estado from " + BaseDatos.TABLA_CUENTA_CLIENTE;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                CuentaCliente obj = new CuentaCliente();
                obj.setIdCuenta(fila.getInt(0));
                obj.setIdCliente(fila.getInt(1));
                obj.setIdCategoria(fila.getInt(2));
                obj.setIdBarrio(fila.getInt(3));
                obj.setIdMedidor(fila.getInt(4));
                obj.setUsuarioCrea(fila.getInt(5));
                obj.setFechaIngreso(Date.valueOf(fila.getString(6)));
                obj.setObservacion(fila.getString(7));
                obj.setDireccion(fila.getString(8));
                obj.setEmail(fila.getString(9));
                obj.setLatitud(fila.getString(10));
                obj.setLongitud(fila.getString(11));
                obj.setEstado(fila.getString(12));
                listaCuentas.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaCuentas;
        }catch(Exception ex){
            return null;
        }
    }
    public List<CuentaCliente> getAllCuentasPostgres(Context context){
        try{
            List<CuentaCliente> listaCuentas = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_CUENTA_CLIENTE + " where id_medidor is not null";
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    CuentaCliente obj = new CuentaCliente();
                    obj.setIdCuenta(fila.getInt("id_cuenta"));
                    obj.setIdCliente(fila.getInt("id_cliente"));
                    obj.setIdCategoria(fila.getInt("id_categoria"));
                    obj.setIdBarrio(fila.getInt("id_barrio"));
                    obj.setIdMedidor(fila.getInt("id_medidor"));
                    obj.setUsuarioCrea(fila.getInt("usuario_crea"));
                    obj.setFechaIngreso(fila.getDate("fecha_ingreso"));
                    obj.setObservacion(fila.getString("observacion"));
                    obj.setDireccion(fila.getString("direccion"));
                    obj.setEmail(fila.getString("email"));
                    obj.setLatitud(fila.getString("latitud"));
                    obj.setLongitud(fila.getString("longitud"));
                    obj.setEstado(fila.getString("estado"));
                    listaCuentas.add(obj);
                    obj = null;
                }
            }
            return listaCuentas;
        }catch(Exception ex){
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(CuentaCliente cuentaCliente, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_cuenta",cuentaCliente.getIdCuenta());
            registro.put("id_cliente",cuentaCliente.getIdCliente());
            registro.put("id_categoria",cuentaCliente.getIdCategoria());
            registro.put("id_barrio",cuentaCliente.getIdBarrio());
            registro.put("id_medidor",cuentaCliente.getIdMedidor());
            registro.put("usuario_crea",cuentaCliente.getUsuarioCrea());
            registro.put("fecha_ingreso",String.valueOf(cuentaCliente.getFechaIngreso()));
            registro.put("observacion",cuentaCliente.getObservacion());
            registro.put("direccion",cuentaCliente.getDireccion());
            registro.put("email",cuentaCliente.getEmail());
            registro.put("latitud",cuentaCliente.getLatitud());
            registro.put("longitud",cuentaCliente.getLongitud());
            registro.put("estado",cuentaCliente.getEstado());
            baseDatos.insert(BaseDatos.TABLA_CUENTA_CLIENTE,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(CuentaCliente cuentaCliente,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            //registro.put("id_perfil",String.valueOf(et_ip_servidor.getText()));
            registro.put("id_cliente",cuentaCliente.getIdCliente());
            registro.put("id_categoria",cuentaCliente.getIdCategoria());
            registro.put("id_barrio",cuentaCliente.getIdBarrio());
            registro.put("id_medidor",cuentaCliente.getIdMedidor());
            registro.put("usuario_crea",cuentaCliente.getUsuarioCrea());
            registro.put("fecha_ingreso",String.valueOf(cuentaCliente.getFechaIngreso()));
            registro.put("observacion",cuentaCliente.getObservacion());
            registro.put("direccion",cuentaCliente.getDireccion());
            registro.put("email",cuentaCliente.getEmail());
            registro.put("latitud",cuentaCliente.getLatitud());
            registro.put("longitud",cuentaCliente.getLongitud());
            registro.put("estado",cuentaCliente.getEstado());
            baseDatos.update(BaseDatos.TABLA_CUENTA_CLIENTE,registro," id_cuenta = " + cuentaCliente.getIdCuenta(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(CuentaCliente cuentaCliente,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_CLIENTE," id_cuenta = " + cuentaCliente.getIdCuenta(),null);
            //Toast.makeText(context, "Perfil Eliminado!!", Toast.LENGTH_SHORT).show();
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
