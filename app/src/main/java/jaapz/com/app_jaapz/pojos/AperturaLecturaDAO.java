package jaapz.com.app_jaapz.pojos;

import android.animation.TimeAnimator;
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

public class AperturaLecturaDAO {
    public List<AperturaLectura> getAllAperturasSQLite(Context context){
        try{
            List<AperturaLectura> listaAperturaLectura = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_apertura,id_anio,id_mes,usuario_crea,fecha,observacion," +
                    "estado_apertura,estado from " + BaseDatos.TABLA_APERTURA_LECTURA;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                AperturaLectura obj = new AperturaLectura();
                obj.setIdApertura(fila.getInt(0));
                obj.setIdAnio(fila.getInt(1));
                obj.setIdMes(fila.getInt(2));
                obj.setUsuarioCrea(fila.getInt(3));
                obj.setFecha(Date.valueOf(fila.getString(4)));
                obj.setObservacion(fila.getString(5));
                obj.setEstadoApertura(fila.getString(6));
                obj.setEstado(fila.getString(7));
                listaAperturaLectura.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaAperturaLectura;
        }catch(Exception ex){
            Toast.makeText(context, "Error primero: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public List<AperturaLectura> getAllAperturasPostgres(Context context){
        try{
            List<AperturaLectura> listaAperturaLecturas = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_APERTURA_LECTURA + " where estado_apertura = '" + Constantes.EST_APERTURA_PROCESO + "' " +
                    "and estado = 'A'";
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    AperturaLectura obj = new AperturaLectura();
                    obj.setIdApertura(fila.getInt("id_apertura"));
                    obj.setIdAnio(fila.getInt("id_anio"));
                    obj.setIdMes(fila.getInt("id_mes"));
                    obj.setUsuarioCrea(fila.getInt("usuario_crea"));
                    obj.setFecha(fila.getDate("fecha"));
                    obj.setObservacion(fila.getString("observacion"));
                    obj.setEstadoApertura(fila.getString("estado_apertura"));
                    obj.setEstado(fila.getString("estado"));
                    listaAperturaLecturas.add(obj);
                    obj = null;
                }
            }
            return listaAperturaLecturas;
        }catch(Exception ex){
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(AperturaLectura aperturaLectura, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_apertura",aperturaLectura.getIdApertura());
            registro.put("id_anio", aperturaLectura.getIdAnio());
            registro.put("id_mes", aperturaLectura.getIdMes());
            registro.put("usuario_crea",aperturaLectura.getUsuarioCrea());
            registro.put("fecha",String.valueOf(aperturaLectura.getFecha()));
            registro.put("observacion", aperturaLectura.getObservacion());
            registro.put("estado_apertura",aperturaLectura.getEstadoApertura());
            registro.put("estado",aperturaLectura.getEstado());
            baseDatos.insert(BaseDatos.TABLA_APERTURA_LECTURA,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(AperturaLectura aperturaLectura,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            //registro.put("id_perfil",String.valueOf(et_ip_servidor.getText()));
            registro.put("id_anio", aperturaLectura.getIdAnio());
            registro.put("id_mes", aperturaLectura.getIdMes());
            registro.put("usuario_crea",aperturaLectura.getUsuarioCrea());
            registro.put("fecha",String.valueOf(aperturaLectura.getFecha()));
            registro.put("observacion", aperturaLectura.getObservacion());
            registro.put("estado_apertura",aperturaLectura.getEstadoApertura());
            registro.put("estado",aperturaLectura.getEstado());
            baseDatos.update(BaseDatos.TABLA_APERTURA_LECTURA,registro," id_apertura = " + aperturaLectura.getIdApertura(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(AperturaLectura aperturaLectura,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_APERTURA_LECTURA," id_apertura = " + aperturaLectura.getIdApertura(),null);
            //Toast.makeText(context, "Perfil Eliminado!!", Toast.LENGTH_SHORT).show();
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
