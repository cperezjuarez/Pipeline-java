package ifc33b.dwesc.gestor_series_plataformes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "serie")
public class Serie {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Se requiere un titulo para la serie")
    @Size(min = 3, max = 25, message = "El título debe de tener entre 3 y 25 caracteres")
    @Column(nullable = false)
    private String titol;
    
    @NotBlank(message = "Se requiere un genero para la serie")
    @Size(min = 3, max = 25, message = "El genero debe de tener entre 3 y 25 caracteres")
    @Column(nullable = false)
    private String genere;

    @NotNull(message = "Se requiere plataforma")
    @ManyToOne
    @JoinColumn(name = "plataforma_id", nullable = false)
    private Plataforma plataforma;

    // Constructores
    public Serie() {}

    public Serie(String titol, String genere, Plataforma plataforma) {
        this.setTitol(titol);
        this.setGenere(genere);
        this.setPlataforma(plataforma);
    }
}
