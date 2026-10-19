package com.evaluacion.api.model.demo.Controller;

import com.evaluacion.api.model.demo.Solicitud;
import com.evaluacion.api.model.demo.repository.SolicitudRepository;
import com.evaluacion.api.model.demo.Service.GestorSolicitudes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudRepository solicitudRepository;
    private final GestorSolicitudes gestorSolicitudes;

    public SolicitudController(SolicitudRepository solicitudRepository, GestorSolicitudes gestorSolicitudes) {
        this.solicitudRepository = solicitudRepository;
        this.gestorSolicitudes = gestorSolicitudes;
    }

    @PostMapping
    public ResponseEntity<Solicitud> crearSolicitud(@Valid @RequestBody Solicitud solicitud) {
        Solicitud nuevaSolicitud = solicitudRepository.save(solicitud);
        gestorSolicitudes.registrarSolicitud(nuevaSolicitud);
        return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Solicitud>> obtenerSolicitudes(Pageable pageable) {
        return ResponseEntity.ok(solicitudRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtenerSolicitudPorId(@PathVariable Long id) {
        return solicitudRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}