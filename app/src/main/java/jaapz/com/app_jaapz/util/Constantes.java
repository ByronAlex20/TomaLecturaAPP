package jaapz.com.app_jaapz.util;

public class Constantes {
    public static String NOMBRE_BD_SQLITE = "base_jaapz";
    public static String NOMBRE_BD_CONFIGURACION = "bd_configuraciones";
    public static Integer VERSION_BD_SQLITE = 1;

    //acciones para la sincronizacion
    public static String EST_SYNC_REALIZADO = "REALIZADO";
    public static String EST_SYNC_PENDIENTE = "PENDIENTE";

    public static String ID_SHARED_PREFERENCES = "ID_PREFERENCE";
    public static String ID_SHARED_PREFERENCES_ESTADO = "ESTADO_PREFERENCE";
    public static String ID_SHARED_PREFERENCES_USUARIO = "ID_USUARIO";

    //valores por defecto de base de datos
    public static String EST_ACTIVO = "A";
    public static String EST_INACTIVO = "I";

    //estados de la apertura
    public static String EST_APERTURA_PROCESO = "EN PROCESO";
    public static String EST_APERTURA_REALIZADO = "REALIZADO";

    public static String ESTADO_TOMA_REALIZADO = "TOMADO";
    //id de los diferentes usuarios que estan registrados en el sistema
    public static int ID_USU_ADMINISTRADOR = 1;
    public static int ID_USU_LECTURA = 2;
    public static int ID_USU_INSPECCION = 3;
    public static int ID_USU_PRESIDENTE = 5;
    public static int ID_USU_REPARACIONES = 6;
    public static int ID_USU_SECRETARIA = 7;
    public static int ID_USU_BODEGA = 8;
    public static int ID_USU_RECAUDACIONES = 9;

    public static String CAT_VIVIENDA = "VIVIENDA";
    public static String CAT_COMERCIAL = "COMERCIAL";
    public static String CAT_ESTABLECIMIENTO = "ESTABLECIMIENTO PÚBLICO";


    public static String EST_FAC_CANCELADO = "CANCELADO";
    public static String EST_FAC_PENDIENTE = "PENDIENTE";

    public static String ORIGEN_MOVIL = "MOVIL";
    public static String ORIGEN_ESCRITORIO = "ESCRITORIO";

    public static String IDENT_LECTURA = "LEC";
}
