package jaapz.com.app_jaapz.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLite extends SQLiteOpenHelper{

    public ConexionSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BaseDatos.CREAR_TABLA_CONFIGURACIONES);
        db.execSQL(BaseDatos.INSERT_TABLA_CONFIGURACIONES);
        db.execSQL(BaseDatos.CREAR_TABLA_APERTURA_LECTURA);
        db.execSQL(BaseDatos.CREAR_TABLA_PERFIL);
        db.execSQL(BaseDatos.CREAR_TABLA_USUARIO);
        db.execSQL(BaseDatos.CREAR_TABLA_ANIO);
        db.execSQL(BaseDatos.CREAR_TABLA_BARRIO);
        db.execSQL(BaseDatos.CREAR_TABLA_CLIENTE);
        db.execSQL(BaseDatos.CREAR_TABLA_CUENTA_CLIENTE);
        db.execSQL(BaseDatos.CREAR_TABLA_MEDIDOR);
        db.execSQL(BaseDatos.CREAR_TABLA_MES);
        db.execSQL(BaseDatos.CREAR_TABLA_PLANILLA);
        db.execSQL(BaseDatos.CREAR_TABLA_RESPONSABLE_LECTURA);
        db.execSQL(BaseDatos.CREAR_TABLA_PLANILLA_DETALLE);
        db.execSQL(BaseDatos.CREAR_TABLA_REGISTRO_FOTOGRAFICO);
        db.execSQL(BaseDatos.CREAR_TABLA_USUARIO_PERFIL);
        /*
        db.execSQL(BaseDatos.CREAR_TABLA_CATEGORIA);
        db.execSQL(BaseDatos.CREAR_TABLA_CICLO_FACTURACION);
        db.execSQL(BaseDatos.CREAR_TABLA_CONVENIO);
        db.execSQL(BaseDatos.CREAR_TABLA_CONVENIO_PLANILLA);
        db.execSQL(BaseDatos.CREAR_TABLA_DETALLE_CONVENIO);
        db.execSQL(BaseDatos.CREAR_TABLA_DETALLE_FACTURA);
        db.execSQL(BaseDatos.CREAR_TABLA_DETALLE_INGRESO);
        db.execSQL(BaseDatos.CREAR_TABLA_DETALLE_PLANILLA);
        db.execSQL(BaseDatos.CREAR_TABLA_DETALLE_REQUERIMIENTO);
        db.execSQL(BaseDatos.CREAR_TABLA_ESTADO_MEDIDOR);
        db.execSQL(BaseDatos.CREAR_TABLA_FACTURA);
        db.execSQL(BaseDatos.CREAR_TABLA_INGRESO);
        db.execSQL(BaseDatos.CREAR_TABLA_MATERIAL);
        db.execSQL(BaseDatos.CREAR_TABLA_ORDEN_DESPACHO);
        db.execSQL(BaseDatos.CREAR_TABLA_ORDEN_DETALLE);
        db.execSQL(BaseDatos.CREAR_TABLA_PAGO);
         db.execSQL(BaseDatos.CREAR_TABLA_REQUERIMIENTO);
        db.execSQL(BaseDatos.CREAR_TABLA_TIPO_MATERIAL);
        db.execSQL(BaseDatos.CREAR_TABLA_TIPO_PAGO);
        db.execSQL(BaseDatos.CREAR_TABLA_TIPO_REQUERIMIENTO);
        db.execSQL(BaseDatos.CREAR_TABLA_TIPO_RUBRO);
        db.execSQL(BaseDatos.CREAR_TABLA_EMPRESA);
        db.execSQL(BaseDatos.CREAR_TABLA_MENU);
        db.execSQL(BaseDatos.CREAR_TABLA_NUMERO_FACTURA);
        db.execSQL(BaseDatos.CREAR_TABLA_NUMERO_UTILIZADOS);
        db.execSQL(BaseDatos.CREAR_TABLA_PERMISO);
        db.execSQL(BaseDatos.CREAR_TABLA_AUDITORIA);
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CONFIGURACIONES);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_PERFIL);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_APERTURA_LECTURA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_ANIO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_BARRIO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CUENTA_CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_MEDIDOR);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_MES);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_PLANILLA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_RESPONSABLE_LECTURA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_PLANILLA_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_REGISTRO_FOTOGRAFICO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_USUARIO_PERFIL);
        /*
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CICLO_FACTURACION);
          db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CONVENIO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CONVENIO_PLANILLA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_DETALLE_CONVENIO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_DETALLE_FACTURA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_DETALLE_INGRESO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_DETALLE_PLANILLA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_DETALLE_REQUERIMIENTO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_ESTADO_MEDIDOR);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_FACTURA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_INGRESO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_MATERIAL);
         db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_ORDEN_DESPACHO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_ORDEN_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_PAGO);
           db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_REQUERIMIENTO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_TIPO_MATERIAL);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_TIPO_PAGO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_TIPO_REQUERIMIENTO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_TIPO_RUBRO);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_NUMERO_FACTURA);
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_NUMERO_UTILIZADOS);

        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_PERMISO);

        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_AUDITORIA);
        */
        onCreate(db);
    }
}
