package com.hazlofacil.gestionproductos.repository;

import com.hazlofacil.gestionproductos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    Optional<Producto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}