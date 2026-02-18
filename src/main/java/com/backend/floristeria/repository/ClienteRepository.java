package com.backend.floristeria.repository;

import com.backend.floristeria.model.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    // Para el buscador tipo "Google" en la pantalla de crear pedido
    // SELECT * FROM clientes WHERE nombre LIKE %termino%
    List<ClienteEntity> findByNombreContainingIgnoreCase(String termino);

}
