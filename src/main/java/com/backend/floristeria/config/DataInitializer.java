package com.backend.floristeria.config;

import com.backend.floristeria.model.usuario.RolUsuario;
import com.backend.floristeria.model.usuario.UsuarioEntity;
import com.backend.floristeria.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                UsuarioEntity admin = new UsuarioEntity();
                admin.setNombre("Administrador");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRolUsuario(RolUsuario.ADMINISTRADOR);
                usuarioRepository.save(admin);
                System.out.println("Administrador creado"+ admin);
            }
        };
    }
}