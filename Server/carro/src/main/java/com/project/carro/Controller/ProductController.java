package com.project.carro.Controller;

import com.project.carro.Dto.Form.ProductForm;
import com.project.carro.Dto.View.ProductView;
import com.project.carro.Dto.View.ShopListSeller;
import com.project.carro.Service.ProductService;
import com.project.carro.Utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/product/")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CommonUtils commonUtils;

    //SELLER API'S

    @PostMapping(value="seller/products",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,String> addProducts(ProductForm form,@RequestParam Long shopId){
        log.info("product add request received");
        productService.saveProduct(form,shopId);
        Map<String,String> map=new HashMap<>();
        map.put("Response","Success");
        return map;
    }

    //ADMIN API'S

    @GetMapping(value="admin/products")
    public List<ProductView> productList(@RequestParam(required = false) Integer page,@RequestParam(required = false) Integer size){
        log.info("product list request from admin ");
        if(page==null){
            page=0;
        }
        if(size==null){
            size=10;
        }
        return productService.getAllProduct(page, size);
    }

    //USER API'S
}
