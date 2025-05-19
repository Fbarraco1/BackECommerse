package com.ecommerce.backecommerce.service;


import com.ecommerce.backecommerce.entity.Talle;
import com.ecommerce.backecommerce.repository.TalleRepository;
import org.springframework.stereotype.Service;

@Service
public class TalleService extends BaseService<Talle,Long> {

    public TalleService(TalleRepository talleRepository) {
        super(talleRepository);
    }
}
