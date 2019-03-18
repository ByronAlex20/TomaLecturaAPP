package jaapz.com.app_jaapz.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jaapz.com.app_jaapz.pojos.SegPerfil;

public class ConexionPostgreSQL extends _Default implements Runnable {
    private Connection conn;
    private String ip;
    private String db;
    private int port;
    private String user;
    private String pass;
    private String url = "jdbc:postgresql://%s:%d/%s";

    public ConexionPostgreSQL(Context context) {
        super();
        cargarDatosConexion(context);
        this.url = String.format(this.url,this.ip, this.port, this.db);
        this.conecta();
        this.desconecta();
    }

    public void cargarDatosConexion(Context context){
        try{
            ConexionSQLiteC cnn = new ConexionSQLiteC(context,Constantes.NOMBRE_BD_CONFIGURACION,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select ip,puerto_pg,bd_pg,usuario,password from " + BaseDatos.TABLA_CONFIGURACIONES;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                ip = fila.getString(0);
                port = fila.getInt(1);
                db = fila.getString(2);
                user = fila.getString(3);
                pass = fila.getString(4);
            }
        }catch(Exception ex){
        }
    }

    @Override
    public void run() {
        try{
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(this.url,this.user,this.pass);
        }catch (Exception e){
            this._mensagem = e.getMessage();
            this._status = false;
        }
    }

    private void conecta(){
        Thread thread = new Thread(this);
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            this._mensagem = e.getMessage();
            this._status = false;
        }
    }

    private void desconecta(){
        if (this.conn!= null){
            try{
                this.conn.close();
            }catch (Exception e){

            }finally {
                this.conn = null;
            }
        }
    }
    //para ejecutar consulta con respuesta
    public ResultSet select(String query){
        this.conecta();
        ResultSet resultSet = null;
        try {
            resultSet = new ExecutePostgreSQL(this.conn, query).execute().get();
        }catch (Exception e){
            this._status = false;
            this._mensagem = e.getMessage();
        }
        return resultSet;
    }

    //para ejecutar una consulta sin repuesta
    public ResultSet execute(String query){
        this.conecta();
        ResultSet resultSet = null;
        try {
            resultSet = new ExecutePostgreSQL(this.conn, query).execute().get();
        }catch (Exception e){
            this._status = false;
            this._mensagem = e.getMessage();
        }
        return resultSet;
    }
}
