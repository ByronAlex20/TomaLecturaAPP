package jaapz.com.app_jaapz.pojos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;

public class BarrioDAO {
    public ArrayList<Barrio> getAllBarriosAsignadosSQLite(Context context){
        try{
            ArrayList<Barrio> listaBarrios = new ArrayList<Barrio>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select b.id_barrio,b.nombre,b.descripcion,b.estado from " +
                    BaseDatos.TABLA_BARRIO + " b, " + BaseDatos.TABLA_RESPONSABLE_LECTURA + " r, " + BaseDatos.TABLA_APERTURA_LECTURA + " a " +
                    "where r.id_apertura = a.id_apertura and r.id_barrio = b.id_barrio and a.estado_apertura = '" + Constantes.EST_APERTURA_PROCESO
                    + "' and r.id_usuario = " + ContextGlobal.getInstance().getIdUsuarioLogeado();
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                Barrio obj = new Barrio();
                obj.setIdBarrio(fila.getInt(0));
                obj.setNombre(fila.getString(1));
                obj.setDescripcion(fila.getString(2));
                obj.setEstado(fila.getString(3));
                listaBarrios.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaBarrios;
        }catch(Exception ex){
            return null;
        }
    }

    public List<Barrio> getAllBarriosPostgres(Context context){
        try{
            List<Barrio> listaBarrios = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_BARRIO;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    Barrio obj = new Barrio();
                    obj.setIdBarrio(fila.getInt("id_barrio"));
                    obj.setNombre(fila.getString("nombre"));
                    obj.setDescripcion(fila.getString("descripcion"));
                    obj.setEstado(fila.getString("estado"));
                    listaBarrios.add(obj);
                    obj = null;
                }
            }
            return listaBarrios;
        }catch(Exception ex){
            return null;
        }
    }
    public List<Barrio> getAllBarriosSQLite(Context context){
        try{
            List<Barrio> listaBarrios = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_barrio,nombre,descripcion,estado from " + BaseDatos.TABLA_BARRIO;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                Barrio obj = new Barrio();
                obj.setIdBarrio(fila.getInt(0));
                obj.setNombre(fila.getString(1));
                obj.setDescripcion(fila.getString(2));
                obj.setEstado(fila.getString(3));
                listaBarrios.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaBarrios;
        }catch(Exception ex){
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(Barrio barrio, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_barrio",barrio.getIdBarrio());
            registro.put("nombre",barrio.getNombre());
            registro.put("descripcion",barrio.getDescripcion());
            registro.put("estado",barrio.getEstado());
            baseDatos.insert(BaseDatos.TABLA_BARRIO,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(Barrio barrio,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            //registro.put("id_perfil",String.valueOf(et_ip_servidor.getText()));
            registro.put("nombre",barrio.getNombre());
            registro.put("descripcion",barrio.getDescripcion());
            registro.put("estado",barrio.getEstado());
            baseDatos.update(BaseDatos.TABLA_BARRIO,registro," id_barrio = " + barrio.getIdBarrio(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(Barrio barrio,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_BARRIO," id_barrio = " + barrio.getIdBarrio(),null);
            //Toast.makeText(context, "Perfil Eliminado!!", Toast.LENGTH_SHORT).show();
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
