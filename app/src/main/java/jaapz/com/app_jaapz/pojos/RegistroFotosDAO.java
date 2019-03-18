package jaapz.com.app_jaapz.pojos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.Constantes;

public class RegistroFotosDAO {
    //insert
    public boolean insertRecordSQLite(RegistroFotos registroFotos, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_registro",registroFotos.getIdRegistro());
            registro.put("id_planilla_det",registroFotos.getIdPlanillaDet());
            registro.put("usuario_crea",registroFotos.getUsuarioCrea());
            registro.put("foto",registroFotos.getFoto());
            registro.put("nombre_foto",registroFotos.getNombreFoto());
            registro.put("estado",registroFotos.getEstado());
            baseDatos.insert(BaseDatos.TABLA_REGISTRO_FOTOGRAFICO,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(RegistroFotos registroFotos,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_planilla_det",registroFotos.getIdPlanillaDet());
            registro.put("usuario_crea",registroFotos.getUsuarioCrea());
            registro.put("foto",registroFotos.getFoto());
            registro.put("nombre_foto",registroFotos.getNombreFoto());
            registro.put("estado",registroFotos.getEstado());
            baseDatos.update(BaseDatos.TABLA_REGISTRO_FOTOGRAFICO,registro," id_registro = " + registroFotos.getIdRegistro(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(RegistroFotos registroFotos,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_REGISTRO_FOTOGRAFICO," id_registro = " + registroFotos.getIdRegistro(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
