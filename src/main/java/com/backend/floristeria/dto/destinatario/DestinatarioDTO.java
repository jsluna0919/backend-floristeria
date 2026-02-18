package com.backend.floristeria.dto.destinatario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinatarioDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
