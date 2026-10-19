package com.evaluacion.api.model.demo.repository;

import com.evaluacion.api.model.demo.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {}
