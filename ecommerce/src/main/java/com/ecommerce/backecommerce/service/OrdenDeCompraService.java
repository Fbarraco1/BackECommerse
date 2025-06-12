package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.dto.CrearOrdenDTO;
import com.ecommerce.backecommerce.dto.OrdenCompraResponseDTO;
import com.ecommerce.backecommerce.entity.*;
import com.ecommerce.backecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public OrdenCompraResponseDTO crearOrden(CrearOrdenDTO dto, Usuario usuario) throws Exception {
        // Cargar dirección con usuario para poder hacer la validación
        Direccion direccion = direccionRepository.findByIdWithUsuario(dto.getIdDireccion())
                .orElseThrow(() -> new Exception("Dirección no encontrada"));

        // Verificar que la dirección pertenece al usuario autenticado
        if (!direccion.getUsuario().getId().equals(usuario.getId())) {
            throw new Exception("La dirección no pertenece al usuario");
        }

        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setUsuario(usuario);
        orden.setDireccionEntrega(direccion);
        orden.setFecha(LocalDate.now());

        List<DetalleOrden> detalles = new ArrayList<>();

        for (CrearOrdenDTO.ProductoCantidadDTO p : dto.getProductos()) {
            Producto producto = productoRepository.findById(p.getIdProducto())
                    .orElseThrow(() -> new Exception("Producto no encontrado"));

            // Verificar que hay suficiente stock
            if (producto.getCantidad() < p.getCantidad()) {
                throw new Exception("Stock insuficiente para el producto: " + producto.getNombre());
            }

            DetalleOrden detalle = new DetalleOrden();
            detalle.setProducto(producto);
            detalle.setCantidad(p.getCantidad());
            detalle.setOrden(orden);

            detalles.add(detalle);
        }

        orden.setDetalle(detalles);
        OrdenDeCompra ordenGuardada = ordenDeCompraRepository.save(orden);

        // Convertir a DTO dentro de la transacción
        return convertirADTO(ordenGuardada);
    }

    private OrdenCompraResponseDTO convertirADTO(OrdenDeCompra orden) {
        OrdenCompraResponseDTO dto = new OrdenCompraResponseDTO();
        dto.setId(orden.getId());
        dto.setFecha(orden.getFecha());

        // Usuario básico
        OrdenCompraResponseDTO.UsuarioBasicoDTO usuarioDTO = new OrdenCompraResponseDTO.UsuarioBasicoDTO();
        usuarioDTO.setId(orden.getUsuario().getId());
        usuarioDTO.setNombre(orden.getUsuario().getNombre());
        usuarioDTO.setEmail(orden.getUsuario().getEmail());
        dto.setUsuario(usuarioDTO);

        // Dirección
        OrdenCompraResponseDTO.DireccionDTO direccionDTO = new OrdenCompraResponseDTO.DireccionDTO();
        direccionDTO.setId(orden.getDireccionEntrega().getId());
        direccionDTO.setCalle(orden.getDireccionEntrega().getCalle());
        direccionDTO.setLocalidad(orden.getDireccionEntrega().getLocalidad());
        direccionDTO.setCp(orden.getDireccionEntrega().getCp());
        dto.setDireccionEntrega(direccionDTO);

        // Detalles
        List<OrdenCompraResponseDTO.DetalleOrdenDTO> detallesDTO = orden.getDetalle().stream()
                .map(detalle -> {
                    OrdenCompraResponseDTO.DetalleOrdenDTO detalleDTO = new OrdenCompraResponseDTO.DetalleOrdenDTO();
                    detalleDTO.setId(detalle.getId());
                    detalleDTO.setCantidad(detalle.getCantidad());

                    OrdenCompraResponseDTO.ProductoDTO productoDTO = new OrdenCompraResponseDTO.ProductoDTO();
                    productoDTO.setId(detalle.getProducto().getId());
                    productoDTO.setNombre(detalle.getProducto().getNombre());
                    productoDTO.setDescripcion(detalle.getProducto().getDescripcion());
                    productoDTO.setPrecio(detalle.getProducto().getPrecio());
                    productoDTO.setMarca(detalle.getProducto().getMarca());
                    productoDTO.setColor(detalle.getProducto().getColor());
                    detalleDTO.setProducto(productoDTO);

                    return detalleDTO;
                }).collect(Collectors.toList());

        dto.setDetalle(detallesDTO);
        return dto;
    }
}