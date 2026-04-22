package org.sanosysalvos.service;

import lombok.RequiredArgsConstructor;
import org.sanosysalvos.dto.MascotaDTO;
import org.sanosysalvos.model.*;
import org.sanosysalvos.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepo;
    private final RazaRepository razaRepo;
    private final EspecieRepository especieRepo;
    private final SexoRepository sexoRepo;

    public List<MascotaDTO> findAll() {
        return mascotaRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MascotaDTO findById(Integer id) {
        return mascotaRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
    }

    public MascotaDTO create(MascotaDTO dto) {
        return toDTO(mascotaRepo.save(toEntity(dto)));
    }

    public MascotaDTO update(Integer id, MascotaDTO dto) {
        mascotaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
        dto.setIdMascota(id);
        return toDTO(mascotaRepo.save(toEntity(dto)));
    }

    public void delete(Integer id) {
        mascotaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
        mascotaRepo.deleteById(id);
    }

    private MascotaDTO toDTO(Mascota m) {
        MascotaDTO dto = new MascotaDTO();
        dto.setIdMascota(m.getIdMascota());
        dto.setNombreMascota(m.getNombreMascota());
        dto.setColorPrimario(m.getColorPrimario());
        dto.setColorSecundario(m.getColorSecundario());
        dto.setTamano(m.getTamano());
        dto.setEdad(m.getEdad());
        dto.setDetallesExtra(m.getDetallesExtra());
        dto.setIdChip(m.getIdChip());
        if (m.getRaza() != null) { dto.setIdRaza(m.getRaza().getIdRaza()); dto.setDescripcionRaza(m.getRaza().getDescripcion()); }
        if (m.getEspecie() != null) { dto.setIdEspecie(m.getEspecie().getIdEspecie()); dto.setDescripcionEspecie(m.getEspecie().getDescripcion()); }
        if (m.getSexo() != null) { dto.setIdSexo(m.getSexo().getIdSexo()); dto.setDescripcionSexo(m.getSexo().getDescripcion()); }
        return dto;
    }

    private Mascota toEntity(MascotaDTO dto) {
        Mascota m = new Mascota();
        m.setIdMascota(dto.getIdMascota());
        m.setNombreMascota(dto.getNombreMascota());
        m.setColorPrimario(dto.getColorPrimario());
        m.setColorSecundario(dto.getColorSecundario());
        m.setTamano(dto.getTamano());
        m.setEdad(dto.getEdad());
        m.setDetallesExtra(dto.getDetallesExtra());
        m.setIdChip(dto.getIdChip());
        if (dto.getIdRaza() != null)
            m.setRaza(razaRepo.findById(dto.getIdRaza()).orElseThrow(() -> new RuntimeException("Raza no encontrada")));
        if (dto.getIdEspecie() != null)
            m.setEspecie(especieRepo.findById(dto.getIdEspecie()).orElseThrow(() -> new RuntimeException("Especie no encontrada")));
        if (dto.getIdSexo() != null)
            m.setSexo(sexoRepo.findById(dto.getIdSexo()).orElseThrow(() -> new RuntimeException("Sexo no encontrado")));
        return m;
    }
}

