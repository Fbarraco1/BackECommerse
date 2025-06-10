package com.ecommerce.backecommerce.repository;

import com.ecommerce.backecommerce.entity.Categoria;
import com.ecommerce.backecommerce.entity.Producto;
import com.ecommerce.backecommerce.entity.Tipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductoCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Producto> filtrarProductos(Long tipoId, Long categoriaId, String color, String marca, Double precioMin, Double precioMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> query = cb.createQuery(Producto.class);
        Root<Producto> producto = query.from(Producto.class);

        List<Predicate> predicates = new ArrayList<>();

        if (tipoId != null) {
            Join<Producto, Categoria> categoriaJoin = producto.join("categoria");
            Join<Categoria, Tipo> tipoJoin = categoriaJoin.join("tipo");
            predicates.add(cb.equal(tipoJoin.get("id"), tipoId));
        }

        if (categoriaId != null) {
            predicates.add(cb.equal(producto.get("categoria").get("id"), categoriaId));
        }

        if (color != null && !color.isEmpty()) {
            predicates.add(cb.equal(producto.get("color"), color));
        }

        if (marca != null && !marca.isEmpty()) {
            predicates.add(cb.equal(producto.get("marca"), marca));
        }

        if (precioMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(producto.get("precio"), precioMin));
        }

        if (precioMax != null) {
            predicates.add(cb.lessThanOrEqualTo(producto.get("precio"), precioMax));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}