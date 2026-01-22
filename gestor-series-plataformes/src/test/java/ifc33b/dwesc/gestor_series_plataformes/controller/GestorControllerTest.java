package ifc33b.dwesc.gestor_series_plataformes.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ifc33b.dwesc.gestor_series_plataformes.dto.SerieRequest;
import ifc33b.dwesc.gestor_series_plataformes.model.Plataforma;

@SpringBootTest
public class GestorControllerTest {

    @Autowired
    private GestorController gestorController;

    // === GET /api/plataformes ===
    @Test
    void getPlataformes_devuelveListaNoNula() {
        var response = gestorController.getPlataformes();
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    void getPlataformes_devuelveListaVaciaCorrectamente() {
        var response = gestorController.getPlataformes();
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getPlataformes_status200() {
        var response = gestorController.getPlataformes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getPlataformes_devuelveTodasLasPlataformas() {
        var response = gestorController.getPlataformes();
        assertEquals(3, response.getBody().size());
    }

    // === GET /api/series/plataforma/{id} ===
    @Test
    void getSeries_devuelveListaNoNulaParaIdValido() {
        var response = gestorController.getSeries(1L);
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    void getSeries_devuelveSeriesCorrectasParaIdValido() {
        var response = gestorController.getSeries(1L);
        assertEquals(5, response.getBody().size());
    }

    @Test
    void getSeries_manejaIdInexistente() {
        var response = gestorController.getSeries(999L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getSeries_manejaIdInvalido() {
        var response = gestorController.getSeries(-1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getSeries_status200() {
        var response = gestorController.getSeries(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // === POST /api/series ===
    @Test
    void createSerie_creaConDatosValidos() {
        SerieRequest request = new SerieRequest("Test", "Test", 1L); // ajusta campos
        var response = gestorController.createSerie(request);
        assertNotNull(response.getBody());
    }

    @Test
    void createSerie_devuelveSerieCreadaNoNula() {
        SerieRequest request = new SerieRequest("Test", "Test", 1L);
        var response = gestorController.createSerie(request);
        assertNotNull(response.getBody());
    }

    @Test
    void createSerie_status201() {
        SerieRequest request = new SerieRequest("Test", "Test", 1l);
        var response = gestorController.createSerie(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    // @Test
    // void createSerie_rechazaDatosInvalidos() {
    //     SerieRequest invalidRequest = new SerieRequest("", "", 1l); // viola @Valid
    //     // Espera excepción o status 400 (depende config)
    //     assertThrows(ConstraintViolationException.class,
    //             () -> gestorController.createSerie(invalidRequest));
    // }

    // @Test
    // void createSerie_manejaBodyMalformado() {
    //     SerieRequest malformed = null;
    //     assertThrows(IllegalArgumentException.class,
    //             () -> gestorController.createSerie(malformed));
    // }
}
