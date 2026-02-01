package ifc33b.dwesc.gestor_series_plataformes.repository;

import ifc33b.dwesc.gestor_series_plataformes.model.Plataforma;
import ifc33b.dwesc.gestor_series_plataformes.model.Serie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class SerieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SerieRepository serieRepository;

    @Test
    @DisplayName("findAll - Should return all series")
    void findAll_ShouldReturnAllSeries() {
        Plataforma plataforma1 = new Plataforma("Test Platform 1");
        Plataforma plataforma2 = new Plataforma("Test Platform 2");

        plataforma1 = entityManager.persistAndFlush(plataforma1);
        plataforma2 = entityManager.persistAndFlush(plataforma2);

        Serie serie1 = new Serie("Test Series 1", "Test Genre 1", plataforma1);
        Serie serie2 = new Serie("Test Series 2", "Test Genre 2", plataforma1);
        Serie serie3 = new Serie("Test Series 3", "Test Genre 3", plataforma2);

        entityManager.persist(serie1);
        entityManager.persist(serie2);
        entityManager.persist(serie3);
        entityManager.flush();
        entityManager.clear();

        List<Serie> result = serieRepository.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(s -> "Test Series 1".equals(s.getTitol())));
        assertTrue(result.stream().anyMatch(s -> "Test Series 2".equals(s.getTitol())));
        assertTrue(result.stream().anyMatch(s -> "Test Series 3".equals(s.getTitol())));
    }

    @Test
    @DisplayName("findAll - Should return empty list when no series exist")
    void findAll_ShouldReturnEmptyListWhenNoSeriesExist() {
        List<Serie> result = serieRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findById - Should return series when exists")
    void findById_ShouldReturnSeriesWhenExists() {
        Plataforma plataforma = new Plataforma("Test Platform");
        plataforma = entityManager.persistAndFlush(plataforma);

        Serie serie = new Serie("Test Series", "Test Genre", plataforma);
        Serie saved = entityManager.persistAndFlush(serie);
        entityManager.clear();

        Optional<Serie> result = serieRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Test Series", result.get().getTitol());
        assertEquals("Test Genre", result.get().getGenere());
        assertEquals(saved.getId(), result.get().getId());
    }

    @Test
    @DisplayName("findById - Should return empty when series does not exist")
    void findById_ShouldReturnEmptyWhenSeriesDoesNotExist() {
        Optional<Serie> result = serieRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("save - Should create new series")
    void save_ShouldCreateNewSeries() {
        Plataforma plataforma = new Plataforma("Test Platform");
        plataforma = entityManager.persistAndFlush(plataforma);

        Serie serie = new Serie("Test Series", "Test Genre", plataforma);
        Serie saved = serieRepository.save(serie);

        assertNotNull(saved.getId());
        assertEquals("Test Series", saved.getTitol());
        assertEquals("Test Genre", saved.getGenere());
        assertEquals(plataforma.getId(), saved.getPlataforma().getId());

        Optional<Serie> found = serieRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Series", found.get().getTitol());
    }

    @Test
    @DisplayName("deleteById - Should delete series")
    void deleteById_ShouldDeleteSeries() {
        Plataforma plataforma = new Plataforma("Test Platform");
        plataforma = entityManager.persistAndFlush(plataforma);

        Serie serie = new Serie("Test Series", "Test Genre", plataforma);
        Serie saved = entityManager.persistAndFlush(serie);
        entityManager.clear();

        serieRepository.deleteById(saved.getId());

        Optional<Serie> found = serieRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("getSeriesInPlataforma - Should return series for specific platform")
    void getSeriesInPlataforma_ShouldReturnSeriesForSpecificPlatform() {
        Plataforma plataforma1 = new Plataforma("Test Platform 1");
        Plataforma plataforma2 = new Plataforma("Test Platform 2");

        plataforma1 = entityManager.persistAndFlush(plataforma1);
        plataforma2 = entityManager.persistAndFlush(plataforma2);

        Serie serie1 = new Serie("Test Series 1", "Test Genre 1", plataforma1);
        Serie serie2 = new Serie("Test Series 2", "Test Genre 2", plataforma1);
        Serie serie3 = new Serie("Test Series 3", "Test Genre 3", plataforma2);

        entityManager.persist(serie1);
        entityManager.persist(serie2);
        entityManager.persist(serie3);
        entityManager.flush();
        entityManager.clear();

        List<Serie> result = serieRepository.getSeriesInPlataforma(plataforma1.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> "Test Series 1".equals(s.getTitol())));
        assertTrue(result.stream().anyMatch(s -> "Test Series 2".equals(s.getTitol())));
        assertFalse(result.stream().anyMatch(s -> "Test Series 3".equals(s.getTitol())));
    }

    @Test
    @DisplayName("getSeriesInPlataforma - Should return empty list when no series for platform")
    void getSeriesInPlataforma_ShouldReturnEmptyListWhenNoSeriesForPlatform() {
        Plataforma plataforma = new Plataforma("Test Platform");
        plataforma = entityManager.persistAndFlush(plataforma);

        List<Serie> result = serieRepository.getSeriesInPlataforma(plataforma.getId());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getSeriesInPlataforma - Should return empty list when platform does not exist")
    void getSeriesInPlataforma_ShouldReturnEmptyListWhenPlatformDoesNotExist() {
        List<Serie> result = serieRepository.getSeriesInPlataforma(999L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
