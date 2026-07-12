package org.sanosysalvos.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanosysalvos.dto.ReporteMascotaDTO;
import org.sanosysalvos.model.*;
import org.sanosysalvos.repository.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteMascotaServiceTest {

    @Mock private ReporteMascotaRepository reporteRepo;
    @Mock private MascotaRepository mascotaRepo;
    @Mock private ContactoRepository contactoRepo;
    @Mock private TipoReporteRepository tipoReporteRepo;
    @Mock private EstatusRepository estatusRepo;
    @Mock private MarcaDistintivaRepository marcaRepo;
    @Mock private OutboxService outboxService;

    @InjectMocks private ReporteMascotaService reporteService;

    private ReporteMascota reporte;
    private Mascota mascota;
    private TipoReporte tipoReporte;
    private Estatus estatus;
    private Raza raza;

    @BeforeEach
    void setUp() {
        raza = new Raza();
        raza.setIdRaza(1);
        raza.setDescripcion("Labrador");

        mascota = new Mascota();
        mascota.setIdMascota(1);
        mascota.setNombreMascota("Firulais");
        mascota.setColorPrimario("negro");
        mascota.setTamano("mediano");
        mascota.setRaza(raza);

        tipoReporte = new TipoReporte();
        tipoReporte.setIdTipoReporte(1);
        tipoReporte.setDescripcionTipoReporte("Mascota perdida");

        estatus = new Estatus();
        estatus.setIdEstatus(1);
        estatus.setDescripcionEstatus("Activo");

        reporte = new ReporteMascota();
        reporte.setIdReporteMascota(1);
        reporte.setMascota(mascota);
        reporte.setTipoReporte(tipoReporte);
        reporte.setEstatus(estatus);
        reporte.setRaza("Labrador");
        reporte.setColor("negro");
        reporte.setTamano("mediano");
    }

    @Test
    void findAll_debeRetornarListaDeReportes() {
        when(reporteRepo.findAll()).thenReturn(List.of(reporte));

        List<ReporteMascotaDTO> result = reporteService.findAll();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getIdReporteMascota());
    }

    @Test
    void findById_debeRetornarReporte_cuandoExiste() {
        when(reporteRepo.findById(1)).thenReturn(Optional.of(reporte));

        ReporteMascotaDTO result = reporteService.findById(1);

        assertNotNull(result);
        assertEquals("Firulais", result.getNombreMascota());
    }

    @Test
    void findById_debeLanzarException_cuandoNoExiste() {
        when(reporteRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reporteService.findById(99));
    }

    @Test
    void delete_debeEliminarReporte_cuandoExiste() {
        when(reporteRepo.findById(1)).thenReturn(Optional.of(reporte));
        doNothing().when(reporteRepo).deleteById(1);

        assertDoesNotThrow(() -> reporteService.delete(1));
        verify(reporteRepo, times(1)).deleteById(1);
    }

    @Test
    void delete_debeLanzarException_cuandoReporteNoExiste() {
        when(reporteRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reporteService.delete(99));
    }
}