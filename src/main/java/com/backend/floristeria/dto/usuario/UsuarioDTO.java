package com.backend.floristeria.dto.usuario;

import com.backend.floristeria.model.usuario.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String username;
    private String password;
    private RolUsuario rolUsuario;
}
