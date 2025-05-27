package com.hazlofacil.gestionproductos.repository;

import com.hazlofacil.gestionproductos.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface VersionRepository extends JpaRepository<Version, UUID> {

    boolean existsByNombreAndProductoId(String nombre, UUID productoId);

    List<Version> findByProductoId(UUID productoId);
}
