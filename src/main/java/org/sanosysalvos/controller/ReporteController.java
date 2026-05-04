package com.sanosysalvos.reportes.controller;

import com.sanosysalvos.reportes.model.Reporte;
import com.sanosysalvos.reportes.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepository;

    @PostMapping
    public ResponseEntity<Reporte> crearReporte(@RequestBody Reporte reporte) {
        Reporte nuevoReporte = reporteRepository.save(reporte);
        return ResponseEntity.ok(nuevoReporte);
    }

    @GetMapping
    public ResponseEntity<List<Reporte>> obtenerReportes() {
        return ResponseEntity.ok(reporteRepository.findAll());
    }
}