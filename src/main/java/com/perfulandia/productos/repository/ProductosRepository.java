package com.perfulandia.productos.repository;


import com.perfulandia.productos.models.productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<productos, Integer> {
}