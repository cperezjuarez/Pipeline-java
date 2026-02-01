package ifc33b.dwesc.gestor_series_plataformes.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieRequest;
import ifc33b.dwesc.gestor_series_plataformes.model.Plataforma;
import ifc33b.dwesc.gestor_series_plataformes.repository.PlataformaRepository;
import ifc33b.dwesc.gestor_series_plataformes.repository.SerieRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class IntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        serieRepository.deleteAll();
        plataformaRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        serieRepository.deleteAll();
        plataformaRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /api/plataformes - Should return all platforms")
    void getPlataformas_ShouldReturnAllPlatforms() throws Exception {
        Plataforma plataforma1 = new Plataforma("Netflix");
        Plataforma plataforma2 = new Plataforma("HBO Max");

        plataformaRepository.save(plataforma1);
        plataformaRepository.save(plataforma2);

        mockMvc.perform(get("/api/plataformes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[?(@.nom == 'Netflix')]").exists())
                .andExpect(jsonPath("$[?(@.nom == 'HBO Max')]").exists());
    }

    @Test
    @DisplayName("GET /api/plataformes - Should return empty list when no platforms exist")
    void getPlataformes_ShouldReturnEmptyListWhenNoPlatformsExist() throws Exception {
        mockMvc.perform(get("/api/plataformes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/series/plataforma/{id} - Should return series for platform")
    void getSeriesByPlataforma_ShouldReturnSeriesForPlatform() throws Exception {
        Plataforma plataforma = new Plataforma("Netflix");
        plataforma = plataformaRepository.save(plataforma);

        mockMvc.perform(get("/api/series/plataforma/{id}", plataforma.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/series/plataforma/{id} - Should return 404 when platform does not exist")
    void getSeriesByPlataforma_ShouldReturn404WhenPlatformDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/series/plataforma/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/series - Should create new series")
    void createSerie_ShouldCreateNewSeries() throws Exception {
        Plataforma plataforma = new Plataforma("Netflix");
        plataforma = plataformaRepository.save(plataforma);

        SerieRequest request = new SerieRequest("Stranger Things", "Sci-Fi", plataforma.getId());

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titol").value("Stranger Things"))
                .andExpect(jsonPath("$.genere").value("Sci-Fi"))
                .andExpect(jsonPath("$.plataformaId").value(plataforma.getId()));

        List<ifc33b.dwesc.gestor_series_plataformes.model.Serie> series = serieRepository.findAll();
        assertEquals(1, series.size());
        assertEquals("Stranger Things", series.get(0).getTitol());
        assertEquals("Sci-Fi", series.get(0).getGenere());
    }

    @Test
    @DisplayName("POST /api/series - Should return 400 when validation fails")
    void createSerie_ShouldReturn400WhenValidationFails() throws Exception {
        SerieRequest request = new SerieRequest("", "", null);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/series - Should return 404 when platform does not exist")
    void createSerie_ShouldReturn404WhenPlatformDoesNotExist() throws Exception {
        SerieRequest request = new SerieRequest("Stranger Things", "Sci-Fi", 999L);

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Complete flow - Create platform, create series, get series")
    void completeFlow_ShouldWorkEndToEnd() throws Exception {
        Plataforma plataforma = new Plataforma("Netflix");
        plataforma = plataformaRepository.save(plataforma);

        SerieRequest request = new SerieRequest("Stranger Things", "Sci-Fi", plataforma.getId());

        mockMvc.perform(post("/api/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/series/plataforma/{id}", plataforma.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titol").value("Stranger Things"))
                .andExpect(jsonPath("$[0].genere").value("Sci-Fi"));
    }
}
