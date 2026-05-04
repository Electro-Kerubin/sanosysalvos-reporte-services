package com.sanosysalvos.reportes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreMascota;
    private String especie;
    private String ubicacionExtravio;
    
    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte = LocalDateTime.now();

    private Long usuarioPublicadorId; 

    @Column(nullable = false)
    private String nombreContacto;
    
    @Column(nullable = false)
    private String telefonoContacto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getUbicacionExtravio() { return ubicacionExtravio; }
    public void setUbicacionExtravio(String ubicacionExtravio) { this.ubicacionExtravio = ubicacionExtravio; }
    public Long getUsuarioPublicadorId() { return usuarioPublicadorId; }
    public void setUsuarioPublicadorId(Long usuarioPublicadorId) { this.usuarioPublicadorId = usuarioPublicadorId; }
    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
}