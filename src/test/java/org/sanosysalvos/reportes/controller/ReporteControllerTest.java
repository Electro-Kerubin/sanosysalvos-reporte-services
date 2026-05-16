package org.sanosysalvos.reportes.controller;

import org.junit.jupiter.api.Test;
import org.sanosysalvos.reportes.dto.ReporteResponse;
import org.sanosysalvos.reportes.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Test
    void crearReporteRespondeEnPrefijoDelGateway() throws Exception {
        when(reporteService.crear(any())).thenReturn(new ReporteResponse(
                1L,
                10L,
                20L,
                "Centro",
                "Perro perdido",
                LocalDateTime.parse("2026-05-15T10:15:30"),
                "reportado",
                null
        ));

        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "mascotaId": 10,
                                  "contactoId": 20,
                                  "ubicacionExtravio": "Centro",
                                  "descripcion": "Perro perdido",
                                  "estado": "reportado"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("reportado"));
    }

    @Test
    void listarReportesRespondeOk() throws Exception {
        when(reporteService.listar(null, null)).thenReturn(List.of(new ReporteResponse(
                2L,
                11L,
                null,
                "Norte",
                "Gato extraviado",
                LocalDateTime.parse("2026-05-15T11:00:00"),
                "reportado",
                null
        )));

        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mascotaId").value(11));
    }
}