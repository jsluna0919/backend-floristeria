package com.backend.floristeria.controller;

import com.backend.floristeria.dto.pedido.PedidoDTO;
import com.backend.floristeria.dto.pedido.PedidoMapper;
import com.backend.floristeria.model.pedido.PedidoEntity;
import com.backend.floristeria.service.PedidoService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:4200") // Permite conexión desde Angular en local
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crear")
    public ResponseEntity<PedidoEntity> crearPedido(@RequestBody PedidoDTO dto,
                                                    Authentication authentication) {
        // Obtenemos el usuario que está logueado (viene del Token JWT)
        String username = authentication.getClass().getName();
        // Convertimos DTO a Entidad
        var pedidoNuevo = PedidoMapper.toEntity(dto);
        var pedidoGuardado = pedidoService.crearPedido(pedidoNuevo, username);
        return ResponseEntity.ok(pedidoGuardado);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoEntity>> listarPedidos(Pageable pageable,
                                                            Authentication authentication) {
        String username = authentication.getClass().getName();
        var usuario =
    }
}
