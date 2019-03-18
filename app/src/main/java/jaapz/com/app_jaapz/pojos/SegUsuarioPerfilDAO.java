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

public class SegUsuarioPerfilDAO {
    public List<SegUsuarioPerfil> getPerfilesUsuarioSQLite(Context context, Integer idUsuario){
        try{
            List<SegUsuarioPerfil> listaUsuarios = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context, Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();

            String cadena = "select id_usuario_perfil,id_perfil,id_usuario,estado from " + BaseDatos.TABLA_USUARIO_PERFIL;// + "" +
                    //" where id_usuario = " + idUsuario + " and estado = " + Constantes.EST_ACTIVO;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                SegUsuarioPerfil obj = new SegUsuarioPerfil();
                obj.setIdUsuarioPerfil(fila.getInt(0));
                obj.setIdPerfil(fila.getInt(1));
                obj.setIdUsuario(fila.getInt(2));
                obj.setEstado(fila.getString(3));
                listaUsuarios.add(obj);
            }
            return listaUsuarios;
        }catch(Exception ex){
            return null;
        }
    }

    public List<SegUsuarioPerfil> getAllUsuariosPerfilSQLite(Context context){
        try{
            List<SegUsuarioPerfil> listaUsuarios = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context, Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();

            String cadena = "select id_usuario_perfil,id_perfil,id_usuario,estado from " + BaseDatos.TABLA_USUARIO_PERFIL;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                SegUsuarioPerfil obj = new SegUsuarioPerfil();
                obj.setIdUsuarioPerfil(fila.getInt(0));
                obj.setIdPerfil(fila.getInt(1));
                obj.setIdUsuario(fila.getInt(2));
                obj.setEstado(fila.getString(3));
                listaUsuarios.add(obj);
            }
            return listaUsuarios;
        }catch(Exception ex){
            return null;
        }
    }
    public List<SegUsuarioPerfil> getAllUsuariosPerfilPostgres(Context context){
        try{
            List<SegUsuarioPerfil> listaUsuarios = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_USUARIO_PERFIL;
            ResultSet resultSet = db.select(cadena);
            if (resultSet != null){
                while (resultSet.next()){
                    SegUsuarioPerfil obj = new SegUsuarioPerfil();
                    obj.setIdUsuario(resultSet.getInt("id_usuario"));
                    obj.setEstado(resultSet.getString("estado"));
                    obj.setIdPerfil(resultSet.getInt("id_perfil"));
                    obj.setIdUsuarioPerfil(resultSet.getInt("id_usuario_perfil"));
                    listaUsuarios.add(obj);
                }
            }
            return listaUsuarios;
        }catch(Exception ex){
            return null;
        }
    }


    //*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //insert
    public boolean insertRecordSQLite(SegUsuarioPerfil usuario, Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_usuario_perfil",usuario.getIdUsuarioPerfil());
            registro.put("id_usuario",usuario.getIdUsuario());
            registro.put("id_perfil",usuario.getIdPerfil());
            registro.put("estado",usuario.getEstado());
            baseDatos.insert(BaseDatos.TABLA_USUARIO_PERFIL,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(SegUsuarioPerfil usuario,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_usuario",usuario.getIdUsuario());
            registro.put("id_perfil",usuario.getIdPerfil());
            registro.put("estado",usuario.getEstado());
            baseDatos.update(BaseDatos.TABLA_USUARIO_PERFIL,registro," id_usuario_perfil = " + usuario.getIdUsuarioPerfil(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(SegUsuarioPerfil usuario,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            baseDatos.delete(BaseDatos.TABLA_USUARIO_PERFIL," id_usuario_perfil = " + usuario.getIdUsuarioPerfil(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
