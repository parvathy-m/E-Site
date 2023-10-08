package com.project.carro.Repository;

import com.project.carro.Entity.AddressEntity;
import org.springframework.data.repository.Repository;

public interface AddressRepository extends Repository< AddressEntity,Long> {
    AddressEntity save(AddressEntity build);
}
