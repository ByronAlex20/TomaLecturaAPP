package jaapz.com.app_jaapz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jaapz.com.app_jaapz.util.BaseDatos;
import jaapz.com.app_jaapz.util.ConexionPostgreSQL;
import jaapz.com.app_jaapz.util.ConexionSQLite;
import jaapz.com.app_jaapz.util.ConexionSQLiteC;
import jaapz.com.app_jaapz.util.Constantes;
import jaapz.com.app_jaapz.util.ContextGlobal;

public class ConfiguracionActivity extends AppCompatActivity {
    EditText et_base_sqlite;
    Integer _id = 0;
    EditText et_ip_servidor;
    EditText et_puerto_bd;
    EditText et_postgres_nombre;
    EditText et_usuario_bd;
    EditText et_clave_bd;

    Button btn_grabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        et_base_sqlite = (EditText)findViewById(R.id.et_base_sqlite);
        et_ip_servidor = (EditText)findViewById(R.id.et_ip_servidor);
        et_puerto_bd = (EditText)findViewById(R.id.et_puerto_bd);
        et_postgres_nombre = (EditText)findViewById(R.id.et_postgres_nombre);
        et_usuario_bd = (EditText)findViewById(R.id.et_usuario_bd);
        et_clave_bd = (EditText)findViewById(R.id.et_clave_bd);
        btn_grabar = (Button)findViewById(R.id.btn_grabar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cargarDatosSQLite();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void cargarDatosSQLite(){
        ConexionSQLiteC cnn = new ConexionSQLiteC(this, Constantes.NOMBRE_BD_CONFIGURACION,null,Constantes.VERSION_BD_SQLITE);
        SQLiteDatabase baseDatos = cnn.getReadableDatabase();
        try {
            Cursor fila = baseDatos.rawQuery("SELECT ip,puerto_pg,bd_pg,estado,id,usuario,password FROM configuracion",null);
            while(fila.moveToNext()){

                et_base_sqlite.setText(Constantes.NOMBRE_BD_SQLITE);
                et_puerto_bd.setText(fila.getString(1));
                et_postgres_nombre.setText(fila.getString(2));
                et_ip_servidor.setText(fila.getString(0));
                _id = fila.getInt(4);

                et_usuario_bd.setText(fila.getString(5));
                et_clave_bd.setText(fila.getString(6));
            }
            baseDatos.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void update(){
        try{
            ConexionSQLiteC cnn = new ConexionSQLiteC( this,Constantes.NOMBRE_BD_CONFIGURACION,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura

            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("ip",String.valueOf(et_ip_servidor.getText()));
            registro.put("puerto_pg",String.valueOf(et_puerto_bd.getText()));
            registro.put("bd_pg",String.valueOf(et_postgres_nombre.getText()));

            registro.put("usuario",String.valueOf(et_usuario_bd.getText()));
            registro.put("password",String.valueOf(et_clave_bd.getText()));

            registro.put("estado","A");
            baseDatos.update(BaseDatos.TABLA_CONFIGURACIONES,registro," id = " + _id,null);
            Toast.makeText(this, "Registrado", Toast.LENGTH_SHORT).show();
            baseDatos.close();
            Intent ListSong = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(ListSong);
            finish();
        }catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void grabar(View view){
        try{
            //Toast.makeText(this, _id, Toast.LENGTH_SHORT).show();
            update();
        }catch(Exception ex){

        }
    }
}

