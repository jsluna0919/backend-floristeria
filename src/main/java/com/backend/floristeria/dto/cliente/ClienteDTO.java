package com.backend.floristeria.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String ciudad;
    private String direccion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
