package com.project.carro.Controller;

import com.project.carro.Dto.Form.LoginForm;
import com.project.carro.Dto.Form.UserDetailsUpdateForm;
import com.project.carro.Dto.View.UserInfo;
import com.project.carro.Dto.View.UserLoginView;
import com.project.carro.Entity.UserEntity;
import com.project.carro.Exceptions.BadRequestException;
import com.project.carro.Dto.Form.UserRegisterForm;
import com.project.carro.Service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserLoginView userLogin(@RequestBody LoginForm form){
        UserLoginView userLoginView=userService.login(form, UserEntity.Role.USER.getValue());
        log.info("login completed");
        return userLoginView;
    }
    @PostMapping("register")
    public void register(@RequestBody UserRegisterForm form){
        if(form.getPassword()!=null||form.getEmail()!=null){
            userService.userRegister(form);
        }else throw new BadRequestException("empty.form");
    }


}
