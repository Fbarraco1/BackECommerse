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
    private final TalleRepository talleRepository;

    public OrdenDeCompraService(
            OrdenDeCompraRepository ordenDeCompraRepository,
            DireccionRepository direccionRepository,
            ProductoRepository productoRepository,
            TalleRepository talleRepository
    ) {
        super(ordenDeCompraRepository);
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.direccionRepository = direccionRepository;
        this.productoRepository = productoRepository;
        this.talleRepository = talleRepository;
    }

    @Transactional
    public OrdenCompraResponseDTO crearOrden(CrearOrdenDTO dto, Usuario usuario) throws Exception {
        // Busco la dirección y valido que sea del usuario
        Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
                .orElseThrow(() -> new Exception("Dirección no encontrada"));

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

            Talle talle = talleRepository.findById(p.getIdTalle())
                    .orElseThrow(() -> new Exception("Talle no encontrado"));

            // Validar que talle corresponde a producto
            if (!talle.getProducto().getId().equals(producto.getId())) {
                throw new Exception("El talle no pertenece al producto");
            }

            // Validar stock
            if (talle.getStock() < p.getCantidad()) {
                throw new Exception("Stock insuficiente para el talle seleccionado");
            }

            // Descontar stock
            talle.setStock(talle.getStock() - p.getCantidad());
            talleRepository.save(talle);

            DetalleOrden detalle = new DetalleOrden();
            detalle.setProducto(producto);
            detalle.setTalle(talle);
            detalle.setCantidad(p.getCantidad());
            detalle.setOrden(orden);
            detalles.add(detalle);
        }

        // Seteo detalles a la orden
        orden.setDetalle(detalles);

        // Guardo la orden primero para que genere ID y luego detalles se guarden por cascada
        OrdenDeCompra ordenGuardada = ordenDeCompraRepository.save(orden);

        return convertirADTO(ordenGuardada);
    }

    private OrdenCompraResponseDTO convertirADTO(OrdenDeCompra orden) {
        OrdenCompraResponseDTO dto = new OrdenCompraResponseDTO();
        dto.setId(orden.getId());
        dto.setFecha(orden.getFecha());

        OrdenCompraResponseDTO.UsuarioBasicoDTO usuarioDTO = new OrdenCompraResponseDTO.UsuarioBasicoDTO();
        usuarioDTO.setId(orden.getUsuario().getId());
        usuarioDTO.setNombre(orden.getUsuario().getNombre());
        usuarioDTO.setEmail(orden.getUsuario().getEmail());
        dto.setUsuario(usuarioDTO);

        OrdenCompraResponseDTO.DireccionDTO direccionDTO = new OrdenCompraResponseDTO.DireccionDTO();
        direccionDTO.setId(orden.getDireccionEntrega().getId());
        direccionDTO.setCalle(orden.getDireccionEntrega().getCalle());
        direccionDTO.setLocalidad(orden.getDireccionEntrega().getLocalidad());
        direccionDTO.setCp(orden.getDireccionEntrega().getCp());
        dto.setDireccionEntrega(direccionDTO);

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

                    OrdenCompraResponseDTO.TalleDTO talleDTO = new OrdenCompraResponseDTO.TalleDTO();
                    talleDTO.setId(detalle.getTalle().getId());
                    talleDTO.setNombre(detalle.getTalle().getNombre());

                    detalleDTO.setProducto(productoDTO);
                    detalleDTO.setTalle(talleDTO);

                    return detalleDTO;
                }).collect(Collectors.toList());

        dto.setDetalle(detallesDTO);
        return dto;
    }

}
