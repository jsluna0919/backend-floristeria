package com.backend.floristeria.dto.pedido;

import com.backend.floristeria.dto.repartidor.RepartidorDTO;
import com.backend.floristeria.dto.usuario.UsuarioDTO;
import com.backend.floristeria.dto.cliente.ClienteDTO;
import com.backend.floristeria.dto.destinatario.DestinatarioDTO;
import com.backend.floristeria.model.pedido.EstadoPago;
import com.backend.floristeria.model.pedido.EstadoPedido;
import com.backend.floristeria.model.pedido.FormaPago;
import com.backend.floristeria.model.pedido.MotivoArreglo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDTO {

    private Long id;
    private ClienteDTO cliente;
    private DestinatarioDTO destinatario;
    private RepartidorDTO repartidor;
    private UsuarioDTO usuario;
    private MotivoArreglo  motivoArreglo;
    private String arregloFloral;
    private String descripcion;
    private String anexos;
    private BigDecimal valorArreglo;
    private String imagen;
    private String mensaje;
    private String ciudadEnvio;
    private String direccionEnvio;
    private String sector;
    private String observaciones;
    private BigDecimal valorEnvio;
    private String fechaEntrega;
    private BigDecimal total;
    private FormaPago formaPago;
    private EstadoPago estadoPago;
    private EstadoPedido estadoPedido;
    private String imgArregloRealizado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}