package com.perfulandia.productos.controller;

import com.perfulandia.productos.dto.productosDTO;
import com.perfulandia.productos.services.ProductosServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductosController {

    @Autowired
    private ProductosServices service;


    @PostMapping
    public productosDTO crear(@RequestBody productosDTO dto) {
        productosDTO creado = service.crear(dto);
        creado.add(linkTo(methodOn(ProductosController.class).obtenerHATEOAS(creado.getId())).withSelfRel());
        creado.add(linkTo(methodOn(ProductosController.class).obtenerTodosHATEOAS()).withRel("todos"));
        return creado;
    }

    @GetMapping("/hateoas/{id}")
    public productosDTO obtenerHATEOAS(@PathVariable Integer id) {
        productosDTO dto = service.obtenerPorId(id);
        if (dto != null) {
            dto.add(linkTo(methodOn(ProductosController.class).obtenerHATEOAS(id)).withSelfRel());
            dto.add(linkTo(methodOn(ProductosController.class).obtenerTodosHATEOAS()).withRel("todos"));
            dto.add(linkTo(methodOn(ProductosController.class).eliminar(id)).withRel("eliminar"));
        }
        return dto;
    }

    @GetMapping("/hateoas")
    public List<productosDTO> obtenerTodosHATEOAS() {
        List<productosDTO> lista = service.listar();
        for (productosDTO dto : lista) {
            dto.add(linkTo(methodOn(ProductosController.class).obtenerHATEOAS(dto.getId())).withSelfRel());
        }
        return lista;
    }

  
    @GetMapping("/{id}")
    public productosDTO getProducto(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }


    @PutMapping("/{id}")
    public productosDTO actualizar(@PathVariable Integer id, @RequestBody productosDTO dto) {
        productosDTO actualizado = service.actualizar(id, dto);
        actualizado.add(linkTo(methodOn(ProductosController.class).obtenerHATEOAS(id)).withSelfRel());
        actualizado.add(linkTo(methodOn(ProductosController.class).obtenerTodosHATEOAS()).withRel("todos"));
        return actualizado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
