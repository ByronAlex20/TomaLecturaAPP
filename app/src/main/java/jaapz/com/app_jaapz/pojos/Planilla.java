package jaapz.com.app_jaapz.pojos;

import java.sql.Date;
import java.sql.Timestamp;

public class Planilla {
    private Integer idPlanilla;
    private Integer idCuenta;
    private Integer idApertura;
    private Integer usuarioCrea;
    private Date fecha;
    private Integer lecturaActual;
    private Integer lecturaAnterior;
    private Integer consumoMinimo;
    private Integer integer;
    private Integer consumo;
    private double totalPagar;
    private String totalLetras;
    private String convenio;
    private String cancelado;
    private String latitud;
    private String longitud;
    private Date fechaIngreso;
    private String estado;
    private String origen;
    private String observaciones;
    private String identInstalacion;
    private String estadoToma;

    public Integer getConsumo() {
        return consumo;
    }

    public void setConsumo(Integer consumo) {
        this.consumo = consumo;
    }

    public Integer getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(Integer idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdApertura() {
        return idApertura;
    }

    public void setIdApertura(Integer idApertura) {
        this.idApertura = idApertura;
    }

    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getLecturaActual() {
        return lecturaActual;
    }

    public void setLecturaActual(Integer lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public Integer getLecturaAnterior() {
        return lecturaAnterior;
    }

    public void setLecturaAnterior(Integer lecturaAnterior) {
        this.lecturaAnterior = lecturaAnterior;
    }

    public Integer getConsumoMinimo() {
        return consumoMinimo;
    }

    public void setConsumoMinimo(Integer consumoMinimo) {
        this.consumoMinimo = consumoMinimo;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getTotalLetras() {
        return totalLetras;
    }

    public void setTotalLetras(String totalLetras) {
        this.totalLetras = totalLetras;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getCancelado() {
        return cancelado;
    }

    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEstado() {
        return estado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdentInstalacion() {
        return identInstalacion;
    }

    public String getEstadoToma() {
        return estadoToma;
    }

    public void setEstadoToma(String estadoToma) {
        this.estadoToma = estadoToma;
    }

    public void setIdentInstalacion(String identInstalacion) {
        this.identInstalacion = identInstalacion;
    }
}
