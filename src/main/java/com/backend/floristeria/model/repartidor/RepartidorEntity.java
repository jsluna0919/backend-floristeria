package com.backend.floristeria.model.repartidor;

import com.backend.floristeria.model.pedido.PedidoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repartidores")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepartidorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String celular;
    @OneToMany(mappedBy = "repartidor", fetch = FetchType.LAZY)
    private List<PedidoEntity> pedidos = new ArrayList<>();
}
