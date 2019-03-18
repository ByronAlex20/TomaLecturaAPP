package jaapz.com.app_jaapz.pojos;

public class SegUsuario {
    private Integer idUsuario;
    private String apellido;
    private String cargo;
    private String cedula;
    private String clave;
    private String direccion;
    private String nuevo;
    private String estado;
    private String nombre;
    private String telefono;
    private String usuario;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellidos) {
        this.apellido = apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
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

    public void setNombre(String nombres) {
        this.nombre = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "SegUsuario{" +
                "idUsuario=" + idUsuario +
                ", apellidos='" + apellido + '\'' +
                ", cargo='" + cargo + '\'' +
                ", cedula='" + cedula + '\'' +
                ", clave='" + clave + '\'' +
                ", direccion='" + direccion + '\'' +
                ", nuevo='" + nuevo + '\'' +
                ", estado='" + estado + '\'' +
                ", nombres='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
