package jaapz.com.app_jaapz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jaapz.com.app_jaapz.pojos.CuentaClienteDAO;

public class AdaptadorBarrio extends BaseAdapter {

    private ArrayList<EntidadBarrio> listBarrio;
    private Context context;

    public AdaptadorBarrio(ArrayList<EntidadBarrio> listBarrio, Context context) {
        this.listBarrio = listBarrio;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listBarrio.size();
    }

    @Override
    public Object getItem(int position) {
        return listBarrio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listBarrio.get(position).getIdBarrio();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CuentaClienteDAO clienteDAO = new CuentaClienteDAO();
        EntidadBarrio item = (EntidadBarrio) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_adptador_barrios,null);
        TextView tvBarrioNombre = (TextView) convertView.findViewById(R.id.tvBarrioNombre);
        TextView tvCantidadClientes = (TextView) convertView.findViewById(R.id.tvCantidadClientes);
        tvBarrioNombre.setText(item.getNombreBarrio());
        String cantidad = "No. Clientes: " + clienteDAO.getCantidadClientesBarrio(item.getIdBarrio(),context);
        tvCantidadClientes.setText(cantidad);
        return convertView;
    }
}
