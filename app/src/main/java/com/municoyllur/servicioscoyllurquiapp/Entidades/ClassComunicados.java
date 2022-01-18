package com.municoyllur.servicioscoyllurquiapp.Entidades;

public class ClassComunicados {
    private String idComunicado;
    private String TipoComunicado;
    private String objetivoComunicado;
    private String titulo;
    private String descripcion;
    private String link;
    private String fechaComunicado;

    public ClassComunicados() {
    }

    public ClassComunicados(String idComunicado, String tipoComunicado, String objetivoComunicado, String titulo, String descripcion, String link, String fechaComunicado) {
        this.idComunicado = idComunicado;
        this.TipoComunicado = tipoComunicado;
        this.objetivoComunicado = objetivoComunicado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
        this.fechaComunicado = fechaComunicado;
    }

    public String getIdComunicado() {
        return idComunicado;
    }

    public void setIdComunicado(String idComunicado) {
        this.idComunicado = idComunicado;
    }

    public String getTipoComunicado() {
        return TipoComunicado;
    }

    public void setTipoComunicado(String tipoComunicado) {
        TipoComunicado = tipoComunicado;
    }

    public String getObjetivoComunicado() {
        return objetivoComunicado;
    }

    public void setObjetivoComunicado(String objetivoComunicado) {
        this.objetivoComunicado = objetivoComunicado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFechaComunicado() {
        return fechaComunicado;
    }

    public void setFechaComunicado(String fechaComunicado) {
        this.fechaComunicado = fechaComunicado;
    }
}