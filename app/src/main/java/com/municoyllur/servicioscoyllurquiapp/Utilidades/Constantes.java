package com.municoyllur.servicioscoyllurquiapp.Utilidades;

public class    Constantes {

    static final String ServerPath="https://servicios.municoyllurqui.gob.pe/Scripts/";
    //Direcciones de los servicios Rest para usuarios
    public static final String GetUsuarios = ServerPath+"obtener_usuarios.php";
    public static final String GetUsuarioID = ServerPath+"get_insert_usuarios.php";
    public static final String insertUsuario = ServerPath+"get_insert_usuarios.php";
    public static final String updatetUsuario = ServerPath+"update_delete_usuarios.php";
    //Notificaciones
    public static final String SendPushUsuario = ServerPath+"sendSinglePush.php";
    //Direcciones de lso servicios Rest para comunicados
    public static final String GetComunicados = ServerPath+"get_insert_comunicados.php";
    //Direcciones de lso servicios Rest para usuarios
    public static final String insertReporte = ServerPath+"get_insert_reportes.php";
}
