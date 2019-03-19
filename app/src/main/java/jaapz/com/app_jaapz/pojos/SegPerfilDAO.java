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

public class SegPerfilDAO {
    public List<SegPerfil> getAllPerfilesById(Integer id,Context context){
        try{
            List<SegPerfil> listaPerfiles = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_perfil,nombre,descripcion,estado from " + BaseDatos.TABLA_PERFIL + " where id_perfil = " + id;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                SegPerfil obj = new SegPerfil();
                obj.setIdPerfil(fila.getInt(0));
                obj.setNombre(fila.getString(1));
                obj.setDescripcion(fila.getString(2));
                obj.setEstado(fila.getString(3));
                listaPerfiles.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaPerfiles;
        }catch(Exception ex){
            return null;
        }
    }

    public List<SegPerfil> getAllPerfilesSQLite(Context context){
        try{
            List<SegPerfil> listaPerfiles = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_perfil,nombre,descripcion,estado from " + BaseDatos.TABLA_PERFIL;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                SegPerfil obj = new SegPerfil();
                obj.setIdPerfil(fila.getInt(0));
                obj.setNombre(fila.getString(1));
                obj.setDescripcion(fila.getString(2));
                obj.setEstado(fila.getString(3));
                listaPerfiles.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaPerfiles;
        }catch(Exception ex){
            return null;
        }
    }
    public List<SegPerfil> getAllPerfilesPostgres(Context context){
        try{
            List<SegPerfil> listaPerfiles = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_PERFIL;
            ResultSet resultSet = db.select(cadena);
            if (resultSet != null){
                while (resultSet.next()){
                    SegPerfil obj = new SegPerfil();
                    obj.setIdPerfil(resultSet.getInt("id_perfil"));
                    obj.setNombre(resultSet.getString("nombre"));
                    obj.setDescripcion(resultSet.getString("descripcion"));
                    obj.setEstado(resultSet.getString("estado"));
                    listaPerfiles.add(obj);
                    obj = null;
                }
            }
            return listaPerfiles;
        }catch(Exception ex){
            return null;
        }
    }

    //*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //insert
    public boolean insertRecordSQLite(SegPerfil perfil, Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_perfil",perfil.getIdPerfil());
            registro.put("nombre",perfil.getNombre());
            registro.put("descripcion",perfil.getDescripcion());
            registro.put("estado",perfil.getEstado());
            baseDatos.insert(BaseDatos.TABLA_PERFIL,null,registro);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(SegPerfil perfil,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            //registro.put("id_perfil",String.valueOf(et_ip_servidor.getText()));
            registro.put("nombre",perfil.getNombre());
            registro.put("descripcion",perfil.getDescripcion());
            registro.put("estado",perfil.getEstado());
            baseDatos.update(BaseDatos.TABLA_PERFIL,registro," id_perfil = " + perfil.getIdPerfil(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(SegPerfil perfil,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            baseDatos.delete(BaseDatos.TABLA_PERFIL," id_perfil = " + perfil.getIdPerfil(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
