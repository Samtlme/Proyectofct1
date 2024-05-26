package com.example.proyectofct.bbdd.entities;

public class Preferencias {

    private Integer id;
    private String ip;
    private String port;
    private String language;
    private String s_icon;
    private String a_icon;
    private String b_icon;

    public Preferencias(Integer id, String ip, String port, String language, String s_icon, String a_icon, String b_icon) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.language = language;
        this.s_icon = s_icon;
        this.a_icon = a_icon;
        this.b_icon = b_icon;
    }
    public Preferencias(String ip, String port, String language, String s_icon, String a_icon, String b_icon) {
        this.ip = ip;
        this.port = port;
        this.language = language;
        this.s_icon = s_icon;
        this.a_icon = a_icon;
        this.b_icon = b_icon;
    }

    public Preferencias() {}

    public Preferencias(String ip, String port, String language) {
        this.ip = ip;
        this.port = port;
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getS_icon() {
        return s_icon;
    }

    public void setS_icon(String s_icon) {
        this.s_icon = s_icon;
    }

    public String getA_icon() {
        return a_icon;
    }

    public void setA_icon(String a_icon) {
        this.a_icon = a_icon;
    }

    public String getB_icon() {
        return b_icon;
    }

    public void setB_icon(String b_icon) {
        this.b_icon = b_icon;
    }
}
