package ifc33b.dwesc.gestor_series_plataformes.dto;

import ifc33b.dwesc.gestor_series_plataformes.model.Serie;
import lombok.Data;

@Data
public class SerieResponse {
    // Atributos
    private Long id;
    private String titol;
    private String genere;
    private Long plataformaId;

    // Constructores
    public SerieResponse(Serie serie) {
        this.setId(serie.getId());
        this.setTitol(serie.getTitol());
        this.setGenere(serie.getGenere());
        this.setPlataformaId(serie.getPlataforma().getId());
    }
}
