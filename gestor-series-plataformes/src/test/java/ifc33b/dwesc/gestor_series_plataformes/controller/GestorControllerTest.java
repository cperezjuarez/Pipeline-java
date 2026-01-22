package ifc33b.dwesc.gestor_series_plataformes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ifc33b.dwesc.gestor_series_plataformes.dto.PlataformaResponse;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieRequest;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieResponse;
import ifc33b.dwesc.gestor_series_plataformes.service.GestorService;

@WebMvcTest(GestorController.class)
class GestorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestorService gestorService;

    @Test
    void getPlataformes_returnsList() throws Exception {
        when(gestorService.getPlataformes()).thenReturn(List.of());

        mockMvc.perform(get("/api/plataformes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createSerie_returnsCreated() throws Exception {
        SerieRequest request = new SerieRequest("Test Serie", "test-desc", 1L);
        SerieResponse response = new SerieResponse();

        when(gestorService.createSerie(any(SerieRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/series")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"nombre\":\"Test\",\"descripcion\":\"test\",\"plataformaId\":1}"))
               .andExpect(status().isCreated());
    }

    @Test
    void getSeriesByPlataforma_returnsList() throws Exception {
        Long plataformaId = 1L;
        SerieResponse serie = new SerieResponse();
        when(gestorService.getSeries(plataformaId)).thenReturn(List.of(serie));

        mockMvc.perform(get("/api/series/plataforma/{id}", plataformaId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getSeriesByPlataforma_returnsEmptyList() throws Exception {
        Long plataformaId = 99L;
        when(gestorService.getSeries(plataformaId)).thenReturn(List.of());

        mockMvc.perform(get("/api/series/plataforma/{id}", plataformaId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void createSerie_invalidRequest_returnsBadRequest() throws Exception {
        // Missing required fields
        mockMvc.perform(post("/api/series")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{}"))
               .andExpect(status().isBadRequest());
    }
}