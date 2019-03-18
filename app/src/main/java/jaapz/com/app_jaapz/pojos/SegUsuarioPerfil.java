package jaapz.com.app_jaapz.pojos;

public class SegUsuarioPerfil {
    private Integer idUsuarioPerfil;
    private Integer idPerfil;
    private Integer idUsuario;
    private String estado;

    public Integer getIdUsuarioPerfil() {
        return idUsuarioPerfil;
    }

    public void setIdUsuarioPerfil(Integer idUsuarioPerfil) {
        this.idUsuarioPerfil = idUsuarioPerfil;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "SegUsuarioPerfil{" +
                "idUsuarioPerfil=" + idUsuarioPerfil +
                ", idPerfil=" + idPerfil +
                ", idUsuario=" + idUsuario +
                ", estado='" + estado + '\'' +
                '}';
    }
}
