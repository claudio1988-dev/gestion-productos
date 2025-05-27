package com.hazlofacil.gestionproductos.service;

import com.hazlofacil.gestionproductos.model.Producto;
import com.hazlofacil.gestionproductos.repository.ProductoRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(UUID id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
     
        
        return producto;
    }


    public Producto crearProducto(@Valid Producto producto) {
        if (productoRepository.existsByNombre(producto.getNombre())) {
            throw new RuntimeException("El nombre del producto ya existe");
        }
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(UUID id, @Valid Producto producto) {
        Producto existente = obtenerPorId(id);
        existente.setNombre(producto.getNombre());
       
        return productoRepository.save(existente);
    }

    public void eliminarProducto(UUID id) {
        Producto existente = obtenerPorId(id);
        productoRepository.delete(existente);
    }
}
