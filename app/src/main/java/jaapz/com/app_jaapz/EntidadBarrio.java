package jaapz.com.app_jaapz;

public class EntidadBarrio {
    private String nombreBarrio;
    private int idBarrio;

    public EntidadBarrio(String nombreBarrio, int idBarrio) {
        this.nombreBarrio = nombreBarrio;
        this.idBarrio = idBarrio;
    }

    public String getNombreBarrio() {
        return nombreBarrio;
    }

    public void setNombreBarrio(String nombreBarrio) {
        this.nombreBarrio = nombreBarrio;
    }

    public int getIdBarrio() {
        return idBarrio;
    }

    public void setIdBarrio(int idBarrio) {
        this.idBarrio = idBarrio;
    }
}
