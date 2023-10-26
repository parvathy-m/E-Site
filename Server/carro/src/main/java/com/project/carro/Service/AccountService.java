package com.project.carro.Service;

import com.project.carro.Repository.ProductRepository;
import com.project.carro.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerService sellerService;
    @Autowired
    CommonUtils commonUtils;

    public void getAllProductList(){

    }

}
