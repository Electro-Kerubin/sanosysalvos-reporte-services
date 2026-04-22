package org.sanosysalvos.controller;

import lombok.RequiredArgsConstructor;
import org.sanosysalvos.dto.ReporteMascotaDTO;
import org.sanosysalvos.dto.ReporteMascotaRequestDTO;
import org.sanosysalvos.service.ReporteMascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteMascotaController {

    private final ReporteMascotaService service;

    @GetMapping
    public ResponseEntity<List<ReporteMascotaDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteMascotaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/tipo/{idTipoReporte}")
    public ResponseEntity<List<ReporteMascotaDTO>> findByTipoReporte(@PathVariable Integer idTipoReporte) {
        return ResponseEntity.ok(service.findByTipoReporte(idTipoReporte));
    }

    @GetMapping("/estatus/{idEstatus}")
    public ResponseEntity<List<ReporteMascotaDTO>> findByEstatus(@PathVariable Integer idEstatus) {
        return ResponseEntity.ok(service.findByEstatus(idEstatus));
    }

    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<ReporteMascotaDTO>> findByMascota(@PathVariable Integer idMascota) {
        return ResponseEntity.ok(service.findByMascota(idMascota));
    }

    @PostMapping
    public ResponseEntity<ReporteMascotaDTO> create(@RequestBody ReporteMascotaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteMascotaDTO> update(@PathVariable Integer id,
                                                    @RequestBody ReporteMascotaRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

