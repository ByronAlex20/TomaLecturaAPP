package jaapz.com.app_jaapz.pojos;

public class PlanillaDetalle {
    private Integer idPlanillaDet;
    private Integer idPlanilla;
    private Integer idInstalacion;
    private Integer idReparacion;
    private Integer idConvenioDet;
    private Integer usuarioCrea;
    private String descripcion;
    private Integer cantidad;
    private double subtotal;
    private String estado;
    private String estadoSync;
    private String identificadorOperacion;

    public String getIdentificadorOperacion() {
        return identificadorOperacion;
    }

    public void setIdentificadorOperacion(String identificadorOperacion) {
        this.identificadorOperacion = identificadorOperacion;
    }

    public Integer getIdPlanillaDet() {
        return idPlanillaDet;
    }

    public void setIdPlanillaDet(Integer idPlanillaDet) {
        this.idPlanillaDet = idPlanillaDet;
    }

    public Integer getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(Integer idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Integer getIdInstalacion() {
        return idInstalacion;
    }

    public void setIdInstalacion(Integer idInstalacion) {
        this.idInstalacion = idInstalacion;
    }

    public Integer getIdReparacion() {
        return idReparacion;
    }

    public void setIdReparacion(Integer idReparacion) {
        this.idReparacion = idReparacion;
    }

    public Integer getIdConvenioDet() {
        return idConvenioDet;
    }

    public void setIdConvenioDet(Integer idConvenioDet) {
        this.idConvenioDet = idConvenioDet;
    }

    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoSync() {
        return estadoSync;
    }

    public void setEstadoSync(String estadoSync) {
        this.estadoSync = estadoSync;
    }
}
