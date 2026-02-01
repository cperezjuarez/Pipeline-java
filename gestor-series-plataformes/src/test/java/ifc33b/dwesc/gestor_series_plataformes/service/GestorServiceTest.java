package ifc33b.dwesc.gestor_series_plataformes.service;

import ifc33b.dwesc.gestor_series_plataformes.dto.PlataformaResponse;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieRequest;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieResponse;
import ifc33b.dwesc.gestor_series_plataformes.exception.PlataformaNotFoundException;
import ifc33b.dwesc.gestor_series_plataformes.model.Plataforma;
import ifc33b.dwesc.gestor_series_plataformes.model.Serie;
import ifc33b.dwesc.gestor_series_plataformes.repository.PlataformaRepository;
import ifc33b.dwesc.gestor_series_plataformes.repository.SerieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestorServiceTest {

    @Mock
    private PlataformaRepository plataformaRepository;

    @Mock
    private SerieRepository serieRepository;

    @InjectMocks
    private GestorService gestorService;

    private Plataforma plataforma1;
    private Plataforma plataforma2;
    private Serie serie1;
    private Serie serie2;
    private SerieRequest serieRequest;

    @BeforeEach
    void setUp() {
        plataforma1 = new Plataforma("Netflix");
        plataforma1.setId(1L);

        plataforma2 = new Plataforma("HBO Max");
        plataforma2.setId(2L);

        serie1 = new Serie("Stranger Things", "Ciencia Ficción", plataforma1);
        serie1.setId(1L);

        serie2 = new Serie("Game of Thrones", "Fantasía", plataforma2);
        serie2.setId(2L);

        serieRequest = new SerieRequest("The Witcher", "Fantasía", 1L);
    }

    @Test
    @DisplayName("getPlataformes - Should return list of platform responses")
    void getPlataformes_ShouldReturnListOfPlatformResponses() {
        List<Plataforma> plataformas = Arrays.asList(plataforma1, plataforma2);
        when(plataformaRepository.findAll()).thenReturn(plataformas);

        List<PlataformaResponse> result = gestorService.getPlataformes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Netflix", result.get(0).getNom());
        assertEquals(2L, result.get(1).getId());
        assertEquals("HBO Max", result.get(1).getNom());
        verify(plataformaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getPlataformes - Should return empty list when no platforms exist")
    void getPlataformes_ShouldReturnEmptyListWhenNoPlatformsExist() {
        when(plataformaRepository.findAll()).thenReturn(Arrays.asList());

        List<PlataformaResponse> result = gestorService.getPlataformes();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(plataformaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getSeries - Should return list of series responses for valid platform id")
    void getSeries_ShouldReturnListOfSeriesResponsesForValidPlatformId() {
        when(plataformaRepository.findById(1L)).thenReturn(Optional.of(plataforma1));
        List<Serie> series = Arrays.asList(serie1);
        when(serieRepository.getSeriesInPlataforma(1L)).thenReturn(series);

        List<SerieResponse> result = gestorService.getSeries(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Stranger Things", result.get(0).getTitol());
        assertEquals("Ciencia Ficción", result.get(0).getGenere());
        assertEquals(1L, result.get(0).getPlataformaId());
        verify(serieRepository, times(1)).getSeriesInPlataforma(1L);
    }

    @Test
    @DisplayName("getSeries - Should return empty list when no series exist for platform")
    void getSeries_ShouldReturnEmptyListWhenNoSeriesExistForPlatform() {
        when(plataformaRepository.findById(1L)).thenReturn(Optional.of(plataforma1));
        when(serieRepository.getSeriesInPlataforma(1L)).thenReturn(Arrays.asList());

        List<SerieResponse> result = gestorService.getSeries(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(serieRepository, times(1)).getSeriesInPlataforma(1L);
    }

    @Test
    @DisplayName("createSerie - Should create and return new series response when platform exists")
    void createSerie_ShouldCreateAndReturnNewSeriesResponseWhenPlatformExists() {
        when(plataformaRepository.findById(1L)).thenReturn(Optional.of(plataforma1));

        Serie savedSerie = new Serie("The Witcher", "Fantasía", plataforma1);
        savedSerie.setId(1L);
        when(serieRepository.save(any(Serie.class))).thenReturn(savedSerie);

        SerieResponse result = gestorService.createSerie(serieRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("The Witcher", result.getTitol());
        assertEquals("Fantasía", result.getGenere());
        assertEquals(1L, result.getPlataformaId());
        verify(plataformaRepository, times(1)).findById(1L);
        verify(serieRepository, times(1)).save(any(Serie.class));
    }

    @Test
    @DisplayName("createSerie - Should throw PlataformaNotFoundException when platform does not exist")
    void createSerie_ShouldThrowPlataformaNotFoundExceptionWhenPlatformDoesNotExist() {
        when(plataformaRepository.findById(999L)).thenReturn(Optional.empty());

        SerieRequest invalidRequest = new SerieRequest("The Witcher", "Fantasía", 999L);

        PlataformaNotFoundException exception = assertThrows(
                PlataformaNotFoundException.class,
                () -> gestorService.createSerie(invalidRequest));

        assertEquals("No se ha encontrado la plataforma con la ID: 999", exception.getMessage());
        verify(plataformaRepository, times(1)).findById(999L);
        verify(serieRepository, never()).save(any(Serie.class));
    }

    @Test
    @DisplayName("createSerie - Should create series with correct platform association")
    void createSerie_ShouldCreateSeriesWithCorrectPlatformAssociation() {
        Serie newSerie = new Serie("The Witcher", "Fantasía", plataforma1);
        newSerie.setId(3L);

        when(plataformaRepository.findById(1L)).thenReturn(Optional.of(plataforma1));
        when(serieRepository.save(any(Serie.class))).thenReturn(newSerie);

        SerieResponse result = gestorService.createSerie(serieRequest);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("The Witcher", result.getTitol());
        assertEquals("Fantasía", result.getGenere());
        assertEquals(1L, result.getPlataformaId());
        verify(plataformaRepository, times(1)).findById(1L);
        verify(serieRepository, times(1)).save(any(Serie.class));
    }
}
