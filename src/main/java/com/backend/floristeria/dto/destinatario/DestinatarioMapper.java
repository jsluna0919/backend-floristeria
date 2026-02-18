package com.backend.floristeria.dto.destinatario;

import com.backend.floristeria.dto.pedido.PedidoDTO;
import com.backend.floristeria.model.destinatario.DestinatarioEntity;
import com.backend.floristeria.model.pedido.PedidoEntity;

import java.util.List;

public class DestinatarioMapper {

    public static DestinatarioDTO toDTO(DestinatarioEntity entity){
        if(entity == null) return null;
        return DestinatarioDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .telefono(entity.getTelefono())
                .fechaCreacion(entity.getFechaCreacion())
                .fechaModificacion(entity.getFechaModificacion())
                .build();
    }

    public static DestinatarioEntity toEntity(DestinatarioDTO dto){
        if (dto == null) return null;
        return DestinatarioEntity.builder()
                .nombre(dto.getNombre())
                .telefono(dto.getTelefono())
                .build();
    }

    public static List<PedidoDTO> toDTOsPedidos(List<PedidoEntity> pedidos){
        if (pedidos == null) return null;
        return pedidos.stream()
                .map(pedido -> PedidoDTO.builder()
                        .id(pedido.getId())
                        .fechaCreacion(pedido.getFechaCreacion())
                        .fechaModificacion(pedido.getFechaModificacion())
                        .build())
                .toList();
    }// Utilizar para mostrar la lista de pedidos de los clientes
}
