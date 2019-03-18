package jaapz.com.app_jaapz.util;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;

public class ExecutePostgreSQL  extends AsyncTask<String,Void,ResultSet> {
    private Connection connection;
    private String query;

    public ExecutePostgreSQL(Connection connection, String query) {
        this.connection = connection;
        this.query = query;
    }

    @Override
    protected ResultSet doInBackground(String... params) {
        ResultSet resultSet = null;
        try{
            resultSet = connection.prepareStatement(query).executeQuery();
        }catch (Exception e){

        }finally {
            try {
                connection.close();
            }catch (Exception ex){

            }
        }
        return resultSet;
    }
}