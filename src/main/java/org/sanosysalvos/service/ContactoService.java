package org.sanosysalvos.service;

import lombok.RequiredArgsConstructor;
import org.sanosysalvos.dto.ContactoDTO;
import org.sanosysalvos.model.Contacto;
import org.sanosysalvos.repository.CanalPreferenciaRepository;
import org.sanosysalvos.repository.ContactoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactoService {

    private final ContactoRepository contactoRepo;
    private final CanalPreferenciaRepository canalRepo;

    public List<ContactoDTO> findAll() {
        return contactoRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ContactoDTO findById(Integer id) {
        return contactoRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado con id: " + id));
    }

    public ContactoDTO create(ContactoDTO dto) {
        return toDTO(contactoRepo.save(toEntity(dto)));
    }

    public ContactoDTO update(Integer id, ContactoDTO dto) {
        contactoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado con id: " + id));
        dto.setIdContacto(id);
        return toDTO(contactoRepo.save(toEntity(dto)));
    }

    public void delete(Integer id) {
        contactoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado con id: " + id));
        contactoRepo.deleteById(id);
    }

    private ContactoDTO toDTO(Contacto c) {
        ContactoDTO dto = new ContactoDTO();
        dto.setIdContacto(c.getIdContacto());
        dto.setNombres(c.getNombres());
        dto.setCorreo(c.getCorreo());
        dto.setTelefono(c.getTelefono());
        if (c.getCanalPreferencia() != null) {
            dto.setIdCanalPreferencia(c.getCanalPreferencia().getIdCanalPreferencia());
            dto.setDescripcionCanalPreferencia(c.getCanalPreferencia().getDescripcion());
        }
        return dto;
    }

    private Contacto toEntity(ContactoDTO dto) {
        Contacto c = new Contacto();
        c.setIdContacto(dto.getIdContacto());
        c.setNombres(dto.getNombres());
        c.setCorreo(dto.getCorreo());
        c.setTelefono(dto.getTelefono());
        if (dto.getIdCanalPreferencia() != null)
            c.setCanalPreferencia(canalRepo.findById(dto.getIdCanalPreferencia())
                    .orElseThrow(() -> new RuntimeException("CanalPreferencia no encontrado")));
        return c;
    }
}

