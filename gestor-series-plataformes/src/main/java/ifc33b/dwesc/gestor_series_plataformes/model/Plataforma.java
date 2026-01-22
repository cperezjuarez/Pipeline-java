package ifc33b.dwesc.gestor_series_plataformes.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "plataforma")
public class Plataforma {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Se requiere un nombre para la plataforma")
    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "plataforma", cascade = CascadeType.ALL)
    private List<Serie> series = new ArrayList<>();

    // Constructores
    public Plataforma() {}

    public Plataforma(String nom) {
        this.setNom(nom);
    }
}
