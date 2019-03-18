package jaapz.com.app_jaapz.pojos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ControllerHelper;

public class SegUsuarioDAO {

    public List<SegUsuario> getUsuarioById(Integer id,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            List<SegUsuario> listaUsuarios = new ArrayList<>();
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();

            String cadena = "select id_usuario,cedula,nombre,apellido,direccion,telefono," +
                    "cargo,usuario,clave,nuevo,estado from " + BaseDatos.TABLA_USUARIO + " " +
                    "where id_usuario = " + id +
                    " and estado = '" + Constantes.EST_ACTIVO + "'";
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                SegUsuario obj = new SegUsuario();
                obj.setIdUsuario(fila.getInt(0));
                obj.setCedula(fila.getString(1));
                obj.setNombre(fila.getString(2));
                obj.setApellido(fila.getString(3));
                obj.setDireccion(fila.getString(4));
                obj.setTelefono(fila.getString(5));
                obj.setCargo(fila.getString(6));
                obj.setUsuario(fila.getString(7));
                obj.setClave(fila.getString(8));
                obj.setNuevo(fila.getString(9));
                obj.setEstado(fila.getString(10));
                listaUsuarios.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaUsuarios;
        }catch(Exception ex){
            return null;
        }
    }
    public List<SegUsuario> buscarUsuario(String cedula,String clave,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            List<SegUsuario> listaUsuarios = new ArrayList<>();
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_usuario,cedula,nombre,apellido,direccion,telefono," +
                    "cargo,usuario,clave,nuevo,estado from " + BaseDatos.TABLA_USUARIO + " " +
                    "where usuario = '" + cedula + "' and clave = '" + clave + "' " +
                    "and estado = '" + Constantes.EST_ACTIVO + "'";

            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                SegUsuario obj = new SegUsuario();
                obj.setIdUsuario(fila.getInt(0));
                obj.setCedula(fila.getString(1));
                obj.setNombre(fila.getString(2));
                obj.setApellido(fila.getString(3));
                obj.setDireccion(fila.getString(4));
                obj.setTelefono(fila.getString(5));
                obj.setCargo(fila.getString(6));
                obj.setUsuario(fila.getString(7));
                obj.setClave(fila.getString(8));
                obj.setNuevo(fila.getString(9));
                obj.setEstado(fila.getString(10));

                listaUsuarios.add(obj);
                obj = null;
            }
            return listaUsuarios;
        }catch(Exception ex){
            return null;
        }
    }
    public List<SegUsuario> getAllUsuariosSQLite(Context context){
        try{
            List<SegUsuario> listaUsuarios = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();

            String cadena = "select id_usuario,cedula,nombre,apellido,direccion,telefono," +
                    "cargo,usuario,clave,nuevo,estado from " + BaseDatos.TABLA_USUARIO;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                SegUsuario obj = new SegUsuario();
                obj.setIdUsuario(fila.getInt(0));
                obj.setCedula(fila.getString(1));
                obj.setNombre(fila.getString(2));
                obj.setApellido(fila.getString(3));
                obj.setDireccion(fila.getString(4));
                obj.setTelefono(fila.getString(5));
                obj.setCargo(fila.getString(6));
                obj.setUsuario(fila.getString(7));
                obj.setClave(fila.getString(8));
                obj.setNuevo(fila.getString(9));
                obj.setEstado(fila.getString(10));

                listaUsuarios.add(obj);
                obj = null;
            }
            return listaUsuarios;
        }catch(Exception ex){
            return null;
        }
    }
    public List<SegUsuario> getAllUsuariosPostgres(Context context){
        try{
            List<SegUsuario> listaUsuarios = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_USUARIO;
            ResultSet resultSet = db.select(cadena);
            if (resultSet != null){
                while (resultSet.next()){
                    SegUsuario obj = new SegUsuario();
                    obj.setIdUsuario(resultSet.getInt("id_usuario"));
                    obj.setCedula(resultSet.getString("cedula"));
                    obj.setNombre(resultSet.getString("nombre"));
                    obj.setApellido(resultSet.getString("apellido"));
                    obj.setDireccion(resultSet.getString("direccion"));
                    obj.setTelefono(resultSet.getString("telefono"));
                    obj.setCargo(resultSet.getString("cargo"));
                    obj.setUsuario(resultSet.getString("usuario"));
                    obj.setClave(resultSet.getString("clave"));
                    obj.setNuevo(resultSet.getString("nuevo"));
                    obj.setEstado(resultSet.getString("estado"));
                    listaUsuarios.add(obj);
                    obj = null;
                }
            }
            return listaUsuarios;
        }catch(Exception ex){
            return null;
        }
    }


    //*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //insert
    public boolean insertRecordSQLite(SegUsuario usuario, Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_usuario",usuario.getIdUsuario());
            registro.put("cedula",usuario.getCedula());
            registro.put("nombre",usuario.getNombre());
            registro.put("apellido",usuario.getApellido());
            registro.put("direccion",usuario.getDireccion());
            registro.put("telefono",usuario.getTelefono());
            registro.put("cargo",usuario.getCargo());
            registro.put("usuario",usuario.getUsuario());
            registro.put("clave",usuario.getClave());
            registro.put("nuevo",usuario.getNuevo());
            registro.put("estado",usuario.getEstado());
            baseDatos.insert(BaseDatos.TABLA_USUARIO,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(SegUsuario usuario,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("cedula",usuario.getCedula());
            registro.put("nombre",usuario.getNombre());
            registro.put("apellido",usuario.getApellido());
            registro.put("direccion",usuario.getDireccion());
            registro.put("telefono",usuario.getTelefono());
            registro.put("cargo",usuario.getCargo());
            registro.put("usuario",usuario.getUsuario());
            registro.put("clave",usuario.getClave());
            registro.put("nuevo",usuario.getNuevo());
            registro.put("estado",usuario.getEstado());
            baseDatos.update(BaseDatos.TABLA_USUARIO,registro," id_usuario = " + usuario.getIdUsuario(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(SegUsuario usuario,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            baseDatos.delete(BaseDatos.TABLA_USUARIO," id_usuario = " + usuario.getIdUsuario(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
