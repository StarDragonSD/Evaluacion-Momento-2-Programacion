package com.evaluacion.api.model.demo.Service;

import com.evaluacion.api.model.demo.Solicitud;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GestorSolicitudes {

    private final List<Solicitud> listaSolicitudes = new ArrayList<>();
    private final Queue<Solicitud> colaAtencion = new LinkedList<>();
    private final Stack<Solicitud> historialAcciones = new Stack<>();

    public void registrarSolicitud(Solicitud solicitud) {
        // Control de flujo: if / else
        if (solicitud.getEstado() == null || solicitud.getEstado().isBlank()) {
            solicitud.setEstado("PENDIENTE");
        }

        listaSolicitudes.add(solicitud);
        colaAtencion.add(solicitud);
        historialAcciones.push(solicitud);
    }

    public Solicitud procesarSiguienteSolicitud() {
        // Control de flujo: while
        while (!colaAtencion.isEmpty()) {
            Solicitud solicitud = colaAtencion.poll();

            // Control de flujo: switch
            switch (solicitud.getEstado()) {
                case "PENDIENTE":
                    solicitud.setEstado("EN_PROCESO");
                    return solicitud;
                case "EN_PROCESO":
                    solicitud.setEstado("RESUELTA");
                    return solicitud;
                default:
                    break;
            }
        }
        return null;
    }

    public List<Solicitud> obtenerLista() {
        return new ArrayList<>(listaSolicitudes);
    }

    public Solicitud obtenerUltimaAccion() {
        return historialAcciones.isEmpty() ? null : historialAcciones.peek();
    }

    // Algoritmo de ordenamiento por ID (Bubble Sort sobre la lista)
    public void ordenarPorId() {
        int n = listaSolicitudes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (listaSolicitudes.get(j).getId() > listaSolicitudes.get(j + 1).getId()) {
                    Solicitud temp = listaSolicitudes.get(j);
                    listaSolicitudes.set(j, listaSolicitudes.get(j + 1));
                    listaSolicitudes.set(j + 1, temp);
                }
            }
        }
    }
}