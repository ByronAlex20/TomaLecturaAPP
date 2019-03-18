package jaapz.com.app_jaapz.pojos;

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

public class PlanillaDAO {
    public List<Planilla> getAllPlanillasSQLite(Context context){
        try{
            List<Planilla> listaPlanillas = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_planilla,id_cuenta,id_apertura,usuario_crea,fecha,lectura_actual," +
                    "lectura_anterior,consumo_minimo,consumo,total_pagar,total_letras,convenio,cancelado," +
                    "latitud,longitud,fecha_ingreso,estado,origen,observaciones,ident_instalacion,estado_toma from " + BaseDatos.TABLA_PLANILLA;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                Planilla obj = new Planilla();
                obj.setIdPlanilla(fila.getInt(0));
                obj.setIdCuenta(fila.getInt(1));
                obj.setIdApertura(fila.getInt(2));
                obj.setUsuarioCrea(fila.getInt(3));
                if(fila.getString(4) != null)
                    obj.setFecha(Date.valueOf(fila.getString(4)));
                obj.setLecturaActual(fila.getInt(5));
                obj.setLecturaAnterior(fila.getInt(6));
                obj.setConsumoMinimo(fila.getInt(7));
                obj.setConsumo(fila.getInt(8));
                obj.setTotalPagar(fila.getDouble(9));
                obj.setTotalLetras(fila.getString(10));
                obj.setConvenio(fila.getString(11));
                obj.setCancelado(fila.getString(12));
                obj.setLatitud(fila.getString(13));
                obj.setLatitud(fila.getString(14));
                if(fila.getString(15) != null)
                    obj.setFechaIngreso(Date.valueOf(fila.getString(15)));
                obj.setEstado(fila.getString(16));
                obj.setOrigen(fila.getString(17));
                obj.setObservaciones(fila.getString(18));
                obj.setIdentInstalacion(fila.getString(19));
                obj.setEstadoToma(fila.getString(20));
                listaPlanillas.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaPlanillas;
        }catch(Exception ex){
            Toast.makeText(context, "SQLite: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public List<Planilla> getAllPlanillasPostgres(Context context,Integer idApertura){
        try{
            List<Planilla> listaPlanillas = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_PLANILLA + " where id_apertura = " + idApertura;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    Planilla obj = new Planilla();
                    obj.setIdPlanilla(fila.getInt("id_planilla"));
                    obj.setIdCuenta(fila.getInt("id_cuenta"));
                    obj.setIdApertura(fila.getInt("id_apertura"));
                    obj.setUsuarioCrea(fila.getInt("usuario_crea"));
                    if(fila.getString("fecha") != null)
                        obj.setFecha(Date.valueOf(fila.getString("fecha")));
                    obj.setLecturaActual(fila.getInt("lectura_actual"));
                    obj.setLecturaAnterior(fila.getInt("lectura_anterior"));
                    obj.setConsumoMinimo(fila.getInt("consumo_minimo"));
                    obj.setConsumo(fila.getInt("consumo"));
                    obj.setTotalPagar(fila.getDouble("total_pagar"));
                    obj.setTotalLetras(fila.getString("total_letras"));
                    obj.setConvenio(fila.getString("convenio"));
                    obj.setCancelado(fila.getString("cancelado"));
                    obj.setLatitud(fila.getString("latitud"));
                    obj.setLatitud(fila.getString("longitud"));
                    /*if(fila.getString("fecha_ingreso") != null)
                        obj.setFechaIngreso(fila.getDate("fecha_ingreso"));
                    */
                    obj.setEstado(fila.getString("estado"));
                    obj.setOrigen("origen");
                    obj.setObservaciones(fila.getString("observaciones"));
                    obj.setIdentInstalacion(fila.getString("ident_instalacion"));
                    if(fila.getInt("consumo") > 0)
                        obj.setEstadoToma(Constantes.ESTADO_TOMA_REALIZADO);

                    listaPlanillas.add(obj);
                    obj = null;
                }
            }
            return listaPlanillas;
        }catch(Exception ex){
            Toast.makeText(context, "Postgres: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(Planilla planilla, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_planilla",planilla.getIdPlanilla());
            registro.put("id_cuenta",planilla.getIdCuenta());
            registro.put("id_apertura",planilla.getIdApertura());
            registro.put("usuario_crea",planilla.getUsuarioCrea());
            if(planilla.getFecha() != null)
                registro.put("fecha",String.valueOf(planilla.getFecha()));
            registro.put("lectura_actual",planilla.getLecturaActual());
            registro.put("lectura_anterior",planilla.getLecturaAnterior());
            registro.put("consumo_minimo",planilla.getConsumoMinimo());
            registro.put("consumo",planilla.getConsumo());
            registro.put("total_pagar",planilla.getTotalPagar());
            registro.put("total_letras",planilla.getTotalLetras());
            registro.put("convenio",planilla.getConvenio());
            registro.put("cancelado",planilla.getCancelado());
            registro.put("latitud",planilla.getLatitud());
            registro.put("longitud",planilla.getLongitud());
            registro.put("origen",planilla.getOrigen());
            if(planilla.getFechaIngreso() != null)
                registro.put("fecha_ingreso",String.valueOf(planilla.getFechaIngreso()));
            registro.put("estado",planilla.getEstado());
            registro.put("observaciones",planilla.getObservaciones());
            registro.put("ident_instalacion",planilla.getIdentInstalacion());
            registro.put("estado_toma",planilla.getEstadoToma());
            baseDatos.insert(BaseDatos.TABLA_PLANILLA,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(Planilla planilla,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_cuenta",planilla.getIdCuenta());
            registro.put("id_apertura",planilla.getIdApertura());
            registro.put("usuario_crea",planilla.getUsuarioCrea());
            if(planilla.getFecha() != null)
                registro.put("fecha",String.valueOf(planilla.getFecha()));
            registro.put("lectura_actual",planilla.getLecturaActual());
            registro.put("lectura_anterior",planilla.getLecturaAnterior());
            registro.put("consumo_minimo",planilla.getConsumoMinimo());
            registro.put("consumo",planilla.getConsumo());
            registro.put("total_pagar",planilla.getTotalPagar());
            registro.put("total_letras",planilla.getTotalLetras());
            registro.put("convenio",planilla.getConvenio());
            registro.put("cancelado",planilla.getCancelado());
            registro.put("latitud",planilla.getLatitud());
            registro.put("longitud",planilla.getLongitud());
            registro.put("origen",planilla.getOrigen());
            if(planilla.getFechaIngreso() != null)
                registro.put("fecha_ingreso",String.valueOf(planilla.getFechaIngreso()));
            registro.put("estado",planilla.getEstado());
            registro.put("observaciones",planilla.getObservaciones());
            registro.put("ident_instalacion",planilla.getIdentInstalacion());
            registro.put("estado_toma",planilla.getEstadoToma());
            baseDatos.update(BaseDatos.TABLA_PLANILLA,registro," id_planilla = " + planilla.getIdPlanilla(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(Planilla planilla,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_PLANILLA," id_planilla = " + planilla.getIdPlanilla(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
