package com.hazlofacil.gestionproductos.service;

import com.hazlofacil.gestionproductos.model.Producto;
import com.hazlofacil.gestionproductos.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoService service;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = new Producto();
        producto.setId(UUID.randomUUID());
        producto.setNombre("Test Producto");
    }

    @Test
    void crearProducto_nombreDuplicado_debeLanzarExcepcion() {
        when(repo.existsByNombre(anyString())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.crearProducto(producto)
        );
        assertEquals("El nombre del producto ya existe", ex.getMessage());
        verify(repo, never()).save(any());
    }

    @Test
    void crearProducto_nombreValido_debeGuardar() {
        when(repo.existsByNombre("TestProduct")).thenReturn(false);
        when(repo.save(producto)).thenReturn(producto);

        Producto creado = service.crearProducto(producto);

        assertNotNull(creado);
        assertEquals("TestProduct", creado.getNombre());
        verify(repo).save(producto);
    }

    @Test
    void obtenerPorId_existe_debeRetornarProducto() {
        UUID id = producto.getId();
        when(repo.findById(id)).thenReturn(java.util.Optional.of(producto));

        Producto found = service.obtenerPorId(id);

        assertSame(producto, found);
        verify(repo).findById(id);
    }

    @Test
    void obtenerPorId_noExiste_debeLanzarNoSuchElement() {
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(
            java.util.NoSuchElementException.class,
            () -> service.obtenerPorId(id)
        );
        verify(repo).findById(id);
    }
}
