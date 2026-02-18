package com.backend.floristeria.repository;

import com.backend.floristeria.model.repartidor.RepartidorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepartidorRepository extends JpaRepository<RepartidorEntity, Long> {

    Optional<RepartidorEntity> findByNombreAndTelefono(String nombre, String telefono);
}
