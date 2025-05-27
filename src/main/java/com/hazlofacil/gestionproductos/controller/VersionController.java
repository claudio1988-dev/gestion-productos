package com.hazlofacil.gestionproductos.controller;

import com.hazlofacil.gestionproductos.model.Version;
import com.hazlofacil.gestionproductos.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Versiones", description = "API para gestionar versiones de productos")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @Operation(summary = "Listar versiones de un producto")
    @GetMapping("/productos/{productoId}/versiones")
    public ResponseEntity<List<Version>> listarPorProducto(@PathVariable UUID productoId) {
        return ResponseEntity.ok(versionService.listarVersionesPorProducto(productoId));
    }

    @Operation(summary = "Obtener una versión por su ID")
    @GetMapping("/versiones/{id}")
    public ResponseEntity<Version> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(versionService.obtenerPorId(id));
    }

    @Operation(summary = "Crear una nueva versión para un producto")
    @PostMapping("/productos/{productoId}/versiones")
    public ResponseEntity<Version> crearVersion(
            @PathVariable UUID productoId,
            @Valid @RequestBody Version version) {
        Version creada = versionService.crearVersion(productoId, version);
        return ResponseEntity.created(URI.create("/api/v1/versiones/" + creada.getId())).body(creada);
    }

    @Operation(summary = "Actualizar una versión existente")
    @PutMapping("/versiones/{id}")
    public ResponseEntity<Version> actualizarVersion(
            @PathVariable UUID id,
            @Valid @RequestBody Version version) {
        return ResponseEntity.ok(versionService.actualizarVersion(id, version));
    }

    @Operation(summary = "Eliminar una versión")
    @DeleteMapping("/versiones/{id}")
    public ResponseEntity<Void> eliminarVersion(@PathVariable UUID id) {
        versionService.eliminarVersion(id);
        return ResponseEntity.noContent().build();
    }
}
