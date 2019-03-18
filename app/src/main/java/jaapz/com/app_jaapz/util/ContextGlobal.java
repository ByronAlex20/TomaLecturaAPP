package jaapz.com.app_jaapz.util;

import android.widget.ListView;

public class ContextGlobal {
    String mensajeDialogo = "";
    int idUsuarioLogeado;
    private final static ContextGlobal instance = new ContextGlobal();
    private ListView lvClientesBarrio;
    public static ContextGlobal getInstance() {
        return instance;
    }

    public String getMensajeDialogo() {
        return mensajeDialogo;
    }
    public void setMensajeDialogo(String mensajeDialogo) {
        this.mensajeDialogo = mensajeDialogo;
    }
    public int getIdUsuarioLogeado() {
        return idUsuarioLogeado;
    }
    public void setIdUsuarioLogeado(int idUsuarioLogeado) {
        this.idUsuarioLogeado = idUsuarioLogeado;
    }

    public ListView getLvClientesBarrio() {
        return lvClientesBarrio;
    }

    public void setLvClientesBarrio(ListView lvClientesBarrio) {
        this.lvClientesBarrio = lvClientesBarrio;
    }


}
