package com.project.carro.Controller;

import com.project.carro.Dto.Form.LoginForm;
import com.project.carro.Dto.Form.ShopDetailsForm;
import com.project.carro.Dto.Form.UserRegisterForm;
import com.project.carro.Dto.View.ShopListSeller;
import com.project.carro.Dto.View.UserLoginView;
import com.project.carro.Entity.UserEntity;
import com.project.carro.Service.SellerService;
import com.project.carro.Service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seller/")
@Slf4j
public class SellerController {
    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @PostMapping(value = "login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserLoginView sellerLogin(@RequestBody LoginForm form){
        UserLoginView userLoginView=userService.login(form, UserEntity.Role.SELLER.getValue());
        log.info("login completed");
        return userLoginView;
    }
    @PostMapping("register")
    public void sellerRegister(@RequestBody UserRegisterForm form){

        log.info("Seller registration started");
        sellerService.register(form);
    }
    @PostMapping(value = "shop-details", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({"ROLE_SELLER"})
    public Map<String,String> shopDetailsRegister(ShopDetailsForm form) throws IOException {
        List<String> doc=sellerService.registerShop(form);
        log.info("Seller Details Saved");
        Map<String,String> success= new HashMap<>();
        success.put("Status","Success");
        success.put("Document 1", doc.get(0));
        success.put("Document 2", doc.get(1));
        return success;
    }

    @GetMapping(value = "shop-details")
    public List<ShopListSeller> shopList(){
        //List<ShopListSeller> shopList=sellerService.shopList();
        log.info("List completed");
        return sellerService.listShop();
    }
}
