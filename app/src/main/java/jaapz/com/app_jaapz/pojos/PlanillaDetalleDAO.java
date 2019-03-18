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

public class PlanillaDetalleDAO {
    public List<PlanillaDetalle> getAllDetPlanillasSQLite(Context context){
        try{
            List<PlanillaDetalle> listaPlanillasDetalle = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_planilla_det,id_planilla,id_instalacion,id_reparacion,id_convenio_det,usuario_crea," +
                    "descripcion,cantidad,subtotal,estado,estado_sync,identificador_operacion from " + BaseDatos.TABLA_PLANILLA_DETALLE;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                PlanillaDetalle obj = new PlanillaDetalle();
                obj.setIdPlanillaDet(fila.getInt(0));
                obj.setIdPlanilla(fila.getInt(1));
                obj.setIdInstalacion(fila.getInt(2));
                obj.setIdReparacion(fila.getInt(3));
                obj.setIdConvenioDet(fila.getInt(4));
                obj.setUsuarioCrea(fila.getInt(5));
                obj.setDescripcion(fila.getString(6));
                obj.setCantidad(fila.getInt(7));
                obj.setSubtotal(fila.getDouble(8));
                obj.setEstado(fila.getString(9));
                obj.setEstadoSync(fila.getString(10));
                obj.setIdentificadorOperacion(fila.getString(11));
                listaPlanillasDetalle.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaPlanillasDetalle;
        }catch(Exception ex){
            Toast.makeText(context, "SQLite " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public List<PlanillaDetalle> getAllDetPlanillasPostgres(Context context,Integer idApertura){
        try{
            List<PlanillaDetalle> listaPlanillasDetalle = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select d.* from " + BaseDatos.TABLA_PLANILLA_DETALLE + " d, " + BaseDatos.TABLA_PLANILLA + " p " +
                    "where d.id_planilla = p.id_planilla and p.id_apertura = " + idApertura;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    PlanillaDetalle obj = new PlanillaDetalle();
                    obj.setIdPlanillaDet(fila.getInt("id_planilla_det"));

                    obj.setIdPlanilla(fila.getInt("id_planilla"));
                    obj.setIdInstalacion(fila.getInt("id_instalacion"));
                    obj.setIdReparacion(fila.getInt("id_reparacion"));
                    obj.setIdConvenioDet(fila.getInt("id_convenio_det"));
                    obj.setUsuarioCrea(fila.getInt("usuario_crea"));
                    obj.setDescripcion(fila.getString("descripcion"));
                    obj.setCantidad(fila.getInt("cantidad"));
                    obj.setSubtotal(fila.getDouble("subtotal"));
                    obj.setEstado(fila.getString("estado"));
                    obj.setIdentificadorOperacion(fila.getString("identificador_operacion"));
                    listaPlanillasDetalle.add(obj);
                    obj = null;
                }
            }
            return listaPlanillasDetalle;
        }catch(Exception ex){
            Toast.makeText(context, "Postgres " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    //insert
    public boolean insertRecordSQLite(PlanillaDetalle planillaDetalle, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_planilla_det",planillaDetalle.getIdPlanillaDet());
            registro.put("id_planilla",planillaDetalle.getIdPlanilla());
            registro.put("id_instalacion",planillaDetalle.getIdInstalacion());
            registro.put("id_reparacion",planillaDetalle.getIdReparacion());
            registro.put("id_convenio_det",planillaDetalle.getIdConvenioDet());
            registro.put("usuario_crea",planillaDetalle.getUsuarioCrea());
            registro.put("descripcion",planillaDetalle.getDescripcion());
            registro.put("cantidad",planillaDetalle.getCantidad());
            registro.put("subtotal",planillaDetalle.getSubtotal());
            registro.put("estado",planillaDetalle.getEstado());
            registro.put("estado_sync",planillaDetalle.getEstadoSync());
            registro.put("identificador_operacion",planillaDetalle.getIdentificadorOperacion());

            baseDatos.insert(BaseDatos.TABLA_PLANILLA_DETALLE,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(PlanillaDetalle planillaDetalle,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_planilla",planillaDetalle.getIdPlanilla());
            registro.put("id_instalacion",planillaDetalle.getIdInstalacion());
            registro.put("id_reparacion",planillaDetalle.getIdReparacion());
            registro.put("id_convenio_det",planillaDetalle.getIdConvenioDet());
            registro.put("usuario_crea",planillaDetalle.getUsuarioCrea());
            registro.put("descripcion",planillaDetalle.getDescripcion());
            registro.put("cantidad",planillaDetalle.getCantidad());
            registro.put("subtotal",planillaDetalle.getSubtotal());
            registro.put("estado",planillaDetalle.getEstado());
            registro.put("estado_sync",planillaDetalle.getEstadoSync());
            registro.put("identificador_operacion",planillaDetalle.getIdentificadorOperacion());
            baseDatos.update(BaseDatos.TABLA_PLANILLA_DETALLE,registro," id_planilla_det = " + planillaDetalle.getIdPlanillaDet(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //delete
    public boolean deleteRecordSQLite(PlanillaDetalle planillaDetalle,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_PLANILLA_DETALLE," id_planilla_det = " + planillaDetalle.getIdPlanillaDet(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
