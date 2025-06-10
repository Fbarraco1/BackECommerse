package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.entity.TalleProducto;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.ProductoRepository;
import com.ecommerce.backecommerce.repository.TalleProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 4. SERVICIO con validación
@Service
@Transactional
public class TalleProductoService extends BaseService<TalleProducto, Long> {

    @Autowired
    private TalleProductoRepository talleProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public TalleProductoService(TalleProductoRepository talleProductoRepository) {
        super(talleProductoRepository);
        this.talleProductoRepository = talleProductoRepository;
    }

    @Override
    public TalleProducto crear(TalleProducto talleProducto) throws Exception {
        // Validar antes de guardar
        validarCantidadPositiva(talleProducto);

        TalleProducto saved = super.crear(talleProducto);

        // Validar consistencia después de guardar
        validarConsistenciaInventario(saved.getProducto().getId());

        return saved;
    }

    @Override
    public TalleProducto actualizar(TalleProducto talleProducto) throws Exception {
        // Validar antes de actualizar
        validarCantidadPositiva(talleProducto);

        TalleProducto updated = super.actualizar(talleProducto);

        // Validar consistencia después de actualizar
        validarConsistenciaInventario(updated.getProducto().getId());

        return updated;
    }

    @Override
    public void eliminar(Long id) throws Exception {
        // Obtener el producto antes de eliminar
        Optional<TalleProducto> optionalTalleProducto = super.buscarPorId(id);
        if (!optionalTalleProducto.isPresent()) {
            throw new Exception("TalleProducto no encontrado con ID: " + id);
        }

        Long productoId = optionalTalleProducto.get().getProducto().getId();

        // Eliminar
        super.eliminar(id);

        // Validar consistencia después de eliminar
        validarConsistenciaInventario(productoId);
    }

    @Override
    public void eliminadoLogico(Long id) throws Exception {
        // Obtener el producto antes de eliminar lógicamente
        Optional<TalleProducto> optionalTalleProducto = super.buscarPorId(id);
        if (!optionalTalleProducto.isPresent()) {
            throw new Exception("TalleProducto no encontrado con ID: " + id);
        }

        Long productoId = optionalTalleProducto.get().getProducto().getId();

        // Eliminación lógica
        super.eliminadoLogico(id);

        // Validar consistencia después de eliminar lógicamente
        validarConsistenciaInventario(productoId);
    }

    // MÉTODOS DE VALIDACIÓN
    private void validarCantidadPositiva(TalleProducto talleProducto) {
        if (talleProducto.getCantidad() == null || talleProducto.getCantidad() < 0) {
            throw new TalleProductoRepository.InventarioInconsistenteException("La cantidad debe ser mayor o igual a 0");
        }
    }

    public void validarConsistenciaInventario(Long productoId) throws Exception {
        Optional<Producto> optionalProducto = productoRepository.findById(productoId);
        if (!optionalProducto.isPresent()) {
            throw new Exception("Producto no encontrado con ID: " + productoId);
        }

        Producto producto = optionalProducto.get();
        Integer sumaTargets = talleProductoRepository.sumCantidadByProductoId(productoId);

        // ✅ Permitir que la suma de talles sea menor o igual a la cantidad total del producto
        if (sumaTargets > producto.getCantidad()) {
            throw new TalleProductoRepository.InventarioInconsistenteException(
                    String.format("La suma de talles (%d) supera la cantidad total del producto (%d).",
                            sumaTargets, producto.getCantidad())
            );
        }
    }

    // MÉTODOS ÚTILES ADICIONALES
    public Integer getCantidadTotalPorProducto(Long productoId) {
        return talleProductoRepository.sumCantidadByProductoId(productoId);
    }

    public List<TalleProducto> getTallesPorProducto(Long productoId) {
        return talleProductoRepository.findByProductoIdAndActivoTrue(productoId);
    }

    public void sincronizarCantidadProducto(Long productoId) throws Exception {
        Optional<Producto> optionalProducto = productoRepository.findById(productoId);
        if (!optionalProducto.isPresent()) {
            throw new Exception("Producto no encontrado con ID: " + productoId);
        }

        Producto producto = optionalProducto.get();
        Integer cantidadCalculada = getCantidadTotalPorProducto(productoId);
        producto.setCantidad(cantidadCalculada);

        try {
            productoRepository.save(producto);
        } catch (Exception e) {
            throw new Exception("Error al sincronizar cantidad del producto: " + e.getMessage());
        }
    }
}
