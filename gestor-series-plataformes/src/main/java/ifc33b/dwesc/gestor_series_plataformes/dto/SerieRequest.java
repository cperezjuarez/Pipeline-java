package ifc33b.dwesc.gestor_series_plataformes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SerieRequest {
    // Atributos
    @NotBlank(message = "Se requiere un titulo para la serie")
    @Size(min = 3, max = 25, message = "El título debe de tener entre 3 y 25 caracteres")
    private String titol;
    
    @NotBlank(message = "Se requiere un genero para la serie")
    @Size(min = 3, max = 25, message = "El genero debe de tener entre 3 y 25 caracteres")
    private String genere;

    @NotNull(message = "Se requiere plataforma")
    private Long plataformaId;

    // Constructor
    public SerieRequest(String titulo, String genere, Long plataformaId) {
        this.setTitol(titulo);
        this.setGenere(genere);
        this.setPlataformaId(plataformaId);
    }
}
