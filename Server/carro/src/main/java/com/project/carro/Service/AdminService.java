package com.project.carro.Service;

import com.project.carro.Dto.View.UserInfo;
import com.project.carro.Entity.SellerDetailsEntity;
import com.project.carro.Entity.UserEntity;
import com.project.carro.Exceptions.NotFoundException;
import com.project.carro.Repository.SellerDetailsRepository;
import com.project.carro.Repository.UserRepository;
import com.project.carro.Utils.CommonConstants;
import com.project.carro.Utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerDetailsRepository sellerDetailsRepository;
    @Autowired
    private CommonUtils commonUtils;
    public Collection<UserInfo> userInfoList(int role) {

        Pageable sortedByName =
                PageRequest.of(0, 3, Sort.by("userName"));
        Page<UserEntity> list= userRepository.findByRole(role,sortedByName);
        Collection<UserInfo> userList=new ArrayList<>();
        if (list.isEmpty()){
            throw new NotFoundException("user.seller.notFound");
        }
        list.forEach(obj-> userList.add(new UserInfo(obj)));
        return userList;
    }

    public String changeStatus(Integer status,Long shopId) {
        SellerDetailsEntity details=sellerDetailsRepository.findByShopId(shopId);
        if(details.getStatus()==status){
            return "Status already updated";
        }
        switch (status){
            case 1-> details.setStatus(CommonConstants.Status.APPROVED.getValue());
            case 2->details.setStatus(CommonConstants.Status.PENDING.getValue());
            case 3->details.setStatus(CommonConstants.Status.ON_HOLD.getValue());
            case 0->details.setStatus(CommonConstants.Status.REJECTED.getValue());
            default -> {
                return "Invalid Status Value";
            }
        }

        sellerDetailsRepository.save(details);
        log.info("Seller Status Updated");
        return "Status updated to "+ commonUtils.status(status);
    }
}