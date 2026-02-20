package com.backend.floristeria.controller;

import com.backend.floristeria.dto.pedido.PedidoDTO;
import com.backend.floristeria.dto.pedido.PedidoMapper;
import com.backend.floristeria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:4200") // Permite conexión desde Angular en local
public class PedidoController{

    @Autowired
    private PedidoService pedidoService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR','VENDEDOR')")
    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto,
                                                    Authentication authentication) {
        // Obtenemos el usuario que está logueado
        String username = authentication.getName();
        // Convertimos DTO a Entidad
        var pedidoNuevo = PedidoMapper.toEntity(dto);
        var pedidoGuardado = pedidoService.crearPedido(pedidoNuevo, username);
        return ResponseEntity.ok(PedidoMapper.toDTO(pedidoGuardado));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR','VENDEDOR')")
    @PatchMapping("/{id}/subir-imagen/{donde}")
    public ResponseEntity<?> subirImagen(@PathVariable Long id,
                                         @RequestParam MultipartFile imagen,
                                         @PathVariable String donde) throws IOException {
        pedidoService.asignarImagen(id, imagen, donde);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR','VENDEDOR','DECORADOR')")
    @GetMapping("/listar")
    public ResponseEntity<Page<PedidoDTO>> listarPedidos(Pageable pageable,
                                                            Authentication authentication) {
        // Obtenemos el usuario que está logueado
        String username = authentication.getName();
        var pedidoEntities = pedidoService.obtenerPedidos(pageable, username);
        Page<PedidoDTO> pedidoDTOS = pedidoEntities.map(PedidoMapper::toDTO);
        return ResponseEntity.ok(pedidoDTOS);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR','VENDEDOR','DECORADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedidoPorId(@PathVariable Long id) {
        var pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(PedidoMapper.toDTO(pedido));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUXILIAR','VENDEDOR','DECORADOR')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarPedido(@PathVariable("id") Long id, @RequestBody PedidoDTO dto,
                                           Authentication authentication) {
        // Obtenemos el usuario que está logueado
        String username = authentication.getName();

        var pedidoNuevo = PedidoMapper.toEntity(dto);
        var pedidoModificado = pedidoService.modificarPedido(id,pedidoNuevo,username);
        var response = PedidoMapper.toDTO(pedidoModificado);
        return ResponseEntity.ok(response);

    }
}