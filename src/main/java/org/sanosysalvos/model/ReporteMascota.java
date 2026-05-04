package com.sanosysalvos.reportes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes_mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteMascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacto_id")
    private Contacto contacto; // quien reporta o contacto a notificar

    private String ubicacionExtravio;
    private String descripcion;

    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte = LocalDateTime.now();

    private String estado; // estado del reporte: "reportado", "en proceso", "encontrado", etc.   
    private String imagenUrl;
}