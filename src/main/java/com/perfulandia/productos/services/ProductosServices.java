package com.perfulandia.productos.services;

import com.perfulandia.productos.models.productos;
import com.perfulandia.productos.dto.productosDTO;
import com.perfulandia.productos.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductosServices {

    @Autowired
    private ProductosRepository productoRepository;

    private productosDTO toDTO(productos producto) {
        return new productosDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioUnitario(),
                producto.getCategoria(),
                producto.getActivo()
        );
    }

    private productos toEntity(productosDTO dto) {
        productos producto = new productos();
        producto.setId(dto.getId()); // importante para actualizar
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(dto.getActivo());
        return producto;
    }

    public productosDTO crear(productosDTO dto) {
        productos producto = toEntity(dto);
        return toDTO(productoRepository.save(producto));
    }

    public List<productosDTO> listar() {
        return productoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public productosDTO obtenerPorId(Integer id) {
        productos producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("productos no encontrado"));
        return toDTO(producto);
    }

    public productosDTO actualizar(Integer id, productosDTO dto) {
        productos existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("productos no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrecioUnitario(dto.getPrecioUnitario());
        existente.setCategoria(dto.getCategoria());
        existente.setActivo(dto.getActivo());

        return toDTO(productoRepository.save(existente));
    }

    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }
}