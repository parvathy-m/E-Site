package com.project.carro.Controller;

import com.project.carro.Dto.Form.LoginForm;
import com.project.carro.Dto.View.UserInfo;
import com.project.carro.Dto.View.UserLoginView;
import com.project.carro.Entity.UserEntity;
import com.project.carro.Service.AdminService;
import com.project.carro.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/admin/")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;
    @PostMapping(value = "login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserLoginView adminLogin(@RequestBody LoginForm form){
        UserLoginView userLoginView=userService.login(form, UserEntity.Role.ADMIN.getValue());
        log.info("login completed");
        return userLoginView;
    }
    @GetMapping("user-info")
    public Collection<UserInfo> userInfo(@RequestParam(required = false,name = "status") Integer role){
        if (role == null) {
            role = 1;
        }
        return adminService.userInfoList(role);
    }
    @PutMapping("shop/{shopId}")
    public Map<String,String> sellerStatus(@PathVariable Long shopId,@RequestParam Integer status){
        log.info("Status change request started");
        String message=adminService.changeStatus(status,shopId);
        log.info("Seller status changed successfully");
        Map<String, String> map=new HashMap<>();
        map.put("Response",message);
        return map;
    }
}
