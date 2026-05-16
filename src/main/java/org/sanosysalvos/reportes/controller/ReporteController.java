package org.sanosysalvos.reportes.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.sanosysalvos.reportes.dto.ReporteCreateRequest;
import org.sanosysalvos.reportes.dto.ReporteResponse;
import org.sanosysalvos.reportes.dto.ReporteUpdateRequest;
import org.sanosysalvos.reportes.service.ReporteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("${app.api-prefix:/api/reportes}")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PostMapping
    public ResponseEntity<ReporteResponse> crear(@Valid @RequestBody ReporteCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ReporteResponse>> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long mascotaId) {
        return ResponseEntity.ok(reporteService.listar(estado, mascotaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponse> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody ReporteUpdateRequest request) {
        return ResponseEntity.ok(reporteService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReporteResponse> actualizarEstado(@PathVariable Long id,
                                                             @RequestParam @NotBlank String estado) {
        return ResponseEntity.ok(reporteService.actualizarEstado(id, estado));
    }
}