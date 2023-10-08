package com.project.carro.Controller;

import com.project.carro.Dto.Form.LoginForm;
import com.project.carro.Dto.Form.UserDetailsUpdateForm;
import com.project.carro.Dto.Form.UserRegisterForm;
import com.project.carro.Dto.View.UserInfo;
import com.project.carro.Dto.View.UserLoginView;
import com.project.carro.Dto.View.UserView;
import com.project.carro.Exceptions.BadRequestException;
import com.project.carro.Service.AccountService;
import com.project.carro.Service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/account/")
@Slf4j
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;


    @PutMapping("user-info")
    public void updateUser(@RequestBody UserDetailsUpdateForm form){
        userService.updateUser(form);
    }
    @GetMapping("user-info")
    public UserInfo userInfo(){
        return userService.userInfo();
    }

    @GetMapping("user-demo")
    public List<Object[]> updateDemo(){
       return userService.updateDemo();
    }


}
