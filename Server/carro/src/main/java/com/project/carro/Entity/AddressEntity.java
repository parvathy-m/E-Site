package com.project.carro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String address;
    private  String building;
    private  String locality;
    private  String district;
    private  String State;
    private  String pin;
    private  String mobile;
    private  Date createdDate;
    private  Date updatedDate;
    private  int flag;
    @OneToOne(mappedBy = "address")
    private SellerDetailsEntity sellerDetails;
}
