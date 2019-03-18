package jaapz.com.app_jaapz.pojos;

public class Medidor {
    private Integer idMedidor;

    private String codigo;

    private String estado;

    private String marca;

    private String modelo;

    private Integer usuarioCrea;

    private Integer idEstadoMedidor;

    public Integer getIdEstadoMedidor() {
        return idEstadoMedidor;
    }

    public void setIdEstadoMedidor(Integer idEstadoMedidor) {
        this.idEstadoMedidor = idEstadoMedidor;
    }

    public Integer getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(Integer idMedidor) {
        this.idMedidor = idMedidor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }
}
