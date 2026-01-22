package ifc33b.dwesc.gestor_series_plataformes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifc33b.dwesc.gestor_series_plataformes.dto.PlataformaResponse;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieRequest;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieResponse;
import ifc33b.dwesc.gestor_series_plataformes.service.GestorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class GestorController {
    @Autowired
    private GestorService gestorService;

    @GetMapping("/plataformes") // Devuelve las plataformas
    public ResponseEntity<List<PlataformaResponse>> getPlataformes() {
        // Service
        List<PlataformaResponse> response = gestorService.getPlataformes();

        // HTTP Response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/series/plataforma/{id}") // Devuelve las series de una plataforma
    public ResponseEntity<List<SerieResponse>> getSeries(@PathVariable Long id) {
        // Service
        List<SerieResponse> response = gestorService.getSeries(id);

        // HTTP Response
        return ResponseEntity.ok(response);
    }

    @PostMapping("/series") // Crea una nueva serie
    public ResponseEntity<SerieResponse> createSerie(@Valid @RequestBody SerieRequest request) {
        // Service
        SerieResponse response = gestorService.createSerie(request);

        // HTTP Response
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}