package com.evaluacion.api.model.demo.Controller;

import com.evaluacion.api.model.demo.Cliente;
import com.evaluacion.api.model.demo.Solicitud;
import com.evaluacion.api.model.demo.Usuario;
import com.evaluacion.api.model.demo.repository.SolicitudRepository;
import com.evaluacion.api.model.demo.repository.UsuarioRepository;
import com.evaluacion.api.model.demo.Service.GestorSolicitudes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final GestorSolicitudes gestorSolicitudes;

    public SolicitudController(SolicitudRepository solicitudRepository,
                               UsuarioRepository usuarioRepository,
                               GestorSolicitudes gestorSolicitudes) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.gestorSolicitudes = gestorSolicitudes;
    }

    // DTO simple para recibir la petición desde Postman
    public static class SolicitudRequest {
        private String descripcion;
        private Long clienteId;

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    }

    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudRequest request) {
        if (request.getDescripcion() == null || request.getDescripcion().isBlank()) {
            return ResponseEntity.badRequest().body("La descripción no puede estar vacía.");
        }
        if (request.getClienteId() == null) {
            return ResponseEntity.badRequest().body("Debe proporcionar el clienteId.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.getClienteId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El usuario con ID " + request.getClienteId() + " no existe.");
        }

        Usuario usuario = usuarioOpt.get();
        if (!(usuario instanceof Cliente)) {
            return ResponseEntity.badRequest().body("El usuario asignado no es un Cliente.");
        }

        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion(request.getDescripcion());
        solicitud.setCliente((Cliente) usuario);
        solicitud.setEstado("PENDIENTE");

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

    @PostMapping("/procesar-siguiente")
    public ResponseEntity<?> procesarSiguiente() {
        Solicitud procesada = gestorSolicitudes.procesarSiguienteSolicitud();
        if (procesada != null) {
            solicitudRepository.save(procesada);
            return ResponseEntity.ok(procesada);
        }
        return ResponseEntity.noContent().build();
    }
}