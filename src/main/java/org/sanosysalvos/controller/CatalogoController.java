package org.sanosysalvos.controller;

import lombok.RequiredArgsConstructor;
import org.sanosysalvos.dto.CatalogoDTO;
import org.sanosysalvos.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final TipoReporteRepository tipoReporteRepo;
    private final EstatusRepository estatusRepo;
    private final CanalPreferenciaRepository canalRepo;
    private final RazaRepository razaRepo;
    private final EspecieRepository especieRepo;
    private final SexoRepository sexoRepo;
    private final MarcaDistintivaRepository marcaRepo;

    @GetMapping("/tipos-reporte")
    public ResponseEntity<List<CatalogoDTO>> getTiposReporte() {
        List<CatalogoDTO> list = tipoReporteRepo.findAll().stream()
                .map(t -> new CatalogoDTO(t.getIdTipoReporte(), t.getDescripcionTipoReporte()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    

    @GetMapping("/estatus")
    public ResponseEntity<List<CatalogoDTO>> getEstatus() {
        List<CatalogoDTO> list = estatusRepo.findAll().stream()
                .map(e -> new CatalogoDTO(e.getIdEstatus(), e.getDescripcionEstatus()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/canales-preferencia")
    public ResponseEntity<List<CatalogoDTO>> getCanalesPreferencia() {
        List<CatalogoDTO> list = canalRepo.findAll().stream()
                .map(c -> new CatalogoDTO(c.getIdCanalPreferencia(), c.getDescripcion()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/razas")
    public ResponseEntity<List<CatalogoDTO>> getRazas() {
        List<CatalogoDTO> list = razaRepo.findAll().stream()
                .map(r -> new CatalogoDTO(r.getIdRaza(), r.getDescripcion(), r.getIdEspecie()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/especies")
    public ResponseEntity<List<CatalogoDTO>> getEspecies() {
        List<CatalogoDTO> list = especieRepo.findAll().stream()
                .map(e -> new CatalogoDTO(e.getIdEspecie(), e.getDescripcion()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/sexos")
    public ResponseEntity<List<CatalogoDTO>> getSexos() {
        List<CatalogoDTO> list = sexoRepo.findAll().stream()
                .map(s -> new CatalogoDTO(s.getIdSexo(), s.getDescripcion()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/marcas-distintivas")
    public ResponseEntity<List<CatalogoDTO>> getMarcasDistintivas() {
        List<CatalogoDTO> list = marcaRepo.findAll().stream()
                .map(m -> new CatalogoDTO(m.getIdMarcaDistintiva(), m.getDescripcion()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}

