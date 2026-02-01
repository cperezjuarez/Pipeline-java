package ifc33b.dwesc.gestor_series_plataformes.repository;

import ifc33b.dwesc.gestor_series_plataformes.model.Plataforma;
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
class PlataformaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Test
    @DisplayName("findAll - Should return all platforms")
    void findAll_ShouldReturnAllPlatforms() {
        Plataforma plataforma1 = new Plataforma("Test Platform 1");
        Plataforma plataforma2 = new Plataforma("Test Platform 2");

        entityManager.persist(plataforma1);
        entityManager.persist(plataforma2);
        entityManager.flush();
        entityManager.clear();

        List<Plataforma> result = plataformaRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> "Test Platform 1".equals(p.getNom())));
        assertTrue(result.stream().anyMatch(p -> "Test Platform 2".equals(p.getNom())));
    }

    @Test
    @DisplayName("findAll - Should return empty list when no platforms exist")
    void findAll_ShouldReturnEmptyListWhenNoPlatformsExist() {
        List<Plataforma> result = plataformaRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findById - Should return platform when exists")
    void findById_ShouldReturnPlatformWhenExists() {
        Plataforma plataforma = new Plataforma("Test Platform");
        Plataforma saved = entityManager.persistAndFlush(plataforma);
        entityManager.clear();

        Optional<Plataforma> result = plataformaRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Test Platform", result.get().getNom());
        assertEquals(saved.getId(), result.get().getId());
    }

    @Test
    @DisplayName("findById - Should return empty when platform does not exist")
    void findById_ShouldReturnEmptyWhenPlatformDoesNotExist() {
        Optional<Plataforma> result = plataformaRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("save - Should create new platform")
    void save_ShouldCreateNewPlatform() {
        Plataforma plataforma = new Plataforma("Test Platform");
        Plataforma saved = plataformaRepository.save(plataforma);

        assertNotNull(saved.getId());
        assertEquals("Test Platform", saved.getNom());

        Optional<Plataforma> found = plataformaRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Platform", found.get().getNom());
    }

    @Test
    @DisplayName("deleteById - Should delete platform")
    void deleteById_ShouldDeletePlatform() {
        Plataforma plataforma = new Plataforma("Test Platform");
        Plataforma saved = entityManager.persistAndFlush(plataforma);
        entityManager.clear();

        plataformaRepository.deleteById(saved.getId());

        Optional<Plataforma> found = plataformaRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}
