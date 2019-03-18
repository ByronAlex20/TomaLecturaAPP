package jaapz.com.app_jaapz.pojos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;

public class ResponsableLecturaDAO {
    public List<ResponsableLectura> getAllResponsablesSQLite(Context context){
        try{
            List<ResponsableLectura> listaResponsables = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_responsable,id_usuario,id_barrio,id_apertura,usuario_crea,fecha," +
                    "estado from " + BaseDatos.TABLA_RESPONSABLE_LECTURA;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                ResponsableLectura obj = new ResponsableLectura();
                obj.setIdResponsable(fila.getInt(0));
                obj.setIdUsuario(fila.getInt(1));
                obj.setIdBarrio(fila.getInt(2));
                obj.setIdApertura(fila.getInt(3));
                obj.setUsuarioCrea(fila.getInt(4));
                obj.setFecha(Date.valueOf(fila.getString(5)));
                obj.setEstado(fila.getString(6));
                listaResponsables.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaResponsables;
        }catch(Exception ex){
            return null;
        }
    }
    public List<ResponsableLectura> getAllResponsablesPostgres(Context context,Integer idApertura){
        try{
            List<ResponsableLectura> listaResponsables = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " where id_apertura = " + idApertura;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    ResponsableLectura obj = new ResponsableLectura();
                    obj.setIdResponsable(fila.getInt("id_responsable"));
                    obj.setIdUsuario(fila.getInt("id_usuario"));
                    obj.setIdBarrio(fila.getInt("id_barrio"));
                    obj.setIdApertura(fila.getInt("id_apertura"));
                    obj.setUsuarioCrea(fila.getInt("usuario_crea"));
                    obj.setFecha(fila.getDate("fecha"));
                    obj.setEstado(fila.getString("estado"));
                    listaResponsables.add(obj);
                    obj = null;
                }
            }
            return listaResponsables;
        }catch(Exception ex){
            return null;
        }
    }

    //*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //insert
    public boolean insertRecordSQLite(ResponsableLectura responsable, Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_responsable",responsable.getIdResponsable());
            registro.put("id_usuario",responsable.getIdUsuario());
            registro.put("id_barrio",responsable.getIdBarrio());
            registro.put("id_apertura",responsable.getIdApertura());
            registro.put("usuario_crea",responsable.getUsuarioCrea());
            registro.put("fecha",String.valueOf(responsable.getFecha()));
            registro.put("estado",responsable.getEstado());
            baseDatos.insert(BaseDatos.TABLA_RESPONSABLE_LECTURA,null,registro);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(ResponsableLectura responsable,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_usuario",responsable.getIdUsuario());
            registro.put("id_barrio",responsable.getIdBarrio());
            registro.put("id_apertura",responsable.getIdApertura());
            registro.put("usuario_crea",responsable.getUsuarioCrea());
            registro.put("fecha",String.valueOf(responsable.getFecha()));
            registro.put("estado",responsable.getEstado());
            baseDatos.update(BaseDatos.TABLA_RESPONSABLE_LECTURA,registro," id_responsable = " + responsable.getIdResponsable(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(ResponsableLectura responsable,Context context){
        try{
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            boolean bandera = false;
            baseDatos.delete(BaseDatos.TABLA_RESPONSABLE_LECTURA," id_responsable = " + responsable.getIdResponsable(),null);
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
