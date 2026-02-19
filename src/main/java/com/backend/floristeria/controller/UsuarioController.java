package com.backend.floristeria.controller;

import com.backend.floristeria.dto.usuario.UsuarioDTO;
import com.backend.floristeria.dto.usuario.UsuarioMapper;
import com.backend.floristeria.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR')")
    @PostMapping("/auth/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO dto) {
        var nuevoUsuario = usuarioService.registrarUsuario(UsuarioMapper.toEntity(dto));
        return ResponseEntity.ok(nuevoUsuario);
    }
}
