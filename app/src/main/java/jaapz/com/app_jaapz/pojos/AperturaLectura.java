package jaapz.com.app_jaapz.pojos;

import java.sql.Date;
import java.sql.Timestamp;

public class AperturaLectura {
    private Integer idApertura;
    private Integer idAnio;
    private Integer idMes;
    private Integer usuarioCrea;
    private Date fecha;
    private String observacion;
    private String estadoApertura;
    private String estado;

    public Integer getIdApertura() {
        return idApertura;
    }

    public void setIdApertura(Integer idApertura) {
        this.idApertura = idApertura;
    }

    public Integer getIdAnio() {
        return idAnio;
    }

    public void setIdAnio(Integer idAnio) {
        this.idAnio = idAnio;
    }

    public Integer getIdMes() {
        return idMes;
    }

    public void setIdMes(Integer idMes) {
        this.idMes = idMes;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstadoApertura() {
        return estadoApertura;
    }

    public void setEstadoApertura(String estadoApertura) {
        this.estadoApertura = estadoApertura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
