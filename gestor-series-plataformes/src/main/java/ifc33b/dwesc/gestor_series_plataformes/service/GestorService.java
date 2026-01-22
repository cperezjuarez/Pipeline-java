package ifc33b.dwesc.gestor_series_plataformes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifc33b.dwesc.gestor_series_plataformes.dto.PlataformaResponse;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieRequest;
import ifc33b.dwesc.gestor_series_plataformes.dto.SerieResponse;
import ifc33b.dwesc.gestor_series_plataformes.exception.PlataformaNotFoundException;
import ifc33b.dwesc.gestor_series_plataformes.model.Plataforma;
import ifc33b.dwesc.gestor_series_plataformes.model.Serie;
import ifc33b.dwesc.gestor_series_plataformes.repository.PlataformaRepository;
import ifc33b.dwesc.gestor_series_plataformes.repository.SerieRepository;

@Service
public class GestorService {
    @Autowired
    PlataformaRepository plataformaRepository;
    @Autowired
    SerieRepository serieRepository;

    // Devuelve todas las plataformas
    public List<PlataformaResponse> getPlataformes() {
        return plataformaRepository.findAll().stream()
                .map(PlataformaResponse::new)
                .collect(Collectors.toList());
    }

    // Devuelve las series de una plataforma
    public List<SerieResponse> getSeries(Long id) {
        return serieRepository.getSeriesInPlataforma(id).stream()
                .map(SerieResponse::new)
                .collect(Collectors.toList());
    }

    // Agregar una nueva serie a una plataforma
    public SerieResponse createSerie(SerieRequest request) {
        Plataforma plataforma = plataformaRepository.findById(request.getPlataformaId())
                .orElseThrow(() -> new PlataformaNotFoundException(request.getPlataformaId()));

        Serie serie = new Serie(request.getTitol(), request.getGenere(), plataforma);
        serieRepository.save(serie);
        return new SerieResponse(serie);
    }
}
