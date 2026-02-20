package com.backend.floristeria.dto.pedido;

import com.backend.floristeria.dto.cliente.ClienteMapper;
import com.backend.floristeria.dto.destinatario.DestinatarioMapper;
import com.backend.floristeria.dto.repartidor.RepartidorMapper;
import com.backend.floristeria.dto.usuario.UsuarioMapper;
import com.backend.floristeria.model.pedido.PedidoEntity;

public class PedidoMapper {

    public static PedidoDTO toDTO(PedidoEntity entity) {
        if (entity == null) return null;
        return PedidoDTO.builder()
                .id(entity.getId())
                .cliente(ClienteMapper.toDTO(entity.getCliente()))
                .destinatario(DestinatarioMapper.toDTO(entity.getDestinatario()))
                .repartidor(RepartidorMapper.toDTO(entity.getRepartidor()))
                .usuario(UsuarioMapper.toDtoResumen(entity.getUsuario()))
                //Arreglo floral
                .motivoArreglo(entity.getMotivoArreglo())
                .arregloFloral(entity.getArregloFloral())
                .descripcion(entity.getDescripcion())
                .anexos(entity.getAnexos())
                .valorArreglo(entity.getValorArreglo())
                .imagen(entity.getImagen())
                .mensaje(entity.getMensaje())
                // Info envio
                .ciudadEnvio(entity.getCiudadEnvio())
                .direccionEnvio(entity.getDireccionEnvio())
                .sector(entity.getSector())
                .observaciones(entity.getObservaciones())
                .valorEnvio(entity.getValorEnvio())
                .fechaEntrega(entity.getFechaEntrega())
                // Info pago
                .formaPago(entity.getFormaPago())
                .estadoPago(entity.getEstadoPago())
                .total(entity.getTotal())
                // Estado pedido
                .estadoPedido(entity.getEstadoPedido())
                // Imagen del arreglo realizado
                .imgArregloRealizado(entity.getImgArregloRealizado())
                //Fecha creacion y modificacion
                .fechaCreacion(entity.getFechaCreacion())
                .fechaModificacion(entity.getFechaModificacion())
                .build();
    } // Mostrar pedido de base de datos

    public static PedidoEntity toEntity(PedidoDTO dto) {
        if (dto == null) return null;
        return PedidoEntity.builder()
                .cliente(ClienteMapper.toEntity(dto.getCliente()))
                .destinatario(DestinatarioMapper.toEntity(dto.getDestinatario()))
                .repartidor(RepartidorMapper.toEntity(dto.getRepartidor()))
                .usuario(UsuarioMapper.toEntity(dto.getUsuario()))
                // Arreglo floral
                .motivoArreglo(dto.getMotivoArreglo())
                .arregloFloral(dto.getArregloFloral())
                .descripcion(dto.getDescripcion())
                .anexos(dto.getAnexos())
                .valorArreglo(dto.getValorArreglo())
                .imagen(dto.getImagen())
                .mensaje(dto.getMensaje())
                // Info envio
                .ciudadEnvio(dto.getCiudadEnvio())
                .direccionEnvio(dto.getDireccionEnvio())
                .sector(dto.getSector())
                .observaciones(dto.getObservaciones())
                .valorEnvio(dto.getValorEnvio())
                .fechaEntrega(dto.getFechaEntrega())
                // Info pago
                .formaPago(dto.getFormaPago())
                .estadoPago(dto.getEstadoPago())
                // Estado pedido
                .estadoPedido(dto.getEstadoPedido())
                .build();
    }// Guardar pedido
}