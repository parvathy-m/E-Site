package com.project.carro.Dto.View;

import com.project.carro.Entity.CategoryEntity;
import com.project.carro.Entity.ProductEntity;
import com.project.carro.Utils.CommonConstants;
import com.project.carro.Utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    private Long productId;
    private String name;
    private Long price;
    private Long quantity;
    private Long discount;
    private String status;
    private Date created_date;
    private Date updated_date;
    private List<String> category;
    private String shop;
    private String seller;
    private  String description;
    private String images;

    public ProductView(ProductEntity p) {
        this.name=p.getName();
        this.category=new ArrayList<>();
        p.getCategory().forEach(obj->{
            this.category.add(obj.getCategory());
        });
        this.created_date=p.getCreated_date();
        this.images=p.getImages();
        this.productId=p.getProductId();
        this.discount=p.getDiscount();
        this.price=p.getPrice();
        this.quantity=p.getQuantity();
        this.updated_date=p.getUpdated_date();
        this.status= switch (p.getStatus()) {
            case 0 -> ProductEntity.Status.OUT_OF_STOCK.getType();
            case 1 -> ProductEntity.Status.IN_STOCK.getType();
            case 2 -> ProductEntity.Status.NOT_AVAILABLE.getType();
            default -> "no possible value";
        };
        this.seller=p.getSeller().getUserName();
        this.shop=p.getShop().getShopName();
        this.description=p.getDescription();
    }
}
