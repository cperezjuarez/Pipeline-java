package ifc33b.dwesc.gestor_series_plataformes.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GestorControllerTest {

    @Autowired
    private GestorController gestorController;

    @Test
    void getPlataformesReturnsNotNull() {
        var response = gestorController.getPlataformes();
        assertNotNull(response);
        assertNotNull(response.getBody());
    }
}
