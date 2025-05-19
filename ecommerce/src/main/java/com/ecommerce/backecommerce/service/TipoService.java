package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Tipo;
import com.ecommerce.backecommerce.repository.BaseRepository;
import com.ecommerce.backecommerce.repository.TipoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoService extends BaseService<Tipo,Long> {
    public TipoService(TipoRepository tipoRepository) {
        super(tipoRepository);
    }
}
