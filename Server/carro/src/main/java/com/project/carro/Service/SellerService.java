package com.project.carro.Service;

import com.project.carro.Dto.Form.ShopDetailsForm;
import com.project.carro.Dto.Form.UserRegisterForm;
import com.project.carro.Dto.View.AddressView;
import com.project.carro.Dto.View.ShopListSeller;
import com.project.carro.Entity.AddressEntity;
import com.project.carro.Entity.SellerDetailsEntity;
import com.project.carro.Entity.UserEntity;
import com.project.carro.Exceptions.BadRequestException;
import com.project.carro.Repository.AddressRepository;
import com.project.carro.Repository.SellerDetailsRepository;
import com.project.carro.Repository.UserRepository;
import com.project.carro.Utils.AWSUtil;
import com.project.carro.Utils.CommonConstants;
import com.project.carro.Utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class SellerService {
    @Autowired
    AWSUtil awsUtil;
    @Autowired
    CommonUtils commonUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    private SellerDetailsRepository sellerDetailsRepository;

    public void register(UserRegisterForm form) {
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        form.setPassword(encodedPassword);
        UserEntity user = userRepository.findByUserNameOrEmail(form.getUserName(), form.getEmail());
        if (user != null) {
            log.info("UserName [ {} ] or Email [ {} ] already registered", user.getUserName(), user.getEmail());
            throw new BadRequestException("invalid.user");
        }
        UserEntity newUser = new UserEntity();
        newUser.setStatus(UserEntity.Status.ACTIVE.getValue());
        newUser.setRole(UserEntity.Role.SELLER.getValue());
        newUser.setPassword(form.getPassword());
        if (form.getUserName() == null) {
            String[] name = form.getEmail().split("@", 2);
            newUser.setUserName(name[0]);
        } else newUser.setUserName(form.getUserName());
        newUser.setEmail(form.getEmail());
        userRepository.save(newUser);
        log.info("Seller saved");
    }

    @Transactional
    public List<String> registerShop(ShopDetailsForm form) throws IOException {
        log.info("seller Details registration started");
        /*String filename = StringUtils.cleanPath(Objects.requireNonNull(form.getDocument().getOriginalFilename()));
        String filename1 = StringUtils.cleanPath(Objects.requireNonNull(form.getDocument_2().getOriginalFilename()));*/

        String fileName = commonUtils.getCurrentUserId() + "__/" + System.currentTimeMillis() + "_" + form.getDocument().getOriginalFilename();
        String filename = commonUtils.getCurrentUserId() + "__/" + System.currentTimeMillis() + "_" + form.getDocument_2().getOriginalFilename();
        String doc1 = awsUtil.uploadFile(form.getDocument(), fileName);
        String doc2 = awsUtil.uploadFile(form.getDocument_2(), filename);
        AddressEntity address = addressRepository.save(AddressEntity.builder()
                .address(form.getAddress())
                .flag(CommonConstants.Flag.ACTIVE.getValue())
                .pin(form.getPin())
                .district(form.getDistrict())
                .State(form.getState())
                .building(form.getBuilding())
                .mobile(form.getMobile())
                .State(form.getState())
                .createdDate(new Date())
                .updatedDate(new Date())
                .locality(form.getLocality())
                .build());
        log.info("Address Saved");
        SellerDetailsEntity details = new SellerDetailsEntity();
        details.setAddress(address);
        details.setShopName(form.getShopName());
        details.setDocument(fileName);
        details.setDocument_2(filename);
        details.setFlag(CommonConstants.Flag.ACTIVE.getValue());
        details.setUser(userRepository.findByEmail(commonUtils.getUserName()));
        details.setCreatedDate(new Date());
        details.setUpdatedDate(new Date());
        details.setStatus(CommonConstants.Status.PENDING.getValue());
        details.setDocument_2ImageUrl(doc1);
        details.setDocumentImageUrl(doc2);
        sellerDetailsRepository.save(details);
        String uploadDir = "Seller-docs/" + details.getShopId();
        List<String> a = new ArrayList<>();
        a.add(doc2);
        a.add(doc1);
        return a;
    }

    public List<ShopListSeller> listShop() {
        System.out.println("something");
        UserEntity user = userRepository.findByEmail(commonUtils.getUserName());
        List<SellerDetailsEntity> list = sellerDetailsRepository.findByUser(user);
        if (list.size() == 0) {
            throw new BadRequestException("invalid.user");
        }

        List<ShopListSeller> shopList = new ArrayList<>();
        list.forEach(
                obj -> {
                    shopList.add(ShopListSeller.builder()
                            .shopName(obj.getShopName())
                            .address(new AddressView(obj.getAddress()))
                            .shopId(obj.getShopId())
                            .flag(commonUtils.flag(obj.getFlag()))
                            .status(commonUtils.status(obj.getStatus()))
                            .createdDate(obj.getCreatedDate())
                            .updatedDate(obj.getUpdatedDate())
                            .document(obj.getDocumentImageUrl())
                            .document_2(obj.getDocument_2ImageUrl())
                            .build());
                }
        );

        return shopList;
    }
}
