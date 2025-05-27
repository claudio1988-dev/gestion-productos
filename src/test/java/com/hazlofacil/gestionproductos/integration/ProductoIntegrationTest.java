package com.hazlofacil.gestionproductos.integration;

import com.hazlofacil.gestionproductos.model.Producto;
import com.hazlofacil.gestionproductos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class ProductoIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("gestionprod_test")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductoRepository repo;

    @Test
    void guardarYRecuperarProducto_deberiaFuncionar() {
        Producto p = new Producto();
        p.setNombre("IntegrationTestProduct");
        Producto saved = repo.save(p);

        assertThat(saved.getId()).isNotNull();
        assertThat(repo.findById(saved.getId()))
            .isPresent()
            .get()
            .extracting(Producto::getNombre)
            .isEqualTo("IntegrationTestProduct");
    }
}
