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

public class ClienteDAO {
    public List<Cliente> getAllClientesSQLite(Context context){
        try{
            List<Cliente> listaCliente = new ArrayList<>();
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getReadableDatabase();
            String cadena = "select id_cliente,usuario_crea,cedula,nombres,apellidos,direccion," +
                    "telefono,genero,email,estado from " + BaseDatos.TABLA_CLIENTE;
            Cursor fila = baseDatos.rawQuery(cadena,null);
            while(fila.moveToNext()){
                Cliente obj = new Cliente();
                obj.setIdCliente(fila.getInt(0));
                obj.setUsuarioCrea(fila.getInt(1));
                obj.setCedula(fila.getString(2));
                obj.setNombres(fila.getString(3));
                obj.setApellidos(fila.getString(4));
                obj.setDireccion(fila.getString(5));
                obj.setTelefono(fila.getString(6));
                obj.setGenero(fila.getString(7));
                obj.setEmail(fila.getString(8));
                obj.setEstado(fila.getString(9));
                listaCliente.add(obj);
                obj = null;
            }
            baseDatos.close();
            return listaCliente;
        }catch(Exception ex){
            return null;
        }
    }
    public List<Cliente> getAllClientesPostgres(Context context){
        try{
            List<Cliente> listaClientes = new ArrayList<>();
            ConexionPostgreSQL db = new ConexionPostgreSQL(context);
            String cadena = "select * from " + BaseDatos.TABLA_CLIENTE;
            ResultSet fila = db.select(cadena);
            if (fila != null){
                while (fila.next()){
                    Cliente obj = new Cliente();
                    obj.setIdCliente(fila.getInt("id_cliente"));
                    obj.setUsuarioCrea(fila.getInt("usuario_crea"));
                    obj.setCedula(fila.getString("cedula"));
                    obj.setNombres(fila.getString("nombre"));
                    obj.setApellidos(fila.getString("apellido"));
                    obj.setDireccion(fila.getString("direccion"));
                    obj.setTelefono(fila.getString("telefono"));
                    obj.setGenero(fila.getString("genero"));
                    obj.setEmail(fila.getString("email"));
                    obj.setEstado(fila.getString("estado"));
                    listaClientes.add(obj);
                    obj = null;
                }
            }
            return listaClientes;
        }catch(Exception ex){
            return null;
        }
    }
    //insert
    public boolean insertRecordSQLite(Cliente cliente, Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite( context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            registro.put("id_cliente",cliente.getIdCliente());
            registro.put("usuario_crea",cliente.getUsuarioCrea());
            registro.put("cedula",cliente.getCedula());
            registro.put("nombres",cliente.getNombres());
            registro.put("apellidos",cliente.getApellidos());
            registro.put("direccion",cliente.getDireccion());
            registro.put("telefono",cliente.getTelefono());
            registro.put("genero",cliente.getGenero());
            registro.put("email",cliente.getEmail());
            registro.put("estado",cliente.getEstado());
            baseDatos.insert(BaseDatos.TABLA_CLIENTE,null,registro);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }

    //update
    public boolean updateRecordSQLite(Cliente cliente,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            //para realizar registros
            ContentValues registro = new ContentValues();
            //registro.put("id_perfil",String.valueOf(et_ip_servidor.getText()));
            registro.put("usuario_crea",cliente.getUsuarioCrea());
            registro.put("cedula",cliente.getCedula());
            registro.put("nombres",cliente.getNombres());
            registro.put("apellidos",cliente.getApellidos());
            registro.put("direccion",cliente.getDireccion());
            registro.put("telefono",cliente.getTelefono());
            registro.put("genero",cliente.getGenero());
            registro.put("email",cliente.getEmail());
            registro.put("estado",cliente.getEstado());
            baseDatos.update(BaseDatos.TABLA_CLIENTE,registro," id_cliente = " + cliente.getIdCliente(),null);
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
    //delete
    public boolean deleteRecordSQLite(Cliente cliente,Context context){
        try{
            boolean bandera = false;
            ConexionSQLite cnn = new ConexionSQLite(context,Constantes.NOMBRE_BD_SQLITE,null,Constantes.VERSION_BD_SQLITE);
            SQLiteDatabase baseDatos = cnn.getWritableDatabase();//abrela base de datos en modo lectura-escritura
            baseDatos.delete(BaseDatos.TABLA_CLIENTE," id_cliente = " + cliente.getIdCliente(),null);
            //Toast.makeText(context, "Perfil Eliminado!!", Toast.LENGTH_SHORT).show();
            baseDatos.close();
            bandera = true;
            return bandera;
        }catch(Exception ex){
            return false;
        }
    }
}
