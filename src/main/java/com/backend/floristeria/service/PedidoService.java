package com.backend.floristeria.service;

import com.backend.floristeria.dto.pedido.PedidoDTO;
import com.backend.floristeria.model.cliente.ClienteEntity;
import com.backend.floristeria.model.destinatario.DestinatarioEntity;
import com.backend.floristeria.model.pedido.EstadoPedido;
import com.backend.floristeria.model.pedido.PedidoEntity;
import com.backend.floristeria.model.repartidor.RepartidorEntity;
import com.backend.floristeria.model.usuario.RolUsuario;
import com.backend.floristeria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

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
    @Autowired
    CloudinaryService cloudinaryService;

    public PedidoEntity crearPedido(PedidoEntity nuevoPedido, String userName){

        var usuario = usuarioRepository.findByUsername(userName)
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
                .estadoPedido(EstadoPedido.PENDIENTE)
                //Fecha creacion
                .fechaCreacion(LocalDateTime.now())
                .build();

        return pedidoRepository.save(pedido);
    }

    public PedidoEntity modificarPedido(Long idPedido, PedidoEntity pedido, String userName){

        var usuario = usuarioRepository.findByUsername(userName);
        var pedidoExistente = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id" + idPedido));
        validarPermisosPorRol(usuario.get().getRolUsuario(), pedido.getEstadoPedido());
        validarTansicion(pedidoExistente.getEstadoPedido(), pedido.getEstadoPedido(), usuario.get().getRolUsuario());

        //Arreglo Floral
        Optional.ofNullable(pedido.getMotivoArreglo()).ifPresent(pedidoExistente::setMotivoArreglo);
        Optional.ofNullable(pedido.getDescripcion()).ifPresent(pedidoExistente::setDescripcion);
        Optional.ofNullable(pedido.getAnexos()).ifPresent(pedidoExistente::setAnexos);
        Optional.ofNullable(pedido.getValorArreglo()).ifPresent(pedidoExistente::setValorArreglo);
        Optional.ofNullable(pedido.getImagen()).ifPresent(pedidoExistente::setImagen);
        Optional.ofNullable(pedido.getMensaje()).ifPresent(pedidoExistente::setMensaje);
        //Info Envio
        Optional.ofNullable(pedido.getCiudadEnvio()).ifPresent(pedidoExistente::setCiudadEnvio);
        Optional.ofNullable(pedido.getDireccionEnvio()).ifPresent(pedidoExistente::setDireccionEnvio);
        Optional.ofNullable(pedido.getSector()).ifPresent(pedidoExistente::setSector);
        Optional.ofNullable(pedido.getObservaciones()).ifPresent(pedidoExistente::setObservaciones);
        Optional.ofNullable(pedido.getValorEnvio()).ifPresent(pedidoExistente::setValorEnvio);
        Optional.ofNullable(pedido.getFechaEntrega()).ifPresent(pedidoExistente::setFechaEntrega);
        //Valor total
        if(pedido.getValorArreglo()!=null && pedido.getValorEnvio()!=null){
            pedidoExistente.setTotal(pedido.getValorArreglo().add(pedido.getValorEnvio()));
        }else if (pedido.getValorArreglo()!=null && pedido.getValorEnvio() ==null){
            pedidoExistente.setTotal(pedido.getValorArreglo().add(pedidoExistente.getValorEnvio()));
        }else if (pedido.getValorArreglo() == null && pedido.getValorEnvio() !=null){
            pedidoExistente.setTotal(pedidoExistente.getValorArreglo().add(pedido.getValorEnvio()));
        }
        //Info pago
        Optional.ofNullable(pedido.getFormaPago()).ifPresent(pedidoExistente::setFormaPago);
        Optional.ofNullable(pedido.getEstadoPago()).ifPresent(pedidoExistente::setEstadoPago);
        //Estado pedido
        Optional.ofNullable(pedido.getEstadoPedido()).ifPresent(pedidoExistente::setEstadoPedido);
        //Imagen arreglo realizado
        Optional.ofNullable(pedido.getImgArregloRealizado()).ifPresent(pedidoExistente::setImgArregloRealizado);
        //Modificar cliente
        if(pedido.getCliente() != null){
            var clienteExistente = pedidoExistente.getCliente();
            var clienteModificado = pedido.getCliente();

            if(clienteModificado.getTipoDocumento() != null) clienteExistente.setTipoDocumento(clienteModificado.getTipoDocumento());
            if(clienteModificado.getNumeroDocumento() != null) clienteExistente.setNumeroDocumento(clienteModificado.getNumeroDocumento());
            if(clienteModificado.getNombre() != null) clienteExistente.setNombre(clienteModificado.getNombre());
            if(clienteModificado.getApellido() != null) clienteExistente.setApellido(clienteModificado.getApellido());
            if(clienteModificado.getTelefono() != null) clienteExistente.setTelefono(clienteModificado.getTelefono());
            if(clienteModificado.getEmail() != null) clienteExistente.setEmail(clienteModificado.getEmail());
            if(clienteModificado.getCiudad() != null) clienteExistente.setCiudad(clienteModificado.getCiudad());
            if(clienteModificado.getDireccion() != null) clienteExistente.setDireccion(clienteModificado.getDireccion());
            clienteExistente.setFechaModificacion(LocalDateTime.now());
            pedidoExistente.setCliente(clienteExistente);
            clienteRepository.save(clienteExistente);
        }
        //Modificar destinatario
        if(pedido.getDestinatario() != null){
            var destinatarioExistente = pedidoExistente.getDestinatario();
            var destinatarioModificado = pedido.getDestinatario();

            if(destinatarioModificado.getNombre() != null) destinatarioExistente.setNombre(destinatarioModificado.getNombre());
            if(destinatarioModificado.getTelefono() != null) destinatarioExistente.setTelefono(destinatarioModificado.getTelefono());
            destinatarioExistente.setFechaModificacion(LocalDateTime.now());
            pedidoExistente.setDestinatario(destinatarioExistente);
            destinatarioRepository.save(destinatarioExistente);
        }
        //Modificar repartidor
        if(pedido.getRepartidor() != null){
            var repartidorExistente = repartidorRepository
                    .findByNombreAndCelular(pedido.getRepartidor().getNombre(), pedido.getRepartidor().getCelular())
                    .orElseGet(() -> repartidorRepository.save(
                            RepartidorEntity.builder()
                                    .nombre(pedido.getRepartidor().getNombre())
                                    .celular(pedido.getRepartidor().getCelular())
                                    .build()
                    ));
            var repartidorModificado = pedido.getRepartidor();
            if(repartidorModificado.getNombre() != null) repartidorExistente.setNombre(repartidorModificado.getNombre());
            if(repartidorModificado.getCelular() != null) repartidorExistente.setCelular(repartidorModificado.getCelular());
            pedidoExistente.setRepartidor(repartidorExistente);
            pedidoRepository.save(pedidoExistente);
        }
        //Modificar fecha de cambios
        pedidoExistente.setFechaModificacion(LocalDateTime.now());
        var guardado = pedidoRepository.save(pedidoExistente);
        return guardado;
    }

    public Page<PedidoEntity> obtenerPedidos(Pageable pageable, String userName) {
        return pedidoRepository.findAll(pageable);
    }

    public PedidoEntity obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public void asignarImagen(Long id, MultipartFile imagen, String donde) throws IOException {
        var pedido =  pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        String url = cloudinaryService.subirImagen(imagen);
        if("referencia".equalsIgnoreCase(donde)){
            pedido.setImagen(url);
        }
        else if ("realizado".equalsIgnoreCase(donde)){
            pedido.setImgArregloRealizado(url);
            pedido.setFechaModificacion(LocalDateTime.now());
        }else {
            throw new IllegalArgumentException("Ubicacion no valida "+ donde);
        }
        pedidoRepository.save(pedido);

    }

    private void validarPermisosPorRol(RolUsuario rol, EstadoPedido nuevoEstado){
        switch (rol){
            case DECORADOR:
                if(nuevoEstado != EstadoPedido.ELABORADO){
                    throw new SecurityException("El decorador solo puede cambiar a 'ELABORADO'");
                }
                break;
            case REPARTIDOR:
                if(nuevoEstado != EstadoPedido.ENTREGADO){
                    throw new SecurityException("El repartidor solo puede cambiar a 'ENTREGADO'");
                }
                break;
        }
    }

    private void validarTansicion(EstadoPedido actual, EstadoPedido nuevo, RolUsuario rol){

        if (actual == EstadoPedido.ENTREGADO && rol != RolUsuario.ADMINISTRADOR && rol != RolUsuario.AUXILIAR) {
            throw new IllegalStateException("El pedido ya fue entregado y no se puede modificar");
        }

        if (actual == EstadoPedido.PENDIENTE && nuevo == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("Un pedido pendiente no se puede entregar sin pasar por produccion");
        }
    }

}