package com.backend.floristeria.dto.cliente;

import com.backend.floristeria.dto.pedido.PedidoDTO;
import com.backend.floristeria.model.cliente.ClienteEntity;
import com.backend.floristeria.model.pedido.PedidoEntity;

import java.util.List;

public class ClienteMapper {

    public static ClienteDTO toDTO(ClienteEntity entity) {
        if (entity == null) return null;
        return ClienteDTO.builder()
                .id(entity.getId())
                .tipoDocumento(entity.getTipoDocumento())
                .numeroDocumento(entity.getNumeroDocumento())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .ciudad(entity.getCiudad())
                .direccion(entity.getDireccion())
                .fechaCreacion(entity.getFechaCreacion())
                .fechaModificacion(entity.getFechaModificacion())
                .build();
    }
    public static ClienteEntity toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        return ClienteEntity.builder()
                .tipoDocumento(dto.getTipoDocumento())
                .numeroDocumento(dto.getNumeroDocumento())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .ciudad(dto.getCiudad())
                .direccion(dto.getDireccion())
                .build();
    }

    private static List<PedidoDTO> toDTOsPedidos(List<PedidoEntity> pedidos) {
        if (pedidos == null) return null;
        return pedidos.stream()
                .map( pedido -> PedidoDTO.builder()
                        .id(pedido.getId())
                        .fechaCreacion(pedido.getFechaCreacion())
                        .fechaModificacion(pedido.getFechaModificacion())
                        .build())
                .toList();
    }//Utilizar para mostrar la lista de pedidos de los clientes
}