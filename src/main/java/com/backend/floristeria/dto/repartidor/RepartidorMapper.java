package com.backend.floristeria.dto.repartidor;

import com.backend.floristeria.dto.pedido.PedidoDTO;
import com.backend.floristeria.model.pedido.PedidoEntity;
import com.backend.floristeria.model.repartidor.RepartidorEntity;

import java.util.List;

public class RepartidorMapper {

    public static RepartidorDTO toDTO(RepartidorEntity entity){
        if (entity == null) return null;
        return RepartidorDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .celular(entity.getCelular())
                .build();
    }

    public static RepartidorEntity toEntity(RepartidorDTO dto){
        if (dto == null) return null;
        return RepartidorEntity.builder()
                .nombre(dto.getNombre())
                .celular(dto.getCelular())
                .build();
    }

    private static List<PedidoDTO> toDTOsPedidos(List<PedidoEntity> pedidos){
        if (pedidos == null) return null;
        return pedidos.stream()
                .map(pedido -> PedidoDTO.builder()
                        .id(pedido.getId())
                        .fechaCreacion(pedido.getFechaCreacion())
                        .fechaModificacion(pedido.getFechaModificacion())
                        .build())
                .toList();
    }// Utilizar para mostrar la lista de pedidos de los repartidores
}