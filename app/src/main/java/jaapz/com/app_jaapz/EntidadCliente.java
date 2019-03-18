package jaapz.com.app_jaapz;

import java.io.Serializable;

public class EntidadCliente implements Serializable, Comparable<EntidadCliente> {
    private String numMedidor;
    private int idCliente;
    private int idCuenta;
    private int idPlanilla;
    private int lecturaActual;
    private int lecturaAnterior;
    private int consumo;
    private String barrio;

    public EntidadCliente(String numMedidor, int lecturaActual, int lecturaAnterior, int consumo, String barrio,int idCliente,int idCuenta, int idPlanilla) {
        this.numMedidor = numMedidor;
        this.lecturaActual = lecturaActual;
        this.lecturaAnterior = lecturaAnterior;
        this.consumo = consumo;
        this.barrio = barrio;
        this.idCliente = idCliente;
        this.idCuenta = idCuenta;
        this.idPlanilla = idPlanilla;

    }

    public EntidadCliente(){

    }
    public String getNumMedidor() {
        return numMedidor;
    }

    public void setNumMedidor(String numMedidor) {
        this.numMedidor = numMedidor;
    }

    public int getLecturaActual() {
        return lecturaActual;
    }

    public void setLecturaActual(int lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public int getLecturaAnterior() {
        return lecturaAnterior;
    }

    public void setLecturaAnterior(int  lecturaAnterior) {
        this.lecturaAnterior = lecturaAnterior;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public int getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(int idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    @Override
    public int compareTo(EntidadCliente o) {
        if (this.consumo < o.consumo) {
            return -1;
        }
        if (this.consumo  > o.consumo) {
            return 1;
        }
        return 0;
    }
}
