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

public class MedidorDAO {
    public List<Medidor> getAllMedidoresSQLite(Context context){
        try{
            List<Medidor> listaMedidor = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_medidor,id_estado,usuario_crea,codigo,marca,modelo,estado from " + BaseDatos.TABLA_MEDIDOR;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                Medidor obj = new Medidor();
                obj.setIdMedidor(fila.getInt(0));
                obj.setIdEstadoMedidor(fila.getInt(1));
                obj.setUsuarioCrea(fila.getInt(2));
                obj.setCodigo(fila.getString(3));
                obj.setMarca(fila.getString(4));
                obj.setModelo(fila.getString(5));
                obj.setEstado(fila.getString(6));
                listaMedidor.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaMedidor;
        }catch(Exception ex){
            return null;
        }
    }

    public List<Medidor> getAllMedidoresPostgres(Context context){
        try{
            List<Medidor> listaMedidor = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_MEDIDOR;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    Medidor obj = new Medidor();
                    obj.setIdMedidor(fila.getInt("id_medidor"));
                    obj.setIdEstadoMedidor(fila.getInt("id_estado"));
                    obj.setUsuarioCrea(fila.getInt("usuario_crea"));
                    obj.setCodigo(fila.getString("codigo"));
                    obj.setMarca(fila.getString("marca"));
                    obj.setModelo(fila.getString("modelo"));
                    obj.setEstado(fila.getString("estado"));
                    listaMedidor.add(obj);
                    obj = null;
                }
            }
            return listaMedidor;
        }catch(Exception ex){
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(Medidor medidor, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_medidor",medidor.getIdMedidor());
            registro.put("id_estado",medidor.getIdEstadoMedidor());
            registro.put("usuario_crea",medidor.getUsuarioCrea());
            registro.put("codigo",medidor.getCodigo());
            registro.put("marca",medidor.getMarca());
            registro.put("modelo",medidor.getModelo());
            registro.put("estado",medidor.getEstado());
            baseDatos.insert(BaseDatos.TABLA_MEDIDOR,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(Medidor medidor,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_estado",medidor.getIdEstadoMedidor());
            registro.put("usuario_crea",medidor.getUsuarioCrea());
            registro.put("codigo",medidor.getCodigo());
            registro.put("marca",medidor.getMarca());
            registro.put("modelo",medidor.getModelo());
            registro.put("estado",medidor.getEstado());
            baseDatos.update(BaseDatos.TABLA_MEDIDOR,registro," id_medidor = " + medidor.getIdMedidor(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(Medidor medidor,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_MEDIDOR," id_medidor = " + medidor.getIdMedidor(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
