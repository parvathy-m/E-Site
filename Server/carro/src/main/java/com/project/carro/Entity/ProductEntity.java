package com.project.carro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<CategoryEntity> category;
    @OneToOne
    private SellerDetailsEntity shop;
    @OneToOne
    private UserEntity seller;
    private Long price;
    private Long quantity;
    private Long discount;
    private Integer status;
    private Date created_date;
    private Date updated_date;
    private String images;
    private String description;

    public enum Status{
        IN_STOCK(1,"IN_STOCK"),
        OUT_OF_STOCK(0,"OUT_OF_STOCK"),
        NOT_AVAILABLE(2,"NOT_AVAILABLE");
        private final Integer value;
        private final String type;

        Status(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }
        public String getType() {
            return type;
        }
    }
}
