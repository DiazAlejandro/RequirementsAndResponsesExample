/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.sql.Time;

/**
 *
 * @author Alejandro Diaz
 */
public class UsuarioModel {
    private String nombre; 
    private String correo;
    private String apellidos; 
    private String celular; 
    private String matricula; 
    private Date fecha_nac;
    private Time hora;

    public UsuarioModel() {
        
    }

    public UsuarioModel(String nombre, String correo, String apellidos, String celular, String matricula, Date fecha_nac, Time hora) {
        this.nombre = nombre;
        this.correo = correo;
        this.apellidos = apellidos;
        this.celular = celular;
        this.matricula = matricula;
        this.fecha_nac = fecha_nac;
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }
    
    

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", correo=" + correo + ", apellidos=" + apellidos + ", celular=" + celular + ", matricula=" + matricula + ", fecha_nac=" + fecha_nac + '}';
    } 
    
}
