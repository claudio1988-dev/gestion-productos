package com.hazlofacil.gestionproductos.service;

import com.hazlofacil.gestionproductos.model.Version;
import com.hazlofacil.gestionproductos.model.Producto;
import com.hazlofacil.gestionproductos.repository.VersionRepository;
import com.hazlofacil.gestionproductos.repository.ProductoRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Version> listarVersiones() {
        return versionRepository.findAll();
    }

    public Version obtenerPorId(UUID id) {
        return versionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Versión no encontrada"));
    }

    public Version crearVersion(UUID productoId, @Valid Version version) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        version.setProducto(producto);
        return versionRepository.save(version);
    }
    

    public Version actualizarVersion(UUID id, @Valid Version version) {
        Version existente = obtenerPorId(id);
        existente.setNumeroVersion(version.getNumeroVersion());
        existente.setFechaLanzamiento(version.getFechaLanzamiento());
        existente.setEstado(version.getEstado());
        existente.setNotas(version.getNotas());
       
        return versionRepository.save(existente);
    }

    public void eliminarVersion(UUID id) {
        Version existente = obtenerPorId(id);
        versionRepository.delete(existente);
    }

    public List<Version> listarVersionesPorProducto(UUID productoId) {
        return versionRepository.findByProductoId(productoId);
    }
}
