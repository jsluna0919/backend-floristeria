package com.backend.floristeria.dto.usuario;

import com.backend.floristeria.dto.pedido.PedidoDTO;
import com.backend.floristeria.model.pedido.PedidoEntity;
import com.backend.floristeria.model.usuario.UsuarioEntity;

import java.util.List;

public class UsuarioMapper {

    public static UsuarioDTO toDto(UsuarioEntity entity){
        if(entity == null)return null;
        return UsuarioDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .rolUsuario(entity.getRolUsuario())
                .build();
    }

    public static UsuarioEntity toEntity(UsuarioDTO dto){
        if(dto == null)return null;
        return UsuarioEntity.builder()
                .nombre(dto.getNombre())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .rolUsuario(dto.getRolUsuario())
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
    }//Utilizar para mostrar la lista de pedidos de los usuarios
}