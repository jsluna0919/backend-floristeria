package com.backend.floristeria.dto.repartidor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepartidorDTO {
    private Long id;
    private String nombre;
    private String celular;
}
