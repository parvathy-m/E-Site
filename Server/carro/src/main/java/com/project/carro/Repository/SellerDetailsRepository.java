package com.project.carro.Repository;

import com.project.carro.Entity.SellerDetailsEntity;
import com.project.carro.Entity.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SellerDetailsRepository extends Repository<SellerDetailsEntity,Long> {
    void save(SellerDetailsEntity details);

    List<SellerDetailsEntity> findById(Long currentUserId);

    //List<SellerDetailsEntity> findBySellerId(Long currentUserId);

    List<SellerDetailsEntity> findByUser(UserEntity currentUserId);

    SellerDetailsEntity findByUserAndShopId(UserEntity byUserId, Long shopId);

    SellerDetailsEntity findByShopId(Long shopId);
}
