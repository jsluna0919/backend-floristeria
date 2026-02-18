package com.backend.floristeria.model.pedido;

import com.backend.floristeria.model.cliente.ClienteEntity;
import com.backend.floristeria.model.destinatario.DestinatarioEntity;
import com.backend.floristeria.model.repartidor.RepartidorEntity;
import com.backend.floristeria.model.usuario.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private ClienteEntity cliente;

    //Destinatario
    @ManyToOne
    @JoinColumn(name = "destinatario_id", referencedColumnName = "id", nullable = false)
    private DestinatarioEntity destinatario;

    //Repartidor
    @ManyToOne
    @JoinColumn(name = "repartidor_id", referencedColumnName = "id", nullable = false)
    private RepartidorEntity repartidor;

    //Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private UsuarioEntity usuario;

    //Arreglo Floral
    @Column(name = "motivo_arreglo", nullable = false)
    @Enumerated(EnumType.STRING)
    private MotivoArreglo motivoArreglo;

    @Column(name = "arreglo_floral", nullable = false)
    private String arregloFloral;

    private String descripcion;

    private String anexos;

    @Column(name = "valor_arreglo")
    private BigDecimal valorArreglo;

    private String imagen;

    private String mensaje;

    //Info Envio
    @Column(name = "ciudad_envio", nullable = false)
    private String ciudadEnvio;

    @Column(name = "direccion_envio", nullable = false)
    private String direccionEnvio;

    private String sector;

    private String observaciones;

    @Column(name = "valor_envio")
    private BigDecimal valorEnvio;

    @Column(name = "fecha_entrega", nullable = false)
    private String fechaEntrega;

    //Info Pago
    @Column(name = "forma_pago", nullable = false)
    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @Column(name = "estado_pago")
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    private BigDecimal total;

    //Estado Pedido
    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;

    //Imagen del arreglo realizado
    private String imgArregloRealizado;

    //Fechas de creacion y modificacion
    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

}
