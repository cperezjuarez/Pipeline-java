package ifc33b.dwesc.gestor_series_plataformes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ifc33b.dwesc.gestor_series_plataformes.dto.PlataformaResponse;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieRequest;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieResponse;
import ifc33b.dwesc.gestor_series_plataformes.model.Plataforma;
import ifc33b.dwesc.gestor_series_plataformes.model.Serie;
import ifc33b.dwesc.gestor_series_plataformes.service.GestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestorController.class)
@ActiveProfiles("test")
class GestorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestorService gestorService;

    @Autowired
    private ObjectMapper objectMapper;

    private PlataformaResponse plataforma1;
    private PlataformaResponse plataforma2;
    private SerieResponse serie1;
    private SerieRequest serieRequest;

    @BeforeEach
    void setUp() {
        plataforma1 = new PlataformaResponse(new Plataforma());
        plataforma1.setId(1L);
        plataforma1.setNom("Netflix");

        plataforma2 = new PlataformaResponse(new Plataforma());
        plataforma2.setId(2L);
        plataforma2.setNom("HBO Max");

        Plataforma plataforma1Model = new Plataforma();
        plataforma1Model.setId(1L);
        Serie serie1Model = new Serie("Stranger Things", "Ciencia Ficción", plataforma1Model);
        serie1Model.setId(1L);
        serie1 = new SerieResponse(serie1Model);

        serieRequest = new SerieRequest("The Witcher", "Fantasía", 1L);
    }

    @Test
    @DisplayName("GET /api/plataformes - Should return list of platforms")
    void getPlataformes_ShouldReturnListOfPlatforms() throws Exception {
        List<PlataformaResponse> plataformas = Arrays.asList(plataforma1, plataforma2);
        when(gestorService.getPlataformes()).thenReturn(plataformas);

        mockMvc.perform(get("/api/plataformes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nom").value("Netflix"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nom").value("HBO Max"));
    }

    @Test
    @DisplayName("GET /api/plataformes - Should return empty list when no platforms exist")
    void getPlataformes_ShouldReturnEmptyListWhenNoPlatformsExist() throws Exception {
        when(gestorService.getPlataformes()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/plataformes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/series/plataforma/{id} - Should return series for valid platform id")
    void getSeries_ShouldReturnSeriesForValidPlatformId() throws Exception {
        List<SerieResponse> series = Arrays.asList(serie1);
        when(gestorService.getSeries(1L)).thenReturn(series);

        mockMvc.perform(get("/api/series/plataforma/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titol").value("Stranger Things"))
                .andExpect(jsonPath("$[0].genere").value("Ciencia Ficción"))
                .andExpect(jsonPath("$[0].plataformaId").value(1));
    }

    @Test
    @DisplayName("GET /api/series/plataforma/{id} - Should return empty list when no series exist for platform")
    void getSeries_ShouldReturnEmptyListWhenNoSeriesExistForPlatform() throws Exception {
        when(gestorService.getSeries(999L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/series/plataforma/{id}", 999L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("POST /api/series - Should create new series and return 201")
    void createSerie_ShouldCreateNewSeriesAndReturn201() throws Exception {
        SerieResponse newSerie = new SerieResponse(new Serie("The Witcher", "Fantasía", new Plataforma()));
        newSerie.setId(3L);
        newSerie.setTitol("The Witcher");
        newSerie.setGenere("Fantasía");
        newSerie.setPlataformaId(1L);

        when(gestorService.createSerie(any(SerieRequest.class))).thenReturn(newSerie);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serieRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.titol").value("The Witcher"))
                .andExpect(jsonPath("$.genere").value("Fantasía"))
                .andExpect(jsonPath("$.plataformaId").value(1));
    }

    @Test
    @DisplayName("POST /api/series - Should return 400 when request body is invalid")
    void createSerie_ShouldReturn400WhenRequestBodyIsInvalid() throws Exception {
        SerieRequest invalidRequest = new SerieRequest("", "", null);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/series - Should return 400 when title is too short")
    void createSerie_ShouldReturn400WhenTitleIsTooShort() throws Exception {
        SerieRequest invalidRequest = new SerieRequest("AB", "Fantasía", 1L);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/series - Should return 400 when title is too long")
    void createSerie_ShouldReturn400WhenTitleIsTooLong() throws Exception {
        SerieRequest invalidRequest = new SerieRequest("A".repeat(26), "Fantasía", 1L);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/series - Should return 400 when genre is too short")
    void createSerie_ShouldReturn400WhenGenreIsTooShort() throws Exception {
        SerieRequest invalidRequest = new SerieRequest("The Witcher", "AB", 1L);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/series - Should return 400 when genre is too long")
    void createSerie_ShouldReturn400WhenGenreIsTooLong() throws Exception {
        SerieRequest invalidRequest = new SerieRequest("The Witcher", "A".repeat(26), 1L);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/series - Should return 400 when plataformaId is null")
    void createSerie_ShouldReturn400WhenPlataformaIdIsNull() throws Exception {
        SerieRequest invalidRequest = new SerieRequest("The Witcher", "Fantasía", null);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
