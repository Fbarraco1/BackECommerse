package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.TalleProducto;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.TalleProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class TalleProductoService extends BaseService<TalleProducto, Long> {
    public TalleProductoService(TalleProductoRepository talleProductoRepository) {
        super(talleProductoRepository);
    }
}
