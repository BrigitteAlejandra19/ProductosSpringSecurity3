package com.example.productosspringsecurity3.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.productosspringsecurity3.security.UserPermission.*;

public enum UserRole {
    ADMIN(Set.of(USER_WRITE, USER_READ, PRODUCTO_READ, PRODUCTO_WRITE)),
    CLIENTE(Set.of(USER_READ, PRODUCTO_READ));

    private final  Set<UserPermission> permissions;



    UserRole(Set<UserPermission> permissions){
    this.permissions = permissions;
         }

     public String getRole(){return this.name();}

    public Set<UserPermission> getPermissions(){
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permisos = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permisos.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permisos;
    }

}
