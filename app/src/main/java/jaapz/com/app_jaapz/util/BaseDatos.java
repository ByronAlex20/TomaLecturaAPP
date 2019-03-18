package jaapz.com.app_jaapz.util;

public class BaseDatos {
    //tablas de la base de datos
    //tabla de configuraciones
    public static final String TABLA_CONFIGURACIONES = "configuracion";
    public static final String CREAR_TABLA_CONFIGURACIONES = "CREATE TABLE configuracion(id integer primary key autoincrement," +
            "ip text,puerto_pg int,bd_pg text,usuario text,password text,estado varchar(1))";
    public static final String INSERT_TABLA_CONFIGURACIONES = "INSERT INTO configuracion(ip,estado,puerto_pg,bd_pg,usuario,password)" +
            "values('192.168.1.1','A',5432,'bd_jaapz','postgres','tricampeon2015')";
    public static final String TABLA_PERFIL = "seg_perfil";
    public static final String CREAR_TABLA_PERFIL = "CREATE TABLE seg_perfil(id_perfil integer primary key autoincrement," +
            "nombre text,descripcion text,estado varchar(1))";
    public static final String TABLA_USUARIO = "seg_usuario";
    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE seg_usuario( id_usuario integer primary key autoincrement, " +
            "cedula text, nombre text, apellido text, direccion text, telefono text, cargo text, " +
            "usuario text, clave text,nuevo text, estado varchar(1))";
    public static final String TABLA_USUARIO_PERFIL = "seg_usuario_perfil";
    public static final String CREAR_TABLA_USUARIO_PERFIL = "CREATE TABLE seg_usuario_perfil(id_usuario_perfil integer primary key autoincrement," +
            "id_perfil integer, id_usuario integer,estado varchar(1))";

    //tablas para la sincronizacion
    public static final String TABLA_ANIO = "anio";
    public static final String CREAR_TABLA_ANIO = "CREATE TABLE anio(id_anio integer primary key autoincrement," +
            " descripcion integer, estado varchar(1))";
    public static final String TABLA_MES = "mes";
    public static final String CREAR_TABLA_MES = "CREATE TABLE mes( id_mes integer primary key autoincrement, " +
            "descripcion text, estado varchar(1) )";
    public static final String TABLA_MEDIDOR = "medidor";
    public static final String CREAR_TABLA_MEDIDOR = "CREATE TABLE medidor( id_medidor integer primary key autoincrement, " +
            "id_estado integer,usuario_crea integer, codigo text, marca text, modelo text, estado varchar(1) )";



    public static final String TABLA_PLANILLA = "planilla";
    public static final String CREAR_TABLA_PLANILLA = "CREATE TABLE planilla(id_planilla integer primary key autoincrement," +
            " id_cuenta integer,id_apertura integer,usuario_crea integer,fecha datetime,lectura_actual integer,ident_instalacion varchar(15)," +
            " lectura_anterior integer,consumo_minimo integer,consumo integer,total_pagar float,total_letras text," +
            " convenio text, cancelado text,latitud text,longitud text,fecha_ingreso datetime,estado varchar(1),origen varchar(20),observaciones text,estado_toma varchar(10))";

    public static final String TABLA_PLANILLA_DETALLE = "planilla_detalle";
    public static final String CREAR_TABLA_PLANILLA_DETALLE = "CREATE TABLE planilla_detalle( id_planilla_det integer primary key autoincrement, " +
            "id_planilla integer, id_instalacion integer, id_reparacion integer, id_convenio_det integer, usuario_crea integer," +
            "descripcion text,cantidad integer, subtotal float, estado varchar(1),estado_sync text,identificador_operacion text )";


    public static final String TABLA_BARRIO = "barrio";
    public static final String CREAR_TABLA_BARRIO = "CREATE TABLE barrio( id_barrio integer primary key autoincrement, " +
            "nombre text, descripcion text, estado varchar(1))";


    public static final String TABLA_CLIENTE = "cliente";
    public static final String CREAR_TABLA_CLIENTE = "CREATE TABLE cliente( id_cliente integer primary key autoincrement, " +
            "usuario_crea integer,cedula text, nombres text, apellidos text, direccion text, telefono text, genero text," +
            "email text, estado varchar(1))";



    public static final String TABLA_APERTURA_LECTURA = "apertura_lectura";
    public static final String CREAR_TABLA_APERTURA_LECTURA = "CREATE TABLE apertura_lectura( id_apertura integer primary key autoincrement, " +
            "id_anio integer, id_mes integer,usuario_crea integer,fecha datetime, observacion text, estado_apertura text,estado varchar(1))";


    public static final String TABLA_CUENTA_CLIENTE = "cuenta_cliente";
    public static final String CREAR_TABLA_CUENTA_CLIENTE = "CREATE TABLE cuenta_cliente( id_cuenta integer primary key autoincrement, " +
            "id_cliente integer, id_categoria integer, id_barrio integer, id_medidor integer,usuario_crea integer, fecha_ingreso datetime, observacion text, " +
            "direccion text,email text, latitud text, longitud text, estado varchar(1) )";
    public static final String TABLA_REGISTRO_FOTOGRAFICO = "registro_fotos";
    public static final String CREAR_TABLA_REGISTRO_FOTOGRAFICO = "CREATE TABLE registro_fotos( id_registro integer primary key autoincrement, " +
            "id_planilla_det integer, usuario_crea integer, foto blob, nombre_foto text, estado varchar(1) )";

    public static final String TABLA_RESPONSABLE_LECTURA = "responsable_lectura";
    public static final String CREAR_TABLA_RESPONSABLE_LECTURA = "CREATE TABLE responsable_lectura( id_responsable integer primary key autoincrement, " +
            "id_usuario integer, id_barrio integer, id_apertura integer, usuario_crea integer, fecha datetime, estado varchar(1) )";
    //----------------------------------------------------------------------------------------------------------------

/*
    public static final String TABLA_CATEGORIA = "jun_categoria";
    public static final String CREAR_TABLA_CATEGORIA = "CREATE TABLE jun_categoria( id_categoria integer primary key autoincrement, " +
            "descripcion text, valor_m3 float, valor_iva float, estado varchar(1) )";


    public static final String TABLA_CONVENIO = "jun_convenio";
    public static final String CREAR_TABLA_CONVENIO = "CREATE TABLE jun_convenio( id_convenio integer primary key autoincrement, " +
            "id_cuenta integer, num_letras integer, fecha_convenio datetime, total_conv float, estado varchar(1) )";
    public static final String TABLA_CONVENIO_PLANILLA = "jun_convenio_planilla";
    public static final String CREAR_TABLA_CONVENIO_PLANILLA = "CREATE TABLE jun_convenio_planilla( id_conv_planilla integer primary key autoincrement, " +
            "id_convenio integer, id_planilla integer, estado varchar(1) )";

    public static final String TABLA_DETALLE_CONVENIO = "jun_detalle_convenio";
    public static final String CREAR_TABLA_DETALLE_CONVENIO = "CREATE TABLE jun_detalle_convenio( id_det_convenio integer primary key autoincrement, " +
            "id_convenio integer, num_letra integer, descripcion text, valor float, estado varchar(1) )";
    public static final String TABLA_DETALLE_FACTURA = "jun_detalle_factura";
    public static final String CREAR_TABLA_DETALLE_FACTURA = "CREATE TABLE jun_detalle_factura( id_fact_det integer primary key autoincrement, " +
            "id_factura integer, id_planilla integer, subtotal float, estado varchar(1) )";
    public static final String TABLA_DETALLE_INGRESO = "jun_detalle_ingreso";
    public static final String CREAR_TABLA_DETALLE_INGRESO = "CREATE TABLE jun_detalle_ingreso( id_det_ingreso integer primary key autoincrement, " +
            "id_ingreso integer, id_material integer, precio float, cantidad integer, det_total float, estado varchar(1) )";

    public static final String TABLA_DETALLE_REQUERIMIENTO = "jun_detalle_requerimiento";
    public static final String CREAR_TABLA_DETALLE_REQUERIMIENTO = "CREATE TABLE jun_detalle_requerimiento( id_det_requerimiento integer primary key autoincrement, " +
            "id_requerimiento integer, id_material integer, estado varchar(1) )";
    public static final String TABLA_ESTADO_MEDIDOR = "jun_estado_medidor";
    public static final String CREAR_TABLA_ESTADO_MEDIDOR = "CREATE TABLE jun_estado_medidor( id_estado_medidor integer primary key autoincrement, " +
            "descripcion text, estado varchar(1) )";
    public static final String TABLA_FACTURA = "jun_factura";
    public static final String CREAR_TABLA_FACTURA = "CREATE TABLE jun_factura( id_factura integer primary key autoincrement, " +
            "id_cuenta integer, num_factura varchar(25), usuario_crea text, total_factura float, estado varchar(1) )";
    public static final String TABLA_INGRESO = "jun_ingreso";
    public static final String CREAR_TABLA_INGRESO = "CREATE TABLE jun_ingreso( id_ingreso integer primary key autoincrement, " +
            "fecha datetime, numero_ing text, subtotal float, total float, usuario_crea text, estado varchar(1) )";
    public static final String TABLA_MATERIAL = "jun_material";
    public static final String CREAR_TABLA_MATERIAL = "CREATE TABLE jun_material( id_material integer primary key autoincrement, " +
            "id_tipo_material integer, id_precio integer, descripcion text, marca text, cantidad integer, estado varchar(1) )";


    public static final String TABLA_ORDEN_DESPACHO = "jun_orden_despacho";
    public static final String CREAR_TABLA_ORDEN_DESPACHO = "CREATE TABLE jun_orden_despacho( id_orden integer primary key autoincrement, " +
            "id_requerimiento integer, num_orden text, fecha datetime, subtotal float, total float, usuario_crea text, estado varchar(1) )";
    public static final String TABLA_ORDEN_DETALLE = "jun_orden_detalle";
    public static final String CREAR_TABLA_ORDEN_DETALLE = "CREATE TABLE jun_orden_detalle( id_orden_detalle integer primary key autoincrement, " +
            "id_orden integer, id_material integer, cantidad integer, precio float, total float, estado varchar(1) )";
    public static final String TABLA_PAGO = "jun_pago";
    public static final String CREAR_TABLA_PAGO = "CREATE TABLE jun_pago( id_pago integer primary key autoincrement, " +
            "id_planilla integer, id_tipo_pago integer, usuario_crea text, valor float, estado varchar(1) )";


    public static final String TABLA_REQUERIMIENTO = "jun_requerimiento";
    public static final String CREAR_TABLA_REQUERIMIENTO = "CREATE TABLE jun_requerimiento( id_requerimiento integer primary key autoincrement, " +
            "id_tipo_requerimiento integer, id_cuenta integer, detalle text, usuario_crea text, estado varchar(1) )";
    public static final String TABLA_TIPO_MATERIAL = "jun_tipo_material";
    public static final String CREAR_TABLA_TIPO_MATERIAL = "CREATE TABLE jun_tipo_material( id_tipo_material integer primary key autoincrement, " +
            "descripcion text, estado varchar(1) )";
    public static final String TABLA_TIPO_PAGO = "jun_tipo_pago";
    public static final String CREAR_TABLA_TIPO_PAGO = "CREATE TABLE jun_tipo_pago( id_tipo_pago integer primary key autoincrement, " +
            "descripcion text, estado varchar(1) )";
    public static final String TABLA_TIPO_REQUERIMIENTO = "jun_tipo_requerimiento";
    public static final String CREAR_TABLA_TIPO_REQUERIMIENTO = "CREATE TABLE jun_tipo_requerimiento( id_tipo_requerimiento integer primary key autoincrement, " +
            "descripcion text, estado varchar(1) )";
    public static final String TABLA_TIPO_RUBRO = "jun_tipo_rubro";
    public static final String CREAR_TABLA_TIPO_RUBRO = "CREATE TABLE jun_tipo_rubro( id_tipo_rubro integer primary key autoincrement, " +
            "descripcion text, estado varchar(1) )";
    public static final String TABLA_AUDITORIA = "seg_auditoria";
    public static final String CREAR_TABLA_AUDITORIA = "CREATE TABLE seg_auditoria( id_auditoria integer primary key autoincrement, " +
            "descripcion text, tabla_afectada text, fecha datetime, usuario_logeado text, estado varchar(1) )";
    public static final String TABLA_EMPRESA = "seg_empresa";
    public static final String CREAR_TABLA_EMPRESA = "CREATE TABLE seg_empresa( id_empresa integer primary key autoincrement, " +
            "ruc text, razon_social text, representante text, direccion text, telefono text, logo blob, estado varchar(1) )";
    public static final String TABLA_MENU = "seg_menu";
    public static final String CREAR_TABLA_MENU = "CREATE TABLE seg_menu( id_menu integer primary key autoincrement, " +
            "id_menu_padre integer, descripcion text, posicion integer, icono blob, nombre_fxml text, fxml_asociado bit, estado varchar(1) )";
    public static final String TABLA_NUMERO_FACTURA = "seg_numero_factura";
    public static final String CREAR_TABLA_NUMERO_FACTURA = "CREATE TABLE seg_numero_factura( id_numero integer primary key autoincrement, " +
            "fecha_inicio datetime, fecha_fin datetime, numero_inicio integer, numero_fin integer, numero_establecidos integer, estado varchar(1) )";
    public static final String TABLA_NUMERO_UTILIZADOS = "seg_numero_utilizados";
    public static final String CREAR_TABLA_NUMERO_UTILIZADOS = "CREATE TABLE seg_numero_utilizados( id_utilizados integer primary key autoincrement, " +
            "id_numero integer, numero_utilizado integer, estado varchar(1) )";
    public static final String TABLA_PERMISO = "seg_permiso";
    public static final String CREAR_TABLA_PERMISO = "CREATE TABLE seg_permiso( id_permiso integer primary key autoincrement, " +
            "id_perfil integer, id_menu integer, estado varchar(1) )";
            */
}
