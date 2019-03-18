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

public class MesDAO {
    public List<Mes> getAllMesesSQLite(Context context){
        try{
            List<Mes> listaMeses = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_mes,descripcion,estado from " + BaseDatos.TABLA_MES;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                Mes obj = new Mes();
                obj.setIdMes(fila.getInt(0));
                obj.setDescripcion(fila.getString(1));
                obj.setEstado(fila.getString(2));
                listaMeses.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaMeses;
        }catch(Exception ex){
            return null;
        }
    }

    public List<Mes> getAllMesesPostgres(Context context){
        try{
            List<Mes> listaMeses = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_MES;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    Mes obj = new Mes();
                    obj.setIdMes(fila.getInt("id_mes"));
                    obj.setDescripcion(fila.getString("descripcion"));
                    obj.setEstado(fila.getString("estado"));
                    listaMeses.add(obj);
                    obj = null;
                }
            }
            return listaMeses;
        }catch(Exception ex){
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(Mes mes, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_mes",mes.getIdMes());
            registro.put("descripcion",mes.getDescripcion());
            registro.put("estado",mes.getEstado());
            baseDatos.insert(BaseDatos.TABLA_MES,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(Mes mes,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("descripcion",mes.getDescripcion());
            registro.put("estado",mes.getEstado());
            baseDatos.update(BaseDatos.TABLA_MES,registro," id_mes = " + mes.getIdMes(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(Mes mes,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_MES," id_mes = " + mes.getIdMes(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
