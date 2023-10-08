package com.project.carro.Dto.View;

import com.project.carro.Entity.AddressEntity;
import com.project.carro.Entity.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ShopListSeller {
    private  Long shopId;
    private  String shopName;
    private Date createdDate;
    private  Date updatedDate;
    private  String flag;
    private  String status;
    private AddressView address;
    private  String document;
    private  String document_2;
}
