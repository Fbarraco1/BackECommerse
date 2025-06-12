package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.dto.CrearOrdenDTO;
import com.ecommerce.backecommerce.entity.*;
import com.ecommerce.backecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenDeCompraService extends BaseService<OrdenDeCompra, Long> {

    private final OrdenDeCompraRepository ordenDeCompraRepository;
    private final DireccionRepository direccionRepository;
    private final ProductoRepository productoRepository;

    public OrdenDeCompraService(
            OrdenDeCompraRepository ordenDeCompraRepository,
            DireccionRepository direccionRepository,
            ProductoRepository productoRepository
    ) {
        super(ordenDeCompraRepository);
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.direccionRepository = direccionRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public OrdenDeCompra crearOrden(CrearOrdenDTO dto, Usuario usuario) throws Exception {
        Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
                .orElseThrow(() -> new Exception("Dirección no encontrada"));

        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setUsuario(usuario);
        orden.setFecha(LocalDate.now());

        List<DetalleOrden> detalles = new ArrayList<>();

        for (CrearOrdenDTO.ProductoCantidadDTO p : dto.getProductos()) {
            Producto producto = productoRepository.findById(p.getIdProducto())
                    .orElseThrow(() -> new Exception("Producto no encontrado"));

            DetalleOrden detalle = new DetalleOrden();
            detalle.setProducto(producto);
            detalle.setCantidad(p.getCantidad());
            detalle.setOrden(orden);

            detalles.add(detalle);
        }

        orden.setDetalle(detalles);

        return ordenDeCompraRepository.save(orden);
    }
}
