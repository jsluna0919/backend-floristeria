package com.backend.floristeria.service;

import com.backend.floristeria.model.usuario.UsuarioEntity;
import com.backend.floristeria.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioEntity registrarUsuario(UsuarioEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity modificarUsuario(String userName, UsuarioEntity usuario) {

        var usuarioExistente = usuarioRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional.ofNullable(usuario.getNombre()).ifPresent(usuarioExistente::setNombre);
        Optional.ofNullable(usuario.getUsername()).ifPresent(usuarioExistente::setUsername);
        Optional.ofNullable(usuario.getPassword()).ifPresent(usuarioExistente::setPassword);
        Optional.ofNullable(usuario.getRolUsuario()).ifPresent(usuarioExistente::setRolUsuario);
        return usuarioRepository.save(usuarioExistente);
    }

    public List<UsuarioEntity> listarUsuarios() {
        var usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("Usuarios no encontrados");
        }
        return usuarios;
    }

    public void eliminarUsuario(String userName) {
        var usuarioExistente = usuarioRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuarioExistente);
    }

}