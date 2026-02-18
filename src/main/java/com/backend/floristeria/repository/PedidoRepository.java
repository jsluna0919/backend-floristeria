package com.backend.floristeria.repository;

import com.backend.floristeria.model.pedido.EstadoPedido;
import com.backend.floristeria.model.pedido.PedidoEntity;
import com.backend.floristeria.model.usuario.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    // -------------------------------------------------------
    // PARA EL ROL DE "PRODUCCIÓN"
    // -------------------------------------------------------
    // Busca pedidos por estado (ej: PENDIENTE, EN_PRODUCCION)
    // Pageable permite enviar ?page=0&size=10 desde el frontend
    Page<PedidoEntity> findByEstado(EstadoPedido estado, Pageable pageable);

    // Búsqueda flexible (varios estados a la vez)
    // Útil para ver "PENDIENTE" y "EN_PRODUCCION" en una sola lista
    Page<PedidoEntity> findByEstadoIn(List<EstadoPedido> estados, Pageable pageable);

    // -------------------------------------------------------
    // PARA EL ROL DE "REPARTIDOR"
    // -------------------------------------------------------
    // Ver solo los pedidos asignados a mí que no han sido entregados
    List<PedidoEntity> findByRepartidorAndEstadoNot(UsuarioEntity repartidor, EstadoPedido estado);

    // -------------------------------------------------------
    // PARA REPORTES (ADMIN / AUXILIAR)
    // -------------------------------------------------------
    // Buscar ventas entre dos fechas
    List<PedidoEntity> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    // Total vendido por vendedor en un rango de fechas (JPQL Personalizado)
    @Query("SELECT p FROM PedidoEntity p WHERE p.usuario.id = :vendedorId AND p.fechaCreacion BETWEEN :inicio AND :fin")
    List<PedidoEntity> reporteVentasPorVendedor(@Param("vendedorId") Long vendedorId,
                                          @Param("inicio") LocalDateTime inicio,
                                          @Param("fin") LocalDateTime fin);

}
