package com.backend.floristeria.service;

import com.backend.floristeria.model.cliente.ClienteEntity;
import com.backend.floristeria.model.destinatario.DestinatarioEntity;
import com.backend.floristeria.model.pedido.EstadoPedido;
import com.backend.floristeria.model.pedido.PedidoEntity;
import com.backend.floristeria.model.usuario.RolUsuario;
import com.backend.floristeria.model.usuario.UsuarioEntity;
import com.backend.floristeria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DestinatarioRepository destinatarioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RepartidorRepository repartidorRepository;

    public PedidoEntity crearPedido(PedidoEntity nuevoPedido, String usernameVendedor){

        var usuario = usuarioRepository.findByUsername(usernameVendedor)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var cliente = clienteRepository.findByTipoDocumentoAndNumeroDocumento(nuevoPedido.getCliente().getTipoDocumento(),
                nuevoPedido.getCliente().getNumeroDocumento())
                .orElseGet( () -> clienteRepository.save(
                        ClienteEntity.builder()
                                .tipoDocumento(nuevoPedido.getCliente().getTipoDocumento())
                                .numeroDocumento(nuevoPedido.getCliente().getNumeroDocumento())
                                .nombre(nuevoPedido.getCliente().getNombre())
                                .apellido(nuevoPedido.getCliente().getApellido())
                                .telefono(nuevoPedido.getCliente().getTelefono())
                                .email(nuevoPedido.getCliente().getEmail())
                                .ciudad(nuevoPedido.getCliente().getCiudad())
                                .direccion(nuevoPedido.getCliente().getDireccion())
                                .fechaCreacion(LocalDateTime.now())
                                .build()
                ));

        var destinatario = destinatarioRepository.findByNombreAndTelefono(nuevoPedido.getCliente().getNombre(),
                nuevoPedido.getCliente().getTelefono())
                .orElseGet( () -> destinatarioRepository.save(
                        DestinatarioEntity.builder()
                                .nombre(nuevoPedido.getCliente().getNombre())
                                .telefono(nuevoPedido.getCliente().getTelefono())
                                .fechaCreacion(LocalDateTime.now())
                                .build()
                ));

        var pedido = PedidoEntity.builder()
                //Cliente
                .cliente(cliente)
                //Destinatario
                .destinatario(destinatario)
                //Usuario quien creo el pedido
                .usuario(usuario)
                //Arreglo floral
                .motivoArreglo(nuevoPedido.getMotivoArreglo())
                .arregloFloral(nuevoPedido.getArregloFloral())
                .descripcion(nuevoPedido.getDescripcion())
                .anexos(nuevoPedido.getAnexos())
                .valorArreglo(nuevoPedido.getValorArreglo())
                .imagen(nuevoPedido.getImagen())
                .mensaje(nuevoPedido.getMensaje())
                //Info envio
                .ciudadEnvio(nuevoPedido.getCiudadEnvio())
                .direccionEnvio(nuevoPedido.getDireccionEnvio())
                .sector(nuevoPedido.getSector())
                .observaciones(nuevoPedido.getObservaciones())
                .valorEnvio(nuevoPedido.getValorEnvio())
                .fechaEntrega(nuevoPedido.getFechaEntrega())
                //Total valor envio + valor arreglo
                .total(nuevoPedido.getValorArreglo().add(nuevoPedido.getValorEnvio()))
                //Info pago
                .formaPago(nuevoPedido.getFormaPago())
                .estadoPago(nuevoPedido.getEstadoPago())
                //Estado pedido por defecto al crear es pendiente
                .estado(EstadoPedido.PENDIENTE)
                //Fecha creacion
                .fechaCreacion(LocalDateTime.now())
                .build();

        return pedidoRepository.save(pedido);
    }

    public PedidoEntity cambiarEstadoPedido(Long idPedido, EstadoPedido nuevoEstado, UsuarioEntity usuario){

        var pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        validarTansicion(pedido.getEstado(), nuevoEstado);
        validarPermisosPorRol(usuario.getRolUsuario(), nuevoEstado);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }



    private void validarPermisosPorRol(RolUsuario rol, EstadoPedido nuevoEstado){
        switch (rol){
            case PRODUCCION:
                if(nuevoEstado != EstadoPedido.ELABORADO){
                    throw new SecurityException("El decorador solo puede cambiar a 'ELABORADO'");
                }
                break;
            case ENVIOS:
                if(nuevoEstado != EstadoPedido.ENTREGADO){
                    throw new SecurityException("El repartidor solo puede cambiar a 'ENTREGADO'");
                }
                break;
        }
    }

    private void validarTansicion(EstadoPedido actual, EstadoPedido nuevo){

        if (actual == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("El pedido ya fue entregado y no se puede modificar");
        }

        if (actual == EstadoPedido.PENDIENTE && nuevo == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("Un pedido pendiente no se puede entregar sin pasara por produccion");
        }
    }

}