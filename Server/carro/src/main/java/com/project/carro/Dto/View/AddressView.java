package com.project.carro.Dto.View;

import com.project.carro.Entity.AddressEntity;
import com.project.carro.Entity.SellerDetailsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class AddressView {
    private  String address;
    private  String building;
    private  String locality;
    private  String district;
    private  String State;
    private  String pin;
    private  String mobile;

    public AddressView(AddressEntity address){
        this.address = address.getAddress();
        this.building = address.getBuilding();
        this.locality = address.getLocality();
        this.district = address.getDistrict();
        this.State = address.getState();
        this.pin = address.getPin();
        this.mobile = address.getMobile();
    }
}
