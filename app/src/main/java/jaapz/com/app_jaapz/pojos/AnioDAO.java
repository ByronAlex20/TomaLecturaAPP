package jaapz.com.app_jaapz.pojos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;

public class AnioDAO {
    public List<Anio> getAllAnioSQLite(Context context){
        try{
            List<Anio> listaAnio = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_anio,descripcion,estado from " + BaseDatos.TABLA_ANIO;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                Anio obj = new Anio();
                obj.setIdAnio(fila.getInt(0));
                obj.setDescripcion(fila.getString(1));
                obj.setEstado(fila.getString(2));
                listaAnio.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaAnio;
        }catch(Exception ex){
            return null;
        }
    }
    public List<Anio> getAllAnioPostgres(Context context){
        try{
            List<Anio> listaAnios = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_ANIO;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    Anio obj = new Anio();
                    obj.setIdAnio(fila.getInt("id_anio"));
                    obj.setDescripcion(fila.getString("descripcion"));
                    obj.setEstado(fila.getString("estado"));
                    listaAnios.add(obj);
                    obj = null;
                }
            }
            return listaAnios;
        }catch(Exception ex){
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(Anio anio, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_anio",anio.getIdAnio());
            registro.put("descripcion",anio.getDescripcion());
            registro.put("estado",anio.getEstado());
            baseDatos.insert(BaseDatos.TABLA_ANIO,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(Anio anio,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            //registro.put("id_perfil",String.valueOf(et_ip_servidor.getText()));
            registro.put("descripcion",anio.getDescripcion());
            registro.put("estado",anio.getEstado());
            baseDatos.update(BaseDatos.TABLA_ANIO,registro," id_anio = " + anio.getIdAnio(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(Anio anio,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_ANIO," id_anio = " + anio.getIdAnio(),null);
            //Toast.makeText(context, "Perfil Eliminado!!", Toast.LENGTH_SHORT).show();
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

}
