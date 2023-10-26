package com.project.carro.Dto.View;

import com.project.carro.Entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private Long user_id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String gender;
    private String status;
    private String role;

    public UserInfo(UserEntity user) {
        this.user_id=user.getUserId();
        this.userName = user.getUserName();
        this.firstName = user.getFName();
        this.lastName = user.getLName();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.gender = user.getGender();
        switch (user.getStatus()) {
            case 1 -> this.status = UserEntity.Status.ACTIVE.getType();
            case 2 -> this.status = UserEntity.Status.DELETED.getType();
        }
        this.role = switch (user.getRole()) {
            case 1 -> UserEntity.Role.USER.getType();
            case 2 -> UserEntity.Role.SELLER.getType();
            case 3 -> UserEntity.Role.ADMIN.getType();
            default -> throw new IllegalStateException("Unexpected value: " + user.getRole());
        };
    }
}
