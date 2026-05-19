package org.sanosysalvos.controller;

import lombok.RequiredArgsConstructor;
import org.sanosysalvos.dto.MascotaDTO;
import org.sanosysalvos.service.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService service;

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** GET /api/mascotas/buscar/raza/{idRaza} */
    @GetMapping("/buscar/raza/{idRaza}")
    public ResponseEntity<List<MascotaDTO>> findByRaza(@PathVariable Integer idRaza) {
        return ResponseEntity.ok(service.findByRaza(idRaza));
    }

    /** GET /api/mascotas/buscar/especie/{idEspecie} */
    @GetMapping("/buscar/especie/{idEspecie}")
    public ResponseEntity<List<MascotaDTO>> findByEspecie(@PathVariable Integer idEspecie) {
        return ResponseEntity.ok(service.findByEspecie(idEspecie));
    }

    /** GET /api/mascotas/buscar/color?color=negro */
    @GetMapping("/buscar/color")
    public ResponseEntity<List<MascotaDTO>> findByColor(@RequestParam String color) {
        return ResponseEntity.ok(service.findByColor(color));
    }

    /** GET /api/mascotas/buscar/tamano?tamano=mediano */
    @GetMapping("/buscar/tamano")
    public ResponseEntity<List<MascotaDTO>> findByTamano(@RequestParam String tamano) {
        return ResponseEntity.ok(service.findByTamano(tamano));
    }

    /** GET /api/mascotas/buscar/chip?idChip=ABC123 */
    @GetMapping("/buscar/chip")
    public ResponseEntity<MascotaDTO> findByChip(@RequestParam String idChip) {
        return ResponseEntity.ok(service.findByChip(idChip));
    }

    /** GET /api/mascotas/buscar/nombre?nombre=firulais */
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<MascotaDTO>> findByNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(service.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> create(@RequestBody MascotaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> update(@PathVariable Integer id, @RequestBody MascotaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
