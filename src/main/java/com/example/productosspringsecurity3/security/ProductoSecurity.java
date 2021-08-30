package com.example.productosspringsecurity3.security;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ProductoSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index.html").permitAll()
                //.antMatchers(HttpMethod.PUT, "/api/productos/**").hasAnyAuthority(UserPermission.PRODUCTO_WRITE.getPermission())
                //.antMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority(UserPermission.PRODUCTO_WRITE.getPermission())
                //.antMatchers(HttpMethod.DELETE, "/api/productos/**").hasAnyRole(ADMIN.getRole())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails usuario1 = User.builder()
                .username("pablo")
                .password(passwordEncoder().encode("password"))
                .authorities(UserRole.ADMIN.getGrantedAuthorities())
                //.roles(UserRole.ADMIN.name())
                .build();
        UserDetails usuario2 = User.builder()
                .username("bri")
                .password(passwordEncoder().encode("password"))
                .authorities(UserRole.ADMIN.getGrantedAuthorities())
                //.roles(UserRole.CLIENTE.name())
                .build();
        UserDetails usuario3= User.builder()
                .username("kel")
                .password(passwordEncoder().encode("password"))
                .authorities(UserRole.CLIENTE.getGrantedAuthorities())
                //.roles(UserRole.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3);
    }
}
