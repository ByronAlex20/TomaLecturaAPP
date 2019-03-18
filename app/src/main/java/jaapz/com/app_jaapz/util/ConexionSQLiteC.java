package jaapz.com.app_jaapz.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteC extends SQLiteOpenHelper {
    public ConexionSQLiteC(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BaseDatos.CREAR_TABLA_CONFIGURACIONES);
        db.execSQL(BaseDatos.INSERT_TABLA_CONFIGURACIONES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + BaseDatos.TABLA_CONFIGURACIONES);
        onCreate(db);
    }
}
