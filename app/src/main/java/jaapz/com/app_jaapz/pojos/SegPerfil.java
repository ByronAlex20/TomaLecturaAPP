package jaapz.com.app_jaapz.pojos;

public class SegPerfil {
    private Integer idPerfil;

    private String descripcion;

    private String estado;

    private String nombre;

    public SegPerfil() {
        this.idPerfil = -1;
        this.descripcion = "";
        this.estado = "A";
        this.nombre = "";
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "SegPerfil{" +
                "idPerfil=" + idPerfil +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
