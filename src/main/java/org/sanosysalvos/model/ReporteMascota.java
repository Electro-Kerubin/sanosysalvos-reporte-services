package org.sanosysalvos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reporte_mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte_mascota")
    private Integer idReporteMascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_reporte")
    private TipoReporte tipoReporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus")
    private Estatus estatus;

    @Column(name = "fecha_extravio")
    private LocalDate fechaExtravio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contacto")
    private Contacto contacto;

    @Column(name = "fecha_avistamiento")
    private LocalDate fechaAvistamiento;

    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca_distintiva")
    private MarcaDistintiva marcaDistintiva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    // ── Campos desnormalizados para el motor de coincidencias ──────────────
    @Column(name = "raza")
    private String raza;

    @Column(name = "color")
    private String color;

    @Column(name = "tamano")
    private String tamano;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;
    // ───────────────────────────────────────────────────────────────────────
}