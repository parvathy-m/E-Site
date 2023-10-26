package com.project.carro.Entity;

import com.project.carro.Dto.Form.UserRegisterForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String password;
    private String email;
    private String mobile;
    private String gender;
    private String fName;
    private String lName;
    private int status;
    private String profileImage;
    private Date createdDate;
    private Date updatedDate;
    private int role;

    public UserEntity(UserRegisterForm form) {
        this.userName= form.getUserName();
        this.password= form.getPassword();
        this.email= form.getEmail();
    }

    public enum Status{
        ACTIVE(1,"Active"),
        DELETED(2,"Blocked");
        private final int value;
        private final String type;

        Status(int value, String type) {
            this.value = value;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
    }
    public  enum Role{
        USER(1,"USER"),
        SELLER(2,"SELLER"),
        ADMIN(3,"ADMIN");
        private final int value;
        private final String type;

        Role(int value, String type) {
            this.value = value;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
    }
}
