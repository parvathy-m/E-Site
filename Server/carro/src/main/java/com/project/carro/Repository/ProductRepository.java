package com.project.carro.Repository;

import com.project.carro.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<ProductEntity,Long> {
    ProductEntity findByProductId(Long currentUserId);

    ProductEntity save(ProductEntity product);

    Page<ProductEntity> findAll(Pageable pageable);
}
