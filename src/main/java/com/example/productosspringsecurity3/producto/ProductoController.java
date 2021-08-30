package com.example.productosspringsecurity3.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;}

    @GetMapping
    private List<Producto> getProductos () { return productoService.getProductos();}

    @GetMapping("{producto_id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTE')")
    private Producto getproductosById (@PathVariable Integer producto_id) { return productoService.getProductosById(producto_id);}

    @GetMapping ("/buscar/{nombre}")
    @PreAuthorize("hasAnyAuthority('producto:write','producto:read')")
    private List<Producto> getProductosByNombreContaining(@PathVariable String nombre) {return productoService.getProductosContaining(nombre);}

    @PutMapping ("/update")
    @PreAuthorize("hasAnyAuthority('producto:write')")
    private Producto updateProductoById(@RequestBody Producto producto) {return  productoService.updateProducto(producto);}

    @DeleteMapping("{producto_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private void deleteProductoById(@PathVariable Integer id){productoService.deleteProductoByID(id);}

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('producto:write')")
    private Producto createProducto(@RequestBody Producto producto) {return  productoService.createProducto(producto);}


    @GetMapping("/mi-rol")
    public String getMirol(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return  securityContext.getAuthentication().getAuthorities().toString();
    }
  }
