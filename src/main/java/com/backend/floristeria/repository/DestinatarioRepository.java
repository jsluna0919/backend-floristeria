package com.backend.floristeria.repository;

import com.backend.floristeria.model.destinatario.DestinatarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DestinatarioRepository extends JpaRepository<DestinatarioEntity, Long> {

    Optional<DestinatarioEntity> findByNombreAndTelefono(String nombre, String telefono);
}