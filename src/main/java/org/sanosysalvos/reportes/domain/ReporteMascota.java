package org.sanosysalvos.reportes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes_mascotas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mascota_id", nullable = false)
    private Long mascotaId;

    @Column(name = "contacto_id")
    private Long contactoId;

    @Column(nullable = false, length = 255)
    private String ubicacionExtravio;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "fecha_reporte", nullable = false)
    private LocalDateTime fechaReporte;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @PrePersist
    void prePersist() {
        if (fechaReporte == null) {
            fechaReporte = LocalDateTime.now();
        }
        if (estado == null || estado.isBlank()) {
            estado = "reportado";
        }
    }
}