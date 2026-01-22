package ifc33b.dwesc.gestor_series_plataformes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ifc33b.dwesc.gestor_series_plataformes.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    // Obtener las series de una plataforma
    @Query("SELECT s FROM Serie s WHERE s.plataforma.id = :plataformaId")
    List<Serie> getSeriesInPlataforma(@Param("plataformaId") Long plataformaId);
}
