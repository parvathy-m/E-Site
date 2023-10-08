package com.project.carro.Service;

import com.project.carro.Dto.Form.ProductForm;
import com.project.carro.Dto.View.ProductView;
import com.project.carro.Entity.CategoryEntity;
import com.project.carro.Entity.ProductEntity;
import com.project.carro.Entity.UserEntity;
import com.project.carro.Exceptions.NotFoundException;
import com.project.carro.Repository.CategoryRepository;
import com.project.carro.Repository.ProductRepository;
import com.project.carro.Repository.SellerDetailsRepository;
import com.project.carro.Repository.UserRepository;
import com.project.carro.Utils.AWSUtil;
import com.project.carro.Utils.CommonConstants;
import com.project.carro.Utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerDetailsRepository sellerDetailsRepository;
    @Autowired
    private AWSUtil awsUtil;
    @Autowired
    private CommonUtils commonUtils;
    @Autowired
    private CategoryRepository categoryRepository;

    public void saveProduct(ProductForm form,Long shopId) {

        ProductEntity product = new ProductEntity();
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setCreated_date(new Date());
        product.setStatus(ProductEntity.Status.IN_STOCK.getValue());
        product.setDiscount(form.getDiscount());
        product.setQuantity(form.getQuantity());
        product.setUpdated_date(new Date());
        product.setSeller(userRepository.findByUserId(commonUtils.getCurrentUserId()));
        product.setShop(sellerDetailsRepository.findByShopId(shopId));
        product.setDescription(form.getDescription());
        product.setCategory(new ArrayList<CategoryEntity>());
        ProductEntity data1 = productRepository.save(product);
        String fileName = "product/"+data1.getProductId()+ System.currentTimeMillis() + "_" + form.getImages().getOriginalFilename();
        data1.setImages(awsUtil.uploadFile(form.getImages(), fileName));
        ProductEntity data = productRepository.save(data1);
        form.getCategory().forEach(obj -> {
            addCategory(obj, data.getProductId());
        });
        log.info("Product save completed");
    }

    public List<ProductView> getAllProduct(Integer page,Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> product = productRepository.findAll(pageable);
        log.info("Product get completed");
        return product.stream().map(ProductView::new).collect(Collectors.toList());
    }

    public void addCategory(String category, Long productId) {
        ProductEntity product = productRepository.findByProductId(productId);
        CategoryEntity code = categoryRepository.findByCode(category);
        if (product != null && code != null) {
            try {
                Collection<CategoryEntity> p=product.getCategory();
                p.add(code);
                log.info("category {} saved to {}", code, productId);
                productRepository.save(product);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (product == null) {
            throw new NotFoundException(HttpStatus.INTERNAL_SERVER_ERROR, "user.notFound");
        } else {
            throw new NotFoundException(HttpStatus.INTERNAL_SERVER_ERROR, "user.notFound");
        }
    }
}
