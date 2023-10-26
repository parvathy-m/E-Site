package com.project.carro.Entity;

import com.project.carro.Dto.Form.ShopDetailsForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seller_details")
public class SellerDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long shopId;
    private  String shopName;
    @OneToOne
    private  AddressEntity address;
    private  String document;
    private  String document_2;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    private  Date createdDate;
    private  Date updatedDate;
    private  int flag;
    private  int status;
    private  String documentImageUrl;
    private  String document_2ImageUrl;
}
