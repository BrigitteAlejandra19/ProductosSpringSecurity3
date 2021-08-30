package com.example.productosspringsecurity3.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    
    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getProductos() {
        return productoRepository.findAll();
            }

    public Producto getProductosById(Integer producto_id) {
       Producto producto = productoRepository.findById(producto_id).orElse(null);
        return producto;
    }

    public List<Producto> getProductosContaining(String nombre) {
         List<Producto> productos = productoRepository.findProductosByNombreContaining(nombre);
        return productos;
    }

    public Producto updateProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public String deleteProductoByID(Integer producto_id) {
        if(!productoRepository.existsById(producto_id))
            throw new IllegalStateException("El animal no existe!");
         productoRepository.deleteById(producto_id);
        return ("Producto Eliminado");
    }

    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }
}
