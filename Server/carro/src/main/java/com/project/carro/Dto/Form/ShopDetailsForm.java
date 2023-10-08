package com.project.carro.Dto.Form;

import com.project.carro.Entity.AddressEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ShopDetailsForm {
    private final String shopName;
    private final String address;
    private final String building;
    private final String locality;
    private final String district;
    private final String State;
    private final String pin;
    private final String mobile;
    private final MultipartFile document;
    private final MultipartFile document_2;
}
