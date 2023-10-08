package com.project.carro.Dto.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterForm {
    private String userName;
    private String password;
    private String email;

}
