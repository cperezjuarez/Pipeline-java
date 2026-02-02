package ifc33b.dwesc.gestor_series_plataformes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GestorSeriesPlataformesApplication
        extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GestorSeriesPlataformesApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GestorSeriesPlataformesApplication.class, args);
    }
}
