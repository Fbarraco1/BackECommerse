package com.ecommerce.backecommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecommerce.backecommerce.entity.ImagenProducto;
import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.ImagenProductoRepository;
import com.ecommerce.backecommerce.repository.ProductoRepository;
import com.ecommerce.backecommerce.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


import java.util.Map;

@Service
public class ImagenProductoService extends BaseService<ImagenProducto, Long> {

    private final Cloudinary cloudinary;
    private final ImagenProductoRepository imagenProductoRepository;
    private final ProductoRepository productoRepository;

    public ImagenProductoService(
            BaseRepository<ImagenProducto, Long> baseRepository,
            Cloudinary cloudinary,
            ImagenProductoRepository imagenProductoRepository,
            ProductoRepository productoRepository
    ) {
        super(baseRepository);
        this.cloudinary = cloudinary;
        this.imagenProductoRepository = imagenProductoRepository;
        this.productoRepository = productoRepository;
    }

    public ImagenProducto subirImagen(MultipartFile file, Long productoId, boolean esPrincipal, Integer orden) throws Exception {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String url = uploadResult.get("secure_url").toString();
            String nombre = uploadResult.get("original_filename").toString();

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new Exception("Producto no encontrado con ID: " + productoId));

            ImagenProducto imagen = new ImagenProducto();
            imagen.setNombre(nombre);
            imagen.setUrl(url);
            imagen.setProducto(producto);
            imagen.setEsPrincipal(esPrincipal);
            imagen.setOrden(orden);

            return imagenProductoRepository.save(imagen);

        } catch (IOException e) {
            throw new Exception("Error al subir la imagen: " + e.getMessage());
        }
    }
    public void eliminarImagen(Long imagenId, Long productoId) throws Exception {
        ImagenProducto imagen = imagenProductoRepository.findById(imagenId)
                .orElseThrow(() -> new Exception("Imagen no encontrada con ID: " + imagenId));

        // Validar que pertenezca al producto
        if (imagen.getProducto() == null || !imagen.getProducto().getId().equals(productoId)) {
            throw new Exception("La imagen no pertenece al producto con ID: " + productoId);
        }

        // Obtener el public_id de Cloudinary desde la URL para poder eliminar la imagen
        String publicId = extraerPublicId(imagen.getUrl());

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new Exception("Error al eliminar imagen de Cloudinary: " + e.getMessage());
        }

        // Eliminar del repositorio
        imagenProductoRepository.delete(imagen);
    }

    // Método auxiliar para extraer el public_id desde la URL
    private String extraerPublicId(String url) {
        String[] partes = url.split("/");
        String nombreConExtension = partes[partes.length - 1];
        return nombreConExtension.split("\\.")[0]; // eliminar la extensión
    }

    public ImagenProducto guardar(ImagenProducto imagenProducto) {
        return imagenProductoRepository.save(imagenProducto);
    }
}
