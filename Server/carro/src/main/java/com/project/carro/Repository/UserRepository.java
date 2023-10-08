package com.project.carro.Repository;

import com.project.carro.Dto.View.UserView;
import com.project.carro.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<UserEntity,Long> {

    void save(UserEntity userEntity);

    UserEntity findByUserName(String username);

    UserEntity findByUserNameAndEmail(String userName, String email);

    UserEntity findByUserNameOrEmail(String userName, String email);

    UserEntity findByEmail(String userName);

    //List<UserEntity> findByRole(int role, Pageable sortedByName);

    UserEntity findByUserNameOrEmailAndRole(String userName, String email, int value);

    UserEntity findByEmailAndRole(String userName, int value);

    Page<UserEntity> findByRole(int role, Pageable sortedByName);

    UserEntity findByUserId(Long currentUserId);

    @Query(
            value = "SELECT user_name,f_name FROM USERS",
            nativeQuery = true)
    List<Object[]> getUser();
}
