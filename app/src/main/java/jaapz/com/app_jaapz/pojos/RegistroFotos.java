package jaapz.com.app_jaapz.pojos;

public class RegistroFotos {
    private Integer idRegistro;
    private Integer idPlanillaDet;
    private Integer UsuarioCrea;
    private byte[] foto;
    private String nombreFoto;
    private String estado;

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }


    public Integer getIdRegistro() { return idRegistro; }

    public void setIdRegistro(Integer idRegistro) { this.idRegistro = idRegistro; }

    public Integer getIdPlanillaDet() {
        return idPlanillaDet;
    }

    public void setIdPlanillaDet(Integer idPlanillaDet) {
        this.idPlanillaDet = idPlanillaDet;
    }

    public Integer getUsuarioCrea() { return UsuarioCrea; }

    public void setUsuarioCrea(Integer usuarioCrea) { UsuarioCrea = usuarioCrea; }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
