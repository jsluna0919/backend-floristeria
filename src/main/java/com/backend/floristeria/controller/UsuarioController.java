package com.backend.floristeria.controller;

import com.backend.floristeria.dto.usuario.UsuarioDTO;
import com.backend.floristeria.dto.usuario.UsuarioMapper;
import com.backend.floristeria.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(UsuarioMapper.toDto(nuevoUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR')")
    @PutMapping("/modificar/{userName}")
    public ResponseEntity<?> modificarUsuario(@PathVariable String userName, @RequestBody UsuarioDTO dto) {
        var modificarUsuario = usuarioService.modificarUsuario(userName, UsuarioMapper.toEntity(dto));
        return ResponseEntity.ok(UsuarioMapper.toDto(modificarUsuario));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR')")
    @GetMapping("/listar")
    public ResponseEntity<?> listarUsuarios() {
        var usuarios = usuarioService.listarUsuarios();
        var dtos = usuarios.stream().map(UsuarioMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR')")
    @DeleteMapping("/eliminar/{username}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String username) {
        usuarioService.eliminarUsuario(username);
        return ResponseEntity.ok("Usuario eliminado");
    }
}