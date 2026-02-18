package com.backend.floristeria.model.usuario;

import com.backend.floristeria.model.pedido.PedidoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String username;
    private String password;
    @Column(name = "rol_usuario")
    @Enumerated(EnumType.STRING)
    private RolUsuario rolUsuario;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<PedidoEntity> pedidos = new ArrayList<>();
}